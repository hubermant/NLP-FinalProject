import sys
import re

COMPLETION_LEFT_SEP = "{"
COMPLETION_RIGHT_SEP = "}"
COMPLETION_REGEX_STRING = "(?P<prefix>\S*)\{(?P<completed>\S*)\}"
completion_regex = re.compile(COMPLETION_REGEX_STRING)

def unsanitize(sentence):
    sentence = sentence.replace(COMPLETION_LEFT_SEP * 2, COMPLETION_LEFT_SEP)
    sentence = sentence.replace(COMPLETION_RIGHT_SEP * 2, COMPLETION_RIGHT_SEP)
    return sentence

def update_completed_words(sentence):
    sentence = unsanitize(sentence)
    for res in completion_regex.finditer(sentence):
        d = res.groupdict()
        yield d['prefix'], d['completed']

        
class NumberOfCompletedWords(object):
    def __init__(self):
        self.number_of_words = 0
        self.number_of_completed_words= 0
    
    def update_results(self, prefix, completed):
        self.number_of_words += 1     
        if completed:
            self.number_of_completed_words +=1
        
    def process_results(self):
        self.pocw = (float(self.number_of_completed_words)/ self.number_of_words)
        
    def print_results(self):
        print 'Total number of words: %d' % self.number_of_words
        print 'Total number of completed words: %d' % self.number_of_completed_words 
        print 'Percentage of completed words (POCW): %.4f' %  self.pocw
    
    # for plotting
    def get_results(self):
        return {'pocw': self.pocw}    
        
    
class SavedKeystrokes(object):
    def __init__(self):
        self.number_of_keystrokes = 0
        self.number_of_actual_keystrokes= 0
        self.number_of_saved_keystrokes = 0
    
    def update_results(self, prefix, completed):
        self.number_of_keystrokes += len(prefix) + len(completed)
        self.number_of_actual_keystrokes += len(prefix)
        self.number_of_saved_keystrokes += len(completed)
    
    def process_results(self):
        self.skr = 1 / (float(self.number_of_keystrokes) / self.number_of_actual_keystrokes)
        self.rskr =  (float(self.number_of_actual_keystrokes) / self.number_of_saved_keystrokes)
    
    def print_results(self):
        print 'Total number of keystrokes (Entire text): %d' % self.number_of_keystrokes
        print 'Actual number of keystrokes : %d' % self.number_of_actual_keystrokes 
        print 'Number of saved keystrokes : %d' % self.number_of_saved_keystrokes
        print 'Saved/Actual keystrokes ratio (RSKR): %.4f' % self.rskr
        print 'Actual/Total keystrokes ratio (SKR) : %.4f' % self.skr
        
    def get_results(self):
        return {'rskr': self.rskr, 'skr': self.skr}

        
class CompletedLettersPerWordSize(object):
    def __init__(self):
        self.number_of_words = 0
        self.sum_of_completed_letters_per_word_size = 0
        self.sum_of_words_size = 0
        
    def update_results(self, prefix, completed):
        self.number_of_words += 1
        word_size = (len(completed) + len(prefix))
        self.sum_of_completed_letters_per_word_size += float(len(completed)) / word_size
        self.sum_of_words_size += word_size
    
    def process_results(self):
        self.clpws = ((float(self.sum_of_completed_letters_per_word_size)) / self.number_of_words)
       
    def print_results(self):
        print 'Average word size: %.4f' % ((float(self.sum_of_words_size)) / self.number_of_words)
        print 'Average completed letters per word size (CLPWS):  %.4f' % self.clpws
    
    def get_results(self):
        return {'clpws': self.clpws}
        
        
metric_classes = [NumberOfCompletedWords,
                   SavedKeystrokes,
                   CompletedLettersPerWordSize]


def process_segment(sentences):
    metrics = [cls() for cls in metric_classes]
    for sentence in sentences:
        for (prefix, completed) in update_completed_words(sentence):
            for metric in metrics:			
                metric.update_results(prefix, completed)
    
    for metric in metrics:
        metric.process_results()
        
    return metrics

def split_to_segments(text):
    #return text[:len(text)/2], text[len(text)/2:]
    delta = 40
    return [text[i:i+delta] for i in  xrange(0, len(text), delta)]
  
def print_metrics_results(title, results):
    print  "=" * 50
    print  title
    print  "=" * 50
    for metric in results:
        print
        metric.print_results()
    print 

    
def save_resutls_to_excel(all_text_results, segments_results, excel_path='res.xlsx'):
    import pandas 
    from collections import defaultdict
    
    all_segment_metric_results = defaultdict(lambda: [])
    for metric_cls in metric_classes:
        metric_results_during_segments = \
            [filter(lambda metric: isinstance(metric, metric_cls), seg_result)[0].get_results() 
                for seg_result in segments_results]
                
        for result in metric_results_during_segments:
            for key, value in result.items():
                all_segment_metric_results[key].append(value)
                
    all_metric_results_series = {}
    for key, values in all_segment_metric_results.items():
        all_metric_results_series[key] = pandas.Series(values, index=range(1, len(values) + 1))
    df = pandas.DataFrame(all_metric_results_series)

    
    all_text_metric_results = defaultdict(lambda: [])
    for metric in all_text_results:
        result = metric.get_results()
        for key, value in result.items():
              all_text_metric_results[key] = [value]
    df2 = pandas.DataFrame(all_text_metric_results)
    
    from pandas.io.excel import ExcelWriter
    
    excel = ExcelWriter(excel_path)
    df.to_excel(excel, 'segments')
    df2.to_excel(excel, 'all')
    excel.save()
def main():
    text = open(sys.argv[1]).readlines()
    all_text_results = process_segment(text)
    
    print_metrics_results("All text resutls", all_text_results)
    
    segments_results = []
    for idx, segment in enumerate(split_to_segments(text)):
        segment_result = process_segment(segment)
        print_metrics_results("Segment %d resutls" % (idx + 1), segment_result)
        segments_results.append(segment_result)
        
    save_resutls_to_excel(all_text_results, segments_results)
        
if __name__ == '__main__':
    main()