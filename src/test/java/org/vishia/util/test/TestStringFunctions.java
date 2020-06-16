//D:/vishia/Java/srcJava_vishiaBase/org/vishia/util/test/TestStringFunctions.java
//==JZcmd==
//JZcmd java org.vishia.util.test.TestStringFunctions.test_comparePos();
//Obj test = 
//==endJZcmd==
package org.vishia.util.test;

import org.vishia.testOrg.TestOrg;
import org.vishia.util.Assert;
import org.vishia.util.StringFunctions;

public class TestStringFunctions
{
  public static void main(String[] args){
    test_comparePos();
    testCompare();
    test_indexOf();
  }
  
  
  private static void testCompare()
  {
    String s1 = "abcdxyz";
    String s2 = "dexy";
    int cmpr = StringFunctions.compare(s1, 4, s2, 2, 4);
    Assert.check(cmpr == 0);
  }
  
  
  private static void test_indexOf()
  {
    int pos;
    pos = "123abcxy".indexOf("abc", 2);
    Assert.check(pos == 3);
    
    pos = StringFunctions.indexOf("123abcxy", "abc", 2);
    Assert.check(pos == 3);
    
  }  
  
  
  public static void test_comparePos()
  {
    TestOrg test = new TestOrg("test_comparePos");
    String s1 = "a";
    String s2 = "b";
    int cmp = StringFunctions.comparePos(s1, 0, s2, 0, Integer.MAX_VALUE);
    test.expect(cmp==-1, true, "only one char per String, a ? b returns -1");
    //
    s1 = "abcx";
    s2 = "abcy";
    cmp = StringFunctions.comparePos(s1, 0, s2, 0, Integer.MAX_VALUE);
    test.expect(cmp ==-4, true, "abcx ? abcy returns -4");
    //
    s1 = "abc";
    s2 = "abcy";
    cmp = StringFunctions.comparePos(s1, 0, s2, 0, Integer.MAX_VALUE);
    test.expect(cmp ==-4, true, "abc ? abcy returns -4");
    //
    s1 = "abcx";
    s2 = "abc";
    cmp = StringFunctions.comparePos(s1, 0, s2, 0, Integer.MAX_VALUE);
    test.expect(cmp ==4, true, "abc ? abcy returns 4");
    //
    s1 = "abcx";
    s2 = "abcx";
    cmp = StringFunctions.comparePos(s1, 0, s2, 0, Integer.MAX_VALUE);
    test.expect(cmp ==0, true, "abcx ? abcx returns 0");
    //
    test.finish();
  }

  
  static void error(String s){
    System.out.println(s);
  }
  
  
}
