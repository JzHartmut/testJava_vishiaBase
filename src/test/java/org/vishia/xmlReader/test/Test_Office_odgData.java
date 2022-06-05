package org.vishia.xmlReader.test;

import java.io.File;
import java.io.IOException;

import org.vishia.odg.data.XmlForOdg_Zbnf;
import org.vishia.util.Arguments;
import org.vishia.util.Debugutil;
import org.vishia.xmlReader.GenXmlCfgJavaData;
import org.vishia.xmlReader.XmlCfg;
import org.vishia.xmlReader.XmlJzCfgAnalyzer;
import org.vishia.xmlReader.XmlJzReader;

public class Test_Office_odgData {
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
  public static final String version = "2022-05-29";

  XmlJzCfgAnalyzer cfgAnalyzer = new XmlJzCfgAnalyzer();  //the root node for reading config

  
  public void analyzeXmlStruct(File fXmlIn) throws IOException {
    File fXmlCfg = new File("D:/vishia/spe/SPE-card/FPGA/src/main/oodg/xmlcfg.xml");
    cfgAnalyzer.readXmlStruct(fXmlIn);
    cfgAnalyzer.writeCfgTemplate(fXmlCfg);
    Debugutil.stop();
  }

  
  
  public void genJavaData() {
    String[] args = 
      { "-cfg:D:/vishia/spe/SPE-card/FPGA/src/main/oodg/xmlcfg.xml"
      , "-dirJava:" + Arguments.replaceEnv("$(TMP)/test_XmlOdg/Java")
      , "-pkg:org.vishia.odg.data"
      , "-class:XmlForOdg"
      };
      
    GenXmlCfgJavaData.smain(args);
  }
  
  
  
  
  public void parseExample() {
      XmlJzReader xmlReader = new XmlJzReader();
      try {
        XmlCfg cfg = xmlReader.readCfg(new File("D:/vishia/spe/SPE-card/FPGA/src/main/oodg/xmlcfg.xml"));
        XmlForOdg_Zbnf data = new XmlForOdg_Zbnf();
        xmlReader.readXml(new File("D:/vishia/spe/SPE-card/FPGA/src/main/oodg/content.xml"), data, cfg);
        Debugutil.stop();
        //System.out.println(data.get_BillOfMaterial().toString()); //set breakpoint here to view data
      } catch (IOException e) {
        e.printStackTrace();
      }

  }
  
  
  
  public static void main(String[] args) {
    Test_Office_odgData main = new Test_Office_odgData();
    try {
//      main.analyzeXmlStruct(new File("d:\\vishia\\spe\\SPE-card\\FPGA\\src\\main\\oodg\\content.xml"));
//      main.genJavaData();
      main.parseExample();
    } catch (Exception e) {
      System.err.println("Unexpected: " + e.getMessage());
      e.printStackTrace(System.err);
    }
    Debugutil.stop();
    
  }



}
