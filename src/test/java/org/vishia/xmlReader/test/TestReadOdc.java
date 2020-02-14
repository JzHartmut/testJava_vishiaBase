package org.vishia.xmlReader.test;

import java.io.File;
import java.io.IOException;

import org.vishia.util.Debugutil;
import org.vishia.xmlReader.XmlJzCfgAnalyzer;

/**This class is an example and a test helper to read OpenOffice (or LibreOffice) calc files. */
public class TestReadOdc
{
  /**Version, License and History:
   * <ul>
   * <li>2018-12-07 created.
   * </ul>
   * 
   * <b>Copyright/Copyleft</b>:
   * For this source the LGPL Lesser General Public License,
   * published by the Free Software Foundation is valid.
   * It means:
   * <ol>
   * <li> You can use this source without any restriction for any desired purpose.
   * <li> You can redistribute copies of this source to everybody.
   * <li> Every user of this source, also the user of redistribute copies
   *    with or without payment, must accept this license for further using.
   * <li> But the LPGL is not appropriate for a whole software product,
   *    if this source is only a part of them. It means, the user
   *    must publish this part of source,
   *    but don't need to publish the whole source of the own product.
   * <li> You can study and modify (improve) this source
   *    for own using or for redistribution, but you have to license the
   *    modified sources likewise under this LGPL Lesser General Public License.
   *    You mustn't delete this Copyright/Copyleft inscription in this source file.
   * </ol>
   * If you are intent to use this sources without publishing its usage, you can get
   * a second license subscribing a special contract with the author. 
   * 
   * @author Hartmut Schorrig = hartmut.schorrig@vishia.de
   * 
   */
  public static final String version = "2018-12-07";

  XmlJzCfgAnalyzer cfgAnalyzer = new XmlJzCfgAnalyzer();  //the root node for reading config

  
  public void analyzeXmlStruct(File fXmlIn) throws IOException {
    File fXmlCfg = new File("D:/vishia/XmlJzReader/Test_ods/xmlcfg.xml");
    cfgAnalyzer.readXmlStruct(fXmlIn);
    cfgAnalyzer.writeCfgTemplate(fXmlCfg);
    Debugutil.stop();
  }

  
  public static void main(String[] args) {
    TestReadOdc main = new TestReadOdc();
    try {
      main.analyzeXmlStruct(new File("D:/vishia/XmlJzReader/Test_ods/content.xml"));
    } catch (Exception e) {
      System.err.println("Unexpected: " + e.getMessage());
      e.printStackTrace(System.err);
    }
    Debugutil.stop();
    
  }


}
