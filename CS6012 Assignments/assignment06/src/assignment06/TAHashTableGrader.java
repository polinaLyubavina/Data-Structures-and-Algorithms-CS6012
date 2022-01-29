package assignment06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TAHashTableGrader {
  static final int BAD_HASH = 0, MED_HASH = 1, GOOD_HASH = 2;
  static final int QUAD_PROBE = 0, SEP_CHAIN = 1;
  static final int TABLE_TEST_COUNT = 26;
  static double[] functorPoints;

  public static class HashFunctorTester implements Runnable {
    int testsRun, testsPassed;
    int functorType;
    String hfName, input;

    public HashFunctorTester(int functorType) {
      this.functorType = functorType;

      switch (functorType) {
      case BAD_HASH:
        hfName = "BadHashFunctor";
        break;
      case MED_HASH:
        hfName = "MediocreHashFunctor";
        break;
      case GOOD_HASH:
        hfName = "GoodHashFunctor";
        break;
      default:
        throw new UnsupportedOperationException("Hash functor type not set");
      }
    }

    public void testHash(String input) {
      this.input = input;
      runTest(this);
    }

    public void run() {
      HashFunctor hf;
      try {
        // NOTE: The assignment did not specify the constructor signature for these, so
        // if
        // a student submits a hash functor which needs parameters in the constructor,
        // fix it below without penalty
        switch (functorType) {
        case BAD_HASH:
          hf = new BadHashFunctor();
          break;
        case MED_HASH:
          hf = new MediocreHashFunctor();
          break;
        case GOOD_HASH:
          hf = new GoodHashFunctor();
          break;
        default:
          throw new UnsupportedOperationException("Hash functor type not set");
        }

        testsRun++;
        int hash = hf.hash(input);
        for (int i = 0; i < 10; i++) {
          if (hf.hash(input) != hash) {
            System.out.println(hfName + " returned an inconsistent value for \"" + input + "\"");
            return;
          }
        }
        testsPassed++; // if we made it here without error
      } catch (Throwable t) {
        System.out.println(hfName + " threw " + t + " for input \"" + input + "\"");
      }
    }
  }

  public static void testHashFunctors() {
    functorPoints = new double[3];
    for (int i = 0; i < 3; i++) {
      HashFunctorTester t = new HashFunctorTester(i);
      System.out.println("---Testing " + t.hfName + "---");

      t.testHash("simple");
      t.testHash("");
      t.testHash(" starts with space");
      t.testHash("This is a really long sentence which is designed to make sure that the "
          + "student's hash function can work on really long strings like this runon "
          + "sentence, which keeps going for a long time without stopping.");
      t.testHash("';!#$*(%#)( ,@.\t>?<\n?/_+\\#! *%$#");
      functorPoints[i] = t.testsPassed / (double) t.testsRun;
      System.out.println();
    }
  }

  public static class TAHashFunctor implements HashFunctor {
    public int hash(String item) {
      if (item.length() == 0) {
        return 0;
      }
      return (int) (item.charAt(item.length() - 1));
    }
  }

  public static class JavaHashFunctor implements HashFunctor {
    public int hash(String item) {
      return Math.abs(item.hashCode());
    }
  }

  public static class HashTableTester implements Runnable {
    double testsRun, testsPassed;
    int tableType, testNum;
    String tableName, resMsg;

    /***
     * Sets test number and starts test in new thread
     *
     * @param testNum
     */
    public void startTest(int tableType, int testNum) {
      this.tableType = tableType;
      this.testNum = testNum;
      if (tableType == QUAD_PROBE) {
        tableName = "QuadProbeHashTable";
      } else { // SEP_CHAIN
        tableName = "ChainingHashTable";
      }
      runTest(this);
    }

    /***
     * Entry point for this test when started in a new thread
     */
    public void run() {
      try {
        resMsg = "";
        testsRun++;
        if (doTest()) {
          testsPassed++;
        } else {
          System.out.println("TEST FAILED : " + resMsg);
        }
      } catch (Throwable t) {
        System.out.println("TEST FAILED : " + resMsg + " threw " + t);
      }
    }

    /***
     * Runs a single test
     *
     * @return true if passed, false if any part failed
     */
    public boolean doTest() {
      Set<String> table;
      List<String> contents;
      //if (tableType == QUAD_PROBE) {
      //if (testNum >= 24) {
      //table = new QuadProbeHashTable(510000, new JavaHashFunctor());
      //} else {
      //  table = new QuadProbeHashTable(5, new TAHashFunctor());
      // }
      //} else { // SEP_CHAIN
      if (testNum >= 24) {
        table = new ChainingHashTable(510000, new JavaHashFunctor());
      } else {
        table = new ChainingHashTable(5, new TAHashFunctor());
      }
    //}

      switch (testNum) {
      case 0:
        resMsg = "contains on empty table";
        return !table.contains("hello");
      case 1:
        resMsg = "containsAll on empty table";
        return !table.containsAll(Arrays.asList("foo", "bar"));
      case 2:
        resMsg = "clear on empty table";
        table.clear();
        return true;
      case 3:
        resMsg = "size on empty table";
        return table.size() == 0;
      case 4:
        resMsg = "isEmpty on empty table";
        return table.isEmpty();
      case 5:
        resMsg = "add to empty table";
        table.add("hey");
        return table.size() == 1;
      case 6:
        resMsg = "addAll to empty table";
        table.addAll(Arrays.asList("hi", "hello"));
        return table.size() == 2;
      case 7:
        resMsg = "contains on singleton table (true)";
        table.add("hey");
        return table.contains("hey");
      case 8:
        resMsg = "contains on singleton table (false)";
        table.add("hey");
        return !table.contains("hi");
      case 9:
        resMsg = "contains on singleton table (same hash)";
        table.add("hey");
        return !table.contains("ahoy");
      case 10:
        resMsg = "clear on singleton table";
        table.add("hey");
        table.clear();
        return table.isEmpty() && !table.contains("hey");
      case 11:
        resMsg = "add item with same hash";
        table.add("cat");
        table.add("bot");
        return table.size() == 2;
      case 12:
        resMsg = "contains on small table (1)";
        table.add("cat");
        table.add("bot");
        table.add("foo");
        return table.contains("cat");
      case 13:
        resMsg = "contains on small table (2)";
        table.add("cat");
        table.add("bot");
        table.add("foo");
        table.remove("bot");
        return !table.contains("bot");
      case 14:
        resMsg = "contains on small table (3)";
        table.add("cat");
        table.add("bot");
        table.add("foo");
        return table.contains("foo");
      case 15:
        resMsg = "containsAll on small table (true)";
        table.add("cat");
        table.add("bot");
        table.add("foo");
        return table.containsAll(Arrays.asList("bot", "foo"));
      case 16:
        resMsg = "containsAll on small table (false)";
        table.add("cat");
        table.add("bot");
        table.add("foo");
        return !table.containsAll(Arrays.asList("bot", "foo", "cat", "hat"));
      case 17:
        resMsg = "containsAll(empty) on small table";
        table.add("cat");
        table.add("bot");
        table.add("foo");
        return table.containsAll(new ArrayList<String>());
      case 18:
        resMsg = "addAll on large table";
        contents = Arrays.asList("foo", "bar", "baz", "cat", "fez", "act", "", "a");
        table.addAll(contents);
        return table.size() == contents.size();
      case 19:
        resMsg = "contains on large table (true)";
        contents = Arrays.asList("foo", "bar", "baz", "cat", "fez", "act", "", "a");
        table.addAll(contents);
        return table.contains("foo") && table.contains("fez") && table.contains("");
      case 20:
        resMsg = "contains on large table (false)";
        contents = Arrays.asList("foo", "bar", "baz", "cat", "fez", "act", "", "a");
        table.addAll(contents);
        return !table.contains("oof") && !table.contains("biz") && !table.contains("b");
      case 21:
        resMsg = "containsAll on large table (true)";
        contents = Arrays.asList("foo", "bar", "baz", "cat", "fez", "act", "", "a");
        table.addAll(contents);
        return table.containsAll(contents);
      case 22:
        resMsg = "containsAll on large table (false)";
        contents = new ArrayList<String>(Arrays.asList("foo", "bar", "baz", "cat", "fez", "act", "", "a"));
        table.addAll(contents);
        contents.add("hey");
        return !table.containsAll(contents);
      case 23:
        resMsg = "clear on large table";
        contents = Arrays.asList("foo", "bar", "baz", "cat", "fez", "act", "", "a");
        table.addAll(contents);
        table.clear();
        for (String s : contents) {
          if (table.contains(s)) {
            return false;
          }
        }
        return table.isEmpty();
      case 24:
        resMsg = "stress test 1";
        for (int i = 0; i < 1000000; i++) {
          if (!table.add("" + i)) {
            return false;
          }
        }
        return table.size() == 1000000;
      case 25:
        resMsg = "stress test 2";
        for (int i = 0; i < 1000000; i++) {
          if (!table.add("" + i)) {
            return false;
          }
        }
        for (int i = 0; i < 1000000; i++) {
          if (!table.contains("" + i)) {
            return false;
          }
        }
        return table.size() == 1000000;
      default:
        System.out.println("Grader tried to run a test that doesn't exist!!");
        System.exit(1);
        return false; // make the compiler stop griping
      }
    }
  }

  /**
   * Used as a marker for printing a relevant error message when a student's code
   * is running infinitely.
   */
  static class RunningInfinitelyException extends Exception {
    private static final long serialVersionUID = 1L;

    public String toString() {
      return "Running Infinitely";
    }
  }

  /***
   * Actually run a test in another thread.
   */
  @SuppressWarnings("deprecation")
  private static void runTest(Runnable r) {
    Thread t = new Thread(r);
    t.start();
    try {
      t.join(10000);
      if (t.isAlive()) {
        t.stop();
        t.join(1000); // give the thread time to print its output
      }
    } catch (InterruptedException e) {
      System.out.println("Running indefinitely.");
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    testHashFunctors();
    double[] tablePoints = new double[2];

    for (int tableType = 0; tableType < 2; tableType++) {
      if (tableType == QUAD_PROBE) {
        System.out.println("---Testing QuadProbeHashTable---");
      } else { // SEP_CHAIN
        System.out.println("---Testing ChainingHashTable---");
      }

      HashTableTester t = new HashTableTester();
      for (int testNum = 0; testNum < TABLE_TEST_COUNT; testNum++) {
        t.startTest(tableType, testNum);
      }
      tablePoints[tableType] = t.testsPassed / t.testsRun;
      System.out.println();
    }

    System.out.println("---Scoring---");

    System.out.println("Compiles and runs with students' tests: \t/5");

    // TA's should still spot-check that the functors are always consistent, would
    // work for
    // any string, and don't make use of Java's String.hashCode() method, and adjust
    // these scores if needed
    System.out.println("BadHashFunctor:\t\t\t\t\t" + Math.round(functorPoints[BAD_HASH] * 5) + "/5");
    System.out.println("MediocreHashFunctor:\t\t\t\t" + Math.round(functorPoints[MED_HASH] * 5) + "/5");
    System.out.println("GoodHashFunctor:\t\t\t\t" + Math.round(functorPoints[GOOD_HASH] * 5) + "/5");

    // prime table size, resize when 0.5 full, repeatedly probes i^2 away from
    // original index (-2 if sequence is wrong), wrap around to beginning of array
    System.out.println("TA spot check of quadratic probing:\t\t/5");

    // array of linked lists--ArrayList okay, may use Java's LinkedList or their
    // own implementation, optional resize when average list size gets big
    System.out.println("TA spot check of separate chaining:\t\t/5");

    System.out.println("QuadProbeHashTable:\t\t\t\t" + Math.round(tablePoints[QUAD_PROBE] * 15) + "/15");
    System.out.println("SeparateChainingHashTable:\t\t\t" + Math.round(tablePoints[SEP_CHAIN] * 15) + "/15");

    System.out.println("Quailty of tests and coding style:\t\t/10");
    System.out.println();
    System.out.println("Subtotal:\t\t\t\t\t/70");
    System.out.println("Analysis document:\t\t\t\t/30");
    System.out.println("Total:\t\t\t\t\t\t/100");
    System.out.println();
    System.out.println("------------------------------");
    System.out.println();
    System.out.println("TA Comments:");
  }

}