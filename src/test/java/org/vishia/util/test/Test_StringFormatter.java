package org.vishia.util.test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.vishia.util.Debugutil;
import org.vishia.util.StringFormatter;
import org.vishia.util.TestOrg;

public class Test_StringFormatter
{

  
  
  
  
  
  
  public static void main(String[] args) {
    TestOrg test = new TestOrg("Test_StringFormatter", 2, args);
    test_writeHexBlock(test);
    test_writeMoreLinesPos(test);
    test.finish();
  }
  
  
  
  static void test_writeHexBlock(TestOrg testParent) {
    TestOrg test = new TestOrg("test_writeHexBlock", 4, testParent); 
    byte[] data = new byte[0x23];
    for(int ix = 0; ix < data.length; ++ix) {
      data[ix] = (byte)ix;
    }
    try{ 
      //FileWriter fw = new FileWriter("t:/test.hex");
      
      Writer fw = new StringWriter();
      
      StringFormatter fm = new StringFormatter(fw, true, "\r\n", 120);
      //writes 0x22 bytes because the last word with 2 bytes is incomplete.
      fm.addHexBlock(data, 0, -1, (short)16, (short)2);  // This calls internally append('\n')
      fm.close();
      String sExpected =    "000000: 0100 0302 0504 0706 0908 0b0a 0d0c 0f0e \r\n000010: 1110 1312 1514 1716 1918 1b1a 1d1c 1f1e \r\n000020: 2120        \r\n";
      test.expect(fw.toString(), sExpected, 6, "Write hexa, see sExpected in src");
      Debugutil.stop();
      //ByteDataAccessSimple.writeHex16(trcByteDst[ixTrace], 0, -1, fout);
    
    } catch(IOException exc) { 
      //System.err.println("IOException file "); 
      test.exception(exc);
    }
    test.finish();
  }
  
  
  
  
  static void test_writeMoreLinesPos(TestOrg testParent) {
    TestOrg test = new TestOrg("test_writeMoreLinesPos", 4, testParent); 
    StringFormatter fm = new StringFormatter(1000);
    try {
      for(int ix = 0; ix <3; ++ix) {
        fm.add("startline").pos(12).addHex(ix, 2).pos(16).addint(ix, "1.1").newline();
      }
      String sExpected =    "startline   00  0.0\nstartline   01  0.1\nstartline   02  0.2\n";
      test.expect(fm.toString(), sExpected, 6, "Write more as one line, see sExpected in src");
      Debugutil.stop();
      //ByteDataAccessSimple.writeHex16(trcByteDst[ixTrace], 0, -1, fout);
    
    } catch(IOException exc) { 
      //System.err.println("IOException file "); 
      test.exception(exc);
    }
    test.finish();
  }
  
  
}
