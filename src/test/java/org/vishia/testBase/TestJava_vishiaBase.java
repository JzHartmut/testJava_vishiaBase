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
    final File currdir = new File(".").getAbsoluteFile().getParentFile();
    final File testfile = new File(currdir, "src/main/java/srcJava_vishiaBase/_make");
    final String currdir2 = System.getProperty("user.dir");
    final File testfile2 = new File(currdir2, "src/main/java/srcJava_vishiaBase/_make");
    if(testfile.exists() && testfile2.exists()) return true;
    
    //to set the current dir to a determined directory the only way is to produce a file with that directory
    // on a known path. This is done by call of +setWDtoTmp.bat or +setWDtoTmp.sh in this SBOX.
    //The following routine reads this file and sets the current dir for the tests.
    FileFunctions.setCurrdirFromFile("$(TMP)/WD_cmpnJava_vishiaBase.var");
    final String currdir3 = System.getProperty("user.dir");
    final File testfile3 = new File(currdir3, "src/main/java/srcJava_vishiaBase/_make");
    final boolean bOk = testfile3.exists();
    if(!bOk) {
      System.err.println("The current directory is not the root of TestJava_vishiaBase\n");
      System.err.println("  either start test from the correct current dir\n");
      System.err.println("  or call \"src/buildScripts/+setWDtoTmp.sh\" or \"...bat\" \n");
    }
    return bOk;
  }
  
}
