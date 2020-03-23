package org.vishia.util.test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.vishia.util.CheckVs;
import org.vishia.util.FilePath;
import org.vishia.util.StringFunctions;

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

  
  private boolean ok = true;
  
  private void expect(CharSequence s1, CharSequence s2) {
    int ok = StringFunctions.compare(s1, s2);
    if(ok != 0) {
      CharSequence msg = CheckVs.stackInfo("", 2, 1);
      CheckVs.consoleErr("error @: %s: %s != %s", msg, s1, s2);
      this.ok = false;
    }
  }
  
  public void test(){
    
    showTestoper();
    StringBuilder buf = new StringBuilder();
    CharSequence file, basepath, localdir, localfile;
    try{
      localdir = this.pLocalNameExt.localdir(buf, null, null, this.env);
      basepath = this.pVarBaseLocal_LocalNameExt.localdir(this.env);
      localdir = this.pVarBaseLocal_LocalNameExt.localdir(this.env);
      localfile = this.pVarBaseLocal_LocalNameExt.localfile(this.env);
      check(StringFunctions.equals(localdir, "varlocal/path/local/path"));
      
      localdir = this.pVarBaseLocal_Base_LocalNameExt.localdir(this.env);
      check(StringFunctions.equals(localdir, "local/path"));
      
      localdir = this.pVarBaseLocal_Base_NameExt.localdir(this.env);
      check(StringFunctions.equals(localdir, "."));
      
    } catch(NoSuchFieldException exc){
      System.err.println(exc.getMessage());
    }
  }


  
  @Test @Tag("teststd")
  void check_DriveAbsBaseLocalNameExt() {
    FilePath fp = new FilePath("d:/base/path:local/path/name.ext");   
    this.ok = true;
    try {
      expect(fp.localnameW(null), "local\\path\\name");
      expect(fp.localname(null), "local/path/name");
      expect(fp.localfile(null), "local/path/name.ext");
    } catch(NoSuchFieldException exc) {
      Assert.assertTrue(true);  //should never thrown, it does not access local variables  
    }
    Assert.assertTrue(this.ok);
  }
  
  
  
  /**This test uses real existing files. It assumes that the current dir is the testProject root dir
   * to gradle standard. It means the java files are relative able to found in the given path. 
   * For manually test select the correct directory where build.gradle is stored.
   * @throws NoSuchFieldException
   */
  @Test @Tag("teststd")
  void testExpand() throws NoSuchFieldException {
    showTestoper();
    String sWildcardPath = "src/main/java:org/**/*.java"; //all java files.
    FilePath pathWildcard = new FilePath(sWildcardPath); 
    File checkdir = new File(pathWildcard.absbasepath(null).toString());
    if(!(checkdir.exists())) {
      System.err.println("check testExpand cannot executed, change to an existing directory.");
      Assert.assertTrue(false);
    }
    else {
      List<FilePath> dst = new LinkedList<FilePath>();
      pathWildcard.expandFiles(dst, null, null, null);
      Assert.assertTrue(dst.size() > 100);  //should contain a lot of files, more than 300, test is ok if some files are found.
    }
  }
  
  
  void testReplWildcard() throws NoSuchFieldException {
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
      check("D:/vishia/ZBNF/srcJava_Zbnf/org/path/exmpl.java".contentEquals(res));  //should contain a lot of files, more than 300, test is ok if some files are found.
    }
  }
  
  
  private static void check(boolean ok) {
    org.junit.Assert.assertTrue(ok);
    if(!ok) {
      //System.err.println(Assert.stackInfo("Test error in: ", 2, 1));
    }
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
    Test_FilePath main = new Test_FilePath();
    try {
      main.check_DriveAbsBaseLocalNameExt();
      main.testReplWildcard();
      main.testExpand();
      main.test(); 
    } catch(Exception exc) {  //all exceptions are unexpected. Not used for regular problems.
      System.err.println("unexpected: " + exc.getMessage());
      exc.printStackTrace(System.err);
    }
  }
  
}
