package assignment05;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class BinarySearchTreeTest {
	
	BinarySearchTree<String> testTree = new BinarySearchTree<String>(); 

	
	/**
	 * Tests - add() and first()
	 */
	@Test
	void testFirst() {
		testTree.add("sergeant");
		testTree.add("micro"); 
		Assert.assertEquals(true, testTree.first().equals("micro")); 
		
	}
	
	
	/**
	 * Tests - addAll(), last(), contains(), containsAll(), 
	 *         remove(), removeAll(), isEmpty()
	 */
	@Test
	void testSecond() {
		ArrayList<String> secondTest = new ArrayList<String>();
		secondTest.add("sergeant");
		secondTest.add("xray"); 
		secondTest.add("senior"); 
		
		testTree.addAll(secondTest); 
		Assert.assertEquals(true, testTree.last().equals("xray")); 
		Assert.assertEquals(true, testTree.contains("sergeant"));
		Assert.assertEquals(true, testTree.contains("xray"));
		Assert.assertEquals(true, testTree.contains("senior"));
		Assert.assertEquals(true, testTree.containsAll(secondTest)); 
		
		secondTest.add("something");
		Assert.assertEquals(false, testTree.containsAll(secondTest)); 
		Assert.assertEquals(false, testTree.isEmpty());
		
		testTree.remove("senior");
		Assert.assertEquals(false, testTree.contains("senior"));
		
		testTree.removeAll(secondTest);
		Assert.assertEquals(false, testTree.containsAll(secondTest)); 
		
		Assert.assertEquals(true, testTree.isEmpty());
	}
	
	/**
	 * Tests toArrayList()
	 */
	@Test
	void testThird() {
		ArrayList<String> thirdTest = new ArrayList<String>();
		thirdTest.add("Tommy"); 
		thirdTest.add("genetics"); 
		thirdTest.add("microsoft");
		thirdTest.add("zelda"); 

		testTree.addAll(thirdTest);
		
		Assert.assertTrue(testTree.toArrayList().toString().compareTo(thirdTest.toString()) == 0);
	}

}
