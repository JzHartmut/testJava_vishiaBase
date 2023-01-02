package org.vishia.socket.test;

import java.nio.charset.Charset;

import org.vishia.byteData.ByteDataAccessSimple;
import org.vishia.byteData.ByteDataSymbolicAccess;
import org.vishia.byteData.ByteDataSymbolicAccess.Variable;
import org.vishia.communication.SocketCommSimple;

public class TestCyclicUdp {
  /**Version and history
   * <ul>
   * <li>2012-08 created
   * </ul>
   */
  public static final String version = "2022-08-26";

  boolean bRun;
  boolean bFinished;
  
  int rxCt = 0;
  
  int waitMainloop_ms = 120;

  final SocketCommSimple so = new SocketCommSimple();
  
  
  /**The current time for points. Note that while debugging the real time will be faulty.
   * Hence this variable is set on start with the absolute time, and then increment by 1 ms for each step.
   */
  long currTime = System.currentTimeMillis();
  
  /**Angle and output value of an oscillation which values are transmitted. */
  volatile float w1, w2, y1, y2;
  
  /**Parameter of the oscillation, angle growth 200 * 10 ms =^ 0.5 Hz */
  float dw1 = (float)(Math.PI) / 20.0f, my1 = 0.5f;
  float dw2 = (float)(Math.PI) / 1000.0f, my2 = 2.0f;
  
  /**Byte buffer for a transmission telegram.  */
  byte[] txData = new byte[0x40];  
  
  ByteDataAccessSimple dataAccess = new ByteDataAccessSimple(this.txData, true);  //bit endian
  
  ByteDataSymbolicAccess txDataAccess = new ByteDataSymbolicAccess(this.dataAccess);
  
  ByteDataSymbolicAccess.Variable way = this.txDataAccess.new Variable("measval", "measval", 0x20, 'S', 0);
  
  ByteDataSymbolicAccess.Variable time_msAfter1970 = this.txDataAccess.new Variable("msAfter1970", "msAfter1970", 0x18, 'J', 0);
  
  TestCyclicUdp(){
    this.txDataAccess.assignData(this.txData, 0);
    this.dataAccess.setIntVal(0x10, 2, 0x1f0);             // the length info on begin of the data
  }
  
  void init(String[] args) {
    Thread rxThread = new Thread(this.runRx, "runRx");
    this.bRun = true;
    boolean b69 = false;
//    int addr = b69? 0xc0a80345 : 0xc0a80344;  //192.168.3.69
    int addr = 0xc0a80345;                       // use a dedicated network card with this configured address on the PC 
    //int addr = 0x7f000001;  //127.0.0.1 localhost
    int port = 0xa005;                           // any free port todo search
    this.so.open(addr, port);
    

    //addr = b69? 0xc0a80344 : 0xc0a80345;  //192.168.3.68
    addr = 0xc0a80447;                           // the dst network card in this network 
    //addr = 0x7f000001;  //127.0.0.1 localhost
    port = 0xeab5;                               //receiver port
    this.so.setDst(addr, port);
    rxThread.start();
  }
  
  void close() {
    this.bRun = false;
    this.so.close();
  }
  
  
  void rxStep() {
    byte[] rxBuffer = new byte[100];
    
    int zRx = this.so.rx(rxBuffer);
    if(zRx >0) {
      String s = new String(rxBuffer, 0, zRx, Charset.forName("ISO-8859-1"));
      System.out.print(s);
    }

  }
  
  
  void txStep() {
    this.w1 += this.dw1;
    this.y1 = this.my1 * (float)(Math.sin(this.w1));
    this.w2 += this.dw2;
    this.y2 = this.my2 * (float)(Math.sin(this.w2));
    //txDataAccess.
    this.time_msAfter1970.setLong(++this.currTime);
    this.way.setInt((int)(10000 * (this.y1 + this.y2)));
    this.so.tx(this.txData, this.txData.length);
  }
  
  
  Runnable runRx = new Runnable() {

    @Override public void run () {
      while(TestCyclicUdp.this.bRun) {
        rxStep();
      }
      TestCyclicUdp.this.bFinished = true;
    }
  };
  
  
  
  void mainLoop ( ) {
    while(!this.bFinished) {
      try {
        Thread.sleep(this.waitMainloop_ms);
        this.txStep();
      } catch (InterruptedException e) {}
      //this.tx("Hello UDP " + this.rxCt + "\n");
    }
    
  }
  
  
  
  public static void main ( String[] args) {
    TestCyclicUdp thiz = new TestCyclicUdp();
    thiz.init(args);
    thiz.mainLoop();
  }

}
