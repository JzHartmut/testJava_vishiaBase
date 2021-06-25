package org.vishia.math.test;

public class Test_trigon16 {

  static int[] arcsinTable = CreateTables_fix16Operations.createArcsinTable();
  
  static short arctan2Norm ( short re, short im) {
    short re1 = re; short im1 = im;
    short angle = 0;
    int quadr = 0;
    if(im1 <0) {
      im1 = (short)-im1;
      quadr = 1;
      angle = (short)0xc000;
    }
    if(re1 <0) {
      re1 = (short)-re1;
      quadr |= 2;
      angle = (short)(angle ^ 0x4000);
    }
    short angle1;    //in the first segment
    if(re1 < im1) {
      angle1 = (short)(0x4000 - arcsin(re1));
    } else {
      angle1 = arcsin(im1);
    }
    switch(quadr) {
    case 0: return angle1;
    case 1: return (short)(- angle1);
    case 3: return (short)(0x8000 + angle1);
    case 2: return (short)(0x8000 - angle1);
    }
    return 0;
  }
  
  static short arcsin(short x) {
    return linInterpol(arcsinTable, x, 10);
  }
  
  
  static short linInterpol ( int[] table, short x, int BITSEGM) {
    int valTable = table[( x + (1 <<(BITSEGM-1) ) ) >>BITSEGM];
    short dx = (short)(( x <<(16 - BITSEGM) ) & 0xffff); 
    short gain = (short)(valTable & 0xffff); 
    valTable += dx * gain;
    short y = (short)(valTable >>16);
    return y;
  }

  
  
  
  static void testArctan ( ) {
    double angle = -Math.PI;
    do {
      double ref = Math.cos(angle);
      double imf = Math.sin(angle);
      short rei = (short)(0x4000 * ref);
      short imi = (short)(0x4000 * imf);
      short angley = arctan2Norm(rei, imi);
      double angleyf = angley * Math.PI / 0x8000;
      double angleydiff = angleyf - angle;
      System.out.printf("%3.5f %3.5f %1.5f\n", 180.0/Math.PI * angle,  180.0/Math.PI * angleyf, angleydiff);
      angle += Math.PI * 0.01f;
    } while (angle < Math.PI);
  }
  
  
  
  public final static void main(String[] args) {
    //testatan2_16();
    //test_cos16q_emC();
    //createSqrtTable4_32();
    //createRsqrtTable2_32();
    testArctan();
 //   testTaylor2();
  }

}
