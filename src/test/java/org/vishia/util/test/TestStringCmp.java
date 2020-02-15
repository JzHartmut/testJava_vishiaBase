package org.vishia.util.test;

import org.junit.Assert;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.vishia.util.StringCmp;

public class TestStringCmp {

  String[] comments = {"//", "(*", "*)"};
  
  /**Tests whether the comment skipping works*/
  @Test @Tag("teststd")
  public void testSkipComments() {
    String s1 = "abc (*xxx*) zz";
    String s2 = "abc zz";
    boolean bOk = StringCmp.compare(s1, s2, true, this.comments);
    Assert.assertTrue("fails: (*comment*) ", bOk);
  }
  
  
  
  /**Tests whether the endline comment skipping works*/
  @Test @Tag("teststd")
  public void testSkipEndlineComment() {
    String s1 = "abc // zz\r\n";
    String s2 = "abc";
    boolean bOk = true; 
    bOk = StringCmp.compare(s1, s2, true, this.comments);
    Assert.assertTrue("fails: abc //comment", bOk);;
    s1 = "abc// zz\r\n";
    s2 = "abc";
    bOk = StringCmp.compare(s1, s2, true, this.comments);
    Assert.assertTrue("fails: abc//comment", bOk);;
  }
  
  
  
  public static void main(String[] args) {
    TestStringCmp thiz = new TestStringCmp();
    try {
      //thiz.testSkipComments();
      thiz.testSkipEndlineComment();
    } catch(Exception exc) {
      System.err.println("Exception: " + exc.getMessage());
      exc.printStackTrace(System.err);
    }
  }
}
