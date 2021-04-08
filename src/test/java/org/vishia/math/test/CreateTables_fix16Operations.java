package org.vishia.math.test;

import java.io.FileWriter;
import java.io.IOException;

public class CreateTables_fix16Operations {

  
  /**A functional interface is necessary for a lambda expression.
   * It represents the type of expression.
   * It does not define how to do, only the basic requirements. */
   @FunctionalInterface interface MathFn {
     double fn(double x); 
   }
  
  public static int[] createTable(int bitsegm, int size, double scalex, double scaley, MathFn fn, String name, int[][] fixpoints) {
    int[] table = new int[size+1];
    double dx2 = scalex/size/2.0 ;          // diff x for 1/2 to left and right from point.
    double yleft = fn.fn(-dx2);          // first left point
    int ixFixpoints = 0;
    for(int ix = 0; ix <= size; ++ix) {
      double x = ix * scalex / size;  //max is 4.0
      double yp0 = fn.fn(x);              // the exact supporting point
      double yright = fn.fn(x + dx2);
      double dy = yright - yleft;                // gain for this segment from left to right point
      double yleft_ = yp0 - dy;
      double yright_ = yp0 + dy;
      double yleftErr = yleft - yleft_;          //error between linear approx. point and exact point
      double yrightErr = yright - yright_;
      double yp = yp0 + (yleftErr + yrightErr) /2/2;  //supporting point adjusted
      //
      int yi = (int)(yp/scaley * 0x8000 + 0.5);  //supporting point as integer.
      short dyi = (short)(dy/scaley * 0x8000 + 0.5);   //gain as integer, as used later.
      if(fixpoints[ixFixpoints][0] == ix) {      // a fix point found, this should be the yi value.
        int yid = fixpoints[ixFixpoints][1] - yi;// difference of correcture
        yi = fixpoints[ixFixpoints][1];
        if(ix ==0) {                             // first point: correct the gain
          dyi -= yid;                            // to reach the same end point of the segment
        } else if(ix == size) {                  // last point: correct the gain
          dyi += yid;
        } else {                                 // correct the point before:
          int ytz = table[ix-1];
          int dytz = (ytz & 0xffff) + yid;       // adjust the gain to catch this point
          ytz = ((ytz + (yid<<15)) & 0xffff0000) | dytz; //and adjust the point with the half of yid
          table[ix-1] = ytz;
          yright += scaley * yid / 0x8000;       // adjust also the next left point to match with the corrected
        }
        if( ++ixFixpoints >= fixpoints.length) { ixFixpoints -=1; } //a next exists or not
      }
      int val = yi<<16 | (dyi & 0xffff);         // build one hex value
      table[ix] = val;
      //
      yleft = yright;                            // use the right point as left point for next segment.
    }
    
    testTable(table, bitsegm, scalex, scaley, fn);
    
    FileWriter wr = null;
    try {
      wr = new FileWriter("T:/" + name + "Table.c", false);
      writeCcodeTable(wr, table, name + "Table");
      //writeCcodeTable(wr, arcTable, "arcsinTable");
      wr.close();
      wr = null;
    }
    catch(Exception exc) {
      if(wr !=null) { try {wr.close(); } catch(IOException e3) {}}
      System.err.println(exc.getMessage());
    }

    return table;
  }



  
  
  

  static void testTable (int[] table, int bitsegm, double scalex, double scaley, MathFn fn) {
    int errimin = 0x8000; int errimax = -0x8000; 
    int step = 1<<bitsegm;
    int xrange = (table.length-1) * step;
    int xmax = step * (table.length-1);             // the size of a segment is 0x200 over all.
    for(int x = 0; x <= xmax; x += 0x20) {
      int ix = (x + step/2) >>bitsegm;
      int valTable = table[ix];
      int yi = (valTable >> 16);
      int yid = (short)(((short)((x<< (16 - bitsegm)) & 0xffff) * ((short)(valTable & 0xffff)))>>16);
      yi += yid;
      double xf = x * (scalex / (xrange));
      double yf = fn.fn(xf);
      int yif = (int)(yf/scaley * 0x8000);
      if(yif == 0x8000) { yif = 0x7fff; }
      int dy = yi - yif;
      if(dy > errimax) { 
        errimax = dy; 
      }
      System.out.printf("%3.3f %4X => %4X %4X %d\n", xf, x, yi, yif, dy);
      if(dy < errimin) { errimin = dy; }
    }

  }

  
  
  
  static void writeCcodeTable ( FileWriter wr, int[] table, String name ) 
  throws IOException {
    wr.append("\n\n");  
    wr.append("static const uint32 ").append(name).append("[] = \n");
    String sep = "{"; 
    for(int ix = 0; ix < table.length; ++ix) {
      int val = table[ix];
      String sVal = Integer.toHexString(val);
      if(sVal.length() >8) { sVal = sVal.substring(sVal.length()-4, sVal.length()); }  //without too much leading FFF 
      if(sVal.length() <8) { sVal = "00000000".substring(0, 8-sVal.length()) + sVal; }  //with leading 000 
      //                                                   // wr line
      wr.append(sep).append(" 0x").append(sVal)
                     .append("  // ").append("" + (ix)).append("\n");
      sep = ",";
    }
    wr.append("};\n");
  }
  


  public static int[] createCosTable() {
    int[][] fixpoints = { {0, 0x7FFF}, {32, 0x0}, {64, -0x8000} };
    int[] table = createTable(9, 64, Math.PI, 1.0, (x)-> Math.cos(x), "cos", fixpoints);
    return table;
  }

  public static int[] createSqrtTable() {
    int[][] fixpoints = { {8, 0x4000} };
    int[] table = createTable(11, 32, 4.0, 2.0, (x)-> Math.sqrt(Math.abs(x)), "sqrt", fixpoints);
    return table;
  }


  public static int[] createRsqrtTable() {
    int[][] fixpoints = { {32, 0x4000} };
    MathFn fn = (x) -> x <=0 ? 0 : 1.0/Math.sqrt(x);
    int[] table = createTable(10, 64, 4.0, 2.0, fn, "rsqrt", fixpoints);
    return table;
  }


  
  
  public final static void main(String[] args) {
    //testatan2_16();
    //test_cos16q_emC();
    //createSqrtTable();
    //createRsqrtTable();
    createCosTable();
 //   testTaylor2();
  }

}
