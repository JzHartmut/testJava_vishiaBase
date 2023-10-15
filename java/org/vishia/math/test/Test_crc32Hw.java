package org.vishia.math.test;

import org.vishia.fpga.stdmodules.CeTime_ifc;
import org.vishia.fpga.stdmodules.CrcGenSerial_Inpifc;
import org.vishia.fpga.stdmodules.CrcGeneratorSerial;
import org.vishia.util.Debugutil;
import org.vishia.util.TestOrg;

public class Test_crc32Hw {

  final Test_crc32 testSw = new Test_crc32();
  
  void testHw ( TestOrg parent ) {
    TestOrg test = new TestOrg("testHw", 5, parent);
    int ixTelg = Test_crc32Hw.this.testSw.ixTelg;
    
    class TestHwData implements CrcGenSerial_Inpifc {
      boolean startCrcHw = false;
      boolean finishData = false;
      boolean finishCrc;
      int ixBit = 0;
      int time;      
      @Override public boolean stateCrc ( int time, int min ) { return this.finishData; }
      @Override public boolean stateBeforeCrc ( int time, int min ) { return !this.startCrcHw;  }
      @Override public boolean getBit ( int time, int min ) { 
        int ixTelg = Test_crc32Hw.this.testSw.ixTelg;
        int b = Test_crc32Hw.this.testSw.telgs[ixTelg][this.ixBit /8];
        int mask = (0x01 << (this.ixBit %8) );
        return ( b & mask ) !=0; 
      }
      public void set ( int time ) {
        this.time = time;
        this.ixBit = time - 10;
        this.startCrcHw = time >= 10;
        this.finishData = this.ixBit >= 8* Test_crc32Hw.this.testSw.telgs[ixTelg].length;
        this.finishCrc = this.time >= 8* Test_crc32Hw.this.testSw.telgs[ixTelg].length + 32 + 10;
      }
    
    }
    
    TestHwData testHwData = new TestHwData();

    CeTime_ifc ce = new CeTime_ifc() {
      @Override public boolean ce() { return true; }  //always true, calculates in any loop, only for software

      @Override public int time () {
        // TODO Auto-generated method stub
        return 0;
      }

      @Override public int period () {
        // TODO Auto-generated method stub
        return 0;
      }

      @Override public String timeGroupName () {
        // TODO Auto-generated method stub
        return null;
      }
    };
        
    int crcHw = 0;
    CrcGeneratorSerial genHw = new CrcGeneratorSerial(ce, testHwData);
    int time = 0;  
    while(!testHwData.finishCrc){
      testHwData.set(time);
      genHw.step(time);
      if(testHwData.finishData && ! testHwData.finishCrc) {
        crcHw <<=1;
        if(genHw.getBitCrc(time, 0)) {
          crcHw |=1;
        }
      }
      genHw.update();
      time+=1;
    }
    Debugutil.stop();    
    test.expect(crcHw == Test_crc32Hw.this.testSw.crcs[ixTelg], 5, "genHw");
    test.finish();
  }
  
  
  public static void main (String[] args) {
    TestOrg test = new TestOrg("Test_crc32", 7, args);
    Test_crc32Hw thiz = new Test_crc32Hw();
    thiz.testHw(test);
    test.finish();
  }

}
