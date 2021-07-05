package org.vishia.util.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

//import org.junit.jupiter.api.Test;
import org.vishia.util.FileFunctions;
import org.vishia.zip.Zip;

public class Test_Zip {

  
  
//  @Test
  public void testJar() {
    
    try {
      FileFunctions.setCurrdirFromFile("$(TMP)/WD_cmpnJava_vishiaBase.var");
      File currdir = new File(".");
      File currdirAbs = currdir.getAbsoluteFile();
      System.out.println("currdir = " + currdirAbs.getPath());
      String[] args = {
        "-o:$$TMP$/test_Zip_testJar.zip"
      , "-manifest:src/main/java/_make/vishiaBase.manifest"
      , "-sort"
      , "-time:2020-03-23+12:34"
      , "src/main/java:**/*.java"  //
      , "src/main/java/_make:*"  //
      };
      Zip.smain(args);
      InputStream inzip = null;
      try {
        inzip = new FileInputStream("T:/tmp/test_Zip_testJar.zip");
        Zip.unzip(inzip);
      
      } catch(Exception exc) {
        System.err.println(exc.getMessage());
      }
      finally {
        if(inzip !=null) { inzip.close(); }
      }
    } catch(Exception exc1) {
      System.out.println("  ERROR: unexpected Excpetion: " + exc1.getMessage());
    }
  }
  
  
  public static void main(String[] args) {
    Test_Zip thiz = new Test_Zip();
    thiz.testJar();
  }
}
