package com.bignibou.bdd.pages;

import java.util.concurrent.TimeUnit;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Home extends AbstractPage {

	private final WebDriver driver;

	public Home(WebDriverProvider driverProvider) {
		super(driverProvider);
		this.driver = driverProvider.get();
	}

	public void open() {
		get("http://localhost:8080/bignibou/");
		manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void enterDetails(String email, String status, String password) {
		driver.findElement(By.name("member.email")).sendKeys(email);
		driver.findElement(By.name("member.status")).sendKeys(status);
		driver.findElement(By.name("member.password")).sendKeys(password);
	}

	public void doRegister() {
		driver.findElement(By.name("member.doRegister")).submit();
	}

}