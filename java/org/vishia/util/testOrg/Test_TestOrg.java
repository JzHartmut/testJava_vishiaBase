package org.vishia.util.testOrg;

import org.vishia.util.TestOrg;

public class Test_TestOrg {

  
  public static void mainTest(String[] args) {
    TestOrg test = new TestOrg("Test_TestOrg", 2, args);
    test1(test);
    test.finish();
      
  }
  
  
  private static void test1(TestOrg testParent) {
    TestOrg test = new TestOrg("test1", 4, testParent);
    test.finish();
  }

  
  
  public static void main(String[] argsNo) {
    String[] args = { "---TESTverbose:3" };
    mainTest(args);
  
  }
  
  
}
