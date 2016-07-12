package com.fastorder.manager.tests.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.Selenium;

public class Connexion {
	
	private Selenium selenium;

    	@Before
    	public void setUp() throws Exception {
    		WebDriver driver = new FirefoxDriver();
        	String baseUrl = "http://localhost:8080/FastOrderV2/home";
        	selenium = new WebDriverBackedSelenium(driver, baseUrl);
    	}


	  @Test
	  public void testConnexionSelenium() throws Exception {
		  selenium.open("/home/");
		  
		  selenium.waitForPageToLoad("30000");
	  }

	  @After
	  public void tearDown() throws Exception {
		  selenium.stop();
	  }
}
