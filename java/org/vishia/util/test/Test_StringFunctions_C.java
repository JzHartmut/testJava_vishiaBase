package org.vishia.util.test;

import java.io.IOException;

import org.vishia.util.StringFunctions;
import org.vishia.util.StringFunctions_C;
import org.vishia.util.TestOrg;

public class Test_StringFunctions_C {
  public static void main(String[] args){
//  test_checkSameItem(args);
//  test_checkMoreSameChars(args);
    try {
    test_intPicture(args);
    } catch (Throwable exc) {
      
    }
//  testCompare();
//  test_indexOf();
}

  
  private static class NrPictResult {
    long nr; String pict; String result;

    public NrPictResult(long nr, String pict, String result) {
      this.nr = nr;  this.pict = pict; this.result = result;
    }
    
    @Override public String toString() {return this.pict + " : " + this.nr + " = " + this.result; }
  }

  
  private static NrPictResult[] testTable_intPicture = {
      new NrPictResult(123, "333333331", "123")
    , new NrPictResult(123, "333333221", "123") 
    , new NrPictResult(123, "333332111", " 123") 
    , new NrPictResult(123, "333331111", "0123")
    , new NrPictResult(123, "3333311.11", "01.23")
      
  };
  
  
  static void test_intPicture ( String[] args) throws IOException {
    TestOrg test = new TestOrg("test_intPicture", 2,  args);
    
    StringBuilder sb = new StringBuilder(50);
    for(NrPictResult e: testTable_intPicture) {
      sb.setLength(0);
      StringFunctions_C.appendIntPict(sb, e.nr, e.pict);
      boolean bOk = StringFunctions.compare(sb, e.result) ==0;
      test.expect(bOk, 5, e.toString());
    }
    test.finish();
    
  }

}
