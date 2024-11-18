package WSS.WSS_Module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;
public class meter_changes {
	
	static ChromeOptions options = new ChromeOptions();
	//WebDriverManager.chromedriver().setup();
	static ChromeDriver driver;
	
	public static void main(String[] args) throws InterruptedException {
		
    //options.addArguments("--headless"); //to run the code without opening the browser
    driver = new ChromeDriver(options);

driver.manage().window().maximize();
driver.get("https://cisuat.jbvnl.co.in/collections/login.htm");
Thread.sleep(3000);
driver.findElement(By.id("username")).sendKeys("test");
driver.findElement(By.id("password")).sendKeys("Phoenix@999");
driver.findElement(By.name("login")).click();
driver.findElement(By.xpath("//span[contains(text(),'Metering')]")).click();
Thread.sleep(3000);


for (int i = 0; i<=4; i++) {
	if (i==0) {
		Non_TOD_MC_LT();
	}
	else if(i==1){
		TOD_MC();
	}
	else if(i==2){
		Assign_New_MC();
	}
	else if (i==3){
		SolarNet_MC();
	}
	else if(i==4) {
		Non_TOD_MC_HT();
	}
	
}

driver.close();


}
	static void Non_TOD_MC_LT(){
		driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul/li[5]/a")).click();
		driver.findElement(By.xpath("//a[normalize-space()='Non TOD Meter Change']")).click();
		System.out.println(driver.findElement(By.xpath("//h4[contains(text(),'Meter Changes')]")).getText());
//		System.out.println("Non_TOD_MC_LT");
	}
	
	static void Non_TOD_MC_HT(){
		driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul/li[5]/a")).click();
		driver.findElement(By.xpath("//a[normalize-space()='Non TOD Meter Change']")).click();
		System.out.println(driver.findElement(By.xpath("//h4[contains(text(),'Meter Changes')]")).getText());
//		System.out.println("Non_TOD_MC_HT");
	}
	
	static void TOD_MC() {
		driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul/li[5]/a")).click();
		driver.findElement(By.xpath("//a[normalize-space()='TOD Meter Change']")).click();
		System.out.println(driver.findElement(By.xpath("//h4[contains(text(),'TOD Meter Changes')]")).getText());
//		System.out.println("TOD_MC");
	}
	
	static void Assign_New_MC() {
		driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul/li[5]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"meterChanges\"]/li[1]/ul/li[5]/a")).click();
		System.out.println(driver.findElement(By.xpath("//h4[contains(text(),'Meter Assigning Details')]")).getText());
//		System.out.println("Assign_New_MC");
	}
	
	static void SolarNet_MC() {
		driver.findElement(By.xpath("//*[@id=\"myNavbar\"]/ul/li[5]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"meterChanges\"]/li[1]/ul/li[6]/a")).click();
		System.out.println(driver.findElement(By.xpath("//h4[contains(text(),'Solar Net Meter Change')]")).getText());
//		System.out.println("SolarNet_MC");
	}

}
