package org.vishia.fileRemote.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.Format;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.vishia.event.EventConsumer;
import org.vishia.event.EventConsumerAwait;
import org.vishia.event.EventSource;
import org.vishia.event.EventThread_ifc;
import org.vishia.event.EventTimerThread;
import org.vishia.event.EventTimerThread_ifc;
import org.vishia.event.EventWithDst;
import org.vishia.event.Payload;
import org.vishia.fileLocalAccessor.FileAccessorLocalJava7;
import org.vishia.fileRemote.FileMark;
import org.vishia.fileRemote.FileRemote;
import org.vishia.fileRemote.FileRemoteCmdEventData;
import org.vishia.fileRemote.FileRemoteProgress;
import org.vishia.fileRemote.FileRemoteProgressEvData;
import org.vishia.fileRemote.FileRemoteWalkerCallbackCopy;
import org.vishia.fileRemote.FileRemoteWalkerCallbackLog;
import org.vishia.fileRemote.XXXFileRemoteWalkerEvent;
import org.vishia.msgDispatch.LogMessage;
import org.vishia.util.Debugutil;
import org.vishia.util.TestOrg;
import org.vishia.testBase.TestJava_vishiaBase;

/**Test operations for the FileRemote concept.
 * The {@link org.vishia.fileLocalAccessor.FileAccessorLocalJava7} especially 
 * {@link org.vishia.fileLocalAccessor.FileAccessorLocalJava7#walkFileTreeExecInThisThread(org.vishia.fileRemote.FileRemote.CmdEventData, boolean, EventWithDst, boolean)}
 * is used inside this operations.
 * @author hartmut
 *
 */
public final class TestFileRemote {

  //protected boolean callbackDone; 
  
  
  long time_ms;
  
  /**Two directories as source and destination for file trees to test.
   * The fdst will be created and afterwards deleted.
   * The fsrc will be used. 
   * 
   */
  FileRemote fsrc, fdst;
  
  /**Only if true then output some information about data, progress etc. 
   * If false prevent this outputs to better see test results.
   * Used in {@link #progressShow}
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
  
  EventSource evSrc = new EventSource("test FileRemote") {
  };


  /**This is a {@link EventTimerThread} which receives events for progress and success,
   * stores it and executes it if prioritized. 
   * Using this thread for this test approaches relieves the execution thread of a command 
   * from calculation time to show the results, no more. It is the test possibility with an thread for progress.
   * In a real application the progress thread can do more activities especially in a graphic application handle with the widgets.
   */
  EventTimerThread progressThread = new EventTimerThread("testFileRemote");


  //tag::callbackCopy[]
  /**This is a event consumer instance containing the event only for done response.
   * The {@link FileRemoteProgress#evBack} does not force an extra thread for the progress and back,
   * instead this instance is used to execute the {@link FileRemoteProgress#processEvent(EventObject)} immediately in the executin thread.
   * Furthermore it does not force an extra thread for the FileRemote command. 
   * It means per default all is executed in the calling thread of a FileRemote command. 
   */
  FileRemoteProgress progressOnlyAwait = new FileRemoteProgress("progressOnlyAwait", null, null);
  
  
  //end::test_copyDirTreeWithCallback[]
  
  
  
  
  
  /**The progress consumer writes some info as progress information.
   * It is also the instance to call {@link EventConsumer#awaitExecution(long)}
   * for success execution. 
   */
  class FileRemoteProgressShow extends FileRemoteProgress {
  
    public FileRemoteProgressShow(String name, EventThread_ifc progressThread, EventThread_ifc cmdThread) {
      super(name, progressThread, cmdThread);
    }

    /**Indicator whether there is progress in {@link FileRemoteProgressEvData#nrofBytesAll}. */
    private long nrBytesAllCmp = 0;
  
    /**This is a test implementation, It shows the progress as console output. */
    @Override protected int processEvent(FileRemoteProgressEvData evProgress, EventWithDst<FileRemoteCmdEventData, FileRemoteProgressEvData> evCmd) {
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
  
  
  } // class

  FileRemoteProgress progressShow = new FileRemoteProgressShow("progressCopyDirTreeWithCallback", this.progressThread, null);

  //end::test_simpleWalkImmediatelyevBack[]
  
  
  
  
  /**The progress consumer writes some info as progress information.
   * It is also the instance to call {@link EventConsumer#awaitExecution(long)}
   * for success execution. 
   */
  FileRemoteProgress refreshDirProgress = new FileRemoteProgressShow("refreshDirProgress", null, null);
  /**Instance used in the test main thread for command data.
   * 
   */
//  final FileRemoteCmdEventData cmdData = new FileRemoteCmdEventData();

  
  
//  final FileRemoteProgressEvData progressData = new FileRemoteProgressEvData();
  
  /**If this event is used, then especially the {@link #progressEventDst} -> {@link EventConsumerAwait#awaitExecution(long, boolean)} 
   * can be used. Without an extra thread. {@link EventConsumerAwait#processEvent(EventObject)} for done is called with the sendEvent.
   */
//  final EventWithDst<FileRemoteProgressEvData,?> evProgressAwait = new EventWithDst<FileRemoteProgressEvData, Payload>
//      ("evProgressAwait", null, this.progressOnlyAwait, null, this.progressData);


  /**If this event is used, then especially the {@link #progressEventDst} -> {@link EventConsumerAwait#awaitExecution(long, boolean)} 
   * can be used. Without an extra thread. {@link EventConsumerAwait#processEvent(EventObject)} for done is called with the sendEvent.
   */
//  final EventWithDst<FileRemoteProgressEvData, FileRemoteCmdEventData> evProgressAwaitCmd = new EventWithDst<FileRemoteProgressEvData, FileRemoteCmdEventData>
//      ("evProgressAwait", null, this.progressOnlyAwait, null, this.progressData, this.cmdData);


  /**If this event is used, then progress data will be printed by the {@link #progressShow} implementation. 
   * 
   */
//  final EventWithDst<FileRemoteProgressEvData,?> evProgressShow = new EventWithDst<FileRemoteProgressEvData, Payload>
//      ("evProgress", null, this.progressShow, null, this.progressData);


  /**If this event is used, then progress data will be printed by the {@link #progressShow} implementation. 
   * The event contains the opponent for the {@link FileRemoteCmdEventData} for the command data.
   */
//  final EventWithDst<FileRemoteProgressEvData, FileRemoteCmdEventData> evProgressShowCmd = 
//      new EventWithDst<FileRemoteProgressEvData, FileRemoteCmdEventData>
//      ("evProgressCmd", null, this.progressShow, null, this.progressData, this.cmdData);


  TestFileRemote(){
    this.fsrc = FileRemote.get("../docuSrcJava_vishiaBase");    //use a proper location with html files (javadoc)
    
  }
  
  
  void testInfo(String text) {
    Msg msg = new Msg(System.currentTimeMillis(), "TestInfo: ", text);
    TestFileRemote.this.msg.add(msg);
    info(msg.toString());
    
  }
  
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
  
  
//  protected void notifyfinish ( Object syncObj ) {
//    synchronized(TestFileRemote.this) {
//      TestFileRemote.this.callbackDone = true;
//      TestFileRemote.this.notify();
//    }
//  }


  
  
  public void test_mkDir(TestOrg parent) {
    TestOrg test = new TestOrg("test_mkDir", 5, parent);
    // ====================================================== create a possible sensible directory in the /tmp/ folder.
    FileRemote fdst = FileRemote.get("/tmp/test_FileRemote/test_mkDir");
    String sError = fdst.mkdir(true, this.progressOnlyAwait.evBack);
    test.expect(sError==null, 3, "mkdir called successfull: %s (error %s)", fdst.getAbsolutePath(), sError);
    boolean bOk = this.progressOnlyAwait.awaitExecution(0, true);
    test.expect(bOk, 3, "mkdir successfull: %s", fdst.getAbsolutePath());
    //
    // ====================================================== create a directory in an non existing drive, should be faulty.
    FileRemote fdst2 = FileRemote.get("Y:\\scratch\\test_FileRemote/test_mkDir");
    sError = fdst2.mkdir(true, this.progressOnlyAwait.evBack);
    test.expect(sError==null, 5, "mkdir called successfull: %s (error %s)", fdst2.getAbsolutePath(), sError);
    bOk = this.progressOnlyAwait.awaitExecution(0, true);
    test.expect(bOk == false, 3, "mkdir non successfull for %s", fdst2.getAbsolutePath());
    
    test.finish();
  }
  
  
  public void test_copyOneThread(TestOrg parent) {
    TestOrg test = new TestOrg("test_copy", 5, parent);
    FileRemote fsrc = FileRemote.get("d:\\vishia\\Java\\deploy\\vishiaBase-2022-07-26-source.zip");
    FileRemote fdst = FileRemote.get("T:/testcopy.zip");
    fsrc.copyTo(fdst, null);
    test.finish();
  }
  
  
  public void test_checkPhysicalDevice(TestOrg parent) {
    TestOrg test = new TestOrg("test_checkPhysicalDevice", 5, parent);
    // Note TODO for common test use input files paths refer to your different physical devices.
    //tag::getFileStore[]
    FileRemote fsrc = FileRemote.get("q:\\VIDEOS\\telgRingCommAnimation.gif.gif");
    FileRemote fdst = FileRemote.get("T:/telgRingCommAnimation.gif");
    try {
      java.nio.file.FileStore fstore5rc = java.nio.file.Files.getFileStore(fsrc.path());
      java.nio.file.FileStore fstoreDst = java.nio.file.Files.getFileStore(fdst.path());
      test.expect(fstore5rc != fstoreDst, 4, "different phyisical devices for %s... and %s...", fsrc.getAbsolutePath().substring(0,8), fdst.getAbsolutePath().substring(0,8));
    } catch (IOException exc) {
      test.exception(exc);
    }
    //end::getFileStore[]
    //tag::getFileSystem[]
    java.nio.file.FileSystem fsSrc = fsrc.path().getFileSystem();
    java.nio.file.FileSystem fsDst = fsrc.path().getFileSystem();
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
  
  
  
  
  public void test_getFileRemote(TestOrg parent) {
    TestOrg test = new TestOrg("test_getFileRemote", 5, parent);
    //
    String sfName = "SRC/file.nonexist";         // Note: the existing directory is named "src". 
    FileRemote fSrc = FileRemote.get(sfName);
    String sAbsPath = fSrc.getAbsolutePath();
    test.expect(!fSrc.isTested(), 3, sfName + " : is not refreshed till now. AbsPath is %s", sAbsPath);
    test.expect(!fSrc.isDirectory(), 3, sfName + " : is not a directory independent of refresh");
   
    FileRemote fSrc2 = FileRemote.get(sfName);
    test.expect(fSrc2 == fSrc, 3, "same instance for two FileRemote with same name");
    
    sfName = "SRC/DOcs";
    fSrc = FileRemote.get(sfName);
    sAbsPath = fSrc.getAbsolutePath();
    test.expect(!fSrc.isTested(), 3, sfName + " : is not refreshed till now. AbsPath is %s", sAbsPath);
    test.expect(!fSrc.isDirectory(), 3, sfName + " : is not a directory independent of refresh");
    test.expect(sAbsPath.endsWith(sfName), 3, sfName + " : this is the name");
    //
    fSrc.refreshProperties();
    sAbsPath = fSrc.getAbsolutePath();
    test.expect(fSrc.isTested(), 3, sfName + " : is not refreshed till now. AbsPath is %s", sAbsPath);
    test.expect(fSrc.isDirectory(), 3, sfName + " : is not a directory independent of refresh");
    test.expect(sAbsPath.endsWith("src/docs"), 3, sfName + " : has correct existing name \"src/docs\"");
    
    
    sfName = "/tmp/file_in_tmpdir.nonexist";
    fSrc = FileRemote.get(sfName);
    sAbsPath = fSrc.getAbsolutePath();
    test.expect(!fSrc.isTested(), 3, sfName + " : is not refreshed till now. AbsPath is %s", sAbsPath);
   
    sfName = "~/file_in_homedir.nonexist";
    fSrc = FileRemote.get(sfName);
    sAbsPath = fSrc.getAbsolutePath();
    test.expect(!fSrc.isTested(), 3, sfName + " : is not refreshed till now. AbsPath is %s", sAbsPath);
   
    //
    sfName = "src/srcJava_vishiaBase";
    fSrc = FileRemote.getDir(sfName);
    test.expect(fSrc.isDirectory(), 3, sfName + " : is a directory independent of refresh");
    //
    sfName = "src/docs/";
    fSrc = FileRemote.get(sfName);
    test.expect(fSrc.isDirectory(), 3, sfName + " : is a directory independent of refresh");
    //
    sfName = "src/docs";
    fSrc = FileRemote.get(sfName);
    test.expect(fSrc.isDirectory(), 3, sfName + " : is a directory because before created as directory, independent of refresh");
    fSrc.refreshProperties();
    boolean bOk = fSrc.exists() && fSrc.isDirectory();
    test.expect(bOk, 3, sfName + " : found, is directory");
    //
    sfName = "child.nonexisting";
    FileRemote fChild = fSrc.child(sfName);
    bOk = !fChild.isTested() && !fChild.isDirectory();
    test.expect(bOk, 3, sfName + " : child created as file, non existing");
    //
    sfName = "childdir_nonexisting/";
    fChild = fSrc.child(sfName);
    sAbsPath = fChild.getAbsolutePath();
    bOk = !fChild.isTested() && fChild.isDirectory();
    test.expect(bOk, 3, sfName + " : child created as file, non existing. AbsPath is %s", sAbsPath);
    //
    sfName = "asciidoc";
    fChild = fSrc.child(sfName);
    sAbsPath = fChild.getAbsolutePath();
    fChild.refreshProperties();
    bOk = fChild.isTested() && fChild.exists() && fChild.isDirectory();
    test.expect(bOk, 3, sfName + " : child created as file, existing as directory. AbsPath is %s", sAbsPath);
    
    test.finish();
  }
  
  
  
  
  
  //tag::test_simpleWalkImmediatelyevBack[]
  public void test_simpleWalkImmediately_evBack(TestOrg parent) {
    TestOrg test = new TestOrg("test_simpleWalkImmediatelyevBack", 5, parent);
    FileRemote fsrc = FileRemote.get("d:/vishia/Java/docuSrcJava_vishiaBase");
    //
    startSeconds();                              // start time to measure
    this.refreshDirProgress.clean();
    fsrc.refreshPropertiesAndChildren(true, this.refreshDirProgress.evBack);
    boolean bOk = this.refreshDirProgress.awaitExecution(10000000, true);
    test.expect(bOk, 5, "progress done comes");
    bOk = ! this.refreshDirProgress.done();
    test.expect(bOk, 3, "progress done is cleaned after awaitExecution(..., true)");
  }
  //tag::test_copyDirTreeWithCallback[]
  /**This operation tests 
   * {@link FileRemote#refreshAndMark(boolean, int, int, int, String, long, org.vishia.fileRemote.FileRemoteWalkerCallback, EventWithDst)}
   * with some files in an existing directory,
   * and then copies the marked files with
   * {@link FileRemote#copyDirTreeTo(boolean, FileRemote, int, int, int, String, int, org.vishia.fileRemote.FileRemoteWalkerCallback, EventWithDst)}
   * The feedback comes to {@link #progressShow}.
   * The time to execute, the amount of files and some progress feedbacks, especially done are tested.
   * If all is met, the progress respectively event-back approach should work.
   * @param parent
   */
  public void test_copyDelDirTreeWithCallback(TestOrg parent) {
    TestOrg test = new TestOrg("test_copy", 5, parent);
    OutputStream outfile;
    this.bInfo = true;
    try{ 
      outfile= new FileOutputStream("T:\\testLog.txt");
    } catch(IOException exc) {
      outfile = null;
      test.exception(exc);
    }
    FileRemote fsrc = FileRemote.get("d:/vishia/Java/docuSrcJava_vishiaBase");    //use a proper location with html files (javadoc)
    //--------------------------------------------- Create the log and back event for progress 
    FileRemoteWalkerCallbackLog log = new FileRemoteWalkerCallbackLog(outfile, System.out, null, false);
    //
    startSeconds();                                        // start time to measure
    this.progressThread.start();                           // this thread is used by the evProgress. Start it before usage.
    this.progressShow.clean();          // clear it before usage.
    boolean bWait = false;
    testInfo("refreshAndMark .../docuSrcJava_vishiaBase/**/File*.html");
    //======>>>>       -------------------------------------- refreshAndMark runs in a FileAccessor thread because bWait=false using the FileWalker
    long time0 = System.nanoTime();                        // it markes and counts all files with mask File*.html.
    fsrc.refreshAndMark(bWait, 20, FileMark.select, FileMark.selectSomeInDir, "**/File*.html", FileMark.ignoreSymbolicLinks, null, this.progressShow.evBack);
    //                                                     // it requests bDone from evProgress
    boolean bOk = this.progressShow.awaitExecution(10000000, true);
    long dtimeMark = System.nanoTime()-time0;
    //======>>>>       
    test.expect(bOk, 3, "refresheAndMark, progress done comes after %f ms", dtimeMark/1000000.0f);
    bOk = ! this.progressShow.done();
    test.expect(bOk, 5, "progress done is cleaned after awaitExecution(..., true)");
    FileRemote fdst = FileRemote.get("T:/testCopyDirTree");
    fdst.mkdir();
    long time1 = System.nanoTime();
    int selectMark = FileMark.select + FileMark.selectSomeInDir;
    int resetMark = FileMark.resetMark + selectMark;
    testInfo("copyDirTreeTo " + fdst.getAbsolutePath());
    //======>>>>      --------------------------------------- copyDirTree runs in a FileAccessor thread using the fileWalker 
    this.progressShow.clean();
    fsrc.copyDirTreeTo(false, fdst, 20, resetMark, resetMark, null, selectMark, this.progressShow.evBack);                // this is the copy routine with callback
    long dTimeCall = System.nanoTime() - time1;
    bOk = this.progressShow.awaitExecution(0, true);
    long dTimeRespond = System.nanoTime() - time1;
    //... this may be normally in the callback operation:
    //... end normally in callback operation
    //========== write test results:             // note: it is not tested whether the file is copied yet.
    test.expect(dTimeCall < 2000000, 3, "time for call copy < 2 ms = %3.3f", dTimeCall/1000000.0);
    test.expect(bOk, 3, "copy progress.awaitExecution() returns true");
    test.expect(dTimeRespond > 10000000, 2, "time for execution copy > 10 ms = %3.3f ms", dTimeRespond/1000000.0);
    boolean bWasCreated = fdst.exists();
    test.expect(bWasCreated, 2, "the destination directory was created");
    System.out.printf("timePrepare = %3.3f, timeCall = %3.3f ms, timeRespond = %3.3f ms\n"
        , (time1 - time0)/1000000.0, dTimeCall/1000000.0, dTimeRespond/1000000.0);
    //
    //======================================================= Test delete of the copied files
    testInfo("deleteFilesDirTree " + fdst.getAbsolutePath());
    this.progressShow.clean();
    FileRemote fdstDel = FileRemote.get("t:/testCopyDirTree");           // do not use the older instance maybe removed.
    test.expect(fdst == fdstDel, 5, "same instance of fdst after copy");
    time1 = System.nanoTime();
    fdstDel.deleteFilesDirTree(false, 20, "org/**/*", this.progressShow.evBack);
    bOk = this.progressShow.awaitExecution(0, true);
    dTimeRespond = System.nanoTime() - time1;
    test.expect(bOk, 3, "delete progress.awaitExecution() returns true");
    test.expect(dTimeRespond > 100000, 2, "time for execution delete > 0.1 ms = %3.3f ms", dTimeRespond/1000000.0);
    bOk = fdst.children().size()==0;
    if(!bOk)
      Debugutil.stop();
    test.expect(bWasCreated && bOk, 2, "the destination directory is empty");
    //
    //======================================================= delete the dst directory, it is empty:
    testInfo("delete directory: " + fdst.getAbsolutePath());
    this.progressShow.clean();   //
    time1 = System.nanoTime();
    fdst.delete(this.progressShow.evBack);
    dTimeCall = System.nanoTime() - time1;
    bOk = this.progressShow.awaitExecution(0, true);
    dTimeRespond = System.nanoTime() - time1;
    test.expect(bOk, 3, "delete one dir progress.awaitExecution() returns true");
    test.expect(dTimeRespond > 1000000, 2, "time for call = %3.3f, time for execution delete > 1 ms = %3.3f ms", dTimeCall/1000000.0, dTimeRespond/1000000.0);
    test.expect(!fdst.exists(), 3, "dst dir is deleted" );
    //
    test.finish();
    testInfo("test_copyDelDirTreeWithCallback finished ");

    Debugutil.stop();
  }
  
  
  
  void test_AnyCallback(TestOrg testParent) {
    
  }
  
  
  void test_WalkerFileLocale(TestOrg testParent) {
    TestOrg test = new TestOrg("test_WalkerFileLocale", 3, testParent);
    FileRemoteCmdEventData co = new FileRemoteCmdEventData();
    test.expect(fsrc.exists(),5,"directory ./docuSrcJava_vishiaBase exists");
    //
    //======================================================= refresh the fsrc tree
    fsrc.cmdRemote(FileRemoteCmdEventData.Cmd.walkRefresh, null, null, 0, 0, co, null);  // evBack can be null because it is on the local file system.
    //co.setCmdWalkLocal(srcdir, cmd, dstdir, markSet, markSetDir, selectFilter, selectMask, cycleProgress, depthWalk);
    EventWithDst<FileRemoteProgressEvData,?> evProgress = new EventWithDst<FileRemoteProgressEvData, Payload>("evProgressLocal", null, null, null, new FileRemoteProgressEvData());
    //======================================================= select all files with the given mask
    //                                                     // select the dir of with selectSomeInDir
    fsrc.walkLocal(null, FileMark.select, FileMark.selectSomeInDir
        , "**/File*.html", 0, 0, null, co, -1, evProgress);
    //
    FileRemote fdst = FileRemote.get("T:/testCopyDirTree");
    FileRemoteWalkerCallbackCopy callbackCopy = new FileRemoteWalkerCallbackCopy();
    callbackCopy.cleanSetDstDir(fdst);
    fdst.mkdir();
    fsrc.walkLocal(fdst, FileMark.select | FileMark.resetMark, FileMark.selectSomeInDir | FileMark.resetMark
        , null, FileMark.select | FileMark.selectSomeInDir, 0, callbackCopy, co, -1, evProgress);
    test.finish();
  }
  
  
  
  public static final void main(String[] args){ 
    test(args);
    FileAccessorLocalJava7.getInstance().close();                // elsewhere JVM hangs in that threads.
  }
  
  public static final void test ( String[] args ){
    TestJava_vishiaBase.setCurrDir_TestJava_vishiaBase();  // determines the current dir for FileRemote
    Locale.setDefault(Locale.ENGLISH);
    TestOrg test = new TestOrg("TestFileRemote", 5, args);
    TestFileRemote thiz = new TestFileRemote();
    thiz.test_mkDir(test);
//    thiz.test_WalkerFileLocale(test);


//    thiz.test_getFileRemote(test);
//    thiz.test_checkPhysicalDevice(test);
//    //thiz.test_copyWithCallback(test);
//    thiz.test_simpleWalkImmediately_evBack(test);
//    thiz.test_copyDelDirTreeWithCallback(test);
//    //thiz.execute2();

    thiz.progressThread.close();
  }

}
