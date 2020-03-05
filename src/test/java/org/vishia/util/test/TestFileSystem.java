package org.vishia.util.test;

import java.io.File;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.vishia.util.FileSystem;
import org.vishia.util.StringFunctions;

public class TestFileSystem
{
  public static void main(String[] args){
    TestFileSystem thiz = new TestFileSystem();
    thiz.test_FileRead();
    test_normalizePath();
  }
  
  
  private static void test_normalizePath()
  {
    String[][] tests = {
      {"D:/test/test1/..", "D:/test"}
    , {"D:/test/test1/../..", "D:/"}
    , {"D:/test///././.././test1/..", "D:/"}
    , {"D:/test/..", "D:/"}
    };
    for(String[] test: tests){
      CharSequence s1 = FileSystem.normalizePath(test[0]);
      int cmpr = StringFunctions.compare(s1, test[1]);
      Assert.assertTrue(cmpr == 0);
    }
  }
  
  
  
  @Tag("teststd")
  @Test
  public void test_FileRead() {
    StringBuilder dst = new StringBuilder();
    List<File> files = new LinkedList<File>();
    FileSystem.addFileToList("src/main/java/org/vishia/util/**/*.java", files);
    int[] zdst = new int[1];
    boolean bExc = false;
    int failedLength = 0;
    long timeRead = 0;
    for(File file: files) {
      dst.setLength(0);
      zdst[0] = 0;
      long startTime = System.nanoTime();
      String error = FileSystem.readFile(file, dst, zdst);
      long readTime = System.nanoTime() - startTime;
      timeRead += readTime;
      if(error !=null) {
        System.err.println("Exception reading " + file.getAbsolutePath() + ": " + error);
        bExc = true;
      }
      if(zdst[0] == 0 || zdst[0] != dst.length()) {
        System.err.println("Length error reading " + file.getAbsolutePath());
        failedLength +=1;
      }
    }
    System.out.printf("Test FileSystem.readFile(file, dst, len) of all org.vishia.util: %f ms\n", timeRead*0.000001 );

    Assert.assertTrue(!bExc);
    Assert.assertTrue(failedLength == 0);
    
  }
  
  
  
}
