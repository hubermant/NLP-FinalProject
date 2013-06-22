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
        
    def print_results(self):
        print 'Total number of words: %d' % self.number_of_words
        print 'Total number of completed words: %d' % self.number_of_completed_words 
        print 'Percentage of completed words: %.4f' %  (float(self.number_of_completed_words)/ self.number_of_words )
    
class SavedKeystrokes(object):
    def __init__(self):
        self.number_of_keystrokes = 0
        self.number_of_actual_keystrokes= 0
        self.number_of_saved_keystrokes = 0
    
    def update_results(self, prefix, completed):
        self.number_of_keystrokes += len(prefix) + len(completed)
        self.number_of_actual_keystrokes += len(prefix)
        self.number_of_saved_keystrokes += len(completed)
        
    def print_results(self):
        print 'Total number of keystrokes (Entire text): %d' % self.number_of_keystrokes
        print 'Actual number of keystrokes : %d' % self.number_of_actual_keystrokes 
        print 'Number of saved keystrokes : %d' % self.number_of_saved_keystrokes
        print 'Actual/Saved keystrokes ratio: %.4f' % (float(self.number_of_actual_keystrokes) / self.number_of_saved_keystrokes)
        print 'Total/Actual keystrokes ratio: %.4f' % (float(self.number_of_keystrokes) / self.number_of_actual_keystrokes)

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
        
    def print_results(self):
        print 'Average word size: %.4f' % ((float(self.sum_of_words_size)) / self.number_of_words)
        print 'Average completed letters per '\
              'word size:  %.4f' % ((float(self.sum_of_completed_letters_per_word_size)) / self.number_of_words)
              
metrics = [NumberOfCompletedWords(),
           SavedKeystrokes(),
           CompletedLettersPerWordSize()]
    
def main():
    text = open(sys.argv[1]).readlines()
    for sentence in text:
        for (prefix, completed) in update_completed_words(sentence):
            for metric in metrics:
                metric.update_results(prefix, completed)
        
    for metric in metrics:
        print  
        metric.print_results()

if __name__ == '__main__':
    main()