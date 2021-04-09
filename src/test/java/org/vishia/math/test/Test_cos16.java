package org.vishia.math.test;

import java.io.FileWriter;
import java.io.IOException;

import org.vishia.util.Debugutil;

public class Test_cos16 {

  //final static short 0x7fff = 30240;
  
  final static short[] cosTable = 
    { (short)(0x8000 * Math.cos(1* Math.PI / 32))
    , 0x7fff                  // cos table calculate in compile time.
    , (short)(0x8000 * Math.cos(1* Math.PI / 32))
    , (short)(0x8000 * Math.cos(2* Math.PI / 32))
    , (short)(0x8000 * Math.cos(3* Math.PI / 32))
    , (short)(0x8000 * Math.cos(4* Math.PI / 32))
    , (short)(0x8000 * Math.cos(5* Math.PI / 32))
    , (short)(0x8000 * Math.cos(6* Math.PI / 32))
    , (short)(0x8000 * Math.cos(7* Math.PI / 32))
    , (short)(0x8000 * Math.cos(8* Math.PI / 32))
    , (short)(0x8000 * Math.cos(9* Math.PI / 32))
    , (short)(0x8000 * Math.cos(10* Math.PI / 32))
    , (short)(0x8000 * Math.cos(11* Math.PI / 32))
    , (short)(0x8000 * Math.cos(12* Math.PI / 32))
    , (short)(0x8000 * Math.cos(13* Math.PI / 32))
    , (short)(0x8000 * Math.cos(14* Math.PI / 32))
    , (short)(0x8000 * Math.cos(15* Math.PI / 32))
    , (short)(0x8000 * Math.cos(16* Math.PI / 32))
    , (short)(0x8000 * Math.cos(17* Math.PI / 32))
    }; 
  
  
  
  final static short[] arcTable = 
    { (short)(0x8000 * Math.asin(-1/ 16.0) / Math.PI)
    , 0                  // cos table calculate in compile time.
    , (short)(0x8000 * Math.asin(1/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(2/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(3/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(4/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(5/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(6/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(7/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(8/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(9/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(10/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(11/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(12/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(13/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(14/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(15/ 16.0) / Math.PI)
    , (short)(0x8000 * Math.asin(16/ 16.0) / Math.PI)
    }; 
  
  final static double[] arcfloatTable = 
    { (Math.atan(1* Math.PI / 64))
    , 0                  // cos table calculate in compile time.
    , (Math.atan(1* Math.PI / 64))
    , (Math.atan(2* Math.PI / 64))
    , (Math.atan(3* Math.PI / 64))
    , (Math.atan(4* Math.PI / 64))
    , (Math.atan(5* Math.PI / 64))
    , (Math.atan(6* Math.PI / 64))
    , (Math.atan(7* Math.PI / 64))
    , (Math.atan(8* Math.PI / 64))
    , (Math.atan(9* Math.PI / 64))
    , (Math.atan(10* Math.PI / 64))
    , (Math.atan(11* Math.PI / 64))
    , (Math.atan(12* Math.PI / 64))
    , (Math.atan(13* Math.PI / 64))
    , (Math.atan(14* Math.PI / 64))
    , (Math.atan(15* Math.PI / 64))
    , (Math.atan(16* Math.PI / 64))
    , (Math.atan(17* Math.PI / 64))
    }; 
  
  final static short[] cosTable2 = 
    {   0x7fff                  // cos table calculate in compile time.
      , (short)(0x8000 * (Math.cos( 1.0f*Math.PI/128)) - ( (Math.cos( 1.0f* Math.PI/128)+Math.cos( 2.0f* Math.PI/128))/2 - Math.cos( 1.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos( 2.0f*Math.PI/128)) - ( (Math.cos( 2.0f* Math.PI/128)+Math.cos( 3.0f* Math.PI/128))/2 - Math.cos( 2.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos( 3.0f*Math.PI/128)) - ( (Math.cos( 3.0f* Math.PI/128)+Math.cos( 4.0f* Math.PI/128))/2 - Math.cos( 3.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos( 4.0f*Math.PI/128)) - ( (Math.cos( 4.0f* Math.PI/128)+Math.cos( 5.0f* Math.PI/128))/2 - Math.cos( 4.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos( 5.0f*Math.PI/128)) - ( (Math.cos( 5.0f* Math.PI/128)+Math.cos( 6.0f* Math.PI/128))/2 - Math.cos( 5.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos( 6.0f*Math.PI/128)) - ( (Math.cos( 6.0f* Math.PI/128)+Math.cos( 7.0f* Math.PI/128))/2 - Math.cos( 6.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos( 7.0f*Math.PI/128)) - ( (Math.cos( 7.0f* Math.PI/128)+Math.cos( 8.0f* Math.PI/128))/2 - Math.cos( 7.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos( 8.0f*Math.PI/128)) - ( (Math.cos( 8.0f* Math.PI/128)+Math.cos( 9.0f* Math.PI/128))/2 - Math.cos( 8.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos( 9.0f*Math.PI/128)) - ( (Math.cos( 9.0f* Math.PI/128)+Math.cos(10.0f* Math.PI/128))/2 - Math.cos( 9.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(10.0f*Math.PI/128)) - ( (Math.cos(10.0f* Math.PI/128)+Math.cos(11.0f* Math.PI/128))/2 - Math.cos(10.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(11.0f*Math.PI/128)) - ( (Math.cos(11.0f* Math.PI/128)+Math.cos(12.0f* Math.PI/128))/2 - Math.cos(11.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(12.0f*Math.PI/128)) - ( (Math.cos(12.0f* Math.PI/128)+Math.cos(13.0f* Math.PI/128))/2 - Math.cos(12.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(13.0f*Math.PI/128)) - ( (Math.cos(13.0f* Math.PI/128)+Math.cos(14.0f* Math.PI/128))/2 - Math.cos(13.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(14.0f*Math.PI/128)) - ( (Math.cos(14.0f* Math.PI/128)+Math.cos(15.0f* Math.PI/128))/2 - Math.cos(14.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(15.0f*Math.PI/128)) - ( (Math.cos(15.0f* Math.PI/128)+Math.cos(16.0f* Math.PI/128))/2 - Math.cos(15.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(16.0f*Math.PI/128)) - ( (Math.cos(16.0f* Math.PI/128)+Math.cos(17.0f* Math.PI/128))/2 - Math.cos(16.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(17.0f*Math.PI/128)) - ( (Math.cos(17.0f* Math.PI/128)+Math.cos(18.0f* Math.PI/128))/2 - Math.cos(17.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(18.0f*Math.PI/128)) - ( (Math.cos(18.0f* Math.PI/128)+Math.cos(19.0f* Math.PI/128))/2 - Math.cos(18.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(19.0f*Math.PI/128)) - ( (Math.cos(19.0f* Math.PI/128)+Math.cos(20.0f* Math.PI/128))/2 - Math.cos(19.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(20.0f*Math.PI/128)) - ( (Math.cos(20.0f* Math.PI/128)+Math.cos(21.0f* Math.PI/128))/2 - Math.cos(20.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(21.0f*Math.PI/128)) - ( (Math.cos(21.0f* Math.PI/128)+Math.cos(22.0f* Math.PI/128))/2 - Math.cos(21.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(22.0f*Math.PI/128)) - ( (Math.cos(22.0f* Math.PI/128)+Math.cos(23.0f* Math.PI/128))/2 - Math.cos(22.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(23.0f*Math.PI/128)) - ( (Math.cos(23.0f* Math.PI/128)+Math.cos(24.0f* Math.PI/128))/2 - Math.cos(23.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(24.0f*Math.PI/128)) - ( (Math.cos(24.0f* Math.PI/128)+Math.cos(25.0f* Math.PI/128))/2 - Math.cos(24.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(25.0f*Math.PI/128)) - ( (Math.cos(25.0f* Math.PI/128)+Math.cos(26.0f* Math.PI/128))/2 - Math.cos(25.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(26.0f*Math.PI/128)) - ( (Math.cos(26.0f* Math.PI/128)+Math.cos(27.0f* Math.PI/128))/2 - Math.cos(26.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(27.0f*Math.PI/128)) - ( (Math.cos(27.0f* Math.PI/128)+Math.cos(28.0f* Math.PI/128))/2 - Math.cos(27.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(28.0f*Math.PI/128)) - ( (Math.cos(28.0f* Math.PI/128)+Math.cos(29.0f* Math.PI/128))/2 - Math.cos(28.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(29.0f*Math.PI/128)) - ( (Math.cos(29.0f* Math.PI/128)+Math.cos(30.0f* Math.PI/128))/2 - Math.cos(29.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(30.0f*Math.PI/128)) - ( (Math.cos(30.0f* Math.PI/128)+Math.cos(31.0f* Math.PI/128))/2 - Math.cos(30.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(31.0f*Math.PI/128)) - ( (Math.cos(31.0f* Math.PI/128)+Math.cos(32.0f* Math.PI/128))/2 - Math.cos(31.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(32.0f*Math.PI/128)) - ( (Math.cos(32.0f* Math.PI/128)+Math.cos(33.0f* Math.PI/128))/2 - Math.cos(32.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(33.0f*Math.PI/128)) - ( (Math.cos(33.0f* Math.PI/128)+Math.cos(34.0f* Math.PI/128))/2 - Math.cos(33.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(34.0f*Math.PI/128)) - ( (Math.cos(34.0f* Math.PI/128)+Math.cos(35.0f* Math.PI/128))/2 - Math.cos(34.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(35.0f*Math.PI/128)) - ( (Math.cos(35.0f* Math.PI/128)+Math.cos(36.0f* Math.PI/128))/2 - Math.cos(35.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(36.0f*Math.PI/128)) - ( (Math.cos(36.0f* Math.PI/128)+Math.cos(37.0f* Math.PI/128))/2 - Math.cos(36.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(37.0f*Math.PI/128)) - ( (Math.cos(37.0f* Math.PI/128)+Math.cos(38.0f* Math.PI/128))/2 - Math.cos(37.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(38.0f*Math.PI/128)) - ( (Math.cos(38.0f* Math.PI/128)+Math.cos(39.0f* Math.PI/128))/2 - Math.cos(38.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(39.0f*Math.PI/128)) - ( (Math.cos(39.0f* Math.PI/128)+Math.cos(40.0f* Math.PI/128))/2 - Math.cos(39.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(40.0f*Math.PI/128)) - ( (Math.cos(40.0f* Math.PI/128)+Math.cos(41.0f* Math.PI/128))/2 - Math.cos(40.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(41.0f*Math.PI/128)) - ( (Math.cos(41.0f* Math.PI/128)+Math.cos(42.0f* Math.PI/128))/2 - Math.cos(41.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(42.0f*Math.PI/128)) - ( (Math.cos(42.0f* Math.PI/128)+Math.cos(43.0f* Math.PI/128))/2 - Math.cos(42.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(43.0f*Math.PI/128)) - ( (Math.cos(43.0f* Math.PI/128)+Math.cos(44.0f* Math.PI/128))/2 - Math.cos(43.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(44.0f*Math.PI/128)) - ( (Math.cos(44.0f* Math.PI/128)+Math.cos(45.0f* Math.PI/128))/2 - Math.cos(44.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(45.0f*Math.PI/128)) - ( (Math.cos(45.0f* Math.PI/128)+Math.cos(46.0f* Math.PI/128))/2 - Math.cos(45.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(46.0f*Math.PI/128)) - ( (Math.cos(46.0f* Math.PI/128)+Math.cos(47.0f* Math.PI/128))/2 - Math.cos(46.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(47.0f*Math.PI/128)) - ( (Math.cos(47.0f* Math.PI/128)+Math.cos(48.0f* Math.PI/128))/2 - Math.cos(47.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(48.0f*Math.PI/128)) - ( (Math.cos(48.0f* Math.PI/128)+Math.cos(49.0f* Math.PI/128))/2 - Math.cos(48.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(49.0f*Math.PI/128)) - ( (Math.cos(49.0f* Math.PI/128)+Math.cos(50.0f* Math.PI/128))/2 - Math.cos(49.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(50.0f*Math.PI/128)) - ( (Math.cos(50.0f* Math.PI/128)+Math.cos(51.0f* Math.PI/128))/2 - Math.cos(50.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(51.0f*Math.PI/128)) - ( (Math.cos(51.0f* Math.PI/128)+Math.cos(52.0f* Math.PI/128))/2 - Math.cos(51.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(52.0f*Math.PI/128)) - ( (Math.cos(52.0f* Math.PI/128)+Math.cos(53.0f* Math.PI/128))/2 - Math.cos(52.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(53.0f*Math.PI/128)) - ( (Math.cos(53.0f* Math.PI/128)+Math.cos(54.0f* Math.PI/128))/2 - Math.cos(53.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(54.0f*Math.PI/128)) - ( (Math.cos(54.0f* Math.PI/128)+Math.cos(55.0f* Math.PI/128))/2 - Math.cos(54.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(55.0f*Math.PI/128)) - ( (Math.cos(55.0f* Math.PI/128)+Math.cos(56.0f* Math.PI/128))/2 - Math.cos(55.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(56.0f*Math.PI/128)) - ( (Math.cos(56.0f* Math.PI/128)+Math.cos(57.0f* Math.PI/128))/2 - Math.cos(56.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(57.0f*Math.PI/128)) - ( (Math.cos(57.0f* Math.PI/128)+Math.cos(58.0f* Math.PI/128))/2 - Math.cos(57.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(58.0f*Math.PI/128)) - ( (Math.cos(58.0f* Math.PI/128)+Math.cos(59.0f* Math.PI/128))/2 - Math.cos(58.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(59.0f*Math.PI/128)) - ( (Math.cos(59.0f* Math.PI/128)+Math.cos(60.0f* Math.PI/128))/2 - Math.cos(59.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(60.0f*Math.PI/128)) - ( (Math.cos(60.0f* Math.PI/128)+Math.cos(61.0f* Math.PI/128))/2 - Math.cos(60.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(61.0f*Math.PI/128)) - ( (Math.cos(61.0f* Math.PI/128)+Math.cos(62.0f* Math.PI/128))/2 - Math.cos(61.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(62.0f*Math.PI/128)) - ( (Math.cos(62.0f* Math.PI/128)+Math.cos(63.0f* Math.PI/128))/2 - Math.cos(62.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(63.0f*Math.PI/128)) - ( (Math.cos(63.0f* Math.PI/128)+Math.cos(64.0f* Math.PI/128))/2 - Math.cos(63.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(64.0f*Math.PI/128)) - ( (Math.cos(64.0f* Math.PI/128)+Math.cos(65.0f* Math.PI/128))/2 - Math.cos(64.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(65.0f*Math.PI/128)) - ( (Math.cos(65.0f* Math.PI/128)+Math.cos(66.0f* Math.PI/128))/2 - Math.cos(65.5f*Math.PI/128)) )
      , (short)(0x8000 * (Math.cos(66.0f*Math.PI/128)) - ( (Math.cos(66.0f* Math.PI/128)+Math.cos(67.0f* Math.PI/128))/2 - Math.cos(66.5f*Math.PI/128)) )
}; 
  
  
  
  final static short angle16_degree_emC(float angle) { return (short)(((angle)/90.0f) * 0x4000); }
  final static short angle16_rad_emC(double angle) { return (short)(((angle)/Math.PI) * 0x8000); }

  
  
  final static short cos16_emC_Taylor2(short angle) {
    short angle1 = angle; 
    short sign = 0;
    if(angle <0) { angle1 = (short)-angle; }    //cos is 0-y-axes symmetric. Note: 0x8000 results in 0x8000.
    if( (angle1 & 0x4000) !=0) {
      angle1 = (short)(0x8000 - angle1);          //cos is point-symmetric on 90°
      sign = -0x8000;
    }
    //now angle1 is in range 0000...0x4000
    int ixTable = angle1 >> 10;         // 16 entries for linear approximation
    short cos1 = cosTable[ixTable];
    short cos2 = cosTable[ixTable+1];
    short cos3 = cosTable[ixTable+2];
    long dcos = cos3 - cos1;
    long dcos1 = cos2 - cos1;
    long dcos2 = cos3 - cos2;
    long ddcos = dcos2 - dcos1; //cos2 - cos1 - cos3 + cos2
    long diff = angle1 & 0x3ff;
    short corr = (short)((diff * dcos1) >>10);
    short corr2 = (short)((diff * diff * ddcos) >>20);
    System.out.printf("%1.5f %1.5f", (float)corr / (float)0x7fff, (float)corr2 / (float)0x7fff);
    short cos0 = (short)(cos2 + corr + corr2);
    if(sign <0) return (short)-cos0;
    else return cos0;
  }

  
  
  //https://www.astro.uni-jena.de/Teaching/Praktikum/pra2002/node288.html ... quadratische Interpolation
  
  final static short interpolqu16(short x, short[] table) {
    assert(x >=0 && x <= 0x4000);      
    int ixTable = x >> 10;         // 16 entries for linear approximation
    int diff = x & 0x3ff;
    short cos1, cos2, cos3;
    if( (diff & 0x200) !=0) {
      diff -= 0x400;                    //seen from the other side, get negative.
      cos1 = table[ixTable+1];
      cos2 = table[ixTable+2];
      cos3 = table[ixTable+3];
    } else {
      cos1 = table[ixTable];
      cos2 = table[ixTable+1];
      cos3 = table[ixTable+2];
    }
    int diff2 = (diff * diff)>>5;
    short dcos31 = (short)((cos3 - cos1));
    short dcos2 = (short)(((cos3 -cos2) - (cos2 - cos1)));
    short yd1 = (short)((diff * dcos31)>>11);
    short yd2 = (short)((diff2 * dcos2)>>16);
    short ret = (short)(cos2 + yd1 + yd2);
    return ret;
  }
  
  
  
  
  final static short cos16q_emC(short angle) {
    short angle1 = angle; 
    short sign = 0;
    if(angle <0) { angle1 = (short)-angle; }    //cos is 0-y-axes symmetric. Note: 0x8000 results in 0x8000.
    if( (angle1 & 0x4000) !=0) {
      angle1 = (short)(0x8000 - angle1);          //cos is point-symmetric on 90°
      sign = -0x8000;
    }
    //now angle1 is in range 0000...0x4000
    return interpolqu16(angle, cosTable);
  }
  

  
  final static short atan2_16_emC(short x, short y) {
    if(x == 0x8000) { return x; }//it is -180°
    if(y == 0x8000) { return (short)0xc000; } // -270°
    int angle = 0;
    int segm = 0;
    if( x < 0) { 
      segm |=1;
      //angle = 0x4000; 
      x = (short)(-x); 
    }
    if( y < 0 ) {
      segm |=2;
      //angle += 0x8000;
      y = (short)-y;
    }
    if(x < y) {
      angle += 0x4000 - interpolqu16(x, arcTable);
    } else {
      angle += interpolqu16(y, arcTable);
      
    }
    switch(segm) {
    case 0: return (short)angle;
    case 1: return (short)(0x8000 - angle);
    case 2: return (short)(-angle);
    case 3: return (short)(0x8000 + angle);
    }
    return 0;
  }
  
  
  
  
  
  
  final static short cos16_emC_LinInterpol(short angle) {
    short angle1 = angle; 
    short sign = 0;
    if(angle <0) { angle1 = (short)-angle; }    //cos is 0-y-axes symmetric. Note: 0x8000 results in 0x8000.
    if( (angle1 & 0x4000) !=0) {
      angle1 = (short)(0x8000 - angle1);          //cos is point-symmetric on 90°
      sign = -0x8000;
    }
    //now angle1 is in range 0000...0x4000
    int ixTable = angle1 >> 10;         // 16 entries for linear approximation
    short cos1 = cosTable[ixTable+1];
    short cos2 = cosTable[ixTable+2];
    long dcos = cos2 - cos1;
    long diff = angle1 & 0x3ff;
    short corr = (short)((diff * dcos) >>10);
    System.out.printf("%1.5f ", (float)corr / (float)0x7fff);
    short cos0 = (short)(cos1 + corr);
    if(sign <0) return (short)-cos0;
    else return cos0;
  }

  
  final static short cos16_emC_LinInterpol32(short angle) {
    short angle1 = angle; 
    short sign = 0;
    if(angle <0) { angle1 = (short)-angle; }    //cos is 0-y-axes symmetric. Note: 0x8000 results in 0x8000.
    if( (angle1 & 0x4000) !=0) {
      angle1 = (short)(0x8000 - angle1);          //cos is point-symmetric on 90°
      sign = -0x8000;
    }
    //now angle1 is in range 0000...0x4000
    int ixTable = angle1 >> 9;         // 32 entries for linear approximation
    short cos1 = cosTable2[ixTable+1];
    short cos2 = cosTable2[ixTable+2];
    long dcos = cos2 - cos1;
    short cos0, corr;
    long diff;
    if( (angle1 & 0x0100) ==0) {
      diff = angle1 & 0x1ff;
      corr = (short)((diff * dcos) >>9);
      cos0 = (short)(cos1 + corr);
    } else {
      diff = (angle1 | 0xffffff00);
      corr = (short)((diff * dcos) >>9);
      cos0 = (short)(cos2 + corr);
    }
    System.out.printf("%1.5f ", (float)corr / (float)0x7fff);
    if(sign <0) return (short)-cos0;
    else return cos0;
  }

  
  final static void testTaylor2() {
    short angle = angle16_degree_emC(45.0f); //45°
    short angle9 = angle16_degree_emC(51.0f);
    int step = 0;
    while(angle < angle9) {
      //short cos16 = cos16_emC_Taylor2(angle);
      short cos16 = cos16_emC_LinInterpol32(angle);
      float cosfi = (float)cos16 / (float)0x7fff;
      float anglef = 180.0f * angle / 0x8000;
      float cosf1 = (float)Math.cos(anglef / 180.0f * Math.PI);
      float dcos = cosfi - cosf1;
      System.out.printf(" *%d  g=%3.3f %1.5f %1.5f  %1.5f \n", step, anglef, cosfi, cosf1, dcos);    
      angle += angle16_degree_emC(0.2f) ;
      step +=1;
    }
  }
  
  
  final static void testinterpol() {
    short angle = angle16_degree_emC(45.0f); //45°
    short angle9 = angle16_degree_emC(51.0f);
    int step = 0;
    while(angle < angle9) {
      short cos16 = cos16_emC_Taylor2(angle);
      float cosfi = (float)cos16 / (float)0x7fff;
      float anglef = 180.0f * angle / 0x8000;
      float cosf1 = (float)Math.cos(anglef / 180.0f * Math.PI);
      float dcos = cosfi - cosf1;
      System.out.printf(" *%d  g=%3.3f %1.5f %1.5f  %1.5f \n", step, anglef, cosfi, cosf1, dcos);    
      angle += angle16_degree_emC(0.2f) ;
      step +=1;
    }
  }
    
    
  final static void testTaylor1() {
    short angle = angle16_degree_emC(45.0f); //45°
    short angle9 = angle16_degree_emC(51.0f);
    int step = 0;
    while(angle < angle9) {
      short cos16 = cos16_emC_Taylor2(angle);
      float cosfi = (float)cos16 / (float)0x7fff;
      float anglef = 180.0f * angle / 0x8000;
      float cosf1 = (float)Math.cos(anglef / 180.0f * Math.PI);
      float dcos = cosfi - cosf1;
      System.out.printf(" *%d  g=%3.3f %1.5f %1.5f  %1.5f \n", step, anglef, cosfi, cosf1, dcos);    
      angle += angle16_degree_emC(0.2f) ;
      step +=1;
    }
  }
  
  
  
  
  public static int[] createCosTable() {
    int[] table = new int[65];
    double dangle2 = Math.PI / 64 / 2 ;          // diff angle for 1/2 to left and right from point.

    double yleft = Math.cos(- dangle2);          // first left point
    for(int ix = 0; ix <= 64; ++ix) {
      double angle = ix* (Math.PI / 64);         // angle of the point.
      double yp0 = Math.cos(angle);              // the exact supporting point
      double yright = Math.cos(angle + dangle2);
      double dy = yright - yleft;                // gain for this segment from left to right point
      double yleft_ = yp0 - dy;
      double yright_ = yp0 + dy;
      double yleftErr = yleft - yleft_;          //error between linear approx. point and exact point
      double yrightErr = yright - yright_;
      double yp = yp0 + (yleftErr + yrightErr) /2/2;  //supporting point adjusted
      //
      int yi = (int)(yp * 0x8000);               //supporting point as integer.
      short dyi = (short)(dy * 0x8000);          //gain as integer, as used later.
      //
      if(ix == 32)
        Debugutil.stop();
      double yerrsum = 0;                        // check the sum of all abbreviation 
      short anglei0 = (short)(0x200 * ix);       // angle to test, the correct segment. 
      
      for(int angleix = -0x100; angleix < 0x0ff; ++angleix) {  // values calculated by this interpolation
        short anglei = (short)(anglei0 + angleix);
        int yi1 = yi + (((short)((anglei<<7) & 0xffff) * dyi + 0x8000)>>16);
        double yif1 = yi1 / ((double)(0x8000));
        double yf1 = Math.cos(anglei * (Math.PI / 0x8000));
        double yerr = (yf1 - yif1) * 0x8000;
        yerrsum += yerr;
        anglei +=1;
      }
      double corrf = yerrsum / 0x200;            // div by nrofPoints summed. 
      
      short corr = (short)(corrf);      // converted ..1.0 => ..0x8000. 
      yi += corr;                                //shift the supporting point for equal area (area error ==0)
      
      int val = yi<<16 | (dyi & 0xffff);
      table[ix] = val;
      yleft = yright;                                 // use the right point as left point for next segment.
    }
    //corr first and last entry, known from cos values
    int dycorr = 0x7fff - (table[0]>>16); 
    table[0] = 0x7fff0000 + (-dycorr & 0xffff);  
    dycorr = 0x8000 - (table[64]>>16);  //note: calculated as positive number 
    table[64] = 0x80000000 + (dycorr & 0xffff);  
    //
    //check the table
    int errimin = 0x8000; int errimax = -0x8000; 
    for(int angle = 0; angle <= 0x4000; angle += 0x10) {
      int ix = (angle + 0x100) >>9;
      int valTable = table[ix];
      int cosi = (valTable >> 16);
      int cosid = (short)(((short)((angle<<7) & 0xffff) * ((short)(valTable & 0xffff)))>>16);
      cosi += cosid;
      double cosf = Math.cos(angle * (Math.PI / 0x8000));
      int cosif = (int)(cosf * 0x8000);
      if(cosif == 0x8000) { cosif = 0x7fff; }
      int dcos = cosi - cosif;
      if(dcos > errimax) { 
        errimax = dcos; 
      }
      System.out.printf("%3.3f %4X => %4X %4X %d\n", angle * (90.0f/0x4000), angle, cosi, cosif, dcos);
      if(dcos < errimin) { errimin = dcos; }
    }
    
    FileWriter wr = null;
    try {
      int[] cosTable2 = createCosTable();
      wr = new FileWriter("T:/cosTable.c", false);
      CreateTables_fix16Operations.writeCcodeTable(wr, cosTable2, 9, "cosTable");
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
  
  
  
  
  static void writeCcodeTableInterpolQu ( FileWriter wr, short[] table, String name ) 
  throws IOException {
    wr.append("\n\n");  
    wr.append("static const int16 ").append(name).append("[][3] = \n");
    int valz = 0x7fff;
    String sep = "{"; 
    short val0 = table[0];
    short val1 = table[1];
    for(int ix = 2; ix < table.length; ++ix) {
      short val2 = table[ix];
      short dval1 = (short)(val2 - val0);
      short dval2 = (short)((val2 - val1) - (val1 - val0));
      String sVal1 = Integer.toHexString(val1);
      //                                                   // output preparation for hex:
      if(sVal1.length() >4) { sVal1 = sVal1.substring(sVal1.length()-4, sVal1.length()); }  //without too much leading FFF 
      if(sVal1.length() <4) { sVal1 = "0000".substring(0, 4-sVal1.length()) + sVal1; }  //with leading 000 
      String sdVal1 = Integer.toHexString(dval1);
      
      if(sdVal1.length() >4) { sdVal1 = sdVal1.substring(sdVal1.length()-4, sdVal1.length()); }  //without too much leading FFF 
      if(sdVal1.length() <4) { sdVal1 = "0000".substring(0, 4-sdVal1.length()) + sdVal1; }  //with leading 000 
      String sdVal2 = Integer.toHexString(dval2);
      
      if(sdVal2.length() >4) { sdVal2 = sdVal2.substring(sdVal2.length()-4, sdVal2.length()); }  //without too much leading FFF 
      if(sdVal2.length() <4) { sdVal2 = "0000".substring(0, 4-sdVal2.length()) + sdVal2; }  //with leading 000 
      //                                                   // wr line
      wr.append(sep).append(" { 0x").append(sVal1)
                     .append(", 0x").append(sdVal1)
                     .append(", 0x").append(sdVal2)
                     .append(" } // ").append("" + (ix-2)).append("\n");
      sep = ",";
      val0 = val1; val1 = val2;
    }
    wr.append("};\n");
  }
  
  
  
  static void testatan2_16 ( ) {
    short x45 = (short)(Math.cos(Math.PI/4) * 0x4000);
    short a45 = atan2_16_emC(x45, x45);
    
    for(int ix =0; ix < 45; ++ix) {
      double angle = 8 * ix / 180.0 * Math.PI;
      short x = (short)(Math.cos(angle) * 0x4000);
      short y = (short)(Math.sin(angle) * 0x4000);
      short a16 = atan2_16_emC(x, y);
      short x16 = angle16_rad_emC(angle);
      short err = (short)(a16 - x16);
      float degree = a16 / (float)0x4000 * 90.0f;
      System.out.printf("%3.3f %4X %4X %4X %4X %4X\n", (float)(180.0/Math.PI *angle), x, y, a16, x16, err);
    }
  }
  
  
  
  
  
  
  static void test_cos16q_emC ( ) {
    short start = 0x0;
    short end = 0x480;
    short dangle = 0x0020;
    short angle = start;
    float cosvalz = 0.707f;
    while(angle < end) {
      float fangle = angle / (float)(0x4000) * 90.0f;
      short cosvalcmp = (short)(Math.cos(fangle / 180.0f * Math.PI)*0x8000);
      short res = cos16q_emC(angle);
      
      float cosval = res / (float)(0x8000) * 1.0f;
      float dcosval = cosval - cosvalz;
      
      int cosderror = cosvalcmp - res;
      cosvalz = cosval;
      System.out.printf("%4X %3.3f => %1.6f d=%1.6f e=%d\n", angle, fangle, cosval, dcosval, cosderror);
      angle += dangle;
    }
  }
  

  
  
  
  
  
  public final static void main(String[] args) {
    //testatan2_16();
    //test_cos16q_emC();
    CreateTables_fix16Operations.createSqrtTable();
    //createCosTable();
 //   testTaylor2();
  }

  
  
}
