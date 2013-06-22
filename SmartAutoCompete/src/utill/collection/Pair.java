package utill.collection;

public class Pair<T, V> {
	
	
	/* ---- Data Members ---- */

	private T firstElement;
	private V secondElement;

	
	/* ---- Constructors ---- */
	
	public Pair(T firstElement, V secondElement) {
		this.firstElement = firstElement;
		this.secondElement = secondElement;
	}
	
	/* ---- Getters and Setters --- */
	
	public void first(T firstElement) {
		this.firstElement = firstElement;
	}
	
	public void second(V secondElement) {
		this.secondElement = secondElement;
	}
	
	public T first() {
		return firstElement;
	}
	
	public V second() {
		return secondElement;
	}
	
	
	@Override
	public String toString() {
		return "(" + firstElement.toString() + " " + secondElement.toString() +")";
	}

}