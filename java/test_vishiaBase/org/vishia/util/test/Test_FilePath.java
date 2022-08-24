package org.vishia.util.test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.vishia.testBase.TestJava_vishiaBase;
import org.vishia.util.FileFunctions;
import org.vishia.util.FilePath;
import org.vishia.util.TestOrg;

public class Test_FilePath
{

  /**This is the implementation of a interface to access somewhat from the environment of FilePath-routines.
   * It is used inside {@link org.vishia.jztxtcmd.JZtxtcmd}.
   * It is possible to access any variable in an filepath, that is used in test here. 
   * There are 2 variables written in the container, "d-base-local" and "local"
   */
  FilePath.FilePathEnvAccess env = new FilePath.FilePathEnvAccess(){

    Map<String, Object> container = new TreeMap<String, Object>();
    { fillContainerEnv();
    }
    
    void fillContainerEnv(){
      FilePath p1 = new FilePath("d:/varbase/path:varlocal/path/");
      this.container.put("d-base-local", p1);
      p1 = new FilePath("varlocal/path/");
      this.container.put("local", p1);
    }
    
    @Override public Object getValue(String variable) throws NoSuchFieldException
    { return this.container.get(variable); }
    
    @Override public CharSequence getCurrentDir(){ return "D:/test/currentdir"; }
  };

  
  FilePath pDriveAbsBaseLocalNameExt = new FilePath("d:/base/path:local/path/name.ext");   
  FilePath pName = new FilePath("name");   
  FilePath pNameExt = new FilePath("name.ext");   
  FilePath pLocalNameExt = new FilePath("local/path/name");   
  FilePath pRelbaseLocalNameExt = new FilePath("base/path:local/path/name.name2.ext");   
  FilePath pDriveRellocalNameExt = new FilePath("d:local/path.name");   
  FilePath pDriveAbsPathNameExt = new FilePath("d:/local/path.name");   
  FilePath pDriveRelbaseLocalNameExt = new FilePath("d:base/path:local/path.name.ext");   
  FilePath pDriveAbsbaseNameExt = new FilePath("d:/base/path:name.ext");   
  FilePath pVariable = new FilePath("&variable");   
  FilePath pVariableBaseNameExt = new FilePath("&variable/base/path:name.ext");   
  
  FilePath pVarBaseLocal_LocalNameExt = new FilePath("&d-base-local/local/path/name.ext");
  FilePath pVarBaseLocal_Base_LocalNameExt = new FilePath("&d-base-local:local/path/name.ext");
  
  FilePath pVarBaseLocal_Base_NameExt = new FilePath("&d-base-local:name.ext");

  FilePath pVar_NameExt = new FilePath("&variable/name.ext");   
  FilePath pVar_Base_LocalNameExt = new FilePath("&variable/base/path:local/path/name.ext");   

  
  

  
  //@Test @Tag("teststd")
  void check_DriveAbsBaseLocalNameExt(TestOrg parent) {
    String testPath = "d:/base/path:local/path/name.ext";
    TestOrg test = new TestOrg("check getting all components from a path " + testPath, 2, parent);
    FilePath fp = new FilePath(testPath);   
    try {
      test.expect(fp.localdir(null), "local/path", 7, "localdir()");
      test.expect(fp.absbasepath(null), "d:/base/path", 7, "absbasepath()");
      test.expect(fp.localnameW(null), "local\\path\\name", 7, "localnameW()");
      test.expect(fp.localname(null), "local/path/name", 7, "localname()");
      test.expect(fp.localfile(null), "local/path/name.ext", 7, "localfile()");
    } catch(NoSuchFieldException exc) {
      test.exception(exc);
    }
    test.finish();
  }
  
  
  
  /**This test uses real existing files. It assumes that the current dir is the testProject root dir
   * to gradle standard. It means the java files are relative able to found in the given path. 
   * For manually test select the correct directory where build.gradle is stored.
   * @throws NoSuchFieldException
   */
  //@Test @Tag("teststd")
  void testExpand(TestOrg parent) throws NoSuchFieldException {
    TestOrg test = new TestOrg("FileFunctions.testExpand", 2, parent);
    //showTestoper();
    String sWildcardPath = "src/main/java/srcJava_vishiaBase:org/**/*.java"; //all java files in this SBOX
    FilePath pathWildcard = new FilePath(sWildcardPath); 
    File checkdir = new File(pathWildcard.absbasepath(null).toString());
    if(!(checkdir.exists())) {
      System.err.println("check testExpand cannot executed, change to an existing directory.");
    }
    else {
      List<FilePath> dst = new LinkedList<FilePath>();
      pathWildcard.expandFiles(dst, null, null, null);
      test.expect(dst.size() > 100, 4, sWildcardPath + " contains more as 100 files");
    }
    test.finish();
  }
  
  
  void testReplWildcard(TestOrg parent) throws NoSuchFieldException {
    TestOrg test = new TestOrg("testReplWildcard", 2, parent);
    showTestoper();
    String sWildcardPath = "D:/vishia/ZBNF/srcJava_Zbnf:org/**/*.java";
    FilePath pathWildcard = new FilePath(sWildcardPath); 
    File checkdir = new File(pathWildcard.absbasepath(null).toString());
    if(!(checkdir.exists())) {
      System.err.println("check testExpand cannot executed, change to an existing directory.");
    }
    else {
      FilePath pathRepl = new FilePath("path/exmpl.java");
      CharSequence res = pathWildcard.absfileReplwildcard(pathRepl, null);
      test.expect(res, "D:/vishia/ZBNF/srcJava_Zbnf/org/path/exmpl.java" , 6, "replacing");
      //check("D:/vishia/ZBNF/srcJava_Zbnf/org/path/exmpl.java".contentEquals(res));  //should contain a lot of files, more than 300, test is ok if some files are found.
    }
    test.finish();
  }
  
  
  private void showTestoper() {
    //System.out.print(Assert.stackInfo("Test: ", 2, 1));
  }
  
  /**Manual test as Java-application.
   * Use as currend directory: ${workspace_loc:testJava_vishiaBase}/../../..
   * No further arguments.
   * @param noArgs
   */
  public static void main(String[] args){ 
    TestJava_vishiaBase.setCurrDir_TestJava_vishiaBase();
    TestOrg test = new TestOrg("Test_FilePath", 1, args); //    g("Test_FilePath", args);
    Test_FilePath main = new Test_FilePath();
    try {
      main.check_DriveAbsBaseLocalNameExt(test);
      main.testReplWildcard(test);
      main.testExpand(test);
    } catch(Exception exc) {  //all exceptions are unexpected. Not used for regular problems.
      test.exception(exc);
      //System.err.println("unexpected: " + exc.getMessage());
      //exc.printStackTrace(System.err);
    }
    test.finish();
  }
  
}
