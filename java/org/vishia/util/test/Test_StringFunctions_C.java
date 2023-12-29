package org.vishia.util.test;

import java.io.IOException;

import org.vishia.util.Debugutil;
import org.vishia.util.StringFunctions;
import org.vishia.util.StringFunctions_C;
import org.vishia.util.TestOrg;

public class Test_StringFunctions_C {
  
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

  
  static void testParseInt ( TestOrg parent) {
    class Testval{ String s; int v; int radix; int parsedChars; int digits; int nrTest; 
      Testval ( int nrTest, String s, int v, int radix, int parsedChars, int digits) { 
        this.nrTest = nrTest;
        this.s = s; this. v = v; this.radix = radix; this.parsedChars = parsedChars; this.digits = digits;
    } }
    Testval[] testval = {
        new Testval(1, "0x80000000", 0x80000000, 10, 10, 8) //parse a hexa number also as negative
      , new Testval(2, "0xffffffff", -1, 16, 10,8) 
      , new Testval(3, "-80000000", 0x80000000, 16, 9, 8)  // max. negative value given in hex
      , new Testval(4, "7fffFFFF", 0x7ffffffF, 16, 8, 8)   // max. positive value given in hex
      , new Testval(5, "80000000", 0x80000000, 16, 8, 8)   // parse also a positive number as hex value.
      , new Testval(6, "-2147483648", 0x80000000, 10, 11, 10) // parse the max negative number completely.
      , new Testval(7, "2147483648", 214748364, 10, 9, 9)  // do not parse a positive number which overflows.
    };
    TestOrg test = new TestOrg("test ", 2, parent);
    for(Testval val: testval) {
      String sf = val.s;
      int zsf = sf.length();
      int[] parsedChars = new int[2];
      if(val.nrTest == 6)
        Debugutil.stop();
      int x = StringFunctions_C.parseIntRadix(sf, 0, zsf, val.radix, parsedChars);
      boolean bOk = x == val.v && parsedChars[0] == val.parsedChars && parsedChars[1] == val.digits;
      if(!bOk)
        Debugutil.stop();
      String msg = sf.substring(0, parsedChars[0]) + " ->" + sf.substring(parsedChars[0]) + " nrDigits=" + parsedChars[1];
      test.expect(bOk, 3, msg);
    }
    test.finish();
  }

  
  
  static void testParseFloat ( TestOrg parent) {
    class Testval{ String s; float v; int parsedChars; int nrTest; char dec; String addChars; 
    Testval ( int nrTest, String s, char dec, String addChars, float v, int parsedChars) { 
      this.nrTest = nrTest;
      this.s = s; this. v = v; this.parsedChars = parsedChars; this.dec = dec; this.addChars=addChars;
    } }
    Testval[] testval = {
        new Testval(1, "3.14159", '.', null, 3.14159f, 7) //parse a hexa number also as negative
      , new Testval(2, "0,000 000 000'000'123'456", ',', " '", 1.23456e-13f, 25) 
      , new Testval(3, "1'123'456.123456789", '.', " '", 1123456.1f, 19)  // max. negative value given in hex
    };
    TestOrg test = new TestOrg("test_parseFloat", 2, parent);
    for(Testval val: testval) {
      String sf = val.s;
      int zsf = sf.length();
      int[] parsedChars = new int[1];
      if(val.nrTest == 6)
        Debugutil.stop();
      float f = StringFunctions_C.parseFloat(sf, 0, -1, val.dec, parsedChars, val.addChars);
      int fi = Float.floatToIntBits(f);
      int fi2 = Float.floatToIntBits(val.v);
      boolean bOk = f == val.v && parsedChars[0] == val.parsedChars;
      if(!bOk)
        Debugutil.stop();
      String msg = sf.substring(0, parsedChars[0]) + " -> " + sf.substring(parsedChars[0]) + f;
      test.expect(bOk, 3, msg);
    }
    test.finish();
  }

  
  
  public static void testSpecific(String[] args) {
    //DataAccess.debugIdent = "dataColor";  //possibility to set a data depending debug break
    TestOrg test = new TestOrg("Test_OutTextPreparer", 1, args);
    try {
      testParseInt(test);
      testParseFloat(test);
      //test_intPicture(args);
      
    } catch (Exception e) {
      test.exception(e);
    }
    test.finish();
  }

  
  
  public static void test(String[] args) {
    //DataAccess.debugIdent = "dataColor";  //possibility to set a data depending debug break
    TestOrg test = new TestOrg("Test_OutTextPreparer", 1, args);
    try {
      testParseInt(test);
      testParseFloat(test);
      test_intPicture(args);
      
    } catch (Exception e) {
      test.exception(e);
    }
    test.finish();
  }

  
  
  public static void main(String[] args) { 
    //testSpecific(args);
    test(args); 
  }
  


}
