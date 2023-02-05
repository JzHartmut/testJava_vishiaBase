package org.vishia.util.test;

public class Test_mutex {

  Access data = new Access(); 
  
  static class Access {
    int v1, v2 =3;
    boolean bData = false;
    boolean bWait = false;
    boolean bNext = false;
  }
  
  public static void main(String[] args) {
    Test_mutex thiz = new Test_mutex();
    thiz.exec();
  }
  
  
  void exec() {
    int baseVal = 0;
    int ctWaitbNext = 0;
    Thread treadmng2 = new Thread(thread3);
    treadmng2.start();
    while(true){
      if(++baseVal >= 10) { baseVal = 0; }
      synchronized(data){                       // with mutex, calculate some data.
        data.v1 = baseVal; 
        data.v2 = baseVal+3;
      }
      synchronized(data) {                       // with mutex: Thread synchronization
        data.bNext = false;                      // remark, wait for data evaluation
        data.bData = true;                       // notice that data are prepared
        if(data.bWait) {                         // only if it is in wait
          data.notify();                         // then notify the other thread
        }
      }
      while(!data.bNext) {                       // wait for data evaluation
        try { Thread.sleep(10);                  // but wait at least 10 ms
        } catch (InterruptedException e) {
          System.out.println("unexpected interrupted sleep");
        }
        if(! data.bNext ) {                      // if after 10 ms no evaluation has occureed
          System.out.print('?');                 // then show it.
          if(++ctWaitbNext >10) {
            ctWaitbNext = 0;
            System.out.println("");
          }
        }
      } // while                                  // data.bNext == true detected, outer loop
    }
    
  }
  
  
  /**Second thread to check whether data are consistent
   * 
   */
  Runnable thread2 = new Runnable() {
    @Override public void run () {
      while(true) {
        int sum;
        synchronized(data) {
          sum = data.v1 - data.v2;
        }
        if (sum != -3) {
          System.out.println("error");
        } else {
          System.out.print(':');
        }
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    
      
  };

  
  Runnable thread3 = new Runnable() {

    @Override public void run () {
      int nLine = 0;
      while(true) {
        int sum;
        try {
          synchronized(data) {                   // should be wrappend with mutex
            if(!data.bData) {
              data.bWait = true;
              data.wait();                       // wait for notify, in the wait, mutex is freed
              data.bWait = false;
            }
            data.bData = false;
          }
        } catch (InterruptedException e) {
          System.out.println("stupid");
        }
        sum = data.v1 - data.v2;
        if (sum != -3) {
          System.out.println("error");
        } else {
          System.out.print('.');
          if(++nLine >=100  ) {
            nLine = 0;
            System.out.println("");
          }
        }
        data.bNext = true;
      }
    }
  };
  
}
