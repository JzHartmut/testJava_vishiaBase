package org.vishia.xmlSimple.test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.vishia.util.TestOrg;
import org.vishia.xmlSimple.XmlSequWriter;

public class Test_XmlSeqWriter {

  public static void main(String[] args) {
    String sOutDir = System.getenv("TMP");
    if(args !=null) for(String arg:args) {
      if(arg.startsWith("-f:")) { sOutDir = arg.substring(3); }
    }
    
    TestOrg test = new TestOrg("Test_XmlSeqWriter", 2, args);
    
    File dir = new File(sOutDir);
    StringBuilder testout = new StringBuilder();
    Test_XmlSeqWriter thiz = new Test_XmlSeqWriter();
    thiz.xwr.setEncoding("UTF-8");
    try {
      //thiz.testWritetree2(new File(dir, "testWritetree2.xml"), testout);
      //thiz.testWritetree2t(new File(dir, "testWritetree2t.xml"), testout);
      //thiz.testWritetree2at(new File(dir, "testWritetree2at.xml"), testout);
      //
      String sCmp = "<?xml version=\"1.0\" encoding=\"US-ASCII\"?>\n"
          + "<root>\n"
          + "  contentRoot\n"
          + "  <child1 attr=\"value with &apos;&#xF6;&apos;\">contentChild &lt;&#x2665;&#x221E;&#x222B;&gt; special chars</child1>\n"
          + "  <child2 attr2=\"text &#x03BB; special chars\"/>\n"
          + "</root>\n";
      thiz.testWriteEncodingFile(new File(dir, "testWriteEncodingFile.xml"), testout, "US-ASCII", sCmp, test);
      //
      sCmp = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n"
          + "<root>\n"
          + "  contentRoot\n"
          + "  <child1 attr=\"value with &apos;ö&apos;\">contentChild &lt;&#x2665;&#x221E;&#x222B;&gt; special chars</child1>\n"
          + "  <child2 attr2=\"text &#x03BB; special chars\"/>\n"
          + "</root>\n";
      thiz.testWriteEncodingFile(new File(dir, "testWriteEncodingFile.xml"), testout, "ISO8859-1", sCmp, test);
      //
      thiz.testProduceExmplBillOfMaterial(new File(dir, "bom_Ascii.xml"), "US-ASCII");
      thiz.testProduceExmplBillOfMaterial(new File(dir, "bom_byteChars.xml"), "ISO-8859-1");
      thiz.testProduceExmplBillOfMaterial(new File(dir, "bom_UTF8.xml"), "UTF-8");
    } catch(Exception exc) {
      System.err.println(exc.getMessage());
      test.exception(exc);
    }
    test.finish();
  }

  
  XmlSequWriter xwr = new XmlSequWriter();

  
  private void testWritetree2(File file, StringBuilder out) throws IOException {
    String sCmp = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<root>contentRoot\n" + 
        "  <child1/>\n" + 
        "</root>\n" + 
        "";
    out.setLength(0);
    xwr.open(file, null, out);
    xwr.writeElement("root");
    xwr.writeText("contentRoot", false);
    xwr.writeElement("child1");  //without content
    xwr.close();
    if(!sCmp.equals(out.toString())) {
      throw new IllegalArgumentException("faulty= testWriteTree");
    }
  }
  

  private void testWritetree2t(File file, StringBuilder out) throws IOException {
    String sCmp = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
        "<root>\n" + 
        "  contentRoot\n" + 
        "  <child1>contentChild</child1>\n" + 
        "</root>\n" + 
        "";
    out.setLength(0);
    xwr.open(file, null, out);
    xwr.writeElement("root");
    xwr.writeText("contentRoot", true);
    xwr.writeElement("child1");
    xwr.writeText("contentChild", false);
    xwr.close();
    if(!sCmp.equals(out.toString())) {
      throw new IllegalArgumentException("faulty= testWriteTree");
    }
  }
  

  private void testWritetree2at(File file, StringBuilder out) throws IOException {
    String sCmp = 
        "<?xml version=\"1.0\" encoding=\"US-ASCII\"?>\n" + 
        "<root>\n" + 
        "  contentRoot\n" + 
        "  <child1 attr=\"value\">\n" + 
        "    contentChild\n" + 
        "  </child1>\n" + 
        "</root>\n" + 
        "";
    out.setLength(0);
    xwr.open(file, "US-ASCII", out);
    xwr.writeElement("root");
    xwr.writeText("contentRoot", true);
    xwr.writeElement("child1");
    xwr.writeAttribute("attr", "value");
    xwr.writeText("contentChild", true);
    xwr.close();
    if(!sCmp.equals(out.toString())) {
      throw new IllegalArgumentException("faulty= testWriteTree");
    }
  }
  
  
  
  private void testWriteEncodingFile(File file, StringBuilder out, String sEncoding, String sCmp, TestOrg testParent) throws IOException {
    TestOrg test = new TestOrg("testWriteEncodingFile: " + sEncoding, 4, testParent);
    
    
    try {
      out.setLength(0);
      this.xwr.setEncoding(sEncoding);
      this.xwr.open(file, null, out);
      this.xwr.writeElement("root");
        this.xwr.writeText("contentRoot", true);
      this.xwr.writeElement("child1");
      { this.xwr.writeAttribute("attr", "value with 'ö'");
        this.xwr.writeText("contentChild <♥∞∫> special chars", false);
        this.xwr.writeElementEnd();
      }
      this.xwr.writeElement("child2");
        this.xwr.writeAttribute("attr2", "text λ special chars");
      this.xwr.close();
      test.expect(out, sCmp, 5, "test of some transliteration for " + sEncoding + " ouptut");
    } 
    catch (Exception exc) {
      this.xwr.fileclose();
      test.exception(exc); 
    }
    test.finish();
  }
  
  public void testProduceExmplBillOfMaterial(File fOut, String sEncoding) {
    class Part { String name, value, footprint, ordernr, description; int count; 
      Part(String name, String value, String footprint, String ordernr, int count, String description) {
        this.name = name; this.value = value; this.footprint = footprint; this.ordernr = ordernr; this.description = description; this.count = count;
      }
    }
    
    List<Part> bom = new LinkedList<Part>();
    bom.add(new Part("R", "3k3", "1206", "123456-789", 23, "some resistors"));
    bom.add(new Part("R", "490 Ohm", "1206", "123433-789", 5, "some other resisitors\nverbose description"));
    bom.add(new Part("C", "4n7", "1206", "123abc-789", 17, ""));
    try {
      this.xwr.open(fOut, sEncoding, null);
      this.xwr.writeElement("BillOfMaterial");  //root
      this.xwr.writeAttribute("xmlns:bom", "www.vishia.org/XmlSeqWriter/ExampleBom");
      for(Part part: bom) {
        this.xwr.writeElement("bom:entry");  //root
        this.xwr.writeAttribute("bom:part", part.name);  //root
        this.xwr.writeAttribute("bom:value", part.value);  //root
        this.xwr.writeAttribute("bom:footprint", part.footprint);  //root
        this.xwr.writeAttribute("bom:ordernumber", part.ordernr);  //root
        this.xwr.writeAttribute("bom:count", Integer.toString(part.count));
        this.xwr.writeText(part.description, false);
        this.xwr.writeElementEnd();
      }
      this.xwr.close();
    } 
    catch (Exception exc) {
      this.xwr.fileclose();
    }
  }
}
