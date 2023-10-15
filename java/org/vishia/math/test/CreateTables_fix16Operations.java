package org.vishia.math.test;

import java.io.FileWriter;
import java.io.IOException;

public class CreateTables_fix16Operations {

  
  /**The functional interface for the operation as lambda expression.
   */
   @FunctionalInterface interface MathFn {
     double fn(double x); 
   }
  
  /**Create a table for linear interpolation for any desired math operation
   * @param bitsegm Number of bits for one segment of linear interpolation (step width dx)
   * @param size Number of entries -1 in the table. 
   *        The table get (size+1) entries, should be 16, 32, 64
   * @param scalex Scaling for the x-value, 
   *        this result is mapped to 0x10000 (the whole 16 bit range)
   * @param scaley Scaling for y-value, this result of the operation is mapped to 0x8000.
   * @param fn The math function as Lambda-expression
   * @param name of the file and table as C const
   * @param fixpoints array of some points [ix] [ yvalue] which should be exactly match
   * @return The table.
   */
  public static int[] createTable(int bitsegm, int size, double scalex, double scaley
      , MathFn fn, String name, int[][] fixpoints) {
    int[] table = new int[size+1];
    double dx2 = scalex/size/2.0 ;          // diff x for 1/2 to left and right from point.
    double yleft = fn.fn(-dx2);          // first left point
    if(yleft >= 0.99997*scaley) { yleft = 0.99997*scaley; } 
    int ixFixpoints = 0;
    for(int ix = 0; ix <= size; ++ix) {
      double x = ix * scalex / size;  //max is 4.0
      double yp0 = fn.fn(x);              // the exact supporting point
      double yright = fn.fn(x + dx2);
      double dy = yright - yleft;                // gain for this segment from left to right point
      double yleft_ = yp0 - dy;
      double yright_ = yp0 + dy;
      if(  yleft_ >= 0.99997*scaley              //left point over driven, especially because reciprocal functions
        && fixpoints[ixFixpoints][0] != ix) {    //But do not a correctur if a manual point is given. 
        double yleftd_ = 0.99997*scaley - yleft_;
        yleft_ = 0.99997*scaley;
        yp0 += yleftd_ /2;  //prevent overdrive by calculation to left
        dy = yright - yleft_;
        yright = yright_; //prevernt yp correcture
      } 
      double yleftErr = yleft - yleft_;          //error between linear approx. point and exact point
      double yrightErr = yright - yright_;
      double yp = yp0 + (yleftErr + yrightErr) /2/2;  //supporting point adjusted
      //
      int yi = (int)(yp/scaley * 0x8000 + 0.5);  //supporting point as integer.
      short dyi = (short)(dy/scaley * 0x8000 + 0.5);   //gain as integer, as used later.
      if(fixpoints[ixFixpoints][0] == ix) {      // a fix point found, this should be the yi value.
        yi = fixpoints[ixFixpoints][1];
        if(fixpoints[ixFixpoints].length >=3) {
          dyi = (short)fixpoints[ixFixpoints][2];       // use given dyi from fixpoints
        } else {
          int yid = fixpoints[ixFixpoints][1] - yi;// difference of correcture
          if(ix ==0) {                             // first point: correct the gain
            dyi -= yid;                            // to reach the same end point of the segment
          } else if(ix == size) {                  // last point: correct the gain
            dyi += yid;
          } else {                                 // correct the point before:
            int ytz = table[ix-1];
            int dytz = (ytz + yid) & 0xffff;       // adjust the gain to catch this point
            ytz = ((ytz + (yid<<15)) & 0xffff0000) | dytz; //and adjust the point with the half of yid
            table[ix-1] = ytz;
            yright += scaley * yid / 0x8000;       // adjust also the next left point to match with the corrected
          }
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
      writeCcodeTable(wr, table, 1<<bitsegm, name + "Table");
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

  
  
  
  static void writeCcodeTable ( FileWriter wr, int[] table, int dx, String name ) 
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
                    .append("  // " + (ix))
                    .append("  ").append(Integer.toHexString(ix * dx)).append("\n");
      sep = ",";
    }
    wr.append("};\n");
  }
  


  public static int[] createCosTable() {
    int[][] fixpoints = { {0, 0x7FFF}, {1, 0x7FD3}, {32, 0x0}, {64, -0x8000} };
    int[] table = createTable(9, 64, Math.PI, 1.0, (x)-> Math.cos(x), "cos", fixpoints);
    return table;
  }

  public static int[] createArcsinTable() {
    int[][] fixpoints = { {16, 0x4000} };
    int[] table = createTable(10, 16, 1.0, Math.PI, (x)-> Math.asin(x), "asin", fixpoints);
    return table;
  }

  public static int[] createSqrtTable() {
    int[][] fixpoints = { 
        {0, 0x0, 0x20c6},   //sqrt(0)==0, but high rise in 0 range.
        //{1, 0x163e}, 
        {8, 0x4000} };
    int[] table = createTable(11, 32, 4.0, 2.0, (x)-> Math.sqrt(Math.abs(x)), "sqrt16_32", fixpoints);
    return table;
  }


  public static int[] createRsqrtTable4_32() {
    int[][] fixpoints = { {8, 0x4000} };
    MathFn fn = new MathFn() {
      @Override public double fn(double x) {
        if(x <=0.25003) { return 1.99993; }
        //else if(x <=0.27) { return 1.9997; }
        else {
          double y = 1.0/Math.sqrt(x);
          if(y >1.99993) { y = 1.99993; }
          return y;
        }
      }
    };
    int[] table = createTable(11, 32, 4.0, 2.0, fn, "rsqrt", fixpoints);
    return table;
  }


  
  
  /**Reciprocal square root till 0x7fff =^ 2.0 with 32 supporting points.
   * @return
   */
  public static int[] createRsqrtTable2_32() {
    int[][] fixpoints = { {16, 0x4000} };
    MathFn fn = new MathFn() {
      @Override public double fn(double x) {
        if(x <=0.25003) { return 1.99993; }
        //else if(x <=0.27) { return 1.9997; }
        else {
          double y = 1.0/Math.sqrt(x);
          if(y >1.99993) { y = 1.99993; }
          return y;
        }
      }
    };
    int[] table = createTable(10, 32, 2.0, 2.0, fn, "rsqrt2_32", fixpoints);
    return table;
  }


  
  
  public final static void main(String[] args) {
    //testatan2_16();
    //test_cos16q_emC();
    createSqrtTable();
    //createSqrtTable4_32();
    //createRsqrtTable2_32();
    createCosTable();
 //   testTaylor2();
  }

}
