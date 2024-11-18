package WSS.WSS_Module;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;

public class wss_module {

	static ChromeOptions options = new ChromeOptions();
//	WebDriverManager.chromedriver().setup();
	static ChromeDriver driver;
	public static void main(String[] args) throws InterruptedException, IOException {
		 driver = new ChromeDriver(options);
		 
		String filePath = "C:\\Users\\raju\\eclipse-workspace\\WSS_Module\\TestData.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook(filePath);
		XSSFSheet sheet = workbook.getSheetAt(1);
		XSSFRow row= sheet.getRow(0);
        // Read data from the specific row in Excel
		
		int totalRows = sheet.getLastRowNum();
		int totalCols = row.getLastCellNum();
		
        driver.get("https://cportaluat.jbvnl.co.in/cportal/#/user/login");
        driver.manage().window().maximize();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//a[@href='#/user/login']")).click();
        
        
        
        
        
        for (int i = 1; i <= totalRows; i++) {
        	String usernameData = ExcelReaderTest.getDataByIndex(filePath, 1, i, 0);
        	String passwordData = ExcelReaderTest.getDataByIndex(filePath, 1, i, 1);
        	Thread.sleep(3000);
        	WebElement username = driver.findElement(By.xpath("//input[@data-placeholder='Account/SC Number']"));
            WebElement password = driver.findElement(By.xpath("//input[@data-placeholder='Password']"));
            
            username.clear();
            username.sendKeys(usernameData);
        	
            password.clear();
            password.sendKeys(passwordData);
        	Thread.sleep(3000);
        	
        	driver.findElement(By.xpath("//button[normalize-space()='Lets Go']")).click(); //Click on submit button
    		Thread.sleep(5000);
    		
//    		String failText= driver.findElement(By.xpath("//b[normalize-space()='* Invalid Account/SC Number']")).getText();
//    		String passText= driver.findElement(By.xpath("//h2[normalize-space()='Welcome To JBVNL Self Care Portal']")).getText();
    		 
    		// Try-catch block to handle element presence
    	    try {
    	        String failText = driver.findElement(By.xpath("//b[normalize-space()='* Invalid Account/SC Number']")).getText();
    	        if (failText.equalsIgnoreCase("* Invalid Account/SC Number")) {
    	            System.out.println(failText);
    	        }
    	    } catch (NoSuchElementException e) {
    	        System.out.println("Failure message not found");
    	    }

    	    try {
    	        String welcome = driver.findElement(By.xpath("//h2[normalize-space()='Welcome To JBVNL Self Care Portal']")).getText();
    	        if (welcome.equalsIgnoreCase("Welcome To JBVNL Self Care Portal")) {
    	            System.out.println(welcome);
    	            driver.findElement(By.xpath("//button[normalize-space()='Ok']")).click(); //Click on 'OK' to open the Home Page

    	            // Validate the given account number with displayed account number in home page
    	            String account_sc = driver.findElement(By.xpath("//div[@class='acc-no']//p[@class='ng-star-inserted']")).getText();
    	            if (account_sc.contains(usernameData)) {
    	                System.out.println(account_sc);
    	            } else {
    	                System.out.println("Displayed account Number doesn't match with given account number");
    	            }
    	            
    	            driver.findElement(By.xpath("//a[@id='navbarDropdownProfile']")).click();
//    	            driver.findElement(By.xpath("//a[contains(text(), 'Log out')]")).click();
    	            driver.findElement(By.xpath("(//a[@class='dropdown-item ng-star-inserted'])[4]")).click();
    	            driver.findElement(By.xpath("//button[normalize-space()='Yes']")).click();
    	            
    	        }
    	    } catch (NoSuchElementException e) {
    	        System.out.println("Welcome message not found");
    	    }
    		
        }
        	
        driver.quit();
		}
		
	}
