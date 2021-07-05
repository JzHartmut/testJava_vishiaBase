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

import org.vishia.testBase.TestJava_vishiaBase;
import org.vishia.util.Debugutil;
import org.vishia.util.FileFunctions;
import org.vishia.util.FileSystem;
import org.vishia.util.StringFunctions;
import org.vishia.util.TestOrg;

public class TestFileFunctions
{
  public static void main(String[] args){
    TestJava_vishiaBase.setCurrDir_TestJava_vishiaBase();
    TestOrg test = new TestOrg("Test FileFunctions", 3, args);
    TestFileFunctions thiz = new TestFileFunctions();
    test_GetDir(test);
    fileProperties();
    thiz.test_FileRead();
    test_normalizePath(test);
    test_addFilesWithBasePath(test);
    test.finish();
  }
  
  
  public static void test_GetDir(TestOrg parent) {
    TestOrg test = new TestOrg("Test GetDir", 7, parent);
  
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
      CharSequence s1 = FileFunctions.normalizePath(test[0]);
      int cmpr = StringFunctions.compare(s1, test[1]);
    }
  }
  
  
  
  public void test_FileRead() {
    StringBuilder dst = new StringBuilder();
    List<File> files = new LinkedList<File>();
    FileFunctions.addFileToList("src/main/java/org/vishia/util/**/*.java", files);
    int[] zdst = new int[1];
    boolean bExc = false;
    int failedLength = 0;
    long timeRead = 0;
    for(File file: files) {
      dst.setLength(0);
      zdst[0] = 0;
      long startTime = System.nanoTime();
      String error = FileFunctions.readFile(file, dst, zdst);
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
    System.out.printf("Test FileFunctions.readFile(file, dst, len) of all org.vishia.util: %f ms\n", timeRead*0.000001 );

    //Assert.assertTrue(!bExc);
    //Assert.assertTrue(failedLength == 0);
    
  }
  
  /**Test routine with examples to test {@link #normalizePath(String)}. */
  public static void test_searchFile(){
    List<File> foundFile = new LinkedList<File>();
    boolean bOk = FileFunctions.addFileToList("D:/**/vishia/**/srcJava_vishiaBase", foundFile);
    //Assert.assertTrue(bOk);
  }  
  
  
  
  /**Test routine for {@link FileFunctions#addFilesWithBasePath(File, String, List)} */
  public static void test_addFilesWithBasePath(TestOrg testParent){
    CharSequence result;
    TestOrg test = new TestOrg("test_addFilesWithBasePath", 6, testParent);
    int nVerbose = 7;
    File dirBase = new File(".");                // it is the current dir for test, should be .../testJava_vishiaBase
    CharSequence cDirBase = FileFunctions.normalizePath(dirBase);  //now we have the absolute normalized path. 
    String sDirBase = cDirBase.toString();       // normalizePath returns a CharSequence to spare effort, now it is persistent.
    List<FileFunctions.FileAndBasePath> files = new ArrayList<FileFunctions.FileAndBasePath>();
    String searchPathLocal = "src/test/jztc/..:**/*";  //should search files only in jztz but path incl. jztc
    String searchPath = sDirBase + '/' + searchPathLocal;
    FileFunctions.addFilesWithBasePath(null, searchPath, files);
    test.expect(files.size()>=1, nVerbose, "number of files in /src/test/jztc >=1");
    for(FileFunctions.FileAndBasePath file: files) {
      test.expect(file.localPath.startsWith("jztc"), nVerbose+1, "localpath jztc?: " + file.localPath);
    }
    files.clear();
    FileFunctions.addFilesWithBasePath(dirBase, searchPathLocal, files);
    test.expect(files.size()>=1, nVerbose, "number of files in /src/test/jztc >=1");
    
    files.clear();
    searchPathLocal = "src/test:jztc/**/*";  //should search files only in jztz but path incl. jztc
    searchPath = sDirBase + '/' + searchPathLocal;
    FileFunctions.addFilesWithBasePath(null, searchPath, files);
    test.expect(files.size()>=1, nVerbose, "number of files in /src/test/jztc >=1");
    for(FileFunctions.FileAndBasePath file: files) {
      test.expect(file.localPath.startsWith("jztc"), nVerbose+1, "localpath jztc?: " + file.localPath);
    }
    files.clear();
    FileFunctions.addFilesWithBasePath(dirBase, searchPathLocal, files);
    test.expect(files.size()>=1, nVerbose, "number of files in /src/test/jztc >=1");
    
    
//    int posSlash = sDirBase.lastIndexOf('/');
//    String sNameParent = sDirBase.substring(posSlash +1);  //It 
//    searchPath = sNameParent + "/**/*";
//    File dirBaseParent = dirBase.getParentFile(); //it does not work, because the file is "."
//    dirBaseParent = FileFunctions.getDir(dirBase);// it works in any case
//    FileFunctions.addFilesWithBasePath(dirBaseParent, searchPath, files);
//    searchPath = sNameParent + "/..:**/*";       // This means the whole file tree of the parent
//    files.clear();
//    FileFunctions.addFilesWithBasePath(dirBase, searchPath, files);
    test.finish();
  }  
  
  
  
  /**Test routine with examples to test {@link #normalizePath(String)}. */
  public static void test_normalizePath(TestOrg testParent){
    CharSequence result;
    int nVerbose = 7;
    boolean bOk = false;
    String[][] sTests = 
      { { "../path//../file",      "../file" }          //0
      , { "../path/file/.",        "../path/file" }     //1
      , { "../path/../.",          ".." }
      , { "../path//../file",      "../file" }
      , { "../path//../**/file",      "../**/file" }
      , { "..\\path\\\\..\\file",  "../file" }          //4
      , { "/../path//../file",     "/../file" }
      , { "./path//file/",         "path/file/" }
      , { "path/./../file",        "file" }
      , { "D:/test/test1/..",      "D:/test"}           //8
      , { "D:/test/test1/../..",   "D:/"}
      , { "D:/test///././.././test1/..", "D:/"}
      , { "D:/test/..",            "D:/"}
      , { "path/file/..:sub/**/*.java" , "path:sub/**/*.java" }  //12
      , { "path/file/..:**/*.java" , "path:file/**/*.java" }  //13
      , { "path/file/.:**/*.java"  , "path/file:**/*.java" }  //14
      , { "path/file:./**/*.java"  , "path/file:**/*.java" }  //15
      , { "path/..:file", "file" }
      , { "", "" }
      , { "", "" }
      , { "", "" }
        };
    TestOrg test = new TestOrg("test_normalizePath", 6, testParent);
    int ixCheckManual = -1;
    if(ixCheckManual >=0) {
      result = FileFunctions.normalizePath( sTests[ixCheckManual][0]); 
      bOk = StringFunctions.equals(result, sTests[ixCheckManual][1]);
    }
    for(String[] sTest : sTests) {
      result = FileFunctions.normalizePath( sTest[0]); 
      bOk = StringFunctions.equals(result, sTest[1]);
      String msg = bOk ? sTest[0] + " => " + sTest[1] : sTest[0] + " /=> " + sTest[1] + " => " + result;
      test.expect(bOk, nVerbose, msg );
    }
    
    //Application: get the absolute path from :
    File dir = new File(".");  //it is the current dir, but without absolute path.
    File parent = dir.getParentFile();  assert(parent == null);  //property of java.io.File
    File dirAbs = dir.getAbsoluteFile();   //java.io.File delivers /path\.
    parent = FileFunctions.getDir(dir);   //builds the real parent. It depends on the start directory of this routine.
    String sParent = parent.getPath().replace("\\", "/");  //the path
    int posSlash = sParent.lastIndexOf('/');
    String sNameParent = sParent.substring(posSlash +1);
    File fileTest = new File(sParent + "/..",sNameParent); //constructed filecontains "/../" in its path
    CharSequence sFileTest = FileFunctions.normalizePath(fileTest.getAbsolutePath());
    CharSequence sDirAbs = FileFunctions.normalizePath(dirAbs.getAbsolutePath());
    test.expect(sFileTest, sParent, nVerbose, fileTest +  " => " + sFileTest );
    //assert(StringFunctions.equals(sFileTest,sDirAbs));
    test.finish();
  }
  

  
}
