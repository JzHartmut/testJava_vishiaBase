package org.vishia.util.test.outTextPreparer;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.vishia.util.Debugutil;
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

  static class OtxPattern {
  
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
    static private final OutTextPreparer otxIfColors = new OutTextPreparer("otxIfColors" 
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
    , Test_OutTextPreparer_CallFor.OtxPattern.class        //static class on construction for sub script.
    , "colors, text"  //arguments need and used
    , "<&text>: <:for:color:colors><:call:otxIfColors:color=color><:if:color_next>, <.if><.for>");  //The pattern.
  
  
  
    /**It is the used output String pattern containing two calls of otxListColors. 
     * With different arguments for both calls the results are different.
     * It is an example for a complex output text. */
    static OutTextPreparer otxCall = new OutTextPreparer("otxCall"
    , Test_OutTextPreparer_CallFor.OtxPattern.class        //static class on construction for sub script.
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
  
  }  
  
  
  /**Ctor empty. */
  Test_OutTextPreparer_CallFor() {
  }


  static private String cmpNumeric = 
        "One line with values at ix=0: 0.000, 0.000 \n"
      + "One line with values at ix=1: 0.500, 0.479 \n"
      + "One line with values at ix=2: 1.000, 0.841 \n"
      + "One line with values at ix=3: 1.500, 0.997 \n"
      + "One line with values at ix=4: 2.000, 0.909 \n"
      + "One line with values at ix=5: 2.500, 0.598 \n"
      + "One line with values at ix=<null>: <null>, <null> \n"
      + "";

  
  static void testNumeric (TestOrg parent) {
    TestOrg test = new TestOrg("testNumeric formatted", 2, parent);
    OutTextPreparer otxNumLine = new OutTextPreparer("otxNumLine"
        , null
        , "ix, f1, f2"    //arguments need and used.
        , "One line with values at ix=<&ix>: <&f1:%1.3f>, <&f2:%1.3f> \n");   //The pattern.
    OutTextPreparer.DataTextPreparer otxData = otxNumLine.createArgumentDataObj();
    StringBuilder sOut = new StringBuilder();
    try {
      for(int ix = 0; ix < 6; ++ix) {
        double f1 = ((float) ix)/2.0;
        double f2 = Math.sin(f1);
        otxData.setArgument("ix", ix);
        otxData.setArgument("f1", f1);
        otxData.setArgument(2, f2);
        otxNumLine.exec(sOut, otxData);
      }
      otxData.setArgument("ix", null);        // test what's happen on null as argument
      otxData.setArgument("f1", null);
      otxData.setArgument(2, null);
      otxNumLine.exec(sOut, otxData);
      
    } catch(IOException exc) {
      test.exception(exc);
    }
    test.expect(sOut, cmpNumeric, 5, "");
    test.finish();
  }
  

  /**
   * @param parent
   */
  /**
   * @param parent
   */
  static void testNumericAccess (TestOrg parent) {
    TestOrg test = new TestOrg("testNumericAccess formatted", 2, parent);
    //
    class Values {  //====Any class which stores the values. Only local for the example. 
      int ix; double f1, f2;
    }
    //                ==== Any instance to store values in a container.
    List<Values> idxValues = new LinkedList<>();
    for(int ix = 0; ix < 6; ++ix) {  // prepare any data
      Values val = new Values();   
      val.ix = ix;
      val.f1 = ((float) ix)/2.0;
      val.f2 = Math.sin(val.f1);
      idxValues.add(val);
    }
    idxValues.add(null);            // last element should return null on access.
    //
    // =================== Afterwards present all data
    OutTextPreparer otxNumAccess = new OutTextPreparer("otxNumAccess"
        , null
        , "values"    //arguments need and used.
        , "<:for:val:values><: >\n" 
        + "One line with values at ix=<&val.ix>: <&val.f1:%1.3f>, <&val.f2:%1.3f> \n"
        + "<.for>");   //The pattern.
    OutTextPreparer.DataTextPreparer otxData = otxNumAccess.createArgumentDataObj();
    StringBuilder sOut = new StringBuilder();
    try {
        otxData.setArgument(0, idxValues);
        otxNumAccess.exec(sOut, otxData);
    } catch(IOException exc) {
      test.exception(exc);
    }
    Debugutil.stop();
    test.expect(sOut, cmpNumeric, 5, "");
    test.finish();
  }
  

  void testCall ( TestOrg parent) throws IOException {
    TestOrg test = new TestOrg("test script with <:call:..>", 2, parent);
    Writer_Appendable sb = new Writer_Appendable(new StringBuilder(500));
    OutTextPreparer.DataTextPreparer vars = OtxPattern.otxCall.createArgumentDataObj();
    //vars.debugOtx = "otxIfColors";   //possibility to set a break point on a special command in the given script.
    //vars.debugIxCmd = 6;             //see usage for this variables. set debugIxCmd = 0 to stop in first cmd to view the this.cmd
    vars.setArgument("dataColor", this.dataColor);        //The data class for access.
    vars.setArgument("text1", "any test text");
    OtxPattern.otxCall.exec(sb, vars);
    test.expect(sb.getContent(), OtxPattern.resultExpected, 7, "Test_OutTextPreparer_CallFor:testCall()");
    test.finish();
  }


  void testClassic() {
    Formatter fm = new Formatter(); 
    fm.format("", this); 
    fm.close();
    PrintStream myprinter = System.out;
    myprinter.printf(OtxPattern.resultExpected, this);
    
  }
  


  public static void testSpecific(String[] args) {
    //DataAccess.debugIdent = "dataColor";  //possibility to set a data depending debug break
    TestOrg test = new TestOrg("Test_OutTextPreparer", 1, args);
    try {
      //Note: The creation of the test instance may cause errors if the OutTextPreparer construction
      //fails because errors in the pattern. It is reported in C# in the calling level of this routine already 
      //because the calling of this static routine is loaded and created the type already.
      //Test_OutTextPreparer_CallFor testObj = new Test_OutTextPreparer_CallFor();
      //nonsense: test.testClassic();
      //testNumeric(test);
      testNumericAccess(test);
      //testObj.testCall(test);
      
    } catch (Exception e) {
      test.exception(e);
    }
    test.finish();
  }

  public static void testAll(String[] args) {
    //DataAccess.debugIdent = "dataColor";  //possibility to set a data depending debug break
    TestOrg test = new TestOrg("Test_OutTextPreparer", 1, args);
    try {
      //Note: The creation of the test instance may cause errors if the OutTextPreparer construction
      //fails because errors in the pattern. It is reported in C# in the calling level of this routine already 
      //because the calling of this static routine is loaded and created the type already.
      ////nonsense: test.testClassic();
      testNumeric(test);
      testNumericAccess(test);
      Test_OutTextPreparer_CallFor testObj = new Test_OutTextPreparer_CallFor();
      testObj.testCall(test);
      
    } catch (Exception e) {
      test.exception(e);
    }
    test.finish();
  }

  
  
  public static void main(String[] args) { 
    //testSpecific(args);
    testAll(args); 
  }
  
  
}
