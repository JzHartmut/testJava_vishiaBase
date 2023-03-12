package org.vishia.fileRemote.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.Format;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import org.vishia.event.EventConsumer;
import org.vishia.event.EventConsumerAwait;
import org.vishia.event.EventSource;
import org.vishia.event.EventTimerThread;
import org.vishia.event.EventTimerThread_ifc;
import org.vishia.event.EventWithDst;
import org.vishia.fileRemote.FileMark;
import org.vishia.fileRemote.FileRemote;
import org.vishia.fileRemote.FileRemoteProgress;
import org.vishia.fileRemote.FileRemoteProgressEvData;
import org.vishia.fileRemote.FileRemoteWalkerCallbackLog;
import org.vishia.fileRemote.XXXFileRemoteWalkerEvent;
import org.vishia.msgDispatch.LogMessage;
import org.vishia.util.Debugutil;
import org.vishia.util.TestOrg;

public final class TestFileRemote {

  protected boolean callbackDone; 
  
  
  long time_ms;
  
  /**Only if true then output some information about data, progress etc. 
   * If false prevent this outputs to better see test results.
   */
  boolean bInfo;
 
  static class Msg {
    long date;
    String src;
    String msg;
    
    public Msg(long date, String src, String msg) {
      this.date = date;
      this.src = src;
      this.msg = msg;
    }
    
    @Override public String toString() {
      StringBuilder ret = new StringBuilder(200);
      ret.append(LogMessage.timeMsg(this.date, this.src));
      ret.append(": ").append(msg);
      return ret.toString();
    }
  }
  
  List<Msg> msg = new LinkedList<Msg>();
  
  protected void startSeconds() {
    this.time_ms = System.currentTimeMillis();
  }
  
  protected float getSeconds() {
    long timeNew = System.currentTimeMillis();
    float ret = (timeNew - this.time_ms)/1000.0f;
    //this.time_ms = timeNew;
    return ret;
  }
  
  
  private void info(String txt) {
    if(this.bInfo) {
      System.out.println(txt);
    }
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
      info(fstoreSrc.toString());
    }
    Iterable<java.nio.file.FileStore> fstoresDst = fsSrc.getFileStores();
    for(java.nio.file.FileStore fstoreDst: fstoresDst) {
      info(fstoreDst.toString());
    }
    //end::getFileSystem[]
    test.finish();
  }
  
  
  
  //tag::test_copyWithCallback[]
//  public void test_copyWithCallback(TestOrg parent) {
//    TestOrg test = new TestOrg("test_copy", 5, parent);
//    FileRemote fsrc = FileRemote.get("q:\\VIDEOS\\telgRingCommAnimation.gif.gif");
//    FileRemote fdst = FileRemote.get("T:/telgRingCommAnimation.gif");
//    fdst.device().activate();                    // start the device thread
//    long time0 = System.nanoTime();
//    FileRemote.CallbackEvent backEvent = new FileRemote.CallbackEvent("test-copycallback", this.callbackCopy, null, this.evSrc);
//    backEvent.occupy(null, true);                // use an event for back info
//    backEvent.setFiles(fsrc, fdst);              // the back event contains the forward event
//    this.callbackDone = false;                   // init definitely state of callback 
//    long time1 = System.nanoTime();
//    fsrc.copyTo(fdst, backEvent);                // this is the copy routine with callback
//    long dTimeCall = System.nanoTime() - time1;
//    waitfinish(backEvent);                       // wait, notify from callback
//    //... this may be normally in the callback operation:
//    String sError = backEvent.errorMsg;          // possible exception message
//    FileRemote.CallbackCmd cmdback = backEvent.getCmd();   // check correctness of operation
//    backEvent.relinquish();                      // relinquish the event for next usage
//    long dTimeRespond = System.nanoTime() - time1;
//    //... end normally in callback operation
//    //========== write test results:             // note: it is not tested whether the file is copied yet.
//    test.expect(cmdback == FileRemote.CallbackCmd.done && sError ==null, 4, "copy operation returns 'done'");
//    test.expect(dTimeCall < 2000000, 3, "time for call < 2 ms = %3.3f", dTimeCall/1000000.0);
//    test.expect(dTimeRespond > 10000000, 2, "time for execution > 10 ms = %3.3f ms", dTimeRespond/1000000.0);
//    System.out.printf("timePrepare = %f, timeCall = %f ms, timeRespond = %f ms\n"
//        , (time1 - time0)/1000000.0, dTimeCall/1000000.0, dTimeRespond/1000000.0);
//    test.finish();
//    try { fdst.device().close(); } catch (IOException e) { }
//  }
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
  EventConsumer callbackCopy = new EventConsumerAwait(null) {
    @Override public int processEvent ( EventObject ev ) {
      //FileRemote.CallbackEvent backEvent = (FileRemote.CallbackEvent) ev;
      notifyfinish( ev );
      return mEventDonotRelinquish;      // will be relinquished after wait
    }

 
  };
  //end::callbackCopy[]
  

  EventTimerThread progressThread = new EventTimerThread("testFileRemote");

  
  
  //tag::test_simpleWalkImmediatelyevBack[]
  public void test_simpleWalkImmediatelyevBack(TestOrg parent) {
    TestOrg test = new TestOrg("test_simpleWalkImmediatelyevBack", 5, parent);
    FileRemote fsrc = FileRemote.get("d:/vishia/Java/docuSrcJava_vishiaBase");
    EventWithDst<FileRemoteProgressEvData,?> evBack = new EventWithDst<FileRemoteProgressEvData, Object>("evProgress", null, this.refreshDirProgress, null, new FileRemoteProgressEvData());
    //
    startSeconds();                              // start time to measure
    this.refreshDirProgress.clear();
    fsrc.refreshPropertiesAndChildren(true, evBack);
    boolean bOk = this.refreshDirProgress.awaitExecution(10000000, true);
    test.expect(bOk, 5, "progress done comes");
    bOk = ! this.refreshDirProgress.done();
    test.expect(bOk, 3, "progress done is cleaned after awaitExecution(..., true)");
  }
  //end::test_simpleWalkImmediatelyevBack[]
  

  
  
  /**The progress consumer writes some info as progress information.
   * It is also the instance to call {@link EventConsumer#awaitExecution(long)}
   * for success execution. 
   */
  FileRemoteProgress refreshDirProgress = new FileRemoteProgress(null) {  // do not associate a thread

    /**This is a test implementation, It shows the progress as console output. */
    @Override protected int processEvent(FileRemoteProgressEvData evProgress, EventWithDst<FileRemote.CmdEvent, ?> evCmd) {
      int ret = EventConsumer.mEventConsumed;
        if(!evProgress.done()) {
          //info(LogMessage.timeMsg(System.currentTimeMillis(), "progress, activate"));
          //evProgress.timeOrder.activate(100); //evProgress.delay);
          float time = 0;
          String line;
          if(evProgress.answer == FileRemote.Cmd.refreshDirPre) {
            line = String.format(" %1.1f sec: %d  %s", time
                , evProgress.nrFilesVisited, evProgress.currDir.toString()
                );
          } else if(evProgress.answer == FileRemote.Cmd.refreshDirPost) {
              line = String.format(" %1.1f sec: files: %d", time
                  , evProgress.nrFilesVisited
                  );
          } else if(evProgress.answer == FileRemote.Cmd.refreshFile) {
            line = String.format(" %1.1f sec: %d  %s", time
              , evProgress.nrFilesVisited, evProgress.currFile.getName()
              );
          } else {
            line = "unknown answer";
          }
          Msg msg = new Msg(System.currentTimeMillis(), "  ", line);
          TestFileRemote.this.msg.add(msg);
          info(msg.toString());
        } else {
          ret |= EventConsumer.mEventConsumFinished;
          super.setDone(null);                             // set done for the EventConsumerAwait
          Msg msg = new Msg(System.currentTimeMillis(), "refreshDir done: ", "");
          TestFileRemote.this.msg.add(msg);
          info(msg.toString());
          this.setAvail(evProgress);
        }
      return ret;
    }
  };
  
  
  
  
  
  
  //tag::test_copyDirTreeWithCallback[]
  /**This operation tests 
   * {@link FileRemote#refreshAndMark(boolean, int, int, int, String, long, org.vishia.fileRemote.FileRemoteWalkerCallback, EventWithDst)}
   * with some files in an existing directory,
   * and then copies the marked files with
   * {@link FileRemote#copyDirTreeTo(boolean, FileRemote, int, int, int, String, int, org.vishia.fileRemote.FileRemoteWalkerCallback, EventWithDst)}
   * The feedback comes to {@link #progressCopyDirTreeWithCallback}.
   * The time to execute, the amount of files and some progress feedbacks, especially done are tested.
   * If all is met, the progress respectively event-back approach should work.
   * @param parent
   */
  public void test_copyDirTreeWithCallback(TestOrg parent) {
    TestOrg test = new TestOrg("test_copy", 5, parent);
    OutputStream outfile;
    try{ 
      outfile= new FileOutputStream("T:\\testLog.txt");
    } catch(IOException exc) {
      outfile = null;
      test.exception(exc);
    }
    //--------------------------------------------- Create the log and back event for progress 
    FileRemote fsrc = FileRemote.get("d:\\vishia\\Java\\docuSrcJava_vishiaBase");
    FileRemoteWalkerCallbackLog log = new FileRemoteWalkerCallbackLog(outfile, System.out, null, false);
    EventWithDst<FileRemoteProgressEvData,?> evProgress = new EventWithDst<FileRemoteProgressEvData, Object>("evProgress", null, this.progressCopyDirTreeWithCallback, null, new FileRemoteProgressEvData());
    //
    startSeconds();                              // start time to measure
    this.progressThread.start();
    this.progressCopyDirTreeWithCallback.clear();
    //======>>>>
    long time0 = System.nanoTime();
    fsrc.refreshAndMark(false, 20, FileMark.select, FileMark.selectSomeInDir, "**/File*.html", FileMark.ignoreSymbolicLinks, null, evProgress);
    //
    boolean bOk = this.progressCopyDirTreeWithCallback.awaitExecution(10000000, true);
    //
    test.expect(bOk, 5, "progress done comes");
    bOk = ! this.progressCopyDirTreeWithCallback.done();
    test.expect(bOk, 5, "progress done is cleaned after awaitExecution(..., true)");
    FileRemote fdst = FileRemote.get("T:/testCopyDirTree");
    fdst.mkdir();
//    progress.timeOrder.clear();
    //this.callbackDone = false;                   // init definitely state of callback 
    long time1 = System.nanoTime();
    //======>>>>
    //fsrc.copyDirTreeTo(fdst, 20, 0,0, "**/*.html", 0, log, copyProgress);                // this is the copy routine with callback
    int selectMark = FileMark.select + FileMark.selectSomeInDir;
    int resetMark = FileMark.resetMark + selectMark;
    //======>>>>
    fsrc.copyDirTreeTo(false, fdst, 20, resetMark, resetMark, null, selectMark, log, evProgress);                // this is the copy routine with callback
    long dTimeCall = System.nanoTime() - time1;
    this.progressCopyDirTreeWithCallback.awaitExecution(0, true);
    //waitfinish(copyProgress);
    long dTimeRespond = System.nanoTime() - time1;
    //evProgress.relinquish();
    //... this may be normally in the callback operation:
    //... end normally in callback operation
    //========== write test results:             // note: it is not tested whether the file is copied yet.
    test.expect(dTimeCall < 2000000, 3, "time for call < 2 ms = %3.3f", dTimeCall/1000000.0);
    test.expect(dTimeRespond > 10000000, 2, "time for execution > 10 ms = %3.3f ms", dTimeRespond/1000000.0);
    System.out.printf("timePrepare = %f, timeCall = %f ms, timeRespond = %f ms\n"
        , (time1 - time0)/1000000.0, dTimeCall/1000000.0, dTimeRespond/1000000.0);
    this.progressThread.close();
    test.finish();
    try { fdst.device().close(); } catch (IOException e) { }
  }
  //end::test_copyDirTreeWithCallback[]
  


  
  
  /**The progress consumer writes some info as progress information.
   * It is also the instance to call {@link EventConsumer#awaitExecution(long)}
   * for success execution. 
   */
  FileRemoteProgress progressCopyDirTreeWithCallback = new FileRemoteProgress(this.progressThread) {

    /**Indicator whether there is progress in {@link FileRemoteProgressEvData#nrofBytesAll}. */
    private long nrBytesAllCmp = 0;

    /**This is a test implementation, It shows the progress as console output. */
    @Override protected int processEvent(FileRemoteProgressEvData evProgress, EventWithDst<FileRemote.CmdEvent, ?> evCmd) {
      int ret = EventConsumer.mEventConsumed;
      float time = getSeconds();
      if(this.nrBytesAllCmp == evProgress.nrofBytesAll && !evProgress.done()) {
        Debugutil.stop();
        info("progress:" + evProgress.currDir.getName());
      } else {
        String line;
        String sDir = evProgress.currDir==null ? "-no dir-" : evProgress.currDir.getAbsolutePath();
        String sFile = evProgress.currFile==null ? "-no file-" : evProgress.currFile.getName();
        if(super.nrFilesAvail >0) {
          int filesPercent = evProgress.nrofFilesSelected * 100 / super.nrFilesAvail;
          int bytesPercent = (int)(evProgress.nrofBytesAll * 100 / super.nrofBytesAllAvail);
          int bytesFilePercent = evProgress.nrofBytesFile <=0 ? 0 : (int)(evProgress.nrofBytesFileCopied * 100 / evProgress.nrofBytesFile);
          line = String.format(" %1.1f sec: bytes:%d%% files: %d%% bytesFile: %d%%  dirs:%d/%d %s %s", time
             , filesPercent, bytesPercent, bytesFilePercent, evProgress.nrDirProcessed, super.nrDirAvail
             , sDir, sFile);
        } else {
          line = String.format(" %1.1f sec: dirs: %d file: %d bytes all:%d files selected: %d, marked: %d %s %s", time
              , evProgress.nrDirVisited, evProgress.nrFilesVisited, evProgress.nrofBytesAll, evProgress.nrofFilesSelected, evProgress.nrofFilesMarked
              , sDir, sFile);
        }
        this.nrBytesAllCmp = evProgress.nrofBytesAll;
        if(!evProgress.done()) {
          //info(LogMessage.timeMsg(System.currentTimeMillis(), "progress, activate"));
          //evProgress.timeOrder.activate(100); //evProgress.delay);
          Msg msg = new Msg(System.currentTimeMillis(), "progress ... ", line);
          TestFileRemote.this.msg.add(msg);
          info(msg.toString());
        } else {
          ret |= EventConsumer.mEventConsumFinished;
          super.setDone(null);                             // set done for the EventConsumerAwait
          Msg msg = new Msg(System.currentTimeMillis(), "progress done: ", line);
          TestFileRemote.this.msg.add(msg);
          info(msg.toString());
          this.setAvail(evProgress);
        }
        Debugutil.stop();
      }
      return ret;
    }


  };
  
 
  
  public static final void main(String[] args){ test(args);}
  
  public static final void test ( String[] args ){
    
    TestOrg test = new TestOrg("TestFileRemote", 5, args);
    TestFileRemote thiz = new TestFileRemote();
    //thiz.test_checkPhysicalDevice(test);
    //thiz.test_copyWithCallback(test);
//    thiz.test_simpleWalkImmediatelyevBack(test);
    thiz.test_copyDirTreeWithCallback(test);
    //thiz.execute2();
  }

}
