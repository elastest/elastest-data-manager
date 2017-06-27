package com.relational.elastest;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by savas on 27/6/2017.
 */
public class AppTest {

  @Test
  public void concatAndConvertString() throws Exception {
    String expectedValue="HELLOsavasWORLD";
    App app=new App();
    String actualValue=app.concatAndConvertString("Hello", "World");
    assertEquals(expectedValue, actualValue);
  }

}