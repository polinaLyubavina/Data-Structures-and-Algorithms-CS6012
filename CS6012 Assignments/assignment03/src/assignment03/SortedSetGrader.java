package assignment03;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;


public class SortedSetGrader {
	static final int WAIT_TIME = 3000; //Number of milliseconds to wait for a test to finish
	
	static final int ADD = 0, ADDALL = 1, CLEAR = 2, COMPARATOR = 3,
					 CONTAINS = 4, CONTAINSALL = 5, FIRST = 6,
					 ISEMPTY = 7, LAST = 8, REMOVE = 9, REMOVEALL = 10,
					 SIZE = 11, TOARRAY = 12, ITERATOR = 13,
					 ITER_REMOVE = 14, NUM_TESTS = 15;
	
	static int[] testsRun;
	static int[] testsPassed;
	static int[] doubledTestsRun; //used for tests worth double points
	static int[] doubledTestsPassed; //used for tests worth double points
	static int[] weight;
	
	static void init() {
		testsRun = new int[NUM_TESTS];
		testsPassed = new int[NUM_TESTS];
		for(int i=0; i<NUM_TESTS; i++) {
			testsRun[i] = 0;
			testsPassed[i] = 0;
		}
		weight = new int[NUM_TESTS];
		weight[ADD] = 8;
		weight[ADDALL] = 2;
		weight[CLEAR] = 1;
		weight[COMPARATOR] = 1;
		weight[CONTAINS] = 7;
		weight[CONTAINSALL] = 2;
		weight[FIRST] = 2;
		weight[ISEMPTY] = 1;
		weight[LAST] = 2;
		weight[REMOVE] = 5;
		weight[REMOVEALL] = 2;
		weight[SIZE] = 1;
		weight[TOARRAY] = 4;
		weight[ITERATOR] = 5;
		weight[ITER_REMOVE] = 2;
	}
	
	static String categoryName(int category) {
		switch(category) {
		case ADD:
			return "add";
		case ADDALL:
			return "addAll";
		case CLEAR:
			return "clear";
		case COMPARATOR:
			return "comparator";
		case CONTAINS:
			return "contains";
		case CONTAINSALL:
			return "containsAll";
		case FIRST:
			return "first";
		case ISEMPTY:
			return "isEmpty";
		case LAST:
			return "last";
		case REMOVE:
			return "remove";
		case REMOVEALL:
			return "removeAll";
		case SIZE:
			return "size";
		case TOARRAY:
			return "toArray";
		case ITERATOR:
			return "iterator";
		case ITER_REMOVE:
			return "iter. remove";
		default:
			return "";
		}
	}
	
	static void subtotal() {
		doubledTestsRun = new int[NUM_TESTS];
		doubledTestsPassed = new int[NUM_TESTS];
		for(int i=0; i<NUM_TESTS; i++) {
			doubledTestsRun[i] = testsRun[i];
			doubledTestsPassed[i] = testsPassed[i];
			testsRun[i] = 0;
			testsPassed[i] = 0;
		}
	}
	
	static void total() {
		DecimalFormat df = new DecimalFormat("#.##");
		double totalScore = 0;
		int totalPossible = 0, totalPassed = 0, totalRun = 0;
		System.out.println("                  SUMMARY");
		System.out.println("==============================================");
		for(int i=0; i<NUM_TESTS; i++) {
			double score = (((double)(testsPassed[i] + (doubledTestsPassed[i]*2))) /
							(testsRun[i] + (doubledTestsRun[i]*2))) * weight[i];
			totalScore += score;
			totalPossible += weight[i];
			totalPassed += (testsPassed[i] += doubledTestsPassed[i]);
			totalRun += (testsRun[i] += doubledTestsRun[i]);
			System.out.print(categoryName(i) + ":\t");
			switch(i) {
			case ADD:
			case ADDALL:
			case CLEAR:
			case FIRST:
			case LAST:
			case REMOVE:
			case SIZE:
				System.out.print("\t");
				break;
			}
			System.out.println(testsPassed[i] + "/" + testsRun[i] + " tests");
			System.out.println("\t\t" + df.format(score) + "/" + weight[i] + " points");
		}
		System.out.println("Subtotal:\t" + totalPassed + "/" + totalRun + " tests");
		System.out.println("\t\t" + df.format(totalScore) + "/" + totalPossible + " points");
		System.out.println("Binary Search:\t/10");
		System.out.println("Student Tests:\t/10");
		System.out.println("Code Style:\t/5");
		System.out.println("Final Score:\t/70");
		System.out.println();
		System.out.println("Explanation: Testing with Comparable and Comparator are each worth");
		System.out.println("half of your points for the automated section.  You receive 10 points");
		System.out.println("for using binary search in your methods and 10 points for your testing.");
		
		System.out.println();
		System.out.print("TA Comments: ");
	}
	
	static <T> BinarySearchSet<T> reset(TreeSet<T> ref, Comparator<? super T> c) {
		BinarySearchSet<T> ss = null;
		try {
			if(c == null) {
				ss = new BinarySearchSet<T>();
			} else {
				ss = new BinarySearchSet<T>(c);
			}
			for(T t : ref) {
				ss.add(t);
			}
		} catch (Exception e) {
			System.out.println("\tThrew " + e + " when resetting set");
			System.out.println("\t\tFuture results may be skewed because the set may have the wrong contents");
		}
		return ss;
	}
	
	//This array will clean any nulls out of their toArray array.
	//This is done so that a broken toArray() method doesn't cause
	//them to lose points on every other method in the grader.
	//This will not be called on their array when actually testing
	//the toArray() method, so they will lose points there if their
	//array is improperly formatted.
	public static Object[] cleanArray(Object[] arr) {
		int valid = 0;
		for(Object o : arr) {
			if(o != null) {
				valid++;
			}
		}
		if(arr.length == valid) {
			return arr;
		} else {
			return Arrays.copyOfRange(arr, 0, valid);
		}
	}
	
	public static <T> T getMid(TreeSet<T> ts) {
		int i = 0;
		for(T t : ts) {
			if(i == ts.size()/2) {
				return t;
			} else {
				i++;
			}
		}
		return null; //if we're here, something went horribly wrong
	}
	
	private static class InverseStringComparator implements Comparator<String> {
		public int compare(String lhs, String rhs) {
			return rhs.compareTo(lhs);
		}
	}
	
	public static class StringWrapper {
		public String s;
		
		public StringWrapper(String _s) {
			s = _s;
		}
		
		@Override
		public boolean equals(Object rhs) {
			if(!(rhs instanceof StringWrapper)) {
				return false;
			}
			return s.equals(((StringWrapper)rhs).s);
		}
		
		@Override
		public String toString() {
			return s;
		}
	}
	
	private static class InverseWrapperComparator implements Comparator<StringWrapper> {
		public int compare(StringWrapper lhs, StringWrapper rhs) {
			return rhs.s.compareTo(lhs.s);
		}
	}
	
	private static abstract class Tester<T> implements Runnable {
		int category;
		String description;
		
		BinarySearchSet<T> retval;
	}
	
	//iterator and iterator-remove
	private static class IteratorTester<T> extends Tester<T> {
		BinarySearchSet<T> ss;
		TreeSet<T> expected;
		TreeSet<T> remove;
		Comparator<? super T> c;
		
		public IteratorTester(int category, String description, BinarySearchSet<T> ss, TreeSet<T> expected, TreeSet<T> remove, Comparator<? super T> c) {
			this.category = category;
			this.description = description;
			this.ss = ss;
			this.expected = expected;
			this.remove = remove;
			this.c = c;
		}
		
		public void run() {
			retval = test(category, description, ss, expected, remove, c);
		}
		
		public static <T> BinarySearchSet<T> test(int category, String description, BinarySearchSet<T> ss, TreeSet<T> expected, TreeSet<T> remove, Comparator<? super T> c) {
			//one test for throwing exception on remove before next
			//one test for returning the right elements
			//one test for throwing exception on extra next at end
			testsRun[category] += 3;
			try{
				Iterator<T> i = ss.iterator();
				try {
					i.remove();
					//if we're still here, the proper exception wasn't thrown
					System.out.println("TEST FAILED: " + description);
					System.out.println("\texception not thrown on iterator.remove() before iterator.next()");
					ss = reset(expected, c);
				} catch (IllegalStateException e) {
					//This is good, this exception should be thrown
					testsPassed[category]++;
				}
				i = ss.iterator();
				for(T t : expected) {
					if(!i.hasNext()) {
						System.out.println("TEST FAILED: " + description);
						System.out.println("\thasNext() returned false early");
						return ss;
					}
					T cur = i.next();
					if(!cur.equals(t)) {
						System.out.println("TEST FAILED: " + description);
						System.out.println("\tnext() returned wrong element");
						return reset(expected, c);
					}
					if(category == ITER_REMOVE && remove.contains(cur)) {
						i.remove();
					}
				}
				testsPassed[category]++; //if we got here, the iterator output everything that should have been there
				if(i.hasNext()) {
					System.out.println("TEST FAILED: " + description);
					System.out.println("\thasNext() returns true when at end of iteration");
					return reset(expected, c);
				}
				try {
					i.next();
					//if we're still here, the proper exception wasn't thrown
					System.out.println("TEST FAILED: " + description);
					System.out.println("\texception not thrown on iterator.next() when there is no next");
					return reset(expected, c);
				} catch (NoSuchElementException e) {
					//This is good, this exception should be thrown
					testsPassed[category]++;
				}
				if(category == ITER_REMOVE) {
					expected.removeAll(remove);
					return test(ITERATOR, description, ss, expected, null, c);
				} else {
					return ss;
				}
			} catch (Exception e) {
				System.out.println("TEST FAILED: " + description);
				System.out.println("\tThrew " + e);
				return reset(expected, c);
			}
		}
	}
	

	//containsAll, removeAll, and addAll
	private static class CollectionParamTester<T> extends Tester<T> {
		BinarySearchSet<T> ss;
		boolean expected;
		Collection<?> param;
		TreeSet<T> after;
		Comparator<? super T> c;
		
		public CollectionParamTester(int category, String description, BinarySearchSet<T> ss, boolean expected, Collection<?> param, TreeSet<T> after, Comparator<? super T> c) {
			this.category = category;
			this.description = description;
			this.ss = ss;
			this.expected = expected;
			this.param = param;
			this.after = after;
			this.c = c;
		}
		
		public void run() {
			retval = _runTestCollectionParam(category, description, ss, expected, param, after, c);
		}
		
		@SuppressWarnings("unchecked")
		static <T> BinarySearchSet<T> _runTestCollectionParam(int category, String description, BinarySearchSet<T> ss, boolean expected, Collection<?> param, TreeSet<T> after, Comparator<? super T> c) {
			testsRun[category]++;
			try {
				boolean res = false;
				switch(category) {
				case CONTAINSALL:
					res = ss.containsAll(param);
					break;
				case REMOVEALL:
					res = ss.removeAll(param);
					break;
				case ADDALL:
					res = ss.addAll((Collection<? extends T>)param);
					break;
				}
				if(res == expected) {
					if(!Arrays.equals(after.toArray(), cleanArray(ss.toArray()))) {
						System.out.println("TEST FAILED: " + description);
						System.out.println("\tContents incorrect after method");
						return reset(after, c);
					}
					testsPassed[category]++;
					return ss;
				} else {
					System.out.println("TEST FAILED: " + description);
					System.out.println("\tReturned " + res);
					return reset(after, c);
				}
			} catch (Exception e) {
				System.out.println("TEST FAILED: " + description);
				System.out.println("\tThrew " + e);
				return reset(after, c);
			}
		}
	}
	
	//add, contains, remove, isEmpty, clear
	private static class BooleanReturnTester<T> extends Tester<T> {
		BinarySearchSet<T> ss;
		boolean expected;
		T param;
		TreeSet<T> after;
		Comparator<? super T> c;
		
		public BooleanReturnTester(int category, String description, BinarySearchSet<T> ss, boolean expected, T param, TreeSet<T> after, Comparator<? super T> c) {
			this.category = category;
			this.description = description;
			this.ss = ss;
			this.expected = expected;
			this.param = param;
			this.after = after;
			this.c = c;
		}
		
		public void run() {
			retval = _runTestBooleanReturn(category, description, ss, expected, param, after, c);
		}
		
		static <T> BinarySearchSet<T> _runTestBooleanReturn(int category, String description, BinarySearchSet<T> ss, boolean expected, T param, TreeSet<T> after, Comparator<? super T> c) {
			testsRun[category]++;
			try {
				boolean res = false;
				switch(category) {
				case ADD:
					res = ss.add(param);
					break;
				case CONTAINS:
					res = ss.contains(param);
					break;
				case REMOVE:
					res = ss.remove(param);
					break;
				case ISEMPTY:
					res = ss.isEmpty();
					break;
				case CLEAR:
					ss.clear();
					res = expected; //hack because there is no return val - makes the test pass
					break;
				}
				if(res == expected) {
					if(!Arrays.equals(after.toArray(), cleanArray(ss.toArray()))) {
						System.out.println("TEST FAILED: " + description);
						System.out.println("\tContents incorrect after method");
						return reset(after, c);
					}
					testsPassed[category]++;
					return ss;
				} else {
					System.out.println("TEST FAILED: " + description);
					System.out.println("\tReturned " + res);
					return reset(after, c);
				}
			} catch (Exception e) {
				System.out.println("TEST FAILED: " + description);
				System.out.println("\tThrew " + e);
				return reset(after, c);
			}
		}
	}
	
	private static class TReturnTester<T> extends Tester<T> {
		BinarySearchSet<T> ss; 
		TreeSet<T> after;
		Comparator<? super T> c;
		
		public TReturnTester(int category, String description, BinarySearchSet<T> ss, TreeSet<T> after, Comparator<? super T> c) {
			this.category = category;
			this.description = description;
			this.ss = ss;
			this.after = after;
			this.c = c;
		}
		
		public void run() {
			retval = _runTestTReturn(category, description, ss, after, c);
		}
		
		static <T> BinarySearchSet<T> _runTestTReturn(int category, String description, BinarySearchSet<T> ss, TreeSet<T> after, Comparator<? super T> c) {
			testsRun[category]++;
			try {
				T res = null;
				T expected = null;
				switch(category) {
				case FIRST:
					res = ss.first();
					expected = after.first();
					break;
				case LAST:
					res = ss.last();
					expected = after.last();
					break;
				}
				if(expected.equals(res)) {
					testsPassed[category]++;
					return ss;
				} else {
					System.out.println("TEST FAILED: " + description);
					System.out.println("\tReturned " + res + " but expected " + expected);
					return reset(after, c);
				}
			} catch (NoSuchElementException e) {
				try {
					after.first();
					//If we're still here, then they threw an exception incorrectly
					System.out.println("TEST FAILED: " + description);
					System.out.println("\tThrew " + e);
					return reset(after, c);
				} catch (NoSuchElementException e2) {
					//This is good, because it means their exception was intended
					testsPassed[category]++;
					return ss;
				}
			} catch (Exception e) {
				System.out.println("TEST FAILED: " + description);
				System.out.println("\tThrew " + e);
				return reset(after, c);
			}
		}
	}

	static <T> BinarySearchSet<T> _runTest(Tester<T> r) {
		Thread t = new Thread(r);
		t.start();
		try {
			t.join(WAIT_TIME);
			if(t.isAlive()) {
				System.out.println("TEST FAILED: " + r.description);
				System.out.println("\tRan for " + (WAIT_TIME/1000) + " seconds without finishing.");
				return r.retval;
			}
			return r.retval;
		} catch (InterruptedException e) {
			System.out.println("Soemthing went wrong with the grader, run again");
			System.exit(1);
			return null; //make java stop complaining...
		}
	}

	static <T> BinarySearchSet<T> testAdd(String description, BinarySearchSet<T> ss, TreeSet<T> ref, T param, Comparator<? super T> c) {
		return _runTest(new BooleanReturnTester<T>(ADD, description, ss, (ref.contains(param)? false : ref.add(param)), param, ref, c));
	}
	
	static <T> BinarySearchSet<T> testAddAll(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Collection<? extends T> param, Comparator<? super T> c) {
		return _runTest(new CollectionParamTester<T>(ADDALL, description, ss, ref.addAll(param), param, ref, c));
	}
	
	static <T> BinarySearchSet<T> testClear(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Comparator<? super T> c) {
		ref.clear();
		return _runTest(new BooleanReturnTester<T>(CLEAR, description, ss, true, null, ref, c));
	}
	
	static <T> BinarySearchSet<T> testComparator(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Comparator<? super T> c) {
		testsRun[COMPARATOR]++;
		try {
			Comparator<? super T> res = ss.comparator();
			if(res == c) {
				testsPassed[COMPARATOR]++;
				return ss;
			} else {
				System.out.println("TEST FAILED: " + description);
				System.out.println("\tReturns " + (res == null ? "null" : "wrong comparator"));
				return reset(ref, c);
			}
		} catch (Exception e) {
			System.out.println("TEST FAILED: " + description);
			System.out.println("\tThrew " + e);
			return reset(ref, c);
		}
	}
	
	static <T> BinarySearchSet<T> testContains(String description, BinarySearchSet<T> ss, TreeSet<T> ref, T param, Comparator<? super T> c) {
		return _runTest(new BooleanReturnTester<T>(CONTAINS, description, ss, ref.contains(param), param, ref, c));
	}
	
	static <T> BinarySearchSet<T> testContainsAll(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Collection<?> param, Comparator<? super T> c) {
		return _runTest(new CollectionParamTester<T>(CONTAINSALL, description, ss, ref.containsAll(param), param, ref, c));
	}
	
	static <T> BinarySearchSet<T> testFirst(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Comparator<? super T> c) {
		return _runTest(new TReturnTester<T>(FIRST, description, ss, ref, c));
	}
	
	static <T> BinarySearchSet<T> testIsEmpty(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Comparator<? super T> c) {
		return _runTest( new BooleanReturnTester<T>(ISEMPTY, description, ss, ref.isEmpty(), null, ref, c));
	}
	
	static <T> BinarySearchSet<T> testLast(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Comparator<? super T> c) {
		return _runTest(new TReturnTester<T>(LAST, description, ss, ref, c));
	}
	
	static <T> BinarySearchSet<T> testRemove(String description, BinarySearchSet<T> ss, TreeSet<T> ref, T param, Comparator<? super T> c) {
		return _runTest(new BooleanReturnTester<T>(REMOVE, description, ss, ref.remove(param), param, ref, c));
	}
	
	static <T> BinarySearchSet<T> testRemoveAll(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Collection<?> param, Comparator<? super T> c) {
		return _runTest(new CollectionParamTester<T>(REMOVEALL, description, ss, ref.removeAll(param), param, ref, c));
	}
	
	static <T> BinarySearchSet<T> testIterator(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Comparator<? super T> c) {
		return _runTest(new IteratorTester<T>(ITERATOR, description, ss, ref, null, c));
	}
	
	static <T> BinarySearchSet<T> testIteratorRemove(String description, BinarySearchSet<T> ss, TreeSet<T> ref, TreeSet<T> toRemove, Comparator<? super T> c) {
		return _runTest(new IteratorTester<T>(ITER_REMOVE, description, ss, ref, toRemove, c));
	}
	
	static <T> BinarySearchSet<T> testSize(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Comparator<? super T> c) {
		testsRun[SIZE]++;
		try {
			int res = ss.size();
			if(res == ref.size()) {
				testsPassed[SIZE]++;
				return ss;
			} else {
				System.out.println("TEST FAILED: " + description);
				System.out.println("\tReturns " + res + " instead of " + ref.size());
				return reset(ref, c);
			}
		} catch (Exception e) {
			System.out.println("TEST FAILED: " + description);
			System.out.println("\tThrew " + e);
			return reset(ref, c);
		}
	}
	
	@SuppressWarnings("unchecked")
	static <T> BinarySearchSet<T> testToArray(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Comparator<? super T> c) {
		testsRun[TOARRAY]++;
		T[] expected = (T[])ref.toArray();
		try {
			T[] res = (T[])ss.toArray();
			if(res == null || res.length != expected.length){
				System.out.println("TEST FAILED: " + description);
				System.out.println("\tArray is null or incorrect length");
				return reset(ref, c);
			}
			for(int i=0; i<res.length; i++) {
				if(!res[i].equals(expected[i])) {
					System.out.println("TEST FAILED: " + description);
					System.out.println("\tIncorrect element in array");
					return reset(ref, c);
				}
			}
			testsPassed[TOARRAY]++;
			return ss;
		} catch (Exception e) {
			System.out.println("TEST FAILED: " + description);
			System.out.println("\tThrew " + e);
			return reset(ref, c);
		}
	}
	
	//Overloads for no Comparator
	static <T> BinarySearchSet<T> testAdd(String description, BinarySearchSet<T> ss, TreeSet<T> ref, T param) {
		return testAdd(description, ss, ref, param, null);
	}
	
	static <T> BinarySearchSet<T> testAddAll(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Collection<? extends T> param) {
		return testAddAll(description, ss, ref, param, null);
	}
	
	static <T> BinarySearchSet<T> testClear(String description, BinarySearchSet<T> ss, TreeSet<T> ref) {
		return testClear(description, ss, ref, null);
	}
	
	static <T> BinarySearchSet<T> testComparator(String description, BinarySearchSet<T> ss, TreeSet<T> ref) {
		return testComparator(description, ss, ref, null);
	}
	
	static <T> BinarySearchSet<T> testContains(String description, BinarySearchSet<T> ss, TreeSet<T> ref, T param) {
		return testContains(description, ss, ref, param, null);
	}
	
	static <T> BinarySearchSet<T> testContainsAll(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Collection<?> param) {
		return testContainsAll(description, ss, ref, param, null);
	}
	
	static <T> BinarySearchSet<T> testFirst(String description, BinarySearchSet<T> ss, TreeSet<T> ref) {
		return testFirst(description, ss, ref, null);
	}
	
	static <T> BinarySearchSet<T> testIsEmpty(String description, BinarySearchSet<T> ss, TreeSet<T> ref) {
		return testIsEmpty(description, ss, ref, null);
	}
	
	static <T> BinarySearchSet<T> testLast(String description, BinarySearchSet<T> ss, TreeSet<T> ref) {
		return testLast(description, ss, ref, null);
	}
	
	static <T> BinarySearchSet<T> testRemove(String description, BinarySearchSet<T> ss, TreeSet<T> ref, T param) {
		return testRemove(description, ss, ref, param, null);
	}
	
	static <T> BinarySearchSet<T> testRemoveAll(String description, BinarySearchSet<T> ss, TreeSet<T> ref, Collection<?> param) {
		return testRemoveAll(description, ss, ref, param, null);
	}
	
	static <T> BinarySearchSet<T> testIterator(String description, BinarySearchSet<T> ss, TreeSet<T> ref) {
		return testIterator(description, ss, ref, null);
	}
	
	static <T> BinarySearchSet<T> testIteratorRemove(String description, BinarySearchSet<T> ss, TreeSet<T> ref, TreeSet<T> toRemove) {
		return testIteratorRemove(description, ss, ref, toRemove, null);
	}
	
	static <T> BinarySearchSet<T> testSize(String description, BinarySearchSet<T> ss, TreeSet<T> ref) {
		return testSize(description, ss, ref, null);
	}
	
	static <T> BinarySearchSet<T> testToArray(String description, BinarySearchSet<T> ss, TreeSet<T> ref) {
		return testToArray(description, ss, ref, null);
	}
	
	public static void testWithComparable() {
		System.out.println("           TESTING WITH COMPARABLE");
		System.out.println("==============================================");
		
		BinarySearchSet<String> ss1 = new BinarySearchSet<String>();
		TreeSet<String> ref1 = new TreeSet<String>();
		
		ss1 = testIsEmpty("empty isEmpty", ss1, ref1);
		ss1 = testIterator("empty iterate", ss1, ref1);
		ss1 = testToArray("empty toArray", ss1, ref1);
		ss1 = testClear("empty clear", ss1, ref1);
		ss1 = testFirst("empty first", ss1, ref1);
		ss1 = testLast("empty last", ss1, ref1);
		ss1 = testComparator("empty null comparator", ss1, ref1);

		ss1 = testSize("Size 0", ss1, ref1);
		ss1 = testAdd("Add string 1", ss1, ref1, "foo");
		ss1 = testSize("Size 1", ss1, ref1);
		ss1 = testAdd("Add string 2", ss1, ref1, "bar");
		ss1 = testSize("Size 2", ss1, ref1);
		ss1 = testAdd("Add string 3", ss1, ref1, "baz");
		ss1 = testSize("Size 3", ss1, ref1);
		ss1 = testAdd("Add string 4", ss1, ref1, "moo");
		ss1 = testSize("Size 4", ss1, ref1);
		ss1 = testAdd("Add string 5", ss1, ref1, "cow");
		ss1 = testSize("Size 5", ss1, ref1);
		ss1 = testAdd("Add string 6", ss1, ref1, "hey");
		ss1 = testSize("Size 6", ss1, ref1);
		ss1 = testAdd("Add string 7", ss1, ref1, "zap");
		ss1 = testSize("Size 7", ss1, ref1);
		ss1 = testAdd("Add string 8", ss1, ref1, "try");
		ss1 = testSize("Size 8", ss1, ref1);
		ss1 = testAdd("Add string 9", ss1, ref1, "act");
		ss1 = testSize("Size 9", ss1, ref1);
		ss1 = testAdd("Add string 10", ss1, ref1, "day");
		ss1 = testSize("Size 10", ss1, ref1);
		ss1 = testAdd("Add string 11", ss1, ref1, "rho");
		ss1 = testSize("Size 11", ss1, ref1);
		
		ss1 = testContains("Contains equivalent item 1", ss1, ref1, "foo");
		ss1 = testContains("Contains equivalent item 2", ss1, ref1, "bar");
		ss1 = testContains("Contains equivalent item 3", ss1, ref1, "rho");
		
		ss1 = testIsEmpty("non-empty isEmpty", ss1, ref1);
		ss1 = testIterator("non-empty iterate", ss1, ref1);
		ss1 = testFirst("non-empty first", ss1, ref1);
		ss1 = testLast("non-empty last", ss1, ref1);
		ss1 = testComparator("non-empty null comparator", ss1, ref1);
		
		ss1 = testAdd("Add duplicate string 0", ss1, ref1, "foo");
		ss1 = testAdd("Add duplicate string 4", ss1, ref1, "cow");
		ss1 = testAdd("Add duplicate string 10", ss1, ref1, "rho");
		
		//Contains should return true
		int i = 0;
		for(String s : ref1) {
			ss1 = testContains("Contains element " + i++, ss1, ref1, s);
		}
		
		//Contains should return false
		ss1 = testContains("Doesn't contain element 1", ss1, ref1, "oof");
		ss1 = testContains("Doesn't contain element 2", ss1, ref1, "food");
		ss1 = testContains("Doesn't contain element 3", ss1, ref1, "ham");
		
		ss1 = testContainsAll("containsAll on identical Collection", ss1, ref1, ref1);
		
		BinarySearchSet<String> ss2 = new BinarySearchSet<String>();
		TreeSet<String> ref2 = new TreeSet<String>();
		
		ss2 = testToArray("empty toArray", ss2, ref2);
		
		ss2 = testIsEmpty("empty isEmpty", ss2, ref2);
		
		ss1 = testContainsAll("containsAll of empty", ss1, ref1, ref2);
		ss2 = testContainsAll("empty containsAll of non-empty", ss2, ref2, ref1);
		
		i = 0;
		for(String s : ref1) {
			ss2 = testContains("empty contains element " + i++, ss2, ref2, s);
		}
		
		ss1 = testRemoveAll("removeAll of empty", ss1, ref1, ref2);
		ss2 = testRemoveAll("empty removeAll of non-empty", ss2, ref2, ref1);
		
		ss1 = testAddAll("addAll from empty", ss1, ref1, ref2);
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1);
		ss2 = testSize("size after addAll", ss2, ref2);
		ss2 = testIsEmpty("isEmpty after addAll", ss2, ref2);
		ss2 = testToArray("toArray after addAll", ss2, ref2);
		
		ss1 = testContainsAll("containsAll on identical Collection", ss1, ref1, ref2);
		
		//test some methods of removing everything
		ss2 = testRemoveAll("removeAll of identical collection", ss2, ref2, ref1);
		ss2 = testSize("size empty", ss2, ref2);
		ss2 = testIsEmpty("isEmpty empty", ss2, ref2);
		ss2 = testIterator("iterate after removeAll", ss2, ref2);
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1);
		ss2 = testSize("size after addAll", ss2, ref2);
		ss2 = testIsEmpty("isEmpty after addAll", ss2, ref2);
		ss2 = testClear("clear", ss2, ref2);
		ss2 = testSize("size after clear", ss2, ref2);
		ss2 = testIsEmpty("isEmpty after clear", ss2, ref2);
		ss2 = testToArray("toArray after clear", ss2, ref2);
		ss2 = testIterator("iterate after clear", ss2, ref2);
		ss2 = testFirst("first after clear", ss2, ref2);
		ss2 = testLast("last after clear", ss2, ref2);
		
		BinarySearchSet<String> ss3 = new BinarySearchSet<String>();
		TreeSet<String> ref3 = new TreeSet<String>();
		
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1);
		ss2 = testSize("size after addAll", ss2, ref2);
		ss2 = testIsEmpty("isEmpty after addAll", ss2, ref2);
		ss2 = testSize("size after retainAll of empty", ss2, ref2);
		ss2 = testIsEmpty("isEmpty after retainAll of empty", ss2, ref2);
		
		//Tests on two empty Collections
		ss2 = testAddAll("empty addAll from empty", ss2, ref2, ref3);
		ss2 = testSize("size after addAll of empty", ss2, ref2);
		ss2 = testIsEmpty("isEmpty after addAll of empty", ss2, ref2);
		
		ss2 = testContainsAll("empty containsAll of empty", ss2, ref2, ref3);
		ss2 = testRemoveAll("empty removeAll of empty", ss2, ref2, ref3);
		ss2 = testClear("clear already empty", ss2, ref2);
		
		i = 0;
		for(String s: ref1) {
			ss2 = testRemove("Empty remove element " + i++, ss2, ref2, s);
		}
		
		//add everything back, then remove some elements, not all
		//add all removed elements to ss3, not necessarily in same order
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1);
		ss3 = testAdd("add element 0", ss3, ref3, ref2.first());
		ss2 = testRemove("remove element 0", ss2, ref2, ref2.first());
		ss3 = testAdd("add new element 0", ss3, ref3, ref2.first());
		ss2 = testRemove("remove new element 0", ss2, ref2, ref2.first());
		ss3 = testAdd("add last element", ss3, ref3, ref2.last());
		ss2 = testRemove("remove last element", ss2, ref2, ref2.last());
		ss3 = testAdd("add new last element", ss3, ref3, ref2.last());
		ss2 = testRemove("remove new last element", ss2, ref2, ref2.last());
		ss3 = testAdd("add middle element", ss3, ref3, getMid(ref2));
		ss2 = testRemove("remove middle element", ss2, ref2, getMid(ref2));
		ss3 = testAdd("add new middle element", ss3, ref3, getMid(ref2));
		ss2 = testRemove("remove new middle element", ss2, ref2, getMid(ref2));
		ss2 = testIterator("iterate after remove", ss2, ref2);
		
		ss2 = testFirst("first after remove", ss2, ref2);
		ss2 = testLast("last after remove", ss2, ref2);
		
		//shouldn't change anything
		ss1 = testAddAll("addAll subset", ss1, ref1, ref2);
		ss2 = testRemoveAll("removeAll non-intersecting 1", ss2, ref2, ref3);
		ss3 = testRemoveAll("removeAll non-intersecting 2", ss3, ref3, ref2);
		
		//do some removes, then put things back
		ss1 = testRemoveAll("removeAll subset", ss1, ref1, ref2);
		ss3 = testAddAll("addAll after retainAll", ss3, ref3, ref1);
		ss1 = testAddAll("addAll after removeAll", ss1, ref1, ref2);
		
		//do the opposites of last time
		ss1 = testRemoveAll("removeAll subset", ss1, ref1, ref3);
		ss2 = testAddAll("addAll after retainAll", ss2, ref2, ref1);
		ss1 = testAddAll("addAll after removeAll", ss1, ref1, ref3);
		
		TreeSet<String> singleRemove = new TreeSet<String>();
		singleRemove.add(ref3.first());
		ss1 = testIteratorRemove("iterator remove first element", ss1, ref1, singleRemove);
		singleRemove.clear();
		singleRemove.add(ref3.last());
		ss1 = testIteratorRemove("iterator remove last element", ss1, ref1, singleRemove);
		singleRemove.clear();
		singleRemove.add(getMid(ref3));
		ss1 = testIteratorRemove("iterator remove middle element", ss1, ref1, singleRemove);
		ss1 = testIteratorRemove("iterator remove several elements", ss1, ref1, ref3);
		ss1 = testAddAll("addAll after iterator remove", ss1, ref1, ref3);
		
		TreeSet<String> addLots = new TreeSet<String>();
		for(i=0; i< 120; i++) {
			addLots.add("" + i);
		}
		
		ss1 = testAddAll("addAll large number of elements", ss1, ref1, addLots);
		
		System.out.println();
	}
	
	public static void testWithBoth() {
		System.out.println("  TESTING WITH COMPARATOR ON COMPARABLE TYPE");
		System.out.println("==============================================");
		
		InverseStringComparator c = new InverseStringComparator();
		
		BinarySearchSet<String> ss1 = new BinarySearchSet<String>(c);
		TreeSet<String> ref1 = new TreeSet<String>(c);
		
		ss1 = testIsEmpty("empty isEmpty", ss1, ref1, c);
		ss1 = testIterator("empty iterate", ss1, ref1, c);
		ss1 = testToArray("empty toArray", ss1, ref1, c);
		ss1 = testClear("empty clear", ss1, ref1, c);
		ss1 = testFirst("empty first", ss1, ref1, c);
		ss1 = testLast("empty last", ss1, ref1, c);
		ss1 = testComparator("empty null comparator", ss1, ref1, c);

		ss1 = testSize("Size 0", ss1, ref1, c);
		ss1 = testAdd("Add string 1", ss1, ref1, "foo", c);
		ss1 = testSize("Size 1", ss1, ref1, c);
		ss1 = testAdd("Add string 2", ss1, ref1, "bar", c);
		ss1 = testSize("Size 2", ss1, ref1, c);
		ss1 = testAdd("Add string 3", ss1, ref1, "baz", c);
		ss1 = testSize("Size 3", ss1, ref1, c);
		ss1 = testAdd("Add string 4", ss1, ref1, "moo", c);
		ss1 = testSize("Size 4", ss1, ref1, c);
		ss1 = testAdd("Add string 5", ss1, ref1, "cow", c);
		ss1 = testSize("Size 5", ss1, ref1, c);
		ss1 = testAdd("Add string 6", ss1, ref1, "hey", c);
		ss1 = testSize("Size 6", ss1, ref1, c);
		ss1 = testAdd("Add string 7", ss1, ref1, "zap", c);
		ss1 = testSize("Size 7", ss1, ref1, c);
		ss1 = testAdd("Add string 8", ss1, ref1, "try", c);
		ss1 = testSize("Size 8", ss1, ref1, c);
		ss1 = testAdd("Add string 9", ss1, ref1, "act", c);
		ss1 = testSize("Size 9", ss1, ref1, c);
		ss1 = testAdd("Add string 10", ss1, ref1, "day", c);
		ss1 = testSize("Size 10", ss1, ref1, c);
		ss1 = testAdd("Add string 11", ss1, ref1, "rho", c);
		ss1 = testSize("Size 11", ss1, ref1, c);
		
		ss1 = testContains("Contains equivalent item 1", ss1, ref1, "foo", c);
		ss1 = testContains("Contains equivalent item 2", ss1, ref1, "bar", c);
		ss1 = testContains("Contains equivalent item 3", ss1, ref1, "rho", c);
		
		ss1 = testIsEmpty("non-empty isEmpty", ss1, ref1, c);
		ss1 = testIterator("non-empty iterate", ss1, ref1, c);
		ss1 = testFirst("non-empty first", ss1, ref1, c);
		ss1 = testLast("non-empty last", ss1, ref1, c);
		ss1 = testComparator("non-empty null comparator", ss1, ref1, c);
		
		ss1 = testAdd("Add duplicate string 0", ss1, ref1, "foo", c);
		ss1 = testAdd("Add duplicate string 4", ss1, ref1, "cow", c);
		ss1 = testAdd("Add duplicate string 10", ss1, ref1, "rho", c);
		
		//Contains should return true
		int i = 0;
		for(String s : ref1) {
			ss1 = testContains("Contains element " + i++, ss1, ref1, s, c);
		}
		
		//Contains should return false
		ss1 = testContains("Doesn't contain element 1", ss1, ref1, "oof", c);
		ss1 = testContains("Doesn't contain element 2", ss1, ref1, "food", c);
		ss1 = testContains("Doesn't contain element 3", ss1, ref1, "ham", c);
		
		ss1 = testContainsAll("containsAll on identical Collection", ss1, ref1, ref1, c);
		
		BinarySearchSet<String> ss2 = new BinarySearchSet<String>(c);
		TreeSet<String> ref2 = new TreeSet<String>(c);
		
		ss2 = testToArray("empty toArray", ss2, ref2, c);
		
		ss2 = testIsEmpty("empty isEmpty", ss2, ref2, c);
		
		ss1 = testContainsAll("containsAll of empty", ss1, ref1, ref2, c);
		ss2 = testContainsAll("empty containsAll of non-empty", ss2, ref2, ref1, c);
		
		i = 0;
		for(String s : ref1) {
			ss2 = testContains("empty contains element " + i++, ss2, ref2, s, c);
		}
		
		ss1 = testRemoveAll("removeAll of empty", ss1, ref1, ref2, c);
		ss2 = testRemoveAll("empty removeAll of non-empty", ss2, ref2, ref1, c);
		
		ss1 = testAddAll("addAll from empty", ss1, ref1, ref2, c);
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1, c);
		ss2 = testSize("size after addAll", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after addAll", ss2, ref2, c);
		ss2 = testToArray("toArray after addAll", ss2, ref2, c);
		
		ss1 = testContainsAll("containsAll on identical Collection", ss1, ref1, ref2, c);
		
		//test some methods of removing everything
		ss2 = testRemoveAll("removeAll of identical collection", ss2, ref2, ref1, c);
		ss2 = testSize("size empty", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty empty", ss2, ref2, c);
		ss2 = testIterator("iterate after removeAll", ss2, ref2, c);
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1, c);
		ss2 = testSize("size after addAll", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after addAll", ss2, ref2, c);
		ss2 = testClear("clear", ss2, ref2, c);
		ss2 = testSize("size after clear", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after clear", ss2, ref2, c);
		ss2 = testToArray("toArray after clear", ss2, ref2, c);
		ss2 = testIterator("iterate after clear", ss2, ref2, c);
		ss2 = testFirst("first after clear", ss2, ref2, c);
		ss2 = testLast("last after clear", ss2, ref2, c);
		
		BinarySearchSet<String> ss3 = new BinarySearchSet<String>(c);
		TreeSet<String> ref3 = new TreeSet<String>(c);
		
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1, c);
		ss2 = testSize("size after addAll", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after addAll", ss2, ref2, c);
		ss2 = testSize("size after retainAll of empty", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after retainAll of empty", ss2, ref2, c);
		
		//Tests on two empty Collections
		ss2 = testAddAll("empty addAll from empty", ss2, ref2, ref3, c);
		ss2 = testSize("size after addAll of empty", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after addAll of empty", ss2, ref2, c);
		
		ss2 = testContainsAll("empty containsAll of empty", ss2, ref2, ref3, c);
		ss2 = testRemoveAll("empty removeAll of empty", ss2, ref2, ref3, c);
		ss2 = testClear("clear already empty", ss2, ref2, c);
		
		i = 0;
		for(String s: ref1) {
			ss2 = testRemove("Empty remove element " + i++, ss2, ref2, s, c);
		}
		
		//add everything back, then remove some elements, not all
		//add all removed elements to ss3, not necessarily in same order
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1, c);
		ss3 = testAdd("add element 0", ss3, ref3, ref2.first(), c);
		ss2 = testRemove("remove element 0", ss2, ref2, ref2.first(), c);
		ss3 = testAdd("add new element 0", ss3, ref3, ref2.first(), c);
		ss2 = testRemove("remove new element 0", ss2, ref2, ref2.first(), c);
		ss3 = testAdd("add last element", ss3, ref3, ref2.last(), c);
		ss2 = testRemove("remove last element", ss2, ref2, ref2.last(), c);
		ss3 = testAdd("add new last element", ss3, ref3, ref2.last(), c);
		ss2 = testRemove("remove new last element", ss2, ref2, ref2.last(), c);
		ss3 = testAdd("add middle element", ss3, ref3, getMid(ref2), c);
		ss2 = testRemove("remove middle element", ss2, ref2, getMid(ref2), c);
		ss3 = testAdd("add new middle element", ss3, ref3, getMid(ref2), c);
		ss2 = testRemove("remove new middle element", ss2, ref2, getMid(ref2), c);
		ss2 = testIterator("iterate after remove", ss2, ref2, c);
		
		ss2 = testFirst("first after remove", ss2, ref2, c);
		ss2 = testLast("last after remove", ss2, ref2, c);
		
		//shouldn't change anything
		ss1 = testAddAll("addAll subset", ss1, ref1, ref2, c);
		ss2 = testRemoveAll("removeAll non-intersecting 1", ss2, ref2, ref3, c);
		ss3 = testRemoveAll("removeAll non-intersecting 2", ss3, ref3, ref2, c);
		
		//do some removes, then put things back
		ss1 = testRemoveAll("removeAll subset", ss1, ref1, ref2, c);
		ss3 = testAddAll("addAll after retainAll", ss3, ref3, ref1, c);
		ss1 = testAddAll("addAll after removeAll", ss1, ref1, ref2, c);
		
		//do the opposites of last time
		ss1 = testRemoveAll("removeAll subset", ss1, ref1, ref3, c);
		ss2 = testAddAll("addAll after retainAll", ss2, ref2, ref1, c);
		ss1 = testAddAll("addAll after removeAll", ss1, ref1, ref3, c);
		
		TreeSet<String> singleRemove = new TreeSet<String>(c);
		singleRemove.add(ref3.first());
		ss1 = testIteratorRemove("iterator remove first element", ss1, ref1, singleRemove, c);
		singleRemove.clear();
		singleRemove.add(ref3.last());
		ss1 = testIteratorRemove("iterator remove last element", ss1, ref1, singleRemove, c);
		singleRemove.clear();
		singleRemove.add(getMid(ref3));
		ss1 = testIteratorRemove("iterator remove middle element", ss1, ref1, singleRemove, c);
		ss1 = testIteratorRemove("iterator remove several elements", ss1, ref1, ref3, c);
		ss1 = testAddAll("addAll after iterator remove", ss1, ref1, ref3, c);
		
		TreeSet<String> addLots = new TreeSet<String>(c);
		for(i=0; i< 120; i++) {
			addLots.add("" + i);
		}
		
		ss1 = testAddAll("addAll large number of elements", ss1, ref1, addLots, c);
		
		System.out.println();
	}
	
	public static void testWithComparator() {
		System.out.println("TESTING WITH COMPARATOR ON NON-COMPARABLE TYPE");
		System.out.println("==============================================");
		
		InverseWrapperComparator c = new InverseWrapperComparator();
		
		BinarySearchSet<StringWrapper> ss1 = new BinarySearchSet<StringWrapper>(c);
		TreeSet<StringWrapper> ref1 = new TreeSet<StringWrapper>(c);
		
		ss1 = testIsEmpty("empty isEmpty", ss1, ref1, c);
		ss1 = testIterator("empty iterate", ss1, ref1, c);
		ss1 = testToArray("empty toArray", ss1, ref1, c);
		ss1 = testClear("empty clear", ss1, ref1, c);
		ss1 = testFirst("empty first", ss1, ref1, c);
		ss1 = testLast("empty last", ss1, ref1, c);
		ss1 = testComparator("empty null comparator", ss1, ref1, c);

		ss1 = testSize("Size 0", ss1, ref1, c);
		ss1 = testAdd("Add string 1", ss1, ref1, new StringWrapper("foo"), c);
		ss1 = testSize("Size 1", ss1, ref1, c);
		ss1 = testAdd("Add string 2", ss1, ref1, new StringWrapper("bar"), c);
		ss1 = testSize("Size 2", ss1, ref1, c);
		ss1 = testAdd("Add string 3", ss1, ref1, new StringWrapper("baz"), c);
		ss1 = testSize("Size 3", ss1, ref1, c);
		ss1 = testAdd("Add string 4", ss1, ref1, new StringWrapper("moo"), c);
		ss1 = testSize("Size 4", ss1, ref1, c);
		ss1 = testAdd("Add string 5", ss1, ref1, new StringWrapper("cow"), c);
		ss1 = testSize("Size 5", ss1, ref1, c);
		ss1 = testAdd("Add string 6", ss1, ref1, new StringWrapper("hey"), c);
		ss1 = testSize("Size 6", ss1, ref1, c);
		ss1 = testAdd("Add string 7", ss1, ref1, new StringWrapper("zap"), c);
		ss1 = testSize("Size 7", ss1, ref1, c);
		ss1 = testAdd("Add string 8", ss1, ref1, new StringWrapper("try"), c);
		ss1 = testSize("Size 8", ss1, ref1, c);
		ss1 = testAdd("Add string 9", ss1, ref1, new StringWrapper("act"), c);
		ss1 = testSize("Size 9", ss1, ref1, c);
		ss1 = testAdd("Add string 10", ss1, ref1, new StringWrapper("day"), c);
		ss1 = testSize("Size 10", ss1, ref1, c);
		ss1 = testAdd("Add string 11", ss1, ref1, new StringWrapper("rho"), c);
		ss1 = testSize("Size 11", ss1, ref1, c);
		
		ss1 = testContains("Contains equivalent item 1", ss1, ref1, new StringWrapper("foo"), c);
		ss1 = testContains("Contains equivalent item 2", ss1, ref1, new StringWrapper("bar"), c);
		ss1 = testContains("Contains equivalent item 3", ss1, ref1, new StringWrapper("rho"), c);
		
		ss1 = testIsEmpty("non-empty isEmpty", ss1, ref1, c);
		ss1 = testIterator("non-empty iterate", ss1, ref1, c);
		ss1 = testFirst("non-empty first", ss1, ref1, c);
		ss1 = testLast("non-empty last", ss1, ref1, c);
		ss1 = testComparator("non-empty null comparator", ss1, ref1, c);
		
		ss1 = testAdd("Add duplicate string 0", ss1, ref1, new StringWrapper("foo"), c);
		ss1 = testAdd("Add duplicate string 4", ss1, ref1, new StringWrapper("cow"), c);
		ss1 = testAdd("Add duplicate string 10", ss1, ref1, new StringWrapper("rho"), c);
		
		//Contains should return true
		int i = 0;
		for(StringWrapper s : ref1) {
			ss1 = testContains("Contains element " + i++, ss1, ref1, s, c);
		}
		
		//Contains should return false
		ss1 = testContains("Doesn't contain element 1", ss1, ref1, new StringWrapper("oof"), c);
		ss1 = testContains("Doesn't contain element 2", ss1, ref1, new StringWrapper("food"), c);
		ss1 = testContains("Doesn't contain element 3", ss1, ref1, new StringWrapper("ham"), c);
		
		ss1 = testContainsAll("containsAll on identical Collection", ss1, ref1, ref1, c);
		
		BinarySearchSet<StringWrapper> ss2 = new BinarySearchSet<StringWrapper>(c);
		TreeSet<StringWrapper> ref2 = new TreeSet<StringWrapper>(c);
		
		ss2 = testToArray("empty toArray", ss2, ref2, c);
		
		ss2 = testIsEmpty("empty isEmpty", ss2, ref2, c);
		
		ss1 = testContainsAll("containsAll of empty", ss1, ref1, ref2, c);
		ss2 = testContainsAll("empty containsAll of non-empty", ss2, ref2, ref1, c);
		
		i = 0;
		for(StringWrapper s : ref1) {
			ss2 = testContains("empty contains element " + i++, ss2, ref2, s, c);
		}
		
		ss1 = testRemoveAll("removeAll of empty", ss1, ref1, ref2, c);
		ss2 = testRemoveAll("empty removeAll of non-empty", ss2, ref2, ref1, c);
		
		ss1 = testAddAll("addAll from empty", ss1, ref1, ref2, c);
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1, c);
		ss2 = testSize("size after addAll", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after addAll", ss2, ref2, c);
		ss2 = testToArray("toArray after addAll", ss2, ref2, c);
		
		ss1 = testContainsAll("containsAll on identical Collection", ss1, ref1, ref2, c);
		
		//test some methods of removing everything
		ss2 = testRemoveAll("removeAll of identical collection", ss2, ref2, ref1, c);
		ss2 = testSize("size empty", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty empty", ss2, ref2, c);
		ss2 = testIterator("iterate after removeAll", ss2, ref2, c);
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1, c);
		ss2 = testSize("size after addAll", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after addAll", ss2, ref2, c);
		ss2 = testClear("clear", ss2, ref2, c);
		ss2 = testSize("size after clear", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after clear", ss2, ref2, c);
		ss2 = testToArray("toArray after clear", ss2, ref2, c);
		ss2 = testIterator("iterate after clear", ss2, ref2, c);
		ss2 = testFirst("first after clear", ss2, ref2, c);
		ss2 = testLast("last after clear", ss2, ref2, c);
		
		BinarySearchSet<StringWrapper> ss3 = new BinarySearchSet<StringWrapper>(c);
		TreeSet<StringWrapper> ref3 = new TreeSet<StringWrapper>(c);
		
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1, c);
		ss2 = testSize("size after addAll", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after addAll", ss2, ref2, c);
		ss2 = testSize("size after retainAll of empty", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after retainAll of empty", ss2, ref2, c);
		
		//Tests on two empty Collections
		ss2 = testAddAll("empty addAll from empty", ss2, ref2, ref3, c);
		ss2 = testSize("size after addAll of empty", ss2, ref2, c);
		ss2 = testIsEmpty("isEmpty after addAll of empty", ss2, ref2, c);
		
		ss2 = testContainsAll("empty containsAll of empty", ss2, ref2, ref3, c);
		ss2 = testRemoveAll("empty removeAll of empty", ss2, ref2, ref3, c);
		ss2 = testClear("clear already empty", ss2, ref2, c);
		
		i = 0;
		for(StringWrapper s: ref1) {
			ss2 = testRemove("Empty remove element " + i++, ss2, ref2, s, c);
		}
		
		//add everything back, then remove some elements, not all
		//add all removed elements to ss3, not necessarily in same order
		ss2 = testAddAll("addAll to empty", ss2, ref2, ref1, c);
		ss3 = testAdd("add element 0", ss3, ref3, ref2.first(), c);
		ss2 = testRemove("remove element 0", ss2, ref2, ref2.first(), c);
		ss3 = testAdd("add new element 0", ss3, ref3, ref2.first(), c);
		ss2 = testRemove("remove new element 0", ss2, ref2, ref2.first(), c);
		ss3 = testAdd("add last element", ss3, ref3, ref2.last(), c);
		ss2 = testRemove("remove last element", ss2, ref2, ref2.last(), c);
		ss3 = testAdd("add new last element", ss3, ref3, ref2.last(), c);
		ss2 = testRemove("remove new last element", ss2, ref2, ref2.last(), c);
		ss3 = testAdd("add middle element", ss3, ref3, getMid(ref2), c);
		ss2 = testRemove("remove middle element", ss2, ref2, getMid(ref2), c);
		ss3 = testAdd("add new middle element", ss3, ref3, getMid(ref2), c);
		ss2 = testRemove("remove new middle element", ss2, ref2, getMid(ref2), c);
		ss2 = testIterator("iterate after remove", ss2, ref2, c);
		
		ss2 = testFirst("first after remove", ss2, ref2, c);
		ss2 = testLast("last after remove", ss2, ref2, c);
		
		//shouldn't change anything
		ss1 = testAddAll("addAll subset", ss1, ref1, ref2, c);
		ss2 = testRemoveAll("removeAll non-intersecting 1", ss2, ref2, ref3, c);
		ss3 = testRemoveAll("removeAll non-intersecting 2", ss3, ref3, ref2, c);
		
		//do some removes, then put things back
		ss1 = testRemoveAll("removeAll subset", ss1, ref1, ref2, c);
		ss3 = testAddAll("addAll after retainAll", ss3, ref3, ref1, c);
		ss1 = testAddAll("addAll after removeAll", ss1, ref1, ref2, c);
		
		//do the opposites of last time
		ss1 = testRemoveAll("removeAll subset", ss1, ref1, ref3, c);
		ss2 = testAddAll("addAll after retainAll", ss2, ref2, ref1, c);
		ss1 = testAddAll("addAll after removeAll", ss1, ref1, ref3, c);
		
		TreeSet<StringWrapper> singleRemove = new TreeSet<StringWrapper>(c);
		singleRemove.add(ref3.first());
		ss1 = testIteratorRemove("iterator remove first element", ss1, ref1, singleRemove, c);
		singleRemove.clear();
		singleRemove.add(ref3.last());
		ss1 = testIteratorRemove("iterator remove last element", ss1, ref1, singleRemove, c);
		singleRemove.clear();
		singleRemove.add(getMid(ref3));
		ss1 = testIteratorRemove("iterator remove middle element", ss1, ref1, singleRemove, c);
		ss1 = testIteratorRemove("iterator remove several elements", ss1, ref1, ref3, c);
		ss1 = testAddAll("addAll after iterator remove", ss1, ref1, ref3, c);
		
		TreeSet<StringWrapper> addLots = new TreeSet<StringWrapper>(c);
		for(i=0; i< 120; i++) {
			addLots.add(new StringWrapper("" + i));
		}
		
		ss1 = testAddAll("addAll large number of elements", ss1, ref1, addLots, c);
		
		System.out.println();
	}
	
	public static void main(String[] args) {
		init();
		testWithComparable();
		subtotal();
		testWithBoth();
		testWithComparator();
		total();
	}
	
}
