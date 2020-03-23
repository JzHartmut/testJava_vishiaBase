package org.vishia.util.test;

import org.junit.jupiter.api.Test;
import org.vishia.zip.Zip;

public class Test_Zip {

  
  
  @Test
  public void testJar() {
    String[] args = {
      "-o:$$TMP$/test_Zip_testJar.zip"
    , "-manifest:src/main/java/_make/vishiaBase.manifest"
    , "-sort"
    , "-time:2020-03-23+12:34"
    , "src/main/java:**.*.java"  //
    , "src/main/java/_make:*"  //
    };
    Zip.smain(args);
  }
  
  
  public static void main(String[] args) {
    Test_Zip thiz = new Test_Zip();
    thiz.testJar();
  }
}
