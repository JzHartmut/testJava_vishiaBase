package org.vishia.testJava;

import org.vishia.math.ComplexDouble;
import org.vishia.util.TestOrg;

public class Test_InnerClasses {

  protected boolean bSimpleAbs;
  
  void test(TestOrg parent) {
    TestOrg test = new TestOrg("test", 2, parent);
    
    this.testClassImpl.set(1.0, 1.0);
    double val = this.testClassImpl.magn();
    test.expect(val==2.0, 3, "other implementation of magnitude calculates 2.0 instead 1.414...");
    test.finish();
  }
  
  /**This is any anonymous inner class implementation. Adequte to the interface implementation -
   * it can only be done in the own class.
   */
  ComplexDouble testClassImpl = new ComplexDouble(0,0) {
    /**This is another implementation of the already given operation. 
     * Only the given operation are visible from outer,
     * hence only overriding is sensible.
     */
    @Override public double magn() { 
      if(Test_InnerClasses.this.bSimpleAbs) { return this.re + this.im; }
      else return super.magn();   // use the original.
    }
  };

  
  /**This class can be instantiated from outside (a composition).
   * Then one have access to things, which are elsewhere not accessible.
   * Thats why it is pronounced as "agent" here.
   * 
   *
   */
  public class Agent {
    
    public void setSimpleAbs(boolean bSimple) {
      Test_InnerClasses.this.bSimpleAbs = bSimple;
    }
  }
  
  
  
  public static void main(String[] args) {
    Test_InnerClasses thiz = new Test_InnerClasses();
    TestOrg test = new TestOrg("Test_InnerClasses", 1, args);
    try {
      thiz.test(test);
    } catch (Exception e) {
      test.exception(e);
    }
    test.finish();

  }
}
