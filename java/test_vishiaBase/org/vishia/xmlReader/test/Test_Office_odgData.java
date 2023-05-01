package org.vishia.xmlReader.test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.vishia.cmd.JZtxtcmdTester;
import org.vishia.odg.data.XmlForOdg;
import org.vishia.odg.data.XmlForOdg_Zbnf;
import org.vishia.util.Arguments;
import org.vishia.util.Debugutil;
import org.vishia.xmlReader.GenXmlCfgJavaData;
import org.vishia.xmlReader.XmlCfg;
import org.vishia.xmlReader.XmlJzCfgAnalyzer;
import org.vishia.xmlReader.XmlJzReader;
import org.vishia.xmlReader.XmlNodeSimpleReader;
import org.vishia.xmlReader.ZmlReader;
import org.vishia.xmlSimple.XmlBeautificator;
import org.vishia.xmlSimple.XmlNodeSimple;

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

  
  /**Analyzes the given XML file example and creates a proper xmlcfg.xml file from that.
   * @param fXmlIn The example file. The xmlCfg will be write beside as xmlcfg.xml
   * @throws IOException
   */
  public void analyzeXmlStruct(File fXmlIn) throws IOException {
    File fXmlCfg = new File("T:/xmlcfg.xml");
    this.cfgAnalyzer = new XmlJzCfgAnalyzer();
    //this.cfgAnalyzer.readXmlStruct(fXmlIn);
//    String sIn = "D:/vishia/SwEng/doc/ofb-UML-Examples.odg";
//    String[] args = new String[] {sIn + ":zip:content.xml", "T:/ofb-UML-Examples.xml"};
//    XmlBeautificator.main(args);
//    this.cfgAnalyzer.readXmlStructZip(new File(sIn), "content.xml");
//    this.cfgAnalyzer.writeData(new File("T:/xmlcfg.txt"));
//    this.cfgAnalyzer.writeCfgText(new File("T:/xmlcfg.cfg"));
//    this.cfgAnalyzer.writeCfgTemplate(fXmlCfg);

//    this.cfgAnalyzer = new XmlJzCfgAnalyzer();
//    this.cfgAnalyzer.readXmlStructZip(new File("d:\\vishia\\Fpga\\doc\\Java2Vhdl_Diagrams.odg"), "content.xml");
//    this.cfgAnalyzer.writeCfgText(new File("T:/Java2Vhdl_Diagrams.odg.cfg"));

//    this.cfgAnalyzer = new XmlJzCfgAnalyzer();
//    this.cfgAnalyzer.readXmlStructZip(new File("d:\\vishia\\spe\\SPE-card\\wrk-src\\src\\src_SpeA_Fpga\\oodg\\modules_SpeA-c.odg"), "content.xml");
//    this.cfgAnalyzer.writeCfgText(new File("T:/modules_SpeA-c.odg.cfg"));
    
    XmlCfg cfg = new XmlCfg();
    cfg.setCfgFromZml(new File("D:/vishia/Java/cmpnJava_vishiaBase/src/srcJava_vishiaBase/java/org/vishia/odg/data/xmlcfg.cfg"));
    
    
    
    
//    this.cfgAnalyzer = new XmlJzCfgAnalyzer();
//    this.cfgAnalyzer.readConfigText(new File("T:/xmlcfg.cfg"));
//    this.cfgAnalyzer.writeCfgText(new File("T:/xmlcfg.back.cfg"));
//    this.cfgAnalyzer.writeCfgTemplate(new File("T:/xmlcfg.back.xml"));
    Debugutil.stop();
    System.out.println("done\n");
  }

  
  
  /**Generates Java source files to store the data due to xmlcfg.xml.
   * It calls immediately {@link GenXmlCfgJavaData#smain(String[])}
   * with here set arguments.
   */
  public void genJavaData() {
    String[] args = 
      { //Arguments.replaceEnv("-cfg:$(TMP)/test_XmlOdg/xmlcfg.xml")
        "-cfg:T:/xmlcfg.xml"
      , "-dirJava:" + Arguments.replaceEnv("$(TMP)/test_XmlOdg/Java")
      , "-pkg:org.vishia.odg.data"
      , "-class:XmlForOdg"
      };
      
    GenXmlCfgJavaData.smain(args);
  }
  
  
  
  
  public void parseExample() {

    
    File testFile = new File("D:/vishia/spe/SPE-card/FPGA/src/main/oodg/content.xml");
    File testFileodg = new File("D:/vishia/spe/SPE-card/FPGA/src/main/oodg/moduls_SpeA_a.odg");
    File fout1 = new File("T:/testXmlRead-a.xml");
    File foutData = new File("T:/testXmlRead-data.html");
    
    
      try {
        
        XmlNodeSimpleReader xmlNodeSimpleReader = new XmlNodeSimpleReader();
        XmlNodeSimple<Object> rootroot = new XmlNodeSimple<Object>("rootroot");
        XmlCfg xmlCfg = XmlNodeSimpleReader.newCfgXmlNodeSimple();
        XmlJzReader xmlReader = new XmlJzReader();
        xmlReader.setNamespaceEntry("xml", "XML");         // it is missing
//        xmlReader.openXmlTestOut(fout1);
//        xmlReader.readXml(testFile, rootroot, xmlCfg);

        //xmlReader.setDebugStopTag("xmlinput:subtree");        
        XmlCfg cfg = xmlReader.readCfg(new File("D:/vishia/spe/SPE-card/FPGA/src/main/oodg/xmlcfg.xml"));
        JZtxtcmdTester.dataHtml(cfg, new File("T:/oodg_xmlcfg.html"));
        xmlReader.setNamespaceEntry("xml", "XML");         // it is missing
        XmlForOdg_Zbnf data = new XmlForOdg_Zbnf();
        xmlReader.setDebugStopTag("text:span");
        xmlReader.openXmlTestOut(fout1);
        xmlReader.readZipXml(testFileodg, "content.xml", data);
        JZtxtcmdTester.dataHtml(data.dataXmlForOdg, foutData);
        XmlForOdg odg = data.dataXmlForOdg;
        odg.prepareData();
        for(Map.Entry<String, XmlForOdg.FBlock> emdl: odg.idxFBlockByType.entrySet()) {
          System.out.print(emdl.getValue().showMdlAggregations());
        }
        Debugutil.stop();
        //System.out.println(data.get_BillOfMaterial().toString()); //set breakpoint here to view data
      } catch (IOException | NoSuchFieldException e) {
        e.printStackTrace();
      }

  }
  
  
  
  
  
  public static void main(String[] args) {
    Test_Office_odgData main = new Test_Office_odgData();
    try {
      main.analyzeXmlStruct(new File("t:\\content.xml"));    // analyzes the given exemplar of XML file
//      main.genJavaData();
//      main.parseExample();
    } catch (Exception e) {
      System.err.println("Unexpected: " + e.getMessage());
      e.printStackTrace(System.err);
    }
    Debugutil.stop();
    
  }



}
