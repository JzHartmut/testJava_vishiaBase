package org.vishia.testOrg;

import org.vishia.util.CheckVs;
import org.vishia.util.StringFunctions;


/**This class supports testing. It may be seen as an alternative concept to the known Unit test concept (org.junit.Assert).
 * But it is more simple and obviously because the test case is shown, all successfull tests can be shown.
 * <br><br>
 * Pattern to use: <pre>
 * void testRoutine() {
 *   TestVishia test = new TestVishia("Test routine description");
 *   result = doSomethingToTest();
 *   test.expect(result == expected, true, "test case description");
 *   test.finish();
 * </pre> 
 * The output for this situation is either:<pre>
 * ==============================================
 * Test routine description
 *   ok: test case description
 * ok  
 * </pre> 
 * or instead "ok" "ERROR".
 * <br>
 * Especially for string comparison the position of the first difference can be shown, for example:<pre>
 * ==============================================
 * Test routine description
 *   ERROR: @45 test case description
 * ERROR  
 * </pre>
 * The evaluation of the tests can be done by searching "ERROR:" in the output text or by text difference evaluation
 * of the produced test output. Especially the second case shows additionally which test cases are changed.
 * The reference output text can be stored in a text file with a specified version in a version management system, 
 * So the progress can be visited. It is some more better than a polish html view result.
 * <br><br>
 * The advantage of this system in comparison to unit test: Very simple. 
 * <br>
 * An individual test can be start in any main() routine of a test class
 * <br>
 * All tests of a module are programmed immediately by a <code>main()</code> routine in a <code>TestAll</code> class.
 * 
 * @author Hartmut Schorrig LPGL license or maybe second license model  with special contract. Do not remove this license entry.
 *
 */
public class TestOrg {

  
  private boolean bOk = true;
  
  
  /**Ctor shows the title and sets the internal {@link #bOk} to true. */
  public TestOrg(String title) {
    outln("=========================================================================");
    outln(title);
    
  }
  
  
  
  /**Compares both CharSequences, should be equal. If not, shows the position of the first difference.
   * This difference position should help for evaluate the problem.
   * The difference is shown on error in form "  ERROR: @pos: txt"
   * @param s1 The both texts, the first may be result on a test result
   * @param s2 The second text should be the expected one.
   * @param bOkTxt true if show the txt on ok, see {@link #expect(boolean, boolean, String)}
   * @param txt The test case description, see {@link #expect(boolean, boolean, String)}
   */
  public void expect(CharSequence s1, CharSequence s2, boolean bOkTxt, String txt) {
    int eq = StringFunctions.comparePos(s1, s2);
    final String txtShow;
    if(eq !=0) {
      txtShow = "@ " + Math.abs(eq) + ": " + txt; //show which position is different.  
    } else { 
      txtShow = txt; 
    }
    expect(eq == 0, bOkTxt, txtShow);
  }
  
  
  
  
  /**core test routine
   * @param cond if false, this.bOk is set to false.
   * @param bOkTxt if true and cond==true then outputs a line: "  ok: txt", false: outputs nothing
   * @param txt if cond==false outputs a line: "  ERROR: txt"
   */
  public void expect(boolean cond, boolean bOkTxt, String txt) {
    if(cond) {
      if(bOkTxt) { out("  ok: "); outln(txt); }
    } else {
      this.bOk = false;
      out("  ERROR: "); outln(txt);
    }
  }
  
  
  /**Shows a proper text on an non expected exception, sets the internal {@link #bOk} to false 
   * 
   * @param exc
   */
  public void exception(Exception exc) {
    this.bOk = false;
    CharSequence msg = CheckVs.exceptionInfo("Exception while test", exc, 1, 5);
    outln(msg);
  }
  
  
  /**This should use as last statement in a test routine. It writes either "ok" or "ERROR" due to internal {@link #bOk}.
   * If at least one error occurs, {@link #bOk} is false.
   */
  public void finish() {
    if(this.bOk) {
      outln("ok");
    } else {
      outln("ERROR");
    }
  }
  
  
  
  
  
  
  
  public static void out(CharSequence txt) {
    System.out.append(txt);
  }
  
  public static void outln(CharSequence txt) {
    System.out.append(txt).append('\n');
  }
  
  
}
