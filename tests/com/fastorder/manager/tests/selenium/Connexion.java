package com.fastorder.manager.tests.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.thoughtworks.selenium.Selenium;

public class Connexion {

	private Selenium selenium;
	private WebDriver driver;

	@Before
	public void setUp(){
		driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8080/FastOrderV2";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}


	@Test
	public void testConnexionSelenium(){
		selenium.open("/login");
		driver.findElement(By.name("mail")).clear();
		driver.findElement(By.name("mail")).sendKeys("pol.patrick1411@gmail.com");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("test1234");
		driver.findElement(By.cssSelector("button.btn.btn-primary")).click();

	}

	@After
	public void tearDown(){
		selenium.stop();
	}
}
