package org.vishia.xmlReader.test;

import java.io.File;
import java.io.IOException;

import org.vishia.testBase.TestJava_vishiaBase;
import org.vishia.util.Arguments;
import org.vishia.util.FileFunctions;
import org.vishia.xmlReader.GenXmlCfgJavaData;
import org.vishia.xmlReader.XmlCfg;
import org.vishia.xmlReader.XmlJzCfgAnalyzer;
import org.vishia.xmlReader.XmlJzReader;
import org.vishia.xmlReader.test.genChgSrc.ClassForBom;
import org.vishia.xmlReader.test.genChgSrc.ClassForBom_Zbnf;

public class Test_XmlJzReaderSimpleExmpl {

  public static void main(String[] args) {
    TestJava_vishiaBase.setCurrDir_TestJava_vishiaBase();
    //analyzeXmlCfg_BomExmpl();
    //genJavaClasses();
    readTheBom();
  }
  
  //tag::analyzeXmlCfg_BomExmpl[]
  static void analyzeXmlCfg_BomExmpl() {
    File fXmlIn = new File("src/test/files/xmlReader/bom_exmpl.xml").getAbsoluteFile();
    
    File fCfgOut = new File(Arguments.replaceEnv("$(TMP)/test_vishiaBase/Test_XmlJzReader/bom_cfg.xml"));
    XmlJzCfgAnalyzer analyzer = new XmlJzCfgAnalyzer();
    try {
      FileFunctions.mkDirPath(fCfgOut.getAbsoluteFile());
      analyzer.readXmlStruct(fXmlIn);
      analyzer.writeCfgTemplate(fCfgOut);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      // TODO: handle exception
    }
  }
  //end::analyzeXmlCfg_BomExmpl[]
  
  //tag::genJavaClass_BomExmpl[]
  static void genJavaClasses() {
    String[] args = 
      { "-cfg:src/test/files/xmlReader/bom_cfg.xml"
      , "-dirJava:" + Arguments.replaceEnv("$(TMP)/test_vishiaBase/Test_XmlJzReader/Java")
      , "-pkg:org.vishia.xmlReader.test"
      , "-class:ClassForBom"
      };
      
    GenXmlCfgJavaData.smain(args);
  }
  //end::genJavaClass_BomExmpl[]
  
  
  //tag::readXml_BomExmpl[]
  static void readTheBom() {
    XmlJzReader xmlReader = new XmlJzReader();
    try {
      XmlCfg cfg = xmlReader.readCfg(new File("src/test/files/xmlReader/bom_cfg.xml"));
      ClassForBom data = new ClassForBom_Zbnf();
      xmlReader.readXml(new File("src/test/files/xmlReader/bom_exmpl.xml"), data, cfg);
      System.out.println(data.get_BillOfMaterial().toString()); //set breakpoint here to view data
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  //end::readXml_BomExmpl[]
  
  
  
}
