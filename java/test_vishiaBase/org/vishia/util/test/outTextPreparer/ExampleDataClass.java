package org.vishia.util.test.outTextPreparer;

import java.util.ArrayList;
import java.util.List;

import org.vishia.util.OutTextPreparer;

public class ExampleDataClass {
  /**Some colors for example for the for control. */
  public List<String> colors1 = new ArrayList<String>();
  public List<String> colors2 = new ArrayList<String>();

  public ExampleDataClass() {
    this.colors1.add("white");
    this.colors1.add("yellow");
    this.colors1.add("red");
    this.colors1.add("blue");
    this.colors1.add("green");
  
    this.colors2.add("cyan");
    this.colors2.add("magenta");
    this.colors2.add("gray");
    this.colors2.add("black");
  }

  
  static final OutTextPreparer XXXotxListColors = new OutTextPreparer("otxListColors"
  , null            //no static data on construction
  , "colors, text"  //arguments need and used
  , "<&text>: <:for:color:colors><&color><:if:color_next>, <.if><.for>");  //The pattern.
}
