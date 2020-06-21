package org.vishia.util.test;

import org.vishia.util.OutputTwice;
import org.vishia.util.TestOrg;

/**This class is used to test TestOrg itself. It produces error messages which shows that the mechanism is correct.
 * <ul>
 * <li>start only main without arguments.
 * <li>The expected output is written to System.out and similar in a StringBuilder.
 * <li>The content in the Stringbuilder is compared with expected, see static variables.
 * <li>An abbreviation is shown in System.err
 * <li>finally it is written whether all is ok. 
 * <li>Hint: If the programm is changed, the expected output texts should be adapted because they contain line numbers.
 * The expected output texts are written on end of the file to prevent changes in output text changes the line numbers again.
 * <ul> 
 * @author hartmut Schorrig, LPGL-License. Do not remove the license remark.
 * @date 2020-06-21
 */
public class Test_TestOrg {


  public static void main(String[] args) {
    TestOrg testX = new TestOrg("Test_TestOrg", 1, args, System.err);
    String[] args0 = { "---TESTverbose:0"};
    CharSequence result = mainA(args0);
    testX.expect(result, "", 2, "test output verbose 0 without error should be empty");

    String[] args1 = { "---TESTverbose:1"};
    result = mainA(args1);
    testX.expect(result, resultA_verbose1, 2, "test output resultA_verbose1 error");
    
    String[] args7 = { "---TESTverbose:7"};
    result = mainA(args7);
    testX.expect(result, resultA_verbose7, 2, "test output resultA_verbose7 error");

    //
    result = mainF(args0);
    testX.expect(result, resultF, 2, "test output resultF error");
    //mainF(args1);
    //mainF(args7);
    //
    result = mainB(args0);
    testX.expect(result, resultB_verbose0, 2, "test output resultB_verbose0 error");
    result = mainB(args1);
    testX.expect(result, resultB_verbose1, 2, "test output resultB_verbose1 error");
    result = mainB(args7);
    testX.expect(result, resultB_verbose7, 2, "test output resultB_verbose7 error");
    //
    testX.finish();
    if(testX.isOk()) {
      System.out.println("ok Test_TestOrg: all test outputs are build successfully");
    } else {
      System.err.println("ERROR Test_TestOrg: see error output");
    }
  }
  
  
  public static CharSequence mainA(String[] args) {
    StringBuilder sb = new StringBuilder(500);
    OutputTwice out = new OutputTwice(sb, System.out);
    TestOrg test = new TestOrg("Test routine description", 1, args, out);
    try {
      boolean result = doSomethingToTest();
      test.expect(result, 7, "test case description");
    }
    catch(Exception exc) {
      test.exception(exc);
    }
    test.finish();
    return sb;
  }
  
  
  public static CharSequence mainF(String[] args) {
    StringBuilder sb = new StringBuilder(500);
    OutputTwice out = new OutputTwice(sb, System.out);
    TestOrg test = new TestOrg("Test routine description", 1, args, out);
    try {
      boolean result = doSomethingFailsTest();
      test.expect(result, 7, "test case description");
    }
    catch(Exception exc) {
      test.exception(exc);
    }
    test.finish();
    return sb;
  }
  
  
  private static boolean doSomethingToTest() { return true; }
  
  private static boolean doSomethingFailsTest() { return false; }
  
  
  public static CharSequence mainB(String[] args) {
    StringBuilder sb = new StringBuilder(500);
    OutputTwice out = new OutputTwice(sb, System.out);
    TestOrg test = new TestOrg("TestExample_TestOrg", 1, args, out);
    try {
      boolean cond = true;
      test.expect(cond, 7, "test case description");
      testAllOkVerbose02(test);
      testAllOkVerbose237(test, true);
      testAllOkVerbose237(test, false);
      testException1(test);
      testException2(test);
      testException1b(test);
    }
    catch(Exception exc) {
      test.exception(exc);
    }
    test.finish();
    return sb;
  }


  private static void testAllOkVerbose02(TestOrg testparent) {
    TestOrg test = new TestOrg("Test_Title_verbose=0", 0, testparent);
    testVerboseOk37(test);
    test.finish();
  }
  
  private static void testAllOkVerbose237(TestOrg testparent, boolean bOk) {
    TestOrg test = new TestOrg("Test_Title_verbose=2", 2, testparent);
    testVerboseOk37(test);
    testVerbose37(test, bOk);
    test.finish();
  }
  
  
  private static void testVerboseOk37(TestOrg parent) {
    TestOrg test = new TestOrg("Testsub ok verbose=3", 3, parent);
    test.expect(true, 4, "test ok verbose=4");
    test.expect(true, 7, "test ok verbose=7");
    test.finish();
  }
  
  
  private static void testVerbose37(TestOrg parent, boolean bOk) {
    TestOrg test = new TestOrg("Testsub verbose=3", 3, parent);
    test.expect(true, 4, "test ok verbose=4");
    test.expect(bOk, 7, "test verbose=7");
    test.finish();
  }
  
  
  private static void testException1(TestOrg testparent) {
    TestOrg test = new TestOrg("Test_Title_verbose=4", 4, testparent);
    try {
      throw new IllegalArgumentException("Exceptiontext");
    }
    catch (Exception exc) {
      test.exception(exc);
    }
    test.finish();
  }
  
  private static void testException1b(TestOrg testparent) {
    TestOrg test = new TestOrg("Test_Title_verbose=4", 4, testparent);
    testException2b(test);
    test.finish();
  }
  
  private static void testException2b(TestOrg parent) {
    TestOrg test = new TestOrg("Test_Title_verbose=4", 4, parent);
    try {
      throw new IllegalArgumentException("Exceptiontext");
    }
    catch (Exception exc) {
      test.exception(exc);
    }
    test.finish();
  }
  
  private static void testException2(TestOrg testparent) {
    TestOrg test = new TestOrg("Test_Title_verbose=4", 4, testparent);
    try {
      throwRoutine();
    }
    catch (Exception exc) {
      test.exception(exc);
    }
    test.finish();
  }
  
  private static void throwRoutine() {
    throw new IllegalArgumentException("Exceptiontext");
  }
  
  
  
  
  public static String resultA_verbose1 = 
      "=========================================================================\n" + 
      "ok Test routine description\n";
  
  public static String resultA_verbose7 = 
      "=========================================================================\n" + 
      "Test routine description\n" + 
      "  ok: test case description\n";
  
  public static String resultF = 
      "=========================================================================\n" + 
      "Test routine description\n" + 
      "  ERROR: test case description @ org.vishia.util.test.Test_TestOrg.mainF(Test_TestOrg.java:72); org.vishia.util.test.Test_TestOrg.main(Test_TestOrg.java:29); \n" + 
      "";
  
  public static String resultB_verbose0 = 
      "=========================================================================\n" + 
      "ok TestExample_TestOrg\n" + 
      "ok Test_Title_verbose=0\n" + 
      "Test_Title_verbose=2\n" + 
      "Testsub verbose=3\n" + 
      "  ERROR: test verbose=7 @ org.vishia.util.test.Test_TestOrg.testVerbose37(Test_TestOrg.java:134); org.vishia.util.test.Test_TestOrg.testAllOkVerbose237(Test_TestOrg.java:118); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:96); \n" + 
      "Test_Title_verbose=4\n" + 
      "  Exception: java.lang.IllegalArgumentException: Exceptiontext @ org.vishia.util.test.Test_TestOrg.testException1(Test_TestOrg.java:142); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:97); org.vishia.util.test.Test_TestOrg.main(Test_TestOrg.java:34); \n" + 
      "Test_Title_verbose=4\n" + 
      "  Exception: java.lang.IllegalArgumentException: Exceptiontext @ org.vishia.util.test.Test_TestOrg.throwRoutine(Test_TestOrg.java:179); org.vishia.util.test.Test_TestOrg.testException2(Test_TestOrg.java:170); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:98); org.vishia.util.test.Test_TestOrg.main(Test_TestOrg.java:34); \n" + 
      "Test_Title_verbose=4\n" + 
      "Test_Title_verbose=4\n" + 
      "  Exception: java.lang.IllegalArgumentException: Exceptiontext @ org.vishia.util.test.Test_TestOrg.testException2b(Test_TestOrg.java:159); org.vishia.util.test.Test_TestOrg.testException1b(Test_TestOrg.java:152); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:99); org.vishia.util.test.Test_TestOrg.main(Test_TestOrg.java:34); \n";
  
  public static String resultB_verbose1 = 
      "=========================================================================\n" + 
      "ok TestExample_TestOrg\n" + 
      "ok Test_Title_verbose=0\n" + 
      "Test_Title_verbose=2\n" + 
      "Testsub verbose=3\n" + 
      "  ERROR: test verbose=7 @ org.vishia.util.test.Test_TestOrg.testVerbose37(Test_TestOrg.java:134); org.vishia.util.test.Test_TestOrg.testAllOkVerbose237(Test_TestOrg.java:118); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:96); \n" + 
      "Test_Title_verbose=4\n" + 
      "  Exception: java.lang.IllegalArgumentException: Exceptiontext @ org.vishia.util.test.Test_TestOrg.testException1(Test_TestOrg.java:142); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:97); org.vishia.util.test.Test_TestOrg.main(Test_TestOrg.java:36); \n" + 
      "Test_Title_verbose=4\n" + 
      "  Exception: java.lang.IllegalArgumentException: Exceptiontext @ org.vishia.util.test.Test_TestOrg.throwRoutine(Test_TestOrg.java:179); org.vishia.util.test.Test_TestOrg.testException2(Test_TestOrg.java:170); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:98); org.vishia.util.test.Test_TestOrg.main(Test_TestOrg.java:36); \n" + 
      "Test_Title_verbose=4\n" + 
      "Test_Title_verbose=4\n" + 
      "  Exception: java.lang.IllegalArgumentException: Exceptiontext @ org.vishia.util.test.Test_TestOrg.testException2b(Test_TestOrg.java:159); org.vishia.util.test.Test_TestOrg.testException1b(Test_TestOrg.java:152); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:99); org.vishia.util.test.Test_TestOrg.main(Test_TestOrg.java:36); \n" + 
      "";
  
  public static String resultB_verbose7 = 
      "=========================================================================\n" + 
      "TestExample_TestOrg\n" + 
      "  ok: test case description\n" + 
      "Test_Title_verbose=0\n" + 
      "Testsub ok verbose=3\n" + 
      "  ok: test ok verbose=4\n" + 
      "  ok: test ok verbose=7\n" + 
      "Test_Title_verbose=2\n" + 
      "Testsub ok verbose=3\n" + 
      "  ok: test ok verbose=4\n" + 
      "  ok: test ok verbose=7\n" + 
      "Testsub verbose=3\n" + 
      "  ok: test ok verbose=4\n" + 
      "  ok: test verbose=7\n" + 
      "Test_Title_verbose=2\n" + 
      "Testsub ok verbose=3\n" + 
      "  ok: test ok verbose=4\n" + 
      "  ok: test ok verbose=7\n" + 
      "Testsub verbose=3\n" + 
      "  ok: test ok verbose=4\n" + 
      "  ERROR: test verbose=7 @ org.vishia.util.test.Test_TestOrg.testVerbose37(Test_TestOrg.java:134); org.vishia.util.test.Test_TestOrg.testAllOkVerbose237(Test_TestOrg.java:118); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:96); \n" + 
      "Test_Title_verbose=4\n" + 
      "  Exception: java.lang.IllegalArgumentException: Exceptiontext @ org.vishia.util.test.Test_TestOrg.testException1(Test_TestOrg.java:142); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:97); org.vishia.util.test.Test_TestOrg.main(Test_TestOrg.java:38); \n" + 
      "Test_Title_verbose=4\n" + 
      "  Exception: java.lang.IllegalArgumentException: Exceptiontext @ org.vishia.util.test.Test_TestOrg.throwRoutine(Test_TestOrg.java:179); org.vishia.util.test.Test_TestOrg.testException2(Test_TestOrg.java:170); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:98); org.vishia.util.test.Test_TestOrg.main(Test_TestOrg.java:38); \n" + 
      "Test_Title_verbose=4\n" + 
      "Test_Title_verbose=4\n" + 
      "  Exception: java.lang.IllegalArgumentException: Exceptiontext @ org.vishia.util.test.Test_TestOrg.testException2b(Test_TestOrg.java:159); org.vishia.util.test.Test_TestOrg.testException1b(Test_TestOrg.java:152); org.vishia.util.test.Test_TestOrg.mainB(Test_TestOrg.java:99); org.vishia.util.test.Test_TestOrg.main(Test_TestOrg.java:38); \n" + 
      "";  
  

}
