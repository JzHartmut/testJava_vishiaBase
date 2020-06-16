package org.vishia.minisys.test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.vishia.mainCmd.PrintStreamAdapter;
import org.vishia.mainCmd.PrintStreamBuffer;
import org.vishia.minisys.GetWebfile;
import org.vishia.util.FileSystem;

public class TestGetWebfile {

  boolean bOk = true;
  
  PrintStream outSys;
  
  @Test
  public void cmdGetWebfile() {
    System.out.println("==== cmdGetWebfile()");
    File dir = new File("build/testMinisys");
    dir.mkdirs();
    FileSystem.cleandir(dir);
    String[] args = { "@src/test/files/minisys/bomVishiaJava.txt", "build/testMinisys/"};
    StringBuilder outBuffer = new StringBuilder(1000);
    this.outSys = PrintStreamBuffer.replaceSystemOut(outBuffer);
    int exitcode = GetWebfile.smain(args);
    check(exitcode == 0, "load files from web");
    exitcode = GetWebfile.smain(args);
    check(exitcode == 1, "files from web exists already");
    //
    System.setOut(outSys);  //important, elsewhere all other check outputs are written to this buffer.
    System.out.println(outBuffer);
    //
    Assert.assertTrue(this.bOk);
  }
  
  
  
  private void check(boolean cond, String text) {
    if(cond) {
      outSys.println("  ok " + text);
    } else {
      outSys.println("  ERROR " + text);
      this.bOk = false;
    }
  }
  
}
