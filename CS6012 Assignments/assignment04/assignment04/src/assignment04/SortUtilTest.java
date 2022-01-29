package assignment04;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SortUtilTest {
	ArrayList<Integer> testArrayOne = new ArrayList<Integer>();
	ArrayList<Integer> testArrayTwo = new ArrayList<Integer>();
	ArrayList<Double> testArrayThree = new ArrayList<Double>();
	ArrayList<String> testArrayFour = new ArrayList<String>();

	Comparator<Integer> intComparator = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			if (o1 > o2) return 1;
			if (o2 > o1) return -1;
			return 0;
		}
  	 };
  	 
  	Comparator<Double> doubleComparator = new Comparator<Double>() {
		public int compare(Double o1, Double o2) {
			if (o1 > o2) return 1;
			if (o2 > o1) return -1;
			return 0;
		}
  	 };
  	 
  	Comparator<String> stringComparator = new Comparator<String>() {
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
  	 };

	@BeforeEach
	void setUp() throws Exception {

		testArrayOne.add(10);
		testArrayOne.add(7);
		testArrayOne.add(8);
		testArrayOne.add(9);
		testArrayOne.add(1);
		testArrayOne.add(5);

		testArrayTwo.add(110);
		testArrayTwo.add(70);
		testArrayTwo.add(28);
		testArrayTwo.add(191);
		testArrayTwo.add(121);
		testArrayTwo.add(50);

		testArrayThree.add(10.0);
		testArrayThree.add(7.1);
		testArrayThree.add(8.6);
		testArrayThree.add(9.7);
		testArrayThree.add(1.21);
		testArrayThree.add(5.7);

		testArrayFour.add("10.0");
		testArrayFour.add("7");
		testArrayFour.add("St Patrick's");
		testArrayFour.add("Catherine the Great");
		testArrayFour.add("Savannah");
		testArrayFour.add("Georgia and Scarlett O'Hara");
	}

	@AfterEach
	void tearDown() throws Exception {
		testArrayOne = new ArrayList<Integer>();
		testArrayTwo = new ArrayList<Integer>();
		testArrayThree = new ArrayList<Double>();
		testArrayFour = new ArrayList<String>();
	}

	@Test
	void testQuicksort() {
		// Call quick sort
		SortUtil.quicksort(testArrayOne, intComparator); 
		SortUtil.quicksort(testArrayTwo, intComparator); 
		SortUtil.quicksort(testArrayThree, doubleComparator); 
		SortUtil.quicksort(testArrayFour, stringComparator); 

		// Check each element
		assertEquals(testArrayOne.get(0), 1);
		assertEquals(testArrayOne.get(1), 5);
		assertEquals(testArrayOne.get(2), 7);
		assertEquals(testArrayOne.get(3), 8);
		assertEquals(testArrayOne.get(4), 9);
		assertEquals(testArrayOne.get(5), 10);

		assertEquals(testArrayTwo.get(0), 28);
		assertEquals(testArrayTwo.get(1), 50);
		assertEquals(testArrayTwo.get(2), 70);
		assertEquals(testArrayTwo.get(3), 110);
		assertEquals(testArrayTwo.get(4), 121);
		assertEquals(testArrayTwo.get(5), 191);

		assertEquals(testArrayThree.get(0), 1.21);
		assertEquals(testArrayThree.get(1), 5.7);
		assertEquals(testArrayThree.get(2), 7.1);
		assertEquals(testArrayThree.get(3), 8.6);
		assertEquals(testArrayThree.get(4), 9.7);
		assertEquals(testArrayThree.get(5), 10.0);

		assertEquals(testArrayFour.get(0), "10.0");
		assertEquals(testArrayFour.get(1), "7");
		assertEquals(testArrayFour.get(2), "Catherine the Great");
		assertEquals(testArrayFour.get(3), "Georgia and Scarlett O'Hara");
		assertEquals(testArrayFour.get(4), "Savannah");
		assertEquals(testArrayFour.get(5), "St Patrick's");
	}
	
	
	@Test
	public void runTimeChecker() {
		System.out.println("quicksort");
		System.out.println("best\taverage\tworst");
		
		for (int i = 0; i < 18; i++) {

			double size = Math.pow(2, i); 
			
			ArrayList<Integer> caseBest = SortUtil.generateBestCase((int)size);
			ArrayList<Integer> caseAverage = SortUtil.generateAverageCase((int) size);
			ArrayList<Integer> caseWorst = SortUtil.generateWorstCase((int)size); 
			
			long total = 0;
			int trials = 100;
			for (int k = 0; k < trials; k++) {
				ArrayList<Integer> toSort = (ArrayList<Integer>) caseBest.clone();
				long start = System.nanoTime();
				SortUtil.quicksort(toSort, intComparator);
				long end = System.nanoTime();
				total += (end - start);
			}
		    
			System.out.print(total / trials);
			System.out.print("\t");
			
			total = 0;
			for (int k = 0; k < trials; k++) {
				ArrayList<Integer> toSort = (ArrayList<Integer>) caseAverage.clone();
				long start = System.nanoTime();
				SortUtil.quicksort(caseAverage, intComparator);
				long end = System.nanoTime();
				total += (end - start);
			}
			System.out.print(total / trials);
			System.out.print("\t");
			
			total = 0;
			for (int k = 0; k < trials; k++) {
				ArrayList<Integer> toSort = (ArrayList<Integer>) caseWorst.clone();
				long start = System.nanoTime();
				SortUtil.quicksort(caseWorst, intComparator);
				long end = System.nanoTime();
				total += (end - start);
			}
			
			System.out.print(total / trials);
			System.out.print("\n");
		}
	}
}
