package assignment04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

class SortUtil {
	
	/**
	 * This method performs a quicksort on the generic ArrayList given as input.
	 */
	public static <T> void quicksort(ArrayList<T> array, Comparator<? super T> comparator) {
		internalQuicksort(array, comparator, 0 ,array.size() - 1);
	}
	
  /**
   * This function takes last element as pivot, places the pivot element at its correct
   * position in sorted array, and places all smaller (smaller than pivot) to left of
   * pivot and all greater elements to right of pivot
   * @array the array that's being quicksorted 
   * @L the left side of the pivot
   * @R the right side of the pivot
   * @initialL keeps the initial value of L
   * @initialR keeps the initial value of R
   * @comparator comares the Objects
   */
	private static <T> void internalQuicksort(ArrayList<T> array, Comparator<? super T> comparator, int L, int R) {
		if(R - L < 1)
			return;
		
		int initialL = L;
		int initialR = R;
		
		/**
		 * Uncomment one of the lines when you want to switch to that pivot. 
		 * pivotLastElement takes the pivot all the way to the right.
		 * pivotFirstElement takes the pivot all the way from the left.
		 * pivotRandomElement takes a random pivot.
		 */
//		T pivot = pivotLastElement(array, L, R); 
//		T pivot = pivotFirstElement(array, L, R); 
		T pivot = pivotRandomElement(array, L, R); 
		R--;
		
		while(L <= R) {
			if(comparator.compare(array.get(L), pivot) <= 0) {
				L++; 
				continue;
			} 
			
			if(comparator.compare(array.get(R), pivot) >= 0) {
				R--; 
				continue;
			} // find its “swapping partner”
			
			Collections.swap(array, L, R); // partners found, swap them
			L++; 
			R--;
		}
		
		Collections.swap(array, L, initialR);
		
		internalQuicksort(array, comparator, initialL, L - 1);
		internalQuicksort(array, comparator, L + 1, initialR);
	}
	
	/**
	 * Sets the pivot to be the most-right element
	 * @array
	 * @low
	 * @high
	 */
	private static <T> T pivotLastElement(ArrayList<T> array, int low, int high) {
		return array.get(high);
	}
	
	/**
	 * Sets the pivot to be the most-left element.
	 * Sets the pivot to go all the way to the right.
	 * @array to be sorted
	 * @low value to the left of the pivot
	 * @high value to the right of the pivot
	 */
	private static <T> T pivotFirstElement(ArrayList<T> array, int low, int high) {
		Collections.swap(array, low, high);
		return array.get(high);
	}
	
	/**
	 * Sets the pivot to be a random element.
	 * Sets the random pivot to go all the way to the right.
	 * @array to be sorted
	 * @low value to the left of the pivot
	 * @high value to the right of the pivot
	 */
	private static <T> T pivotRandomElement(ArrayList<T> array, int low, int high) {
	    Random rand = new Random();
	    Collections.swap(array, low + rand.nextInt(high - low), high);
		return array.get(high);
	}
	
	/**
	 * This method generates and returns an ArrayList of integers 1 to size in descending order.
	 * @ReturnList returns the list in descending order
	 * @size is the size of the array
	 */
	public static ArrayList<Integer> generateWorstCase (int size){
	    ArrayList<Integer> ReturnList = new ArrayList<>(size);
	    for(int i = 0; i < size; i ++){
	        ReturnList.add(0, i);
	    }
	    return ReturnList;
	}
	
	/**
	 * This method generates and returns an ArrayList of integers 1 to size in ascending order.
	 * @ReturnList returns the list in ascending order
	 * @size is the size of the array
	 */
	public static ArrayList<Integer> generateBestCase(int size) {
		ArrayList<Integer> ReturnList = new ArrayList<>(size);
		for(int i = 1; i < size; i++) {
			ReturnList.add(i);
		}
		return ReturnList; 
	}
	
	/**
	 * This method generates and returns an ArrayList of integers 1 to size in permuted order 
	 * @ReturnList returns the list in permuted order
	 * @size is the size of the array
	 * @random is the new random number out of 2000
	 */
	public static ArrayList<Integer> generateAverageCase (int size){
	    ArrayList<Integer> ReturnList = new ArrayList<>(size);
	    Random random = new Random(2000);
	    while (ReturnList.size() < size) {
	        //Get Random numbers between range
	        int randomNumber = random.nextInt((size - 0) + 1) + 0;
	        //Check for duplicate values
	        if (!ReturnList.contains(randomNumber)) {
	            ReturnList.add(randomNumber);
	        }
	    }
	    return ReturnList;
	}
	
	

	public static void main(String args[])
    {
        ArrayList<Integer> array = new ArrayList<Integer>();
        array.add(10);
        array.add(7);
        array.add(8);
        array.add(9);
        array.add(1);
        array.add(5);
        
        ArrayList<Integer> arrayTwo = new ArrayList<Integer>();
        arrayTwo.add(110);
        arrayTwo.add(70);
        arrayTwo.add(28);
        arrayTwo.add(191);
        arrayTwo.add(121);
        arrayTwo.add(50);
        
        Comparator<Integer> intComparator = new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				if (o1 > o2) return 1;
				if (o2 > o1) return -1;
				return 0;
			}
      	 };
  
        SortUtil.quicksort(array, intComparator);        
        SortUtil.quicksort(arrayTwo, intComparator);
        
        System.out.println("sorted array" + array);
        System.out.println("sorted array" + arrayTwo);
    }

}
