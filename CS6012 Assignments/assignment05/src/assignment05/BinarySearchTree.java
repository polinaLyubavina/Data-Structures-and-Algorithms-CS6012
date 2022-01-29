package assignment05;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet<T> {
	
	BinaryNode<T> root;
	int size; 
	
	/**
	 * A no-parameter constructor which creates an empty BST.
	 */
	public BinarySearchTree() {
		
		this.size = 0; 
		
	}
	
	public class BinaryNode<T> {
		
		T data;
		BinaryNode<T> left;
		BinaryNode<T> right; 
		
		public BinaryNode(T given_data) {
			
			this.data = given_data; 
			this.left = null;
			this.right = null; 
		}
	}

	  /**
	   * Returns the first (i.e., smallest) item in this set.
	   * 
	   * @throws NoSuchElementException
	   *           if the set is empty
	   */
	@Override
	public T first() throws NoSuchElementException {

		BinaryNode<T> currentNode = this.root;
		
		if(currentNode.data == null) {
			throw new NoSuchElementException(); 
		}
		while(currentNode.left != null) {
			currentNode = currentNode.left; 
		}
		return currentNode.data;
	}

	  /**
	   * Returns the last (i.e., largest) item in this set.
	   * 
	   * @throws NoSuchElementException
	   *           if the set is empty
	   */
	@Override
	public T last() throws NoSuchElementException {

		BinaryNode<T> currentNode = this.root;
		
		if(currentNode.data == null) {
			throw new NoSuchElementException(); 
		}
		while(currentNode.right != null) {
			currentNode = currentNode.right; 
		}
		return currentNode.data;
		
	}

	  /**
	   * Ensures that this set contains the specified item.
	   * 
	   * @param item
	   *          - the item whose presence is ensured in this set
	   * @return true if this set changed as a result of this method call (that is, if
	   *         the input item was actually inserted); otherwise, returns false
	   * @throws NullPointerException
	   *           if the item is null
	   */
	@Override
	public boolean add(T element) throws NullPointerException {
		
	    if (element == null) {
	        throw new NullPointerException();
	    }
	    if (size == 0) {
	        BinaryNode<T> newRoot = new BinaryNode<T>(element);
	        root = newRoot;
	        size++;
	        return true;
	    } else {
	    	boolean added = Insert(root, element);
	    	if (added) {
	    		size++;
	    	}
	        return added;

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
	   * @throws NullPointerException
	   *           if any of the items is null
	   */
	@Override
	public boolean addAll(Collection<? extends T> elements) throws NullPointerException {

		boolean changed = false; 
		
	    if (elements == null) {
	        throw new NullPointerException();
	    }
	    
	    for(T element : elements) {
	    	if(this.add(element)) {
	    		changed = true; 
	    	}
	    }
	    return changed; 
	}

	  /**
	   * Removes all items from this set. The set will be empty after this method
	   * call.
	   */
	@Override
	public void clear() {
		
		this.root = null;	
	}

	  /**
	   * Determines if there is an item in this set that is equal to the specified
	   * item.
	   * 
	   * @param item
	   *          - the item sought in this set
	   * @return true if there is an item in this set that is equal to the input item;
	   *         otherwise, returns false
	   * @throws NullPointerException
	   *           if the item is null
	   */
	@Override
	public boolean contains(T item) throws NullPointerException {
		
		return this.containsHelper(item, this.root);

	}
	
	/**
	 * Helper function
	 * Checks if the tree contains the element using recursion. 
	 * @param element
	 * @param node
	 * @return true if the element is present, false if the element isn't there
	 * @throws NullPointerException if item is null
	 */
	public boolean containsHelper(T item, BinaryNode<T> node) throws NullPointerException {
		
		if(item == null) {
			throw new NullPointerException(); 
		}
		
		if(node != null) {
			if(item.compareTo(node.data) == 0) {
				return true; 
			}
			if(item.compareTo(node.data) < 0 && node.left != null) {
				return containsHelper(item, node.left);
			}
			if(item.compareTo(node.data) > 0 && node.right != null) {
				return containsHelper(item, node.right);
			}
		}
		
		
		
		return false;
		
	}

	  /**
	   * Determines if for each item in the specified collection, there is an item in
	   * this set that is equal to it.
	   * 
	   * @param items
	   *          - the collection of items sought in this set
	   * @return true if for each item in the specified collection, there is an item
	   *         in this set that is equal to it; otherwise, returns false
	   * @throws NullPointerException
	   *           if any of the items is null
	   */
	@Override
	public boolean containsAll(Collection<? extends T> elements) throws NullPointerException {
		
		boolean contains = true;

		for(T element : elements) {
			if(element == null) {
				throw new NullPointerException(); 
			}
			if (!this.contains(element)) {
				contains = false;
			}	
		}
		
		return contains; 
		

	}

	  /**
	   * Returns true if this set contains no items.
	   */
	@Override
	public boolean isEmpty() {

		if(this.root == null) {
			return true;
		} 
		else {
			return false; 
		}
		
	}

	  /**
	   * Ensures that this set does not contain the specified item.
	   * This method mainly calls deleteNode()
	   * @param item
	   *          - the item whose absence is ensured in this set
	   * @return true if this set changed as a result of this method call (that is, if
	   *         the input item was actually removed); otherwise, returns false
	   * @throws NullPointerException
	   *           if the item is null
	   */
	@Override
	public boolean remove(T item) throws NullPointerException {

		boolean changed = false;
		if(contains(item)){
			changed = true;
		}
		if (changed) {
			root = deleteNode(root, item);
			return true;
		} else {
			return false;
		}
		

	}
	
	/**
	 * Helper function.
	 * Finds and deletes a node using recursion. 
	 * @param node - node to start on
	 * @param item - element to remove
	 * @return true if removed, false if not.
	 * @throws NullPointerException if item is null
	 */
	private BinaryNode<T> deleteNode(BinaryNode<T> node, T item) throws NullPointerException {
		
		/**Base case: if the tree is empty*/
		if(node == null) {
			return node;
		}
		if(item == null) {
			throw new NullPointerException();  
		}
		
		/**else, recur down the tree*/
		if(item.compareTo(node.data) < 0) {
			node.left = deleteNode(node.left, item); 
		}
		else if (item.compareTo(node.data) > 0) {
			node.right = deleteNode(node.right, item); 
		}
		
		/**If item is similar to the root, then delete root*/
		else {
			
			/**Node with only one child or no child*/
			if(node.left == null) {
				return node.right;
			}
			else if(node.right == null) {
				return node.left;
			}
			
			/**Node with two children: get the inorder successor
			 * (smallest in the right subtree)
			 */
			node.data = minValue(node.right);
			
			/**Delete the inorder successor*/
			node.right = deleteNode(node.right, node.data); 
		}
		
		return node; 
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
	   * @throws NullPointerException
	   *           if any of the items is null
	   */
	@Override
	public boolean removeAll(Collection<? extends T> items) throws NullPointerException {

		boolean changed = false;
		
		if(this.root == null) {
			return false;
		}
		
		for(T item : items) {
			if(item == null) {
				throw new NullPointerException(); 
			}
			if(this.remove(item)) {
				changed = true;
			}
		}
		return changed;
	}

	  /**
	   * Returns the number of items in this set.
	   */
	@Override
	public int size() {

		return size; 

	}
	
	  /**
	   * Returns an ArrayList containing all of the items in this set, in sorted
	   * order.
	   */
	@Override 
	public ArrayList<T> toArrayList() {
		ArrayList<T> list = new ArrayList<T>();
		inOrder(this.root, list);
		return list;
	}
	
	/**
	 * Helper function
	 * Finds the in-order traversal of the BST
	 * 
	 * @return A list of the data set in the BST in in-order
	 */
	public void inOrder(BinaryNode<T> node, ArrayList<T> list){
		if(node != null){
			inOrder(node.left, list);
			list.add(node.data);
			inOrder(node.right, list);	
		}
	}
	
	/**
	 * @param root - current node to compare the data to.
	 * @param data - data to be compared against.
	 * @return true if a new node with a unique data element was inserted; false if the data was already contained in the list.
	 */
	private boolean Insert(BinaryNode<T> node, T data) {
	    if (data.compareTo(node.data) == 0) {
	        return false;
	    }
	    if (data.compareTo(node.data) < 0) {
	        if (node.left == null) {
	        	node.left = new BinaryNode<T>(data);
	            return true;
	        } else {
	            return Insert(node.left, data);
	        }
	    } else {
	        if (node.right == null) {
	        	node.right = new BinaryNode<T>(data);
	            return true;
	        } else {
	            return Insert(node.right, data);
	        }
	    }

	}
	
	/**
	 * Searches the BST
	 * @param node - node to start with
	 * @param item - element to search for
	 * @return true if found, false if absent. 
	 */
	private BinaryNode<T> search(BinaryNode<T> node, T item) {
		
		if(node == null) {
			return node;
		}
		if(item.equals(node.data)) {
			return node;
		}
		
		if(item.compareTo(node.data) < 0 ) {
			return search(node.left, item);
		}
		else {
			return search(node.right, item); 
		}
	}
	
	/**
	 * Returns min inorder successor from left
	 * @param root of the BST.
	 * @return min value inorder successor 
	 */
	private T minValue(BinaryNode<T> node) {
		
		T minV = node.data;
		
		while(node.left != null) {
			
			minV = node.left.data;
			node = node.left; 
		}
		
		return minV; 
		
	}

}
