package org.vishia.ctrl.test;

import java.util.Locale;

import org.vishia.ctrl.PIDf_Ctrl;
import org.vishia.util.Debugutil;

public class Test_PIDf_Ctrl {

  
  static class PIDf_Ctrl_Acc extends PIDf_Ctrl {
    public PIDf_Ctrl_Acc(float Tstep) { super(Tstep); }
  
    float wxP() { return this.wxP; }
    float wxPD() { return this.wxPD; }
    float dwxP() { return this.dwxP; }
    float qI() { return super.yI(); }
    float qD1() { return this.qD1; }
  }
  
  
  public static void main(String[] args) {
    test2_PIDi_Ctrl_emC();
  }
  
  
  static void test1_PIDi_Ctrl_emC ( ) {

    Locale locale = new Locale("sw", "Sw");
    
    float Tctrl = 0.000050f;
    PIDf_Ctrl_Acc pid = new PIDf_Ctrl_Acc(Tctrl);
    PIDf_Ctrl.Par par = new PIDf_Ctrl.Par(0.002f);
    PIDf_Ctrl.ParFactors[] parFactors = new PIDf_Ctrl.ParFactors[1]; //its a back reference<

    //float Tstep = 0.000060f;
    float yLim = 12.0f;  //The maximal controller output should match to the CPU-specific int data type (application decision)
    float kP = 10.0f;
    float Tn = 0.00f;
    float Td = 0.002f;  //for dwx=0.01: dwxD=0.01 * 2/0.05 = 0.4; yD=kP*dwxD = 4.0 
    float Tsd = 0.001f;


    par.init(Tctrl, yLim, kP, Tn, Td, Tsd, parFactors);
    pid.param(parFactors[0]);
    pid.setLim(yLim);



    float wx = 2.0f;

    int nCtStop = 100;

    for(int nCt = 0; nCt < 400; ++nCt) {
    
      wx -=0.01f;                                 // decreased the error of control from 100 .. 0 .. -60
    
      if(nCt == nCtStop) {
        Debugutil.stop();
      }
      float y = pid.step(wx, null);
      float qI = pid.qI() / (float)(1L<<54);
      System.out.printf(locale, "%d: wx=%3.6f dwxP=%3.6f y= %3.3f, yI=%3.3f, yP = %3.3f, yD=%3.3f\n"
                              , nCt, wx,      pid.dwxP(),  y,     qI,      pid.wxP(), pid.wxPD());

    }


  }

  
  
  static void test2_PIDi_Ctrl_emC ( ) {

    Locale locale = new Locale("sw", "Sw");
    
    float Tctrl = 0.000050f;
    PIDf_Ctrl_Acc pid = new PIDf_Ctrl_Acc(Tctrl);
    PIDf_Ctrl.Par par = new PIDf_Ctrl.Par(0.002f);
    PIDf_Ctrl.ParFactors[] parFactors = new PIDf_Ctrl.ParFactors[1]; //its a back reference<

    //float Tstep = 0.000060f;
    float yLim = 12.0f;  //The maximal controller output should match to the CPU-specific int data type (application decision)
    float kP = 10.0f;
    float Tn = 0.0001f;
    float Td = 0.001f;  //for dwx=0.01: dwxD=0.01 * 2/0.05 = 0.4; yD=kP*dwxD = 4.0 
    float Tsd = 0.0005f;


    par.init(Tctrl, yLim, kP, Tn, Td, Tsd, parFactors);
    pid.param(parFactors[0]);
    pid.setLim(yLim);


    float ys = 0; 

    int nCtStop = 100;

    for(int nCt = 0; nCt < 400; ++nCt) {
    
      ys += 0.01f * (pid.y() - ys);   // It is a PT1 with T1 = 100 * Tctrl
      
      float wx = 2.0f -  ys;          // ref value for ys is 0.5, build ctrl error
    
      if(nCt == nCtStop) {
        Debugutil.stop();
      }
      float y = pid.step(wx, null);
      float qI = pid.qI();
      System.out.printf(locale, "%d: wx=%3.6f dwxP=%3.6f y= %3.3f, yI=%3.3f, yP = %3.3f, yD=%3.3f\n"
                              , nCt, wx,      pid.dwxP(),  y,     qI,      pid.wxP(), pid.wxPD());

    }


  }

  
  
  
}
