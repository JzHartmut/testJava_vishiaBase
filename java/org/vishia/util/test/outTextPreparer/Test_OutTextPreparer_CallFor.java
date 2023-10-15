package org.vishia.util.test.outTextPreparer;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.vishia.util.OutTextPreparer;
import org.vishia.util.StringFunctions;
import org.vishia.util.TestOrg;
import org.vishia.util.Writer_Appendable;

public class Test_OutTextPreparer_CallFor
{
  /**Example for a user class, here with two lists of some colors. */
  public static class DataColor {

    /**Some colors for example for the for control. */
    public List<String> colors1 = new ArrayList<String>();
    public List<String> colors2 = new ArrayList<String>();

    public DataColor() {
      this.colors1.add("white");
      this.colors1.add("yellow");
      this.colors1.add("red");
      this.colors1.add("blue");
      this.colors1.add("green");
    
      this.colors2.add("cyan");
      this.colors2.add("magenta");
      this.colors2.add("gray");
      this.colors2.add("black");
    }

  } //class DataColor

  /**The user class, as example here. */
  DataColor dataColor = new DataColor();

  /**Output text patten to show the color list.  
   * It uses a variable 'colors'.
   * The value for 'colors' should be an container, the content of the items are shown.
   * This out text pattern is called inside another pattern in this example, see otxCall. 
   */
  static final OutTextPreparer otxListColors = new OutTextPreparer("otxListColors"
  , null            //no static data on construction
  , "colors, text"  //arguments need and used
  , "<&text>: <:for:color:colors><&color><:if:color_next>, <.if><.for>");  //The pattern.


  /**Output text patten to show special texts for each colors with an if-chain.  
   * It uses a variable 'color' which should be supplied as String value 
   * This out text pattern is called inside another pattern in this example, see otxListIfColors. 
   */
  static final OutTextPreparer otxIfColors = new OutTextPreparer("otxIfColors" 
  , null            //no static data on construction
  , "color"         //arguments need and used
  , "<:if:color == 'white'>wh"  //The pattern: example for a if-chain
  + "<:elsif:color == 'yellow'>ye"
  + "<:elsif:color ?starts 'gr'>green or gray"
  + "<:elsif:color ?ends 'ck'>black"
  + "<:elsif:color ?contains 'a'>a-type-<&color>"
  + "<:else>unknown<.if>");

  /**Output text pattern to organize the color output with the otxIfColors-sub-pattern.
   */
  static final OutTextPreparer otxListIfColors = new OutTextPreparer("otxListIfColors" 
  , Test_OutTextPreparer_CallFor.class            //no static data on construction
  , "colors, text"  //arguments need and used
  , "<&text>: <:for:color:colors><:call:otxIfColors:color=color><:if:color_next>, <.if><.for>");  //The pattern.



  /**It is the used output String pattern containing two calls of otxListColors. 
   * With different arguments for both calls the results are different.
   * It is an example for a complex output text. */
  static OutTextPreparer otxCall = new OutTextPreparer("otxCall"
  , Test_OutTextPreparer_CallFor.class
  , "dataColor, text1"    //arguments need and used.
  , "Text: <&text1> \n"   //The pattern.
  + "Test Call colors1: <:call:otxListColors: colors = dataColor.colors1, text='Colors-1'> END\n"
  + "Test Call colors2: <:call:otxListColors: colors = dataColor.colors2, text='Colors-2' > END\n"
  + "Test Call colors1: <:call:otxListIfColors: colors = dataColor.colors1, text='Colors-1a'> END\n"
  + "Test Call colors1: <:call:otxListIfColors: colors = dataColor.colors2, text='Colors-2a'> END\n"
  );

  /**To compare the test result. This text will be produced. */
  static String resultExpected = 
    "Text: any test text \n" 
  + "Test Call colors1: Colors-1: white, yellow, red, blue, green END\n"
  + "Test Call colors2: Colors-2: cyan, magenta, gray, black END\n"
  + "Test Call colors1: Colors-1a: wh, ye, unknown, unknown, green or gray END\n"
  + "Test Call colors1: Colors-2a: a-type-cyan, a-type-magenta, green or gray, black END\n";
  
  
  
  
  /**Ctor empty. */
  Test_OutTextPreparer_CallFor() {
  }



  void testCall ( TestOrg parent) throws IOException {
    TestOrg test = new TestOrg("test script with <:call:..>", 2, parent);
    Writer_Appendable sb = new Writer_Appendable(new StringBuilder(500));
    OutTextPreparer.DataTextPreparer vars = otxCall.createArgumentDataObj();
    //vars.debugOtx = "otxIfColors";   //possibility to set a break point on a special command in the given script.
    //vars.debugIxCmd = 6;             //see usage for this variables. set debugIxCmd = 0 to stop in first cmd to view the this.cmd
    vars.setArgument("dataColor", this.dataColor);        //The data class for access.
    vars.setArgument("text1", "any test text");
    otxCall.exec(sb, vars);
    test.expect(sb.getContent(), resultExpected, 7, "Test_OutTextPreparer_CallFor:testCall()");
    test.finish();
  }


  void testClassic() {
    Formatter fm = new Formatter(); 
    fm.format("", this); 
    fm.close();
    PrintStream myprinter = System.out;
    myprinter.printf(resultExpected, this);
    
  }
  


  public static void test(String[] args) {
    //DataAccess.debugIdent = "dataColor";  //possibility to set a data depending debug break
    TestOrg test = new TestOrg("Test_OutTextPreparer", 1, args);
    try {
      //Note: The creation of the test instance may cause errors if the OutTextPreparer construction
      //fails because errors in the pattern. It is reported in C# in the calling level of this routine already 
      //because the calling of this static routine is loaded and created the type already.
      Test_OutTextPreparer_CallFor testObj = new Test_OutTextPreparer_CallFor();
      //nonsense: test.testClassic();
      testObj.testCall(test);
      
    } catch (Exception e) {
      test.exception(e);
    }
    test.finish();
  }

  
  
  public static void main(String[] args) { test(args); }
  
  
}
