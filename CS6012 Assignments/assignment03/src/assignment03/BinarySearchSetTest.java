package assignment03;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BinarySearchSetTest {

	BinarySearchSet<Integer> emptySet = new BinarySearchSet<Integer>();
	BinarySearchSet<Integer> testSet = new BinarySearchSet<Integer>();
	BinarySearchSet<Integer> testSetHoles = new BinarySearchSet<Integer>();
	ArrayList<Integer> testArrayList = new ArrayList<Integer>();
	ArrayList<Integer> unordered = new ArrayList<Integer>();
	
	@BeforeEach
	public void setUp() throws Exception {
		testSet.add(1);
		testSet.add(2);
		testSet.add(3);
		testSet.add(4);
		testSet.add(5);
		testSet.add(6);
		testSet.add(7);
		testSet.add(8);
		testSet.add(9);
		testSet.add(10);
		
		testSetHoles.add(1);
		testSetHoles.add(5);
		testSetHoles.add(9);
		
		testArrayList.add(11);
		testArrayList.add(12);
		testArrayList.add(13);
		testArrayList.add(14);
		testArrayList.add(15);
		testArrayList.add(16);
		testArrayList.add(17);
		testArrayList.add(18);
		testArrayList.add(19);
		testArrayList.add(20);
		
		unordered.add(3);
		unordered.add(1);
		unordered.add(2);
		unordered.add(5);
		unordered.add(4);
	}
	
	@Test
	void testContains() throws Exception {
		
		Assert.assertEquals(false, testSet.contains(0));
		Assert.assertEquals(true, testSet.contains(1));
		Assert.assertEquals(true, testSet.contains(2));
		Assert.assertEquals(true, testSet.contains(3));
		Assert.assertEquals(true, testSet.contains(4));
		Assert.assertEquals(true, testSet.contains(5));
		Assert.assertEquals(true, testSet.contains(6));
		Assert.assertEquals(true, testSet.contains(7));
		Assert.assertEquals(true, testSet.contains(8));
		Assert.assertEquals(true, testSet.contains(9));
		Assert.assertEquals(true, testSet.contains(10));
		Assert.assertEquals(false, testSet.contains(11));
	}
	
	@Test
	void testComparator() throws Exception {
		
		Assert.assertNotNull(testSet.comparator());
	}
	
	@Test
	void testFirst() throws Exception {
		
		Assert.assertTrue(testSet.first().equals((Integer) 1));
	}
	
	@Test
	void testLast() throws Exception {
		
		Assert.assertTrue(testSet.last().equals((Integer) 10));
	}
	
	@Test
	public void testAdd() throws Exception {
		this.testSet.add(11);
		Assert.assertEquals(11, testSet.size());
		Assert.assertTrue(testSet.last().equals((Integer) 11));
		
		for (int i = 1; i < 10; i++) {
			this.testSetHoles.add((Integer) i);
		}
		for (int i = 1; i < 10; i++) {
			Assert.assertEquals(i - 1, this.testSetHoles.binarySearch((Integer) i));
		}
		
	}
	

	@Test
	public void testAddAll() throws Exception {
		
		testSet.clear();
		testSet.addAll(testArrayList);
		Assert.assertTrue(testSet.last().equals((Integer) 20));
		
		emptySet.addAll(unordered);
		for (int i = 1; i < 6; i++) {
			Assert.assertEquals(i - 1, this.emptySet.binarySearch((Integer) i));
		}
	}
	

	@Test
	public void testClear() {
		this.testSet.clear();
		Assert.assertEquals(0, testSet.size());
	}
	
	@Test
	public void testIsEmpty() throws Exception {
		
		Assert.assertEquals(false, testSet.isEmpty());
		testSet.clear();
		Assert.assertEquals(true, testSet.isEmpty());
	}
	
	@Test
	public void testSize() {
		
		Assert.assertEquals(10, testSet.size());
		testSet.add(12);
		Assert.assertEquals(11, testSet.size());

	}
	
	@Test
	public void testRemove() {
		Assert.assertEquals(true, testSet.contains(1));
		Assert.assertEquals(true, testSet.contains(2));
		Assert.assertEquals(true, testSet.contains(3));
		Assert.assertEquals(true, testSet.contains(4));
		Assert.assertEquals(true, testSet.contains(5));
		Assert.assertEquals(true, testSet.contains(6));
		Assert.assertEquals(true, testSet.contains(7));
		Assert.assertEquals(true, testSet.contains(8));
		Assert.assertEquals(true, testSet.contains(9));
		Assert.assertEquals(true, testSet.contains(10));
		
		testSet.remove((Integer) 5);
		
		Assert.assertEquals(true, testSet.contains(1));
		Assert.assertEquals(true, testSet.contains(2));
		Assert.assertEquals(true, testSet.contains(3));
		Assert.assertEquals(true, testSet.contains(4));
		Assert.assertEquals(false, testSet.contains(5));
		Assert.assertEquals(true, testSet.contains(6));
		Assert.assertEquals(true, testSet.contains(7));
		Assert.assertEquals(true, testSet.contains(8));
		Assert.assertEquals(true, testSet.contains(9));
		Assert.assertEquals(true, testSet.contains(10));
		
		testSet.remove((Integer) 2);
		testSet.remove((Integer) 8);
		testSet.remove((Integer) 1);
		testSet.remove((Integer) 10);
		
		Assert.assertEquals(false, testSet.contains(1));
		Assert.assertEquals(false, testSet.contains(2));
		Assert.assertEquals(true, testSet.contains(3));
		Assert.assertEquals(true, testSet.contains(4));
		Assert.assertEquals(false, testSet.contains(5));
		Assert.assertEquals(true, testSet.contains(6));
		Assert.assertEquals(true, testSet.contains(7));
		Assert.assertEquals(false, testSet.contains(8));
		Assert.assertEquals(true, testSet.contains(9));
		Assert.assertEquals(false, testSet.contains(10));
	}
	
	@Test
	public void testRemoveAll() {
		Assert.assertEquals(true, testSet.contains(1));
		Assert.assertEquals(true, testSet.contains(2));
		Assert.assertEquals(true, testSet.contains(3));
		Assert.assertEquals(true, testSet.contains(4));
		Assert.assertEquals(true, testSet.contains(5));
		Assert.assertEquals(true, testSet.contains(6));
		Assert.assertEquals(true, testSet.contains(7));
		Assert.assertEquals(true, testSet.contains(8));
		Assert.assertEquals(true, testSet.contains(9));
		Assert.assertEquals(true, testSet.contains(10));
		
		testSet.removeAll(testArrayList);
		
		Assert.assertEquals(true, testSet.contains(1));
		Assert.assertEquals(true, testSet.contains(2));
		Assert.assertEquals(true, testSet.contains(3));
		Assert.assertEquals(true, testSet.contains(4));
		Assert.assertEquals(true, testSet.contains(5));
		Assert.assertEquals(true, testSet.contains(6));
		Assert.assertEquals(true, testSet.contains(7));
		Assert.assertEquals(true, testSet.contains(8));
		Assert.assertEquals(true, testSet.contains(9));
		Assert.assertEquals(true, testSet.contains(10));
		
		testSet.removeAll(unordered);
		
		Assert.assertEquals(false, testSet.contains(1));
		Assert.assertEquals(false, testSet.contains(2));
		Assert.assertEquals(false, testSet.contains(3));
		Assert.assertEquals(false, testSet.contains(4));
		Assert.assertEquals(false, testSet.contains(5));
		Assert.assertEquals(true, testSet.contains(6));
		Assert.assertEquals(true, testSet.contains(7));
		Assert.assertEquals(true, testSet.contains(8));
		Assert.assertEquals(true, testSet.contains(9));
		Assert.assertEquals(true, testSet.contains(10));
	}
	
	
	@Test
	public void runTimeChecker() {
		System.out.println("contains");
		
		for (int i = 0; i < 22; i++) {
			BinarySearchSet<Integer> timingSet = new BinarySearchSet<Integer>();

			for (int j = 0; j < Math.pow(2, i); j++) {
				timingSet.add(j);
			}
			
			long total = 0;
			int trials = 100000;
			for (int k = 0; k < trials; k++) {
				long start = System.nanoTime();
				timingSet.contains((Integer) (int) Math.pow(2, i));
				long end = System.nanoTime();
				total += (end - start);
			}
			
			System.out.println(Math.pow(2, i) + " " + total / trials);
		}
		
		System.out.println("add");
		for (int i = 0; i < 22; i++) {
			
			BinarySearchSet<Integer> timingSet = new BinarySearchSet<Integer>();

			for (int j = 0; j < Math.pow(2, i); j++) {
				timingSet.add(j);
			}
			
			long total = 0;
			int trials = 20000;
			for (int k = 0; k < trials; k++) {
				long start = System.nanoTime();
				timingSet.add((Integer) (int) Math.pow(2, i));
				long end = System.nanoTime();
				total += (end - start);
				timingSet.remove((Integer) (int) Math.pow(2, i));
				
			}
			
			System.out.println(Math.pow(2, i) + " " + total / trials);
		}
	}
	

}
