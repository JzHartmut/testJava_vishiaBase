package org.vishia.byteData.test;

import org.vishia.util.TestOrg;

public class Test_ByteDataAccess {

  
  
  public static void main(String[] cmdArgs) {
    TestOrg test = new TestOrg("Text_ByteDataAccess", 1, cmdArgs);
    
  }
  
  public static void testSimple_A(TestOrg testParent) {
    TestOrg test = new TestOrg("Text_ByteDataAccess", 4, testParent);
    byte[] data = new byte[16];
    Exmpl_ByteDataAccess accData = new Exmpl_ByteDataAccess(data);
    
  }
  
  
}
