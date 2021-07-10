//Note: Test with jzcmd: call jzcmd with this java file with its full path:
//D:/vishia/Java/srcJava_vishiaBase/org/vishia/byteData/test/TestByteDataAccessBase.java
//==JZcmd==
//Note: The JZcmd detect its code after //JZcmd too. It starts on the //==JZcmd== label.
//JZcmd Obj a = java org.vishia.byteData.test.TestByteDataAccessBase.main();
//==endJZcmd==

package org.vishia.byteData.test;

import org.vishia.util.TestOrg;

/**
 * @author hartmut
 * @since 2015-08-03, LPGL license. It is only an example for test.
 * @since 2021-07 moved to the TestJava_vishiaBase component, improved for better test.
 *
 */
public class TestByteDataAccessBase
{
  byte[] data = new byte[100];
  
  ExampleByteDataAccessBase accHead = new ExampleByteDataAccessBase();
  
  ExampleStringOrInt accStringOrInt = new ExampleStringOrInt();
  
  
  
  /**Writes a String in the structure, test it with {@link #testread(int)}. */
  int testwrite(String testString, TestOrg testParent)
  {
    TestOrg test = new TestOrg("Text_ByteDataAccess", 4, testParent);
    this.accHead.assignClear(this.data);
    this.accHead.set_HeadSignification(0xabcd1234);
    this.accHead.set_cmd(1);                     // Note: written as big endian. Note2: cmp with (byte)val  for negative values!
    boolean bOk = (this.data[0] == (byte)0xab && this.data[1] == (byte)0xcd && this.data[2] == 0x12 && this.data[3] == 0x34 
                && this.data[5] == 1 );
    test.expect(bOk, 5, "setInt big Endian");
    this.accHead.addChild(this.accStringOrInt);
    this.accStringOrInt.write(testString);
    int lengthWithAllChild = this.accHead.getLengthTotal();
    this.accHead.set_length(lengthWithAllChild);
    test.finish();
    return lengthWithAllChild;
  }
  

  /**Writes two integer in the structure, test it with {@link #testread(int)}. */
  int testwrite(int cmd, int value1, short value2)
  {
    this.accHead.assignClear(this.data);
    this.accHead.set_HeadSignification(0xabcd);
    this.accHead.set_cmd(cmd);
    this.accHead.addChild(this.accStringOrInt);
    this.accStringOrInt.write(value1, value2);
    int lengthWithAllChild = this.accHead.getLengthTotal();
    this.accHead.set_length(lengthWithAllChild);
    return lengthWithAllChild;
  }
  
  /**Reads the written data and output its to System.out. */
  void testread(int lengthData)
  { this.accHead.assign(this.data, lengthData);
    int signification = this.accHead.get_headSignification();
    int cmd = this.accHead.get_cmd();
    int length = this.accHead.get_length();
    if(this.accHead.sufficingBytesForNextChild(ExampleStringOrInt.kMinLength)){
      this.accHead.addChild(this.accStringOrInt, length - this.accHead.getLengthCurrent());
      ExampleStringOrInt.JavaData data = this.accStringOrInt.read();
      if(data.str !=null) {
        System.out.println("TestByteDataAccess - read, sign=" + signification + ", cmd=" + cmd + ", length=" + length 
            + ", text=" + data.str);
      } else {
        System.out.println("TestByteDataAccess - read, sign=" + signification + ", cmd=" + cmd + ", length=" + length 
            + ", val1,2=" + data.s1 + "," + data.s2);
      }
    }
  }

  
  /**Testsequence. */
  void test(String[] args)
  { 
    TestOrg test = new TestOrg("Text_ByteDataAccess", 4, args);
    this.accHead.setBigEndian(true);
    int length = testwrite("Hello ByteData", test);
    testread(length);
    this.accHead.setBigEndian(false);  //use another endian.
    length = testwrite(2, 17, (short)1000);
    testread(length);
    test.finish();
  }
  
  public static void main(String[] args){
    TestByteDataAccessBase thiz = new TestByteDataAccessBase();
    thiz.test(args);
  }
}
