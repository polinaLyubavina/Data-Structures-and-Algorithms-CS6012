package assignment06;

import java.util.Collection;
import java.util.LinkedList;

public class ChainingHashTable implements Set<String> {
	
	
	private LinkedList<String>[] storage;	// an array of entries -- each entry is a linked list
	private int size; 					// number of elements contained in this hash set
	private HashFunctor hash; 				// the hash function
	private boolean countCollision;			// indicating whether counting collisions for methods
	private int collisionCounts; 			// the total number of collisions for all operations
	
	
	/**
	 * Constructs a hashing table of the given capacity 
	 */
	@SuppressWarnings("unchecked")
	public ChainingHashTable(int capacity, HashFunctor functor) {
		
		storage = (LinkedList<String>[]) new LinkedList[capacity];
		
		for(int i = 0; i < capacity; i++) {
			storage[i] = new LinkedList<String>(); 
		}
		
		size = 0;
		hash = functor;
		countCollision = false;
		collisionCounts = 0;
		
	}

	
	  /**
	   * Ensures that this set contains the specified item.
	   * 
	   * @param item
	   *          - the item whose presence is ensured in this set
	   * @return true if this set changed as a result of this method call (that is, if
	   *         the input item was actually inserted); otherwise, returns false
	   */
	@Override
	public boolean add(String item) {

		if(contains(item)) {
			return false; 
		}
		else {
			storage[hashCode(item)].addFirst(item);
			size++;
			return true; 
		}
		
	}

	
	
	  /**
	   * Ensures that this set contains all items in the specified collection.
	   * 
	   * @param items
	   *          - the collection of items whose presence is ensured in this set
	   * @return true if this set changed as a result of this method call (that is, if
	   *         any item in the input collection was actually inserted); otherwise,
	   *         returns false
	   */
	@Override
	public boolean addAll(Collection<? extends String> items) {

		boolean flag = false;			//the status indicating whether this set is altered or not
		
		for( String s : items) {
			flag = add(s) || flag;		// if it ever adds the blag gets set to true
		}
		return flag; 
		
	}

	
	
	  /**
	   * Removes all items from this set. The set will be empty after this method
	   * call.
	   */
	@Override
	public void clear() {

		int capacity = storage.length; 
		
		storage = (LinkedList<String>[]) new LinkedList[capacity];
		
		for(int i = 0; i < capacity; i++) {
			storage[i] = new LinkedList<String>(); 
		}
		
		size = 0;
		
	}

	
	
	  /**
	   * Determines if there is an item in this set that is equal to the specified
	   * item.
	   * 
	   * @param item
	   *          - the item sought in this set
	   * @return true if there is an item in this set that is equal to the input item;
	   *         otherwise, returns false
	   */
	@Override
	public boolean contains(String item) {

		// compute the hash code of the given item -- this is the position the 
		// item should be located if there is no collision
		int pos = hashCode(item);
		// number of collisions in locating the given item
		int count = storage[pos].indexOf(item); 
		
		if(count == -1) {				// the item is not in the set
			if(countCollision )	{		// we are counting collisions -- update collisionCounts
				collisionCounts = storage[pos].size(); 
			}
			return false;
		}
		else {							// the item is not in the set
			if(countCollision) {		// we are counting collisions -- update collisionCounts
				collisionCounts = count; 
			}
			return true; 
		}
		
	}

	
	
	  /**
	   * Determines if for each item in the specified collection, there is an item in
	   * this set that is equal to it.
	   * 
	   * @param items
	   *          - the collection of items sought in this set
	   * @return true if for each item in the specified collection, there is an item
	   *         in this set that is equal to it; otherwise, returns false
	   */
	@Override
	public boolean containsAll(Collection<? extends String> items) {

		// the status indicating if this set contains all items in the given collection
		boolean flag = true;
		
		for(String s : items) {
			flag = contains(s) && flag; 		// if it contains it stays true, if not flag becomes false
		}
		return flag; 
		
	}

	
	
	  /**
	   * Returns true if this set contains no items.
	   */
	@Override
	public boolean isEmpty() {
		
		return size == 0; 
		
	}

	
	
	  /**
	   * Ensures that this set does not contain the specified item.
	   * 
	   * @param item
	   *          - the item whose absence is ensured in this set
	   * @return true if this set changed as a result of this method call (that is, if
	   *         the input item was actually removed); otherwise, returns false
	   */
	@Override
	public boolean remove(String item) {

		if(storage[hashCode(item)].remove(item)) {			// the item is in the set
			size--;
			return true;
		}
		else {
			return false;
		}
		
	}

	
	
	  /**
	   * Ensures that this set does not contain any of the items in the specified
	   * collection.
	   * 
	   * @param items
	   *          - the collection of items whose absence is ensured in this set
	   * @return true if this set changed as a result of this method call (that is, if
	   *         any item in the input collection was actually removed); otherwise,
	   *         returns false
	   */
	@Override
	public boolean removeAll(Collection<? extends String> items) {

		// the status indicating whether this set is altered or not
		boolean flag = false;
		
		for(String s : items) {
			// try to remove each item in the collection
			flag = remove(s) || flag; 			// if flag true, stay true
		}
		return flag; 
		
	}

	
	
	  /**
	   * Returns the number of items in this set.
	   */
	@Override
	public int size() {

		return this.size; 
		
	}
	
	
	
	/**
	 * Helper function
	 * Computes the hash code of the given item using the hash function associated
	 * with this hash set.
	 */
	public int hashCode(String item) {
		
		int result = hash.hash(item)%storage.length;
		
		if(result >= 0) {
			return result;
		}
		// wrap around to avoid negative hash code
		else {
			return (result + storage.length); 
		}
	}


}
