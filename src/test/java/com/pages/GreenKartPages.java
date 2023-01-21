package com.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class GreenKartPages {
	public GreenKartPages(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

		@FindBy (how = How.XPATH, using = "//p[@class='product-price']")	
		private static List <WebElement> allPrices;

		public  List<WebElement> getAllPrices() {
			return allPrices;
		}

		public  void setAllPrices(List<WebElement> allPrices) {
			GreenKartPages.allPrices = allPrices;
		}
}
