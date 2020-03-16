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
    int errorpos = StringCmp.compare(s1, s2, true, this.comments);
    Assert.assertTrue("fails: (*comment*) ", errorpos==-1);
  }
  
  
  
  /**Tests whether the endline comment skipping works*/
  @Test @Tag("teststd")
  public void testSkipEndlineComment() {
    String s1 = "abc // zz\r\n";
    String s2 = "abc";
    int errorpos; 
    errorpos = StringCmp.compare(s1, s2, true, this.comments);
    Assert.assertTrue("fails: abc //comment", errorpos == -1);;
    s1 = "abc// zz\r\n";
    s2 = "abc";
    errorpos = StringCmp.compare(s1, s2, true, this.comments);
    Assert.assertTrue("fails: abc//comment", errorpos == -1);;
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
