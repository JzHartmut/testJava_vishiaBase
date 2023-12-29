package org.vishia.util.test.outTextPreparer;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;

import org.vishia.util.OutTextPreparer;
import org.vishia.util.TestOrg;
import org.vishia.util.Writer_Appendable;

public class Test_OutTextPreparerScript {
  
  
  /**Version, history and license.
   * <ul>
   * <li>2023-06-18: frame <:otx:args>...<.otx> 
   * <li>2023-06-18: test nested <:if>...<.if> 
   * <li>2023-05-: Creation for test with script
   * </ul>
   * 
   * <b>Copyright/Copyleft</b>:
   * For this source the LGPL Lesser General Public License,
   * published by the Free Software Foundation is valid.
   * It means:
   * <ol>
   * <li> You can use this source without any restriction for any desired purpose.
   * <li> You can redistribute copies of this source to everybody.
   * <li> Every user of this source, also the user of redistribute copies
   *    with or without payment, must accept this license for further using.
   * <li> But the LPGL is not appropriate for a whole software product,
   *    if this source is only a part of them. It means, the user
   *    must publish this part of source,
   *    but don't need to publish the whole source of the own product.
   * <li> You can study and modify (improve) this source
   *    for own using or for redistribution, but you have to license the
   *    modified sources likewise under this LGPL Lesser General Public License.
   *    You mustn't delete this Copyright/Copyleft inscription in this source file.
   * </ol>
   * If you are intent to use this sources without publishing its usage, you can get
   * a second license subscribing a special contract with the author. 
   * 
   * @author Hartmut Schorrig = hartmut.schorrig@vishia.de
   */
  public static final String version = "2023-06-18";


  /**To compare the test result. This text will be produced. */
  static String resultExpected = 
    "Text: any test text \n" 
  + "Test Call colors1: Colors-1: white, yellow, red, blue, green END\n"
  + "Test Call colors2: Colors-2: cyan, magenta, gray, black END\n"
  + "Test Call colors1: Colors-1a: wh, ye, unknown, unknown, green or gray:green: green END\n"
  + "Test Call colors1: Colors-2a: a-type-cyan, a-type-magenta, green or gray:gray, black END\n";

  
  public void testScriptCall ( TestOrg parent ) {
    TestOrg test = new TestOrg("test script with <:call:..>", 2, parent);
    InputStream inTpl = null;
    Map<String, Object> otps = new TreeMap<String, Object>();
    OutTextPreparer mainOtx = null;
    try {
      inTpl = Test_OutTextPreparerScript.class.getResourceAsStream("templateColorCall.otxt");  //pathInJar with slash: from root.
      
      mainOtx = OutTextPreparer.readTemplateCreatePreparer(inTpl, "===", AccessClass.class, otps, "otxCall");
      inTpl.close(); inTpl = null;
    } catch(Exception exc) {
      CharSequence sExc = org.vishia.util.ExcUtil.exceptionInfo("unexpected", exc, 0, 10);
      System.err.println(sExc);
    } finally { 
      if(inTpl !=null) { try{ inTpl.close(); } catch(IOException exc) { throw new RuntimeException("cannot close ressource" + exc.getMessage()); } }
    }
    ExampleDataClass data = new ExampleDataClass();
    AccessClass data2 = new AccessClass(); data2.exmplData = "accessClass";
    Writer_Appendable sb = new Writer_Appendable(new StringBuilder(500));
    try {
      OutTextPreparer.DataTextPreparer vars = mainOtx.createArgumentDataObj();
      vars.setArgument("dataColor", data);        //The data class for access.
      vars.setArgument("text1", "any test text");
      vars.setExecObj(data2);
      mainOtx.exec(sb, vars);
    } catch(Exception exc) {
      CharSequence sExc = org.vishia.util.ExcUtil.exceptionInfo("unexpected", exc, 0, 10);
      System.err.println(sExc);
    }    
    test.expect(sb.getContent(), resultExpected, 7, "Test_OutTextPreparer_CallFor:testCall()");
    test.finish();

    try{ sb.close();} catch(IOException exc) { throw new RuntimeException("cannot close", exc); }
    
    
  }
  
  
  /**Test of any script whether it is processable. 
   * This is for manually test of user scripts.
   * @param parent Test
   * @param byClass Class where the script in jar is able to find 
   * @param name Name of the script
   */
  public static void testScript_B ( TestOrg parent, Class<?> byClass, String name ) {
    TestOrg test = new TestOrg("test script with <:call:..>", 2, parent);
    InputStream inTpl = null;
    Map<String, Object> otps = new TreeMap<String, Object>();
    OutTextPreparer mainOtx = null;
    try {
      inTpl = byClass.getResourceAsStream(name);  //pathInJar with slash: from root.
      
      mainOtx = OutTextPreparer.readTemplateCreatePreparer(inTpl, "===", AccessClass.class, otps, "otxCall");
      inTpl.close(); inTpl = null;
    } catch(Exception exc) {
      CharSequence sExc = org.vishia.util.ExcUtil.exceptionInfo("unexpected", exc, 0, 10);
      System.err.println(sExc);
    } finally { 
      if(inTpl !=null) { try{ inTpl.close(); } catch(IOException exc) { throw new RuntimeException("cannot close ressource" + exc.getMessage()); } }
    }
    ExampleDataClass data = new ExampleDataClass();
    AccessClass data2 = new AccessClass(); data2.exmplData = "accessClass";
    Writer_Appendable sb = new Writer_Appendable(new StringBuilder(500));
    try {
      OutTextPreparer.DataTextPreparer vars = mainOtx.createArgumentDataObj();
      vars.setArgument("dataColor", data);        //The data class for access.
      vars.setArgument("text1", "any test text");
      vars.setExecObj(data2);
      mainOtx.exec(sb, vars);
    } catch(Exception exc) {
      CharSequence sExc = org.vishia.util.ExcUtil.exceptionInfo("unexpected", exc, 0, 10);
      System.err.println(sExc);
    }    
    test.expect(sb.getContent(), resultExpected, 7, "Test_OutTextPreparer_CallFor:testCall()");
    test.finish();

    try{ sb.close();} catch(IOException exc) { throw new RuntimeException("cannot close", exc); }
    
    
  }
  
  
 
  public static void test(String[] args) {
    //DataAccess.debugIdent = "dataColor";  //possibility to set a data depending debug break
    TestOrg test = new TestOrg("Test_OutTextPreparer", 1, args);
    try {
      //Note: The creation of the test instance may cause errors if the OutTextPreparer construction
      //fails because errors in the pattern. It is reported in C# in the calling level of this routine already 
      //because the calling of this static routine is loaded and created the type already.
      //testScript_B(test, org.vishia.fbcl.translate.TranslationScripts.class, "cHeader.txt");
      Test_OutTextPreparerScript thiz = new Test_OutTextPreparerScript();
      thiz.testScriptCall(test);
      //nonsense: test.testClassic();
      
    } catch (Exception e) {
      test.exception(e);
    }
    test.finish();
  }

  
  public static void testSpecific(String[] args) {
    //DataAccess.debugIdent = "dataColor";  //possibility to set a data depending debug break
    TestOrg test = new TestOrg("Test_OutTextPreparer", 1, args);
    try {
      //Note: The creation of the test instance may cause errors if the OutTextPreparer construction
      //fails because errors in the pattern. It is reported in C# in the calling level of this routine already 
      //because the calling of this static routine is loaded and created the type already.
      //testScript_B(test, org.vishia.fbcl.translate.TranslationScripts.class, "cHeader.txt");
      Test_OutTextPreparerScript thiz = new Test_OutTextPreparerScript();
      thiz.testScriptCall(test);
      //nonsense: test.testClassic();
      
    } catch (Exception e) {
      test.exception(e);
    }
    test.finish();
  }

  
  public static void main (String[] args) {
    test(args);
    //testSpecific(args);
  }
}
