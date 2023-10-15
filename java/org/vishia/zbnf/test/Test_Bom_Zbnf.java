package org.vishia.zbnf.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.text.ParseException;
import java.util.List;

import org.vishia.mainCmd.MainCmdLoggingStream;
import org.vishia.mainCmd.PrintStreamBuffer;
import org.vishia.testBase.TestJava_vishiaBase;
import org.vishia.zbnf.GenZbnfJavaData;
import org.vishia.zbnf.ZbnfParseResultItem;
import org.vishia.zbnf.ZbnfParser;


public class Test_Bom_Zbnf {
  
  
  /**Test of parsing the bill of material example.
   * Note: You should set the working directory to the root of cmnJava_vishiaBase
   * where src/test etc. can be found. 
   * @param args not necessary, can be null
   */
  public static void main(String[] args) {
    TestJava_vishiaBase.setCurrDir_TestJava_vishiaBase();
    genDstClassForBom();
    Test_Bom_Zbnf thiz = new Test_Bom_Zbnf();
    
    thiz.parseBom();
  }
  
  
  
  
  void parseBom() {
    MainCmdLoggingStream log = new MainCmdLoggingStream(System.out);
    ZbnfParser zparser = new ZbnfParser(log);  //Note: parser needs a log output
    try {
      zparser.setSyntax(new File("src/test/files/zbnfParser/billOfMaterial.zbnf"));
      File finput = new File("src/test/files/zbnfParser/billOfMaterial.txt");
      Charset csInput = Charset.forName("US-ASCII");
      boolean bOk = zparser.parseFile(finput, 20000, null, csInput);
      if(!bOk) {
        String sError = zparser.getSyntaxErrorReport();
        System.err.println(sError);
      } 
      else {                           // ok parsing
        ZbnfParseResultItem result = zparser.getFirstParseResult();
        showResultBom(result, 0);
      }
    } catch (IllegalCharsetNameException | UnsupportedCharsetException | IOException | ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
  
  //tag::genDstClassForBom[]
  /**Generates the sources for destination classes for the given billOfMaterial.zbnf
   */
  static void genDstClassForBom() {
    String[] args_genJavaOutClass = 
      { "-s:src/test/files/zbnfParser/billOfMaterial.zbnf"
      , "-dirJava:$(TMP)/exmpl_ZbnfParser_Bom"
      , "-pkg:org.vishia.zbnf.test.gen"
      , "-class:Bom_Data"
      };
    GenZbnfJavaData.smain(args_genJavaOutClass);
  }
  //end::genDstClassForBom[]
  
  
  static String sIndent = "                                                            ";
  
  
  
  
  /**This operation is only used to demonstrate what is the result of the parsind. 
   * It is not practicable, only for showing the principle.
   * @param result
   */
  void showResultBom(ZbnfParseResultItem result, int level) {
    System.out.append("\n").append(sIndent.substring(0, 2*level));
    System.out.append(result.toString());
    List<ZbnfParseResultItem> children = result.listChildren();
    if(children !=null) {
      for(ZbnfParseResultItem child: children) {
        showResultBom(child, level+1);
      }
    }
  }
  
  
  
}
