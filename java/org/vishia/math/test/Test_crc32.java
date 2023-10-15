package org.vishia.math.test;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.vishia.fpga.stdmodules.Ce_ifc;
import org.vishia.fpga.stdmodules.CrcGenSerial_Inpifc;
import org.vishia.fpga.stdmodules.CrcGeneratorSerial;
import org.vishia.util.Debugutil;
import org.vishia.util.TestOrg;

public class Test_crc32 {

  
  class CRC32bit {

    int crc32_rev, crc32;

    static final int CRC32MASKREV = 0xEDB88320; 
    static final int CRC32 = 0x04c11db7; 

    /**This follows {@linkplain https://de.wikipedia.org/wiki/Zyklische_Redundanzprüfung}
     * "Modifizierte CRC32: Startwert 111..., ..."
     * @param bit
     */
    public void stepBit_CrcRevers ( boolean bit ) {
      if ((this.crc32_rev & 1)!=0 != bit) {
        this.crc32_rev = (this.crc32_rev >>> 1) ^ CRC32MASKREV;
      } else {
        this.crc32_rev = (this.crc32_rev >>> 1);
      }
    }

    public int stepByte ( byte[] inp, int zinp ) {
      this.crc32_rev = -1;
      for(int ixinp = 0; ixinp < zinp; ++ixinp) {
        byte in1= inp[ixinp];
        for(int ixb = 0; ixb < 8; ++ixb) {
          stepBit_CrcRevers( (in1 & 0x01)!=0);
          in1 >>>=1;
        }
      }
      return ~this.crc32_rev;
    }
    
    /**This is a common algorithm for CRC from {@linkplain https://de.wikipedia.org/wiki/Zyklische_Redundanzprüfung}
     * It does not produce the necessary CRC for Ethernet. 
     * This is not only because start with MSB. 
     * @param bit
     */
    public void stepBitShl ( boolean bit ) {
      if (((this.crc32 & 0x80000000)!=0) != bit) {
        this.crc32 = (this.crc32 << 1) ^ CRC32;
      } else {
        this.crc32 = (this.crc32 << 1);
      }
    }

    public int stepByteMSB ( byte[] inp, int zinp ) {
      this.crc32 = 0xffffffff;
      for(int ixinp = 0; ixinp < zinp; ++ixinp) {
        byte in1= inp[ixinp];
        for(int ixb = 0; ixb < 8; ++ixb) {
          stepBitShl( (in1 & 0x80) !=0);
          in1 <<=1;
        }
      }
      return ~this.crc32;
    }
    
  } // class CRC32bit




  
  CRC32 crcCaluclator = new CRC32();

  
  
  /**See {@linkplain https://wiki.osdev.org/CRC32}
   * @author hartmut
   *
   */
  static class CRC32Table {

    final static int[] poly8_lookup =
      {
      0, 0x77073096, 0xEE0E612C, 0x990951BA,
      0x076DC419, 0x706AF48F, 0xE963A535, 0x9E6495A3,
      0x0EDB8832, 0x79DCB8A4, 0xE0D5E91E, 0x97D2D988,
      0x09B64C2B, 0x7EB17CBD, 0xE7B82D07, 0x90BF1D91,
      0x1DB71064, 0x6AB020F2, 0xF3B97148, 0x84BE41DE,
      0x1ADAD47D, 0x6DDDE4EB, 0xF4D4B551, 0x83D385C7,
      0x136C9856, 0x646BA8C0, 0xFD62F97A, 0x8A65C9EC,
      0x14015C4F, 0x63066CD9, 0xFA0F3D63, 0x8D080DF5,
      0x3B6E20C8, 0x4C69105E, 0xD56041E4, 0xA2677172,
      0x3C03E4D1, 0x4B04D447, 0xD20D85FD, 0xA50AB56B,
      0x35B5A8FA, 0x42B2986C, 0xDBBBC9D6, 0xACBCF940,
      0x32D86CE3, 0x45DF5C75, 0xDCD60DCF, 0xABD13D59,
      0x26D930AC, 0x51DE003A, 0xC8D75180, 0xBFD06116,
      0x21B4F4B5, 0x56B3C423, 0xCFBA9599, 0xB8BDA50F,
      0x2802B89E, 0x5F058808, 0xC60CD9B2, 0xB10BE924,
      0x2F6F7C87, 0x58684C11, 0xC1611DAB, 0xB6662D3D,
      0x76DC4190, 0x01DB7106, 0x98D220BC, 0xEFD5102A,
      0x71B18589, 0x06B6B51F, 0x9FBFE4A5, 0xE8B8D433,
      0x7807C9A2, 0x0F00F934, 0x9609A88E, 0xE10E9818,
      0x7F6A0DBB, 0x086D3D2D, 0x91646C97, 0xE6635C01,
      0x6B6B51F4, 0x1C6C6162, 0x856530D8, 0xF262004E,
      0x6C0695ED, 0x1B01A57B, 0x8208F4C1, 0xF50FC457,
      0x65B0D9C6, 0x12B7E950, 0x8BBEB8EA, 0xFCB9887C,
      0x62DD1DDF, 0x15DA2D49, 0x8CD37CF3, 0xFBD44C65,
      0x4DB26158, 0x3AB551CE, 0xA3BC0074, 0xD4BB30E2,
      0x4ADFA541, 0x3DD895D7, 0xA4D1C46D, 0xD3D6F4FB,
      0x4369E96A, 0x346ED9FC, 0xAD678846, 0xDA60B8D0,
      0x44042D73, 0x33031DE5, 0xAA0A4C5F, 0xDD0D7CC9,
      0x5005713C, 0x270241AA, 0xBE0B1010, 0xC90C2086,
      0x5768B525, 0x206F85B3, 0xB966D409, 0xCE61E49F,
      0x5EDEF90E, 0x29D9C998, 0xB0D09822, 0xC7D7A8B4,
      0x59B33D17, 0x2EB40D81, 0xB7BD5C3B, 0xC0BA6CAD,
      0xEDB88320, 0x9ABFB3B6, 0x03B6E20C, 0x74B1D29A,
      0xEAD54739, 0x9DD277AF, 0x04DB2615, 0x73DC1683,
      0xE3630B12, 0x94643B84, 0x0D6D6A3E, 0x7A6A5AA8,
      0xE40ECF0B, 0x9309FF9D, 0x0A00AE27, 0x7D079EB1,
      0xF00F9344, 0x8708A3D2, 0x1E01F268, 0x6906C2FE,
      0xF762575D, 0x806567CB, 0x196C3671, 0x6E6B06E7,
      0xFED41B76, 0x89D32BE0, 0x10DA7A5A, 0x67DD4ACC,
      0xF9B9DF6F, 0x8EBEEFF9, 0x17B7BE43, 0x60B08ED5,
      0xD6D6A3E8, 0xA1D1937E, 0x38D8C2C4, 0x4FDFF252,
      0xD1BB67F1, 0xA6BC5767, 0x3FB506DD, 0x48B2364B,
      0xD80D2BDA, 0xAF0A1B4C, 0x36034AF6, 0x41047A60,
      0xDF60EFC3, 0xA867DF55, 0x316E8EEF, 0x4669BE79,
      0xCB61B38C, 0xBC66831A, 0x256FD2A0, 0x5268E236,
      0xCC0C7795, 0xBB0B4703, 0x220216B9, 0x5505262F,
      0xC5BA3BBE, 0xB2BD0B28, 0x2BB45A92, 0x5CB36A04,
      0xC2D7FFA7, 0xB5D0CF31, 0x2CD99E8B, 0x5BDEAE1D,
      0x9B64C2B0, 0xEC63F226, 0x756AA39C, 0x026D930A,
      0x9C0906A9, 0xEB0E363F, 0x72076785, 0x05005713,
      0x95BF4A82, 0xE2B87A14, 0x7BB12BAE, 0x0CB61B38,
      0x92D28E9B, 0xE5D5BE0D, 0x7CDCEFB7, 0x0BDBDF21,
      0x86D3D2D4, 0xF1D4E242, 0x68DDB3F8, 0x1FDA836E,
      0x81BE16CD, 0xF6B9265B, 0x6FB077E1, 0x18B74777,
      0x88085AE6, 0xFF0F6A70, 0x66063BCA, 0x11010B5C,
      0x8F659EFF, 0xF862AE69, 0x616BFFD3, 0x166CCF45,
      0xA00AE278, 0xD70DD2EE, 0x4E048354, 0x3903B3C2,
      0xA7672661, 0xD06016F7, 0x4969474D, 0x3E6E77DB,
      0xAED16A4A, 0xD9D65ADC, 0x40DF0B66, 0x37D83BF0,
      0xA9BCAE53, 0xDEBB9EC5, 0x47B2CF7F, 0x30B5FFE9,
      0xBDBDF21C, 0xCABAC28A, 0x53B39330, 0x24B4A3A6,
      0xBAD03605, 0xCDD70693, 0x54DE5729, 0x23D967BF,
      0xB3667A2E, 0xC4614AB8, 0x5D681B02, 0x2A6F2B94,
      0xB40BBE37, 0xC30C8EA1, 0x5A05DF1B, 0x2D02EF8D
      };
    
    int[] filledTable = new int[256]; 
    
    //calculate a checksum on a buffer -- start address = p, length = bytelength
    static int crc32_byte(byte[] p, int bytelength)
    {
      int ixp = 0;
      int crc = 0xffffffff;
      while (ixp < bytelength) {
        int ixTable = (crc ^ p[ixp++]) & 0xff;
        crc = CRC32Table.poly8_lookup[ixTable] ^ (crc >>> 8);
      }
      // return (~crc); also works
      return (crc ^ 0xffffffff);
    }

    //calculate a checksum on a buffer -- start address = p, length = bytelength
    static int crc32_int(int[] p, int bytelength)
    {
      int ixp = 0;
      int crc = 0xffffffff;
      while (ixp < bytelength) {
        int ixTable = (crc ^ p[ixp++]) & 0xff;
        crc = CRC32Table.poly8_lookup[ixTable] ^ (crc >>> 8);
      }
      // return (~crc); also works
      return (crc ^ 0xffffffff);
    }

    //Fill the lookup table -- table = the lookup table base address
    void crc32_fill(int[] table){
      int index=0,z;
      do{
        table[index]=index;
        for( z=8; z>0; z--) {
          table[index] = (table[index]&1)!=0 ? (table[index]>>1) ^ 0xEDB88320  : table[index]>>1;
        }
      }while( ++index < 256);
    }
  } // class CRC32Table
  
  //calculate a checksum on a buffer -- start address = p, length = bytelength
  static int crc32_bit(byte[] p, int bytelength)
  {
    int ixp = 0;
    int crc = 0xffffffff;
    while (ixp < bytelength) {
      for(int ixBit = 0; ixBit <8; ++ixBit) {
        
        int ixTable = (crc ^ p[ixp++]) & 0xff;
        crc = CRC32Table.poly8_lookup[ixTable] ^ (crc >>> 8);
      }
    }
    // return (~crc); also works
    return (crc ^ 0xffffffff);
  }

  
  

  int[] telg0 = { 0xaf, 0xfe, 0xca, 0xfe, 0xad, 0xac, 0xab, 0xba}; //, 0xf2, 0x47, 0xba, 0x36 };
  //0x4f, 0xe2, 0x5d, 0x6c}; //endian
  //0x36, 0xba, 0x47, 0xf2 }; bit in byte revers 
  //0x6c, 0x5d, 0xe2, 0x4f};  //orig

  int[] telg2 = {
      0x54, 0x0d, 
      0,0,0,0,0,0
      , 0xaa, 0xc0 
      , 0xca, 0xfe 
      , 0xad, 0xac 
      , 0xaf, 0xfe 
      , 0x12, 0x34 
      , 0x23, 0x45 
      , 0xab, 0xba 
      ,  0x09, 0x77 
      ,  0x89, 0xab 
      ,  0x0, 0x0 
      ,  0xac, 0xca 
  };
  
  
  int[] telg3 = {
        0x000C //  <<= begin telg
      , 0xF6FF //
      , 0xA7FF //
      , 0x000A //
      , 0xCD30 //
      , 0xEB19 //
      , 0x0800 //
      , 0x4500 //
      , 0x002C //
      , 0x4580 //
      , 0x0000 //
      , 0x8011 //
      , 0x6D67 //
      , 0xC0A8 //
      , 0x0344 //
      , 0xC0A8 //
      , 0x0345 //
      , 0xA003 //
      , 0xA003 //
      , 0x0018 //
      , 0xCE0D //
      , 0x4865 //  <<= begin payload "Hallo "
      , 0x6C6C //
      , 0x6F20 //
      , 0x5544 //   <<== "UDP 28965\n"
      , 0x5020 //
      , 0x3238 //
      , 0x3936 //
      , 0x350A //
      , 0x0000 //  <<=== maybe fill word
//      , 0x0934 //  <== CRC as received  602c   9fd3
//      , 0x236D //                       c4b6   3b49
  };
  
  
  int[] telg4 = {
  0x000C  
, 0xF6FF  
, 0xA7FF  
, 0x000A  
, 0xCD30  
, 0xEB19  
, 0x0800  
, 0x4500  
, 0x0028  
, 0x8BCD  
, 0x0000  
, 0x8011  
, 0x271E  
, 0xC0A8  
, 0x0344  
, 0xC0A8  
, 0x0345  
, 0xA003  
, 0xA003  
, 0x0014  
, 0x3E84  
, 0x4865  
, 0x6C6C  
, 0x6F20  
, 0x5544  
, 0x5020  
, 0x300A  
, 0x0000  
, 0x0000  
, 0x0000  
//, 0x0261 //, 0x4086
//, 0xd435 //, 0x2bac

//, 0x6eff //, 0x76ff //, 0x8900 //, 0x9100 //, 0x4086  
//, 0x2505 //, 0xa4a0 //, 0x5b5f //, 0xdafa //, 0x2BAC  
  };
  

  int[] telg5 = {0x000A, 0xCD30, 0xEB19, 0x000C, 0xF6FF, 0xA7FF, 0x0800, 0x4500, 0x0028, 0xBAB8, 0x0000, 0x8011, 0xF832, 0xC0A8, 0x0345, 0xC0A8, 0x0344, 0xA003, 0xA003, 0x0014, 0x3E84, 0x4865, 0x6C6C, 0x6F20, 0x5544, 0x5020, 0x300A, 0x0000, 0x0000, 0x0000
  }; //, 0xC7DA, 0x4526
     
  
  
  int[][] telgs = {this.telg0, { 0x31}, this.telg2, this.telg3, this.telg4, this.telg5 };

  int[] crcs = { 0x6c5de24f, 0x83dcefb7, 0xfe36b8c1, 0x30dbf773, 0x8ba20129, 0x000};
                                        // 7f6c'1d83    //bit 0..7 receivedU
  int ixTelg = 5;

  
  /**
   * @param parent
   */
  public void test1 ( TestOrg parent ) {
    TestOrg test = new TestOrg("test1", 5, parent);
    
    int zBytes = 2* this.telgs[this.ixTelg].length;
    byte[] telgBytes = new byte[ zBytes+4];
    int ixBytes =0;
    for(int data : this.telgs[this.ixTelg]) { 
      telgBytes[ixBytes++] = (byte)((data>>8) & 0x00ff); 
      telgBytes[ixBytes++] = (byte)((data) & 0x00ff); 
    } 
    assert(ixBytes == zBytes);
    
    int crcTable = CRC32Table.crc32_byte(telgBytes, zBytes);
    
    test.expect(crcTable == this.crcs[this.ixTelg], 3, "genTable");  //01101100010111011110001001001111
                                                                //10110110110111000010110010000111
    
    Checksum genJava = new CRC32();
    genJava.reset();
//    for(int val: this.telgs[this.ixTelg]) { genJava.update(val); }
    for(ixBytes = 0; ixBytes < zBytes; ++ixBytes) { genJava.update(telgBytes[ixBytes]); }
    int crcJava = (int)genJava.getValue();
    test.expect(crcJava == this.crcs[this.ixTelg], 5, "genJava");
    
    CRC32bit genBit = new CRC32bit();
    int crcBit = genBit.stepByte(telgBytes, zBytes);
    test.expect(crcBit == this.crcs[this.ixTelg], 5, "genBit");
    int ix = zBytes;                                       // append the CRC into the buffer too
    int crcBitAppend = crcBit;
    //crcBit = ~crcBit;
    do {
      int crcByte = crcBitAppend >>24;
      byte crcRevers = 0;
      for(int ixb = 0; ixb < 8; ++ixb) { 
        crcRevers <<= 1; 
        if((crcByte & 1) !=0) { crcRevers |= 1; }
        crcByte >>>=1;
      }
      telgBytes[ix] = crcRevers; 
      //telgBytes[ix] = (byte)crcByte; 
      crcBitAppend <<= 8; 
    } while(++ix < telgBytes.length);
    int crcBitCrc = genBit.stepByte(telgBytes, zBytes+4);  //2cfd2b79
    test.expect(crcBitCrc == 0, 5, "genBit with CRC");     //check value 0x0xCBF43926 ??
    
    int crcBitMsb = genBit.stepByteMSB(telgBytes, zBytes);
    ix = zBytes;
    do { telgBytes[ix] = (byte) (crcBitMsb >>24); crcBitMsb <<= 8; } while(++ix < telgBytes.length);
    int crcBitMsbCrc = genBit.stepByteMSB(telgBytes, zBytes + 4);
    test.expect(crcBitMsbCrc == 0, 5, "stepByteMSB with CRC results in CRC=0");
    
    

    test.finish();
  }
  
  
  void testFillTable ( ) {
    CRC32Table genTable = new CRC32Table();
    genTable.crc32_fill(genTable.filledTable);
    Debugutil.stop();
  }
  
  
  public static void main (String[] args) {
    TestOrg test = new TestOrg("Test_crc32", 7, args);
    Test_crc32 thiz = new Test_crc32();
    thiz.testFillTable();
    thiz.test1(test);
    test.finish();
  }
}
