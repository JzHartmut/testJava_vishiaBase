package org.vishia.util.test;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.vishia.util.Debugutil;
import org.vishia.util.FileFunctions;
import org.vishia.util.FileSystem;
import org.vishia.util.StringFunctions;

public class TestFileSystem
{
  public static void main(String[] args){
    TestFileSystem thiz = new TestFileSystem();
    fileProperties();
    thiz.test_FileRead();
    test_normalizePath();
  }
  
  
  
  private static void fileProperties() {
    try {
      File file = new File("d:\\vishia\\Java\\Eclipse_Pj\\vishiaJavaL\\bin");
      boolean bExist = file.exists();
      Path pFile = FileSystems.getDefault().getPath("d:\\vishia\\Java\\Eclipse_Pj\\vishiaJavaL\\bin");
      Path linkedPath = pFile.toRealPath();
      BasicFileAttributeView attr = Files.getFileAttributeView(pFile, BasicFileAttributeView.class);
      Debugutil.stop();
    } catch(Throwable exc) {
      System.err.println(exc.getMessage());
    }
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
  
  /**Test routine with examples to test {@link #normalizePath(String)}. */
  public static void test_searchFile(){
    List<File> foundFile = new LinkedList<File>();
    boolean bOk = FileSystem.addFileToList("D:/**/vishia/**/srcJava_vishiaBase", foundFile);
    Assert.assertTrue(bOk);
  }  
  
  
  
  /**Test routine with examples to test {@link #normalizePath(String)}. */
  public static void test_addFilesWithBasePath(){
    CharSequence result;
    File dir = new File(".");  //it is the current dir, but without absolute path.
    File dirAbs = dir.getAbsoluteFile();
    File parent = FileSystem.getDir(dir);   //builds the real parent. It depends on the start directory of this routine.
    File grandparent = FileSystem.getDir(parent);
    String sParent = parent.getPath().replace("\\", "/");  //the path
    int posSlash = sParent.lastIndexOf('/');
    String sNameParent = sParent.substring(posSlash +1);
    String searchPath = sNameParent + "/**/*";
    List<FileFunctions.FileAndBasePath> files = new ArrayList<FileFunctions.FileAndBasePath>();
    FileSystem.addFilesWithBasePath(grandparent, searchPath, files);
    searchPath = sNameParent + "/..:**/*";
    files.clear();
    FileSystem.addFilesWithBasePath(parent, searchPath, files);
  }  
  
  
  
  /**Test routine with examples to test {@link #normalizePath(String)}. */
  public static void test_normalizePath2(){
    CharSequence result;
    result = FileSystem.normalizePath("../path//../file"); 
    assert(result.toString().equals( "../file"));
    result = FileSystem.normalizePath("../path/file/."); 
    assert(result.toString().equals( "../path/file"));
    result = FileSystem.normalizePath("../path/../."); 
    assert(result.toString().equals( ".."));
    result = FileSystem.normalizePath("../path//../file"); 
    assert(result.toString().equals( "../file"));
    result = FileSystem.normalizePath("..\\path\\\\..\\file"); 
    assert(result.toString().equals( "../file"));
    result = FileSystem.normalizePath("/../path//../file"); 
    assert(result.toString().equals( "/../file"));  //not for praxis, but correct
    result = FileSystem.normalizePath("./path//file/"); 
    assert(result.toString().equals( "path/file/"));    //ending "/" will not deleted.
    result = FileSystem.normalizePath("path/./../file"); 
    assert(result.toString().equals( "file"));       

    File dir = new File(".");  //it is the current dir, but without absolute path.
    File dirAbs = dir.getAbsoluteFile();
    File parent = dir.getParentFile();  assert(parent == null);  //property of java.io.File
    parent = FileSystem.getDir(dir);   //builds the real parent. It depends on the start directory of this routine.
    String sParent = parent.getPath().replace("\\", "/");  //the path
    int posSlash = sParent.lastIndexOf('/');
    String sNameParent = sParent.substring(posSlash +1);
    File fileTest = new File(sParent + "/..",sNameParent); //constructed filecontains "/../" in its path
    CharSequence sFileTest = FileSystem.normalizePath(fileTest.getAbsolutePath());
    CharSequence sDirAbs = FileSystem.normalizePath(dirAbs.getAbsolutePath());
    assert(StringFunctions.equals(sFileTest,sDirAbs));
  }
  

  
}
