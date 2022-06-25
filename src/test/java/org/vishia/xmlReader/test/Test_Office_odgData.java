package org.vishia.xmlReader.test;

import java.io.File;
import java.io.IOException;

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
    this.cfgAnalyzer.readXmlStruct(fXmlIn);
    this.cfgAnalyzer.writeCfgTemplate(fXmlCfg);
    Debugutil.stop();
  }

  
  
  /**Generates Java source files to store the data due to xmlcfg.xml.
   * It calls immediately {@link GenXmlCfgJavaData#smain(String[])}
   * with here set arguments.
   */
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

    
    File testFile = new File("D:/vishia/spe/SPE-card/FPGA/src/main/oodg/content.xml");
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
        xmlReader.readXml(testFile, data, cfg);
        JZtxtcmdTester.dataHtml(data.dataXmlForOdg, foutData);
        evaluateData(data.dataXmlForOdg);
        Debugutil.stop();
        //System.out.println(data.get_BillOfMaterial().toString()); //set breakpoint here to view data
      } catch (IOException | NoSuchFieldException e) {
        e.printStackTrace();
      }

  }
  
  
  void evaluateData(XmlForOdg data) {
    XmlForOdg.Office_document_content doc = data.get_office_document_content();
    XmlForOdg.Office_body body = doc.get_office_body();
    XmlForOdg.Office_automatic_styles styles = doc.get_office_automatic_styles();
    styles.prepStyles();
    XmlForOdg.Office_drawing drawing = body.get_office_drawing();
    XmlForOdg.Draw_page page = drawing.get_draw_page();
    for(XmlForOdg.Draw_g group:  page.get_draw_g()) {      // general: a module is inside a group
      System.out.println("== group");
      for(XmlForOdg.Draw_frame textFrame: group.get_draw_frame()) {
        String text = null;
        String drawStyle = textFrame.get_draw_style_name();
        String drawTextStyle = textFrame.get_draw_text_style_name();
        String id = textFrame.get_draw_id();
        String drawStyleParent = styles.idxStyle.get(drawStyle);
        String drawTextStyleParent = styles.idxStyle.get(drawTextStyle);
        
        XmlForOdg.Text_p textp = textFrame.get_draw_text_box().get_text_p();
        if(textp !=null) {
          XmlForOdg.Text_span textspan = textp.get_text_span();
          if(textspan !=null) {
            text = textspan.get_text();
          } else {
            text = textp.get_text_s();
//            System.out.println("text:frame without text:span");
          }
        }
        System.out.println("  " + text + ":" + drawStyleParent + "/" + id);
      }
    }
    for(XmlForOdg.Draw_connector con:  page.get_draw_connector()) {      // general: a module is inside a group
      String style = con.get_draw_style_name();
      String start = con.get_draw_start_shape();
      String startpoint = con.get_draw_start_glue_point();
      String end = con.get_draw_end_shape();
      String endpoint = con.get_draw_end_glue_point();
      System.out.println("== connector" + " " + start + "." + startpoint + " -->" + end + "." + endpoint);
    }
    Debugutil.stop();
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
