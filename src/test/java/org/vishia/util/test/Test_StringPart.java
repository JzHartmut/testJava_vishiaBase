package org.vishia.util.test;

//import org.junit.Assert;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
import org.vishia.util.StringPart;
import org.vishia.util.TestOrg;

public class Test_StringPart
{
  public static void main(String[] args){
    Test_StringPart thiz = new Test_StringPart();
    TestOrg test = new TestOrg("Test_StringPart", 4, args);
    //thiz.test_seek_len_basics();
    //test_getCircumScriptionToAnyChar();
    thiz.test_IteratorLines(test);
  }

  static void check(boolean cond, String text) {
//    Assert.assertTrue(text, cond);
    if(!cond) {
      System.out.println("failed: " + text);
    } else {
      System.out.println("ok: " + text);
    }
  }
  
  

  
  
//  @Test @Tag("teststd")
  void test_seek_len_basics() {
    String str = " 0123456789ABC";
    int pos0 = str.indexOf('0');  //to compare, pos of 0
    StringPart sp = new StringPart(str);
    CharSequence res = sp.seek('0', 0).lento('4').getCurrent();  //inclusive 0, exclusive 4
    check(res.equals("0123"), "simple seek('c').lento('c')");
    //
    sp.setParttoMax().seek("01").lento("56");
    check(sp.getCurrent().equals("01234"), "simple seek(\"ss\").lento(\"ss\")");
    check(sp.getCurrentPosition()== pos0, "getCurrentPosition()");
    check(sp.length() == 5, "length()");
    //
    sp.lento("78");  //will not be found, because outside of current range
    check(sp.length() == 0 && sp.getCurrentPosition() == pos0, "lento(\"outsideStr\") - length()==0"); 
    //
    sp.len0end();
    check(sp.length() == str.length() - pos0 , "len0end() sets the length to last end.");
    //
    sp.lentoAnyChar("C9");
    sp.setCurrentMaxPart();
    sp.lento("AB").len0end();  //sets to "9" because "AB" is outside
    check(sp.getCurrent().equals("012345678"), "lentoAnyChar\"cc\").setCurrentMaxPart()");
  }
  
  
  
  void test_IteratorLines(TestOrg testParent) {
    TestOrg test = new TestOrg("test_IteratorLines", 7, testParent);
    StringPart sp = new StringPart("");  //it is empty
    for(StringPart line: sp) {
      test.expect(false, 7, "empty Stringpart should not have an element");
      assert(line == null);
    }
    sp.close();
    //
    sp = new StringPart("+first  \r\n+second \r+third  ");
    int lineCt = 0;
    for(StringPart line: sp) {
      lineCt +=1;
      test.expect(line.startsWith("+") && line.length() == 8, 8, line.toString());
    }
    test.expect(lineCt == 3, 8, "3 lines");
    sp.close();
    
    
    test.finish();
  }
  
  
  
  
}
