package assignment06;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ChainingHashTableTest {

				
	ChainingHashTable testTree = new ChainingHashTable(12, new MediocreHashFunctor()); 

	
	
	/**
	 * Tests - addAll(), contains(), containsAll(), 
	 *         remove(), removeAll(), isEmpty()
	 */
	@Test
	void testSecond() {
		ArrayList<String> secondTest = new ArrayList<String>();
		secondTest.add("sergeant");
		secondTest.add("xray"); 
		secondTest.add("senior"); 
		
		testTree.addAll(secondTest); 
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
		

}
