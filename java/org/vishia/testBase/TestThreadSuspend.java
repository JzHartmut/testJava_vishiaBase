package org.vishia.testBase;

/**This is an example for multithreading. 
 * As recommended in Java {@link Thread#suspend()} and {@link Thread#destroy()} is not used.
 * Instead the thread2 itself asks for suspend and stop request
 * and executes it in its own life cycle. 
 * @author Hartmut Schorrig
 * @date 2023-07-05
 *
 */
public class TestThreadSuspend {

  
  boolean thread2Stop = false;
  boolean suspend2 = false;
  
  
  /**as usual in Java: A Runnable class ist the wrapper for the Thread function */
  Runnable thread2Fn = new Runnable() {        
    @Override public void run () {
      TestThreadSuspend.this.run2();             // call the run2() in the outer class  
    }
  };
  
  
  /**Defines and creates the thread, do not start yet. 
   * start needs thread2.start(); */
  Thread thread2 = new Thread(this.thread2Fn, "thread2");
  
  
  /**The run operation for the thread2
   * 
   */
  void run2 () {
    while(!this.thread2Stop) {                   // query the finish or stop command from outside by itself. 
      try {                                      // means, do the stop if it is proper for own. 
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // unexpected, do nothing
      }
      if(this.suspend2) {                        // query the suspend command from outside by itself
        try {                                    // means, do the suspend if it is proper for own. 
          synchronized(this.thread2Fn) {         // use the threadFn as Object for wait/notify
            this.thread2Fn.wait();
          }
        } catch (InterruptedException e) {
          // unexpected, do nothing
        }
      }
      System.out.println("thread2");
    }
    System.out.println("thread2 finished");
    
  }
  
  
  
  
  /**Init of the main class functionality */
  void init() {
    this.thread2.start();
  }
  
  
  /**Run operation for the main*/
  void run() {
    int time = 0;
    while(time < 12) {
      time +=1;
      try {
        Thread.sleep(1500);
      } catch (InterruptedException e) {
        // unexpected, do nothing
      }
      System.out.println("thread1 time = " + time);
      if(time == 3) {
        this.suspend2 = true;                    // give a suspend command to thread2
      }
      else if(time == 7) {
        this.suspend2 = false;                   // release the suspend, should notify the thread2
        synchronized(this.thread2Fn) {           // use the threadFn as Object for wait/notify
          this.thread2Fn.notify();
        }
      }
      else if(time == 10) {                      // give a stop command to thread2
        this.thread2Stop = true; 
      }
    }
    System.out.println("main thread finished");
  }
  
  
  public static void main(String[] args) {
  
    TestThreadSuspend thiz = new TestThreadSuspend();
    thiz.init();
    thiz.run();
    
  }
  
}
