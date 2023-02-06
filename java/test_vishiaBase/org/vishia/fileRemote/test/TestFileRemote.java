package org.vishia.fileRemote.test;

import java.io.IOException;
import java.util.EventObject;

import org.vishia.event.EventConsumer;
import org.vishia.event.EventSource;
import org.vishia.event.EventTimerThread;
import org.vishia.event.EventTimerThread_ifc;
import org.vishia.fileRemote.FileMark;
import org.vishia.fileRemote.FileRemote;
import org.vishia.fileRemote.FileRemoteProgressTimeOrder;
import org.vishia.util.Debugutil;
import org.vishia.util.TestOrg;

public final class TestFileRemote {

  protected boolean callbackDone; 
  
  
  long time_ms;
  
  
  protected void startSeconds() {
    this.time_ms = System.currentTimeMillis();
  }
  
  protected float getSeconds() {
    long timeNew = System.currentTimeMillis();
    float ret = (timeNew - this.time_ms)/1000.0f;
    //this.time_ms = timeNew;
    return ret;
  }
  
  public void test_copyOneThread(TestOrg parent) {
    TestOrg test = new TestOrg("test_copy", 5, parent);
    FileRemote fsrc = FileRemote.get("d:\\vishia\\Java\\deploy\\vishiaBase-2022-07-26-source.zip");
    FileRemote fdst = FileRemote.get("T:/testcopy.zip");
    fsrc.copyTo(fdst, null);
    test.finish();
  }
  
  
  EventSource evSrc = new EventSource("test FileRemote") {
  };

  
  
  public void test_checkPhysicalDevice(TestOrg parent) {
    TestOrg test = new TestOrg("test_checkPhysicalDevice", 5, parent);
    // Note TODO for common test use input files paths refer to your different physical devices.
    //tag::getFileStore[]
    FileRemote fsrc = FileRemote.get("q:\\VIDEOS\\telgRingCommAnimation.gif.gif");
    FileRemote fdst = FileRemote.get("T:/telgRingCommAnimation.gif");
    try {
      java.nio.file.FileStore fstore5rc = java.nio.file.Files.getFileStore(fsrc.path);
      java.nio.file.FileStore fstoreDst = java.nio.file.Files.getFileStore(fdst.path);
      test.expect(fstore5rc != fstoreDst, 4, "different phyisical devices for %s... and %s...", fsrc.getAbsolutePath().substring(0,8), fdst.getAbsolutePath().substring(0,8));
    } catch (IOException exc) {
      test.exception(exc);
    }
    //end::getFileStore[]
    //tag::getFileSystem[]
    java.nio.file.FileSystem fsSrc = fsrc.path.getFileSystem();
    java.nio.file.FileSystem fsDst = fsrc.path.getFileSystem();
    test.expect(fsSrc == fsDst, 4, "same file systems for %s... and %s...", fsrc.getAbsolutePath().substring(0,8), fdst.getAbsolutePath().substring(0,8));
    Iterable<java.nio.file.FileStore> fstoresSrc = fsSrc.getFileStores();
    for(java.nio.file.FileStore fstoreSrc: fstoresSrc) {
      System.out.println(fstoreSrc.toString());
    }
    Iterable<java.nio.file.FileStore> fstoresDst = fsSrc.getFileStores();
    for(java.nio.file.FileStore fstoreDst: fstoresDst) {
      System.out.println(fstoreDst.toString());
    }
    //end::getFileSystem[]
    test.finish();
  }
  
  
  
  //tag::test_copyWithCallback[]
  public void test_copyWithCallback(TestOrg parent) {
    TestOrg test = new TestOrg("test_copy", 5, parent);
    FileRemote fsrc = FileRemote.get("q:\\VIDEOS\\telgRingCommAnimation.gif.gif");
    FileRemote fdst = FileRemote.get("T:/telgRingCommAnimation.gif");
    fdst.device().activate();                    // start the device thread
    long time0 = System.nanoTime();
    FileRemote.CallbackEvent backEvent = new FileRemote.CallbackEvent(this.callbackCopy, null, this.evSrc);
    backEvent.occupy(null, true);                // use an event for back info
    backEvent.setFiles(fsrc, fdst);              // the back event contains the forward event
    this.callbackDone = false;                   // init definitely state of callback 
    long time1 = System.nanoTime();
    fsrc.copyTo(fdst, backEvent);                // this is the copy routine with callback
    long dTimeCall = System.nanoTime() - time1;
    waitfinish(backEvent);                       // wait, notify from callback
    //... this may be normally in the callback operation:
    String sError = backEvent.errorMsg;          // possible exception message
    FileRemote.CallbackCmd cmdback = backEvent.getCmd();   // check correctness of operation
    backEvent.relinquish();                      // relinquish the event for next usage
    long dTimeRespond = System.nanoTime() - time1;
    //... end normally in callback operation
    //========== write test results:             // note: it is not tested whether the file is copied yet.
    test.expect(cmdback == FileRemote.CallbackCmd.done && sError ==null, 4, "copy operation returns 'done'");
    test.expect(dTimeCall < 2000000, 3, "time for call < 2 ms = %3.3f", dTimeCall/1000000.0);
    test.expect(dTimeRespond > 10000000, 2, "time for execution > 10 ms = %3.3f ms", dTimeRespond/1000000.0);
    System.out.printf("timePrepare = %f, timeCall = %f ms, timeRespond = %f ms\n"
        , (time1 - time0)/1000000.0, dTimeCall/1000000.0, dTimeRespond/1000000.0);
    test.finish();
    try { fdst.device().close(); } catch (IOException e) { }
  }
  //end::test_copyWithCallback[]
  
  
  
  protected void waitfinish ( Object syncObj) {
    synchronized(this) {                         // wait for notify
      while(!this.callbackDone ) {                  // wait only if notify will still be executed
        try { this.wait(10000); } catch(InterruptedException exc) {}
      }
    }
    
  }

  
  protected void notifyfinish ( Object syncObj ) {
    synchronized(TestFileRemote.this) {
      TestFileRemote.this.callbackDone = true;
      TestFileRemote.this.notify();
    }
  }

  
  
  //tag::callbackCopy[]
  EventConsumer callbackCopy = new EventConsumer() {
    @Override public int processEvent ( EventObject ev ) {
      //FileRemote.CallbackEvent backEvent = (FileRemote.CallbackEvent) ev;
      notifyfinish( ev );
      return mEventDonotRelinquish;      // will be relinquished after wait
    }
  };
  //end::callbackCopy[]
  

  EventTimerThread execThread;

  
  
  
  
  //tag::test_copyDirTreeWithCallback[]
  public void test_copyDirTreeWithCallback(TestOrg parent) {
    TestOrg test = new TestOrg("test_copy", 5, parent);
    this.execThread = new EventTimerThread("testFileRemote");
    FileRemoteProgressTimeOrder copyProgress = new FileRemoteProgressTimeOrder( "copyProgress", this.evSrc, this.progress, this.execThread, 100);
    boolean bOk = copyProgress.occupy(this.evSrc, this.progress, this.execThread, true);
    test.expect(bOk, 6, "copyProgress timeorder occupied");
    startSeconds();
    this.execThread.start();
    copyProgress.clear();
    copyProgress.activate(100);                  // show status in callbackCopy just in 200 ms cycle.
    FileRemote fsrc = FileRemote.get("d:\\vishia\\Java\\docuSrcJava_vishiaBase\\org");
    this.callbackDone = false;                   // init definitely state of callback 
    fsrc.refreshAndMark(false, "**/*.html", FileMark.select, 20, null, copyProgress);
    waitfinish(copyProgress);
    //fsrc.refreshPropertiesAndChildren(null, true, null);
    FileRemote fdst = FileRemote.get("T:/testCopyDirTree");
    fdst.mkdir();
    this.callbackDone = false;                   // init definitely state of callback 
    fdst.device().activate();                    // start the device thread
    copyProgress.setAvailClear();
    copyProgress.activate(100);
    long time0 = System.nanoTime();
    this.callbackDone = false;                   // init definitely state of callback 
    long time1 = System.nanoTime();
    //======>>>>
    fsrc.copyDirTreeTo(fdst, 3, "**/*", 0, null, copyProgress);                // this is the copy routine with callback
    System.out.printf("%1.1f finsih\n", getSeconds());

    long dTimeCall = System.nanoTime() - time1;
    waitfinish(copyProgress);
    long dTimeRespond = System.nanoTime() - time1;
    copyProgress.relinquish();
    //... this may be normally in the callback operation:
    //... end normally in callback operation
    //========== write test results:             // note: it is not tested whether the file is copied yet.
    test.expect(dTimeCall < 2000000, 3, "time for call < 2 ms = %3.3f", dTimeCall/1000000.0);
    test.expect(dTimeRespond > 10000000, 2, "time for execution > 10 ms = %3.3f ms", dTimeRespond/1000000.0);
    System.out.printf("timePrepare = %f, timeCall = %f ms, timeRespond = %f ms\n"
        , (time1 - time0)/1000000.0, dTimeCall/1000000.0, dTimeRespond/1000000.0);
    this.execThread.close();
    test.finish();
    try { fdst.device().close(); } catch (IOException e) { }
  }
  //end::test_copyDirTreeWithCallback[]
  


  
  
  EventConsumer progress = new EventConsumer() {

    long nrBytesAllCmp = 0;

    @Override public int processEvent ( EventObject ev ) {
      FileRemoteProgressTimeOrder evProgress = (FileRemoteProgressTimeOrder)ev;
      float time = getSeconds();
      StringBuilder msg = new StringBuilder();
      if(this.nrBytesAllCmp == evProgress.nrofBytesAll) {
        Debugutil.stop();
      }
      this.nrBytesAllCmp = evProgress.nrofBytesAll;
      if(!evProgress.bDone) {
        evProgress.activate(100); //evProgress.delay);
      } else {
        msg.append("done");
        notifyfinish(this);
      }
      System.out.printf("%1.1f: bytes all:%d files aa: %d, %s\n", time, evProgress.nrofBytesAll, evProgress.nrFilesProcessed, msg);
      Debugutil.stop();
      return 0;
    }
    
  };
  
 
  
  public static final void main(String[] args){ test(args);}
  
  public static final void test ( String[] args ){
    TestOrg test = new TestOrg("TestFileRemote", 5, args);
    TestFileRemote thiz = new TestFileRemote();
    //thiz.test_checkPhysicalDevice(test);
    //thiz.test_copyWithCallback(test);
    thiz.test_copyDirTreeWithCallback(test);
    //thiz.execute2();
  }

}
