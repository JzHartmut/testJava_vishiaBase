package org.vishia.util.test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
import org.vishia.testOrg.TestOrg;
import org.vishia.util.FileFunctions;
import org.vishia.util.FilePath;

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
  void check_DriveAbsBaseLocalNameExt() {
    String testPath = "d:/base/path:local/path/name.ext";
    TestOrg test = new TestOrg("check getting all components from a path " + testPath);
    FilePath fp = new FilePath(testPath);   
    try {
      test.expect(fp.localdir(null), "local/path", false, "localdir()");
      test.expect(fp.absbasepath(null), "d:/base/path", false, "absbasepath()");
      test.expect(fp.localnameW(null), "local\\path\\name", false, "localnameW()");
      test.expect(fp.localname(null), "local/path/name", false, "localname()");
      test.expect(fp.localfile(null), "local/path/name.ext", false, "localfile()");
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
  void testExpand() throws NoSuchFieldException {
    TestOrg test = new TestOrg("FileFunctions.testExpand");
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
      test.expect(dst.size() > 100, true, sWildcardPath + " contains more as 100 files");
    }
    test.finish();
  }
  
  
  void testReplWildcard() throws NoSuchFieldException {
    TestOrg test = new TestOrg("testReplWildcard");
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
      test.expect(res, "D:/vishia/ZBNF/srcJava_Zbnf/org/path/exmpl.java" , false, "faulty replacing");
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
  public static void main(String[] noArgs){ 
    //to set the current dir to a determined directory the only way is to produce a file with that directory
    // on a known path. This is done by call of +setWDtoTmp.bat or +setWDtoTmp.sh in this SBOX.
    //The following routine reads this file and sets the current dir for the tests.
    FileFunctions.setCurrdirFromFile("$(TMP)/WD_cmpnJava_vishiaBase.var");
    TestOrg test = new TestOrg("Test_FilePath");
    Test_FilePath main = new Test_FilePath();
    try {
      main.check_DriveAbsBaseLocalNameExt();
      main.testReplWildcard();
      main.testExpand();
    } catch(Exception exc) {  //all exceptions are unexpected. Not used for regular problems.
      test.exception(exc);
      //System.err.println("unexpected: " + exc.getMessage());
      //exc.printStackTrace(System.err);
    }
    test.finish();
  }
  
}
