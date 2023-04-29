package org.vishia.testBase;

import java.io.File;

import org.vishia.util.FileFunctions;
import org.vishia.util.test.Test_all_util;

public class TestJava_vishiaBase {

  public static void main(String[] noArgs){ 
    setCurrDir_TestJava_vishiaBase();
    Test_all_util.main(noArgs);
  }  

  
  
  
  public static boolean setCurrDir_TestJava_vishiaBase() {
    //assume that the project is inside the testJava_vishiaBase tree;
    File basedir = new File(".").getAbsoluteFile();
    while( basedir !=null && !(new File(basedir, "src/srcJava_vishiaBase/java/org/vishia").exists())) {
      basedir = basedir.getParentFile();
    }
    if(basedir !=null) {
      System.setProperty("user.dir", basedir.getAbsolutePath());
      return true;
    }
    final String currdir2 = System.getProperty("user.dir");
    final File testfile2 = new File(currdir2, "src/srcJava_vishiaBase/java/org/vishia");
    if(testfile2.exists()) return true;
    
    //to set the current dir to a determined directory the only way is to produce a file with that directory
    // on a known path. This is done by call of +setWDtoTmp.bat or +setWDtoTmp.sh in this SBOX.
    //The following routine reads this file and sets the current dir for the tests.
    FileFunctions.setCurrdirFromFile("/tmp/WD_cmpnJava_vishiaBase.var");
    final String currdir3 = System.getProperty("user.dir");
    final File testfile3 = new File(currdir3, "src/srcJava_vishiaBase/java/org/vishia");
    final boolean bOk = testfile3.exists();
    if(!bOk) {
      System.err.print("The current directory is not the root of TestJava_vishiaBase\n");
      System.err.print("  either start test from the correct current dir\n");
      System.err.print("  or call \"src/buildScripts/+setWDtoTmp.sh\" or \"...bat\" \n");
    }
    return bOk;
  }
  
}
