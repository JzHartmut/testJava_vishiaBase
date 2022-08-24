package org.vishia.util.test;


import java.io.IOException;

import org.vishia.util.FileWriter;
import org.vishia.util.StringFormatter;


public class WriteUTF8 {

  public static void main(String[] args) {
    writeUTF8();
  }
  
  
  
  public static void writeUTF8() {
    FileWriter f = new FileWriter();
    try {
      if(f.open("/tmp/UTF-8.txt", "UTF-8", false) ==0) {
        
        int code = 0x20;
        StringFormatter sf = new StringFormatter(100);
        while(code < 0x10000) {
          //char cc = (char)code;
          sf.addHex(code, 4);
          for(int ix = 0; ix < 16; ++ix) {
            sf.add("  ").add((char)(code +ix));
          }
          sf.add('\n');
          code += 16;
          f.write(sf.toString());
          sf.reset();
        }
        sf.close();
      }
      f.close();
    } catch(IOException exc) {
      System.err.println(exc.getMessage());
    }
  }
}


