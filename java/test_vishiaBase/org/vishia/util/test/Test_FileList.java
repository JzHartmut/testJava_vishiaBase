package org.vishia.util.test;

import org.vishia.util.FileFunctions;
import org.vishia.util.FileList;

public class Test_FileList {

  
  public static void main(String[] argsNotUsed) {
    //to set the current dir to a determined directory the only way is to produce a file with that directory
    // on a known path. This is done by call of +setWDtoTmp.bat or +setWDtoTmp.sh in this SBOX.
    //The following routine reads this file and sets the current dir for the tests.
    FileFunctions.setCurrdirFromFile("$(TMP)/WD_cmpnJava_vishiaBase.var");
    
    String[] args =  {"L", "-l:$(TMP)/.filestest", "-d:src", "-m:**/java/**/test*/*.java"};
    FileList.main(args);
  }
}
