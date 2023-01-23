package com.steps;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pages.GreenKartPages;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GreenKartSteps {

	WebDriver driver;
	GreenKartPages pf;
	int totalPrice;
	List<Integer>prices=new ArrayList<Integer>();
	int secondPrice;
	int thirdPrice;
	@Given("^go to Green Kart home page$")
	public void go_to_Green_Kart_home_page() throws Throwable {
		driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		driver.manage().window().maximize();
		pf= new GreenKartPages(driver); 
	    
	}

	@Given("^user verify the page title$")
	public void user_verify_the_page_title() throws Throwable {
		String expected="GreenKart - veg and fruits kart";
	    String actual= driver.getTitle();
	    Assert.assertTrue("The title doesn't match", actual.contains(expected));
	   //System.out.println(driver.getTitle()); 
	   // Assert.assertEquals(expected, actual);
	}

	@When("^user capture all items from the page$")
	public void user_capture_all_items_from_the_page() throws Throwable {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)"); 
		List<WebElement> elements= pf.getAllPrices();
	    WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	    
	    for (WebElement element : elements) {
			String price= element.getText().trim();
			int priceInt= Integer.parseInt(price);
			prices.add(priceInt);	
		}
//	    for (int i=0; i<elements.size(); i++) {
//	    	String price= elements.get(i).getText().trim();
//			int priceInt= Integer.parseInt(price);
//			prices.add(priceInt);
//	    	//System.out.println(priceInt);
//	    }
	    
	}

	@When("^print sorted prices in decending order$")
	public void print_sorted_prices_in_decending_order() throws Throwable {
	    Collections.sort(prices, Collections.reverseOrder());
	    System.out.println(prices);
	    
	}

	@When("^add second plus third item into cart$")
	public void add_second_plus_third_item_into_cart() throws Throwable {
		secondPrice= prices.get(1);
		thirdPrice= prices.get(2);
		totalPrice= secondPrice+thirdPrice;
		driver.findElement(By.xpath("//p[contains(text(),'"+secondPrice+"')]/following-sibling::div[2]")).click();
		driver.findElement(By.xpath("//p[contains(text(),'"+thirdPrice+"')]/following-sibling::div[2]")).click();
		
	    
	}

	@Then("^user go to cart page$")
	public void user_go_to_cart_page() throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@alt='Cart']")));
		   driver.findElement(By.xpath("//*[@alt='Cart']")).click();
		   driver.findElement(By.xpath("//*[contains(text(),'PROCEED TO CHECKOUT']")).click();
	    
	}

	@Then("^user verify total price$")
	public void user_verify_total_price() throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='totAmt']"))); 
	    String totalPriceString= driver.findElement(By.xpath("//*[@class='totAmt']")).getText();
		Integer totalPriceInt=Integer.parseInt(totalPriceString);
		Integer totalPriceAddition=secondPrice +thirdPrice;
	    Assert.assertTrue("The total price is incorrect", totalPriceInt.equals(totalPriceAddition));  
	    
	}

	@Then("^user go to next page to select country \"([^\"]*)\"$")
	public void user_go_to_next_page_to_select_country(String arg1) throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Place Order']")));  
	    driver.findElement(By.xpath("//*[contains(text(),'Place Order']")).click();
	    driver.findElement(By.xpath("//*[@style=\"width: 200px;\"]")).click();
	    driver.findElement(By.xpath("//*[@value='" + arg1 + "']")).click();
	    driver.findElement(By.xpath("//*[@type='checkbox']")).click();
	    driver.findElement(By.xpath("//*[contains(text(),'Proceed']")).click();     
	    
	}

	@Then("^user verify succesfully shipped items$")
	public void user_verify_succesfully_shipped_items(String arg1) throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'" + arg1 + "')]")));	
	    WebElement actualElement = driver.findElement(By.xpath("//*[contains(text(),'Thank you,your order has been placed successfully')]"));
	    String actual = actualElement.getText();
	    Assert.assertTrue("The text for product shipment is incorrect", actual.contains(arg1)); 
	    
	}

}

