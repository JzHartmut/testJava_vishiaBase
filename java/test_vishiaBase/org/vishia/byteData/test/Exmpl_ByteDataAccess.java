package org.vishia.byteData.test;

import org.vishia.byteData.ByteDataAccessBase;

public class Exmpl_ByteDataAccess extends ByteDataAccessBase {

  private final static int 
    kPosF1 = 0
  , kPosF2 = 4
  , kPosI1 = 8
  , kPosI2 = 0xa
  , kSizeof = 0x10;

  
  
  
  public Exmpl_ByteDataAccess(byte[] data) {
    super(kSizeof);
    assert(data.length >= kSizeof);
    assign(data);
  }

  
  public void float1(float val) { super.setFloat(kPosF1, val); }
  
  
}
