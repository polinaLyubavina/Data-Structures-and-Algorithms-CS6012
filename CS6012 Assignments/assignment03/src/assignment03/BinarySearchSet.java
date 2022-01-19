package assignment03;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchSet<E> implements SortedSet<E>, Iterable<E> {
	
	private Comparator<E> myComparator;
	private int ARRAY_SIZE = 8;
	private int filled; // number of elements in myArray
	private E[] myArray;

	/*
	 * Constructor for BinarySearchSet()
	 * This constructor assumes that the elements are ordered using their natural ordering.
	 */
	public BinarySearchSet() {
//		E test = (E) new Object();
//		if (test instanceof Comparable) {
		myComparator = new NaturalComparator();
		this.myArray = (E[]) new Object[ARRAY_SIZE];
		this.filled = 0; 
//		}
		
	}
	
	/*
	 * Constructor for BinarySearchSet(Comparator<? super E> comparator)
	 * This constructor assumes that the elements are ordered using the provided comparator.
	 */
	public BinarySearchSet(Comparator<? super E> comparator) {
		myComparator = (Comparator<E>) comparator;
		this.myArray = (E[]) new Object[ARRAY_SIZE];
		this.filled = 0; 
	}

	/*
	 * @return The comparator used to order the elements in this set, or null if
	 *         this set uses the natural ordering of its elements (i.e., uses
	 *         Comparable).
	 */
	@Override
	public Comparator<? super E> comparator() {
		if(this.myComparator != null) {
			return this.myComparator;
		}
		else {
			return null;
		}
	}

	/*
	 * @return the first (lowest, smallest) element currently in this set
	 * @throws NoSuchElementException if the set is empty
	 */
	@Override
	public E first() throws NoSuchElementException {
		if(this.filled == 0) {
			throw new NoSuchElementException();
		}
		else {
			return this.myArray[0]; 
		}
	}

	/*
	 * @return the last (highest, largest) element currently in this set
	 * @throws NoSuchElementException if the set is empty
	 */
	@Override
	public E last() throws NoSuchElementException {
		if(this.filled == 0) {
			throw new NoSuchElementException();
		}
		else {
			return this.myArray[this.filled - 1]; 
		}
	}

	/*
	 * Adds the specified element to this set if it is not already present and
	 * not set to null.
	 * 
	 * @param element to be added to this set
	 * @return true if this set did not already contain the specified element
	 */
	@Override
	public boolean add(E element) {
		if(this.contains(element)) {
			return false;
		}
		/*
		 * If the array is full, we must double the size of the array by copying all elements
		 * of this array into a temporary array @tempArray.
		 * After copying the contents of the original array into a temporary array, we then
		 * place all the contents from the temporary array back into the original array.
		 */
		if(this.myArray.length == this.filled) {
			E[] tempArray = (E[]) new Object[myArray.length * 2];  
			for(int i = 0; i < myArray.length; i++) {
				tempArray[i] = myArray[i];
			}
			this.myArray = tempArray; 
		}
		/*
		 * Now we must insert the contents from our array into the binarySearch method
		 */
		int indexToInsert = binarySearch(element);
		
		if (indexToInsert == -1) {
			indexToInsert = this.filled;
		}
		
		for(int i = this.filled; i > indexToInsert; i--) {
			this.myArray[i] = this.myArray[i - 1];
		}
		this.myArray[indexToInsert] = element;
		this.filled++;
		
		return true;
	}
	
	/*
	 * Does a binary search to return the element
	 * @param element
	 * @return
	 */
	public int binarySearch(E element) {
		return internalBinarySearch(element, 0, filled - 1);
	}
	
	private int internalBinarySearch(E element, int left, int right) {
		
		if(this.filled == 0) {
			return 0; 
		}
		
		if(left > right) {
			return left;
		}
		
		int mid = (left + right) / 2;
		/*
		 *  use the comparator to check for equality
		 *  if the comparator returns 0 then it's equal
		 */
		if(myComparator.compare(element, this.myArray[mid]) == 0) {
			return mid;
		}
		/*
		 * use the comparator to check for less than 0
		 * if less than 0 then the element is smaller than the mid element of the array
		 * check the first half
		 */
		else if (myComparator.compare(element, this.myArray[mid]) < 0) {
			return internalBinarySearch(element, left, mid - 1);
		}
		/*
		 * it's not equal and it's not less than so it's greater than
		 * check the second half
		 */
		else {
			return internalBinarySearch(element, mid + 1, right);
		}
	}

	/*
	 * Adds all of the elements in the specified collection to this set if they
	 * are not already present and not set to null.
	 * 
	 * @param c collection containing elements to be added to this set
	 * @return true if this set changed as a result of the call
	 */
	@Override
	public boolean addAll(Collection<? extends E> elements) {
		boolean changes = false;
		for(E elementInSet : elements) {
			System.out.println(elementInSet);
			if(add(elementInSet)) {
				changes = true;
			}
		}
		return changes;
	}

	/*
	 * Removes all of the elements from this set. The set will be empty after
	 * this call returns.
	 */
	@Override
	public void clear() {
		this.myArray = (E[]) new Object[ARRAY_SIZE];
		this.filled = 0; 
	}

	/*
	 * @param o element whose presence in this set is to be tested
	 * @return true if this set contains the specified element
	 */
	@Override
	public boolean contains(Object element) {
		if(this.filled == 0) {
			return false;
		}
		else {
			int locationToCheck = binarySearch((E) element);
			if (locationToCheck < this.filled && this.myArray[locationToCheck].equals((E) element)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/*
	 * @param c collection to be checked for containment in this set
	 * @return true if this set contains all of the elements of the specified
	 *         collection
	 */
	@Override
	public boolean containsAll(Collection<?> elements) {
		for(Object collection : elements) {
			if(!this.contains(elements)) {
				return false;
			}
		}
		return true; 
	}

	/*
	 * @return true if this set contains no elements
	 */
	@Override
	public boolean isEmpty() {
		if(this.filled == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/*
	 * @return an iterator over the elements in this set, where the elements are
	 *         returned in sorted (ascending) order
	 */
	@Override
	public Iterator<E> iterator() {
		Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < filled - 1;
            }

            @Override
            public E next() {
                return myArray[currentIndex++];
            }

            @Override
            public void remove() {
            	if (currentIndex > 0) {
            		BinarySearchSet.this.remove(myArray[currentIndex - 1]);
            	}
            }
        };
        return it;
	}

	/*
	 * Removes the specified element from this set if it is present.
	 * 
	 * @param o object to be removed from this set, if present
	 * @return true if this set contained the specified element
	 * @found sets to true when element is found in the Array
	 */
	@Override
	public boolean remove(Object element) {
		boolean found = false;
		if(this.contains(element)) {
			for(int i = 0; i < this.myArray.length; i++) {
				/*
				 * If @found is true, then the block executes and moves
				 * the entire array over to fill in the gap.
				 */
				if (found) {
					this.myArray[i-1] = this.myArray[i];
					this.myArray[i] = null;
				}
				/*
				 * Finds the element in the array and sets it to null. 
				 */
				if(this.myArray[i] == element) {
					found = true;
					this.myArray[i] = null; 
					this.filled--;
				}
			}
		}
		else {
			return false;
		}
		return true; 
	}

	/*
	 * Removes from this set all of its elements that are contained in the
	 * specified collection.
	 * 
	 * @param c collection containing elements to be removed from this set
	 * @return true if this set changed as a result of the call
	 * @modified is a boolean tracking whether the set has been modified. 
	 */
	@Override
	public boolean removeAll(Collection<?> elements) {
		boolean modified = false;
		for(Object element : elements) {
			if (this.remove(element)) {
				modified = true;
			}
		}
		return modified;
	}

	/*
	 * @return the number of elements in this set
	 */
	@Override
	public int size() {
		return this.filled;
	}

	/*
	 * @return an array containing all of the elements in this set, in sorted
	 *         (ascending) order.
	 */
	@Override
	public Object[] toArray() {
		return this.myArray;
	}

}
