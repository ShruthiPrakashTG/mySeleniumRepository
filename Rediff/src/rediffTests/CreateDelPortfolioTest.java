package rediffTests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;



public class CreateDelPortfolioTest {
	static WebDriver driver = null;
	@Test(priority=1)
	public void createPortfolio()
	{
		
			String browser = "Mozilla";// xls, xml
			
			// script
			if(browser.equals("Mozilla")){
				driver = new FirefoxDriver();
			}else if(browser.equals("Chrome")){
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Downloads\\Shruthi Installers\\chromedriver_win32\\chromedriver.exe");
				
				//ChromeOptions ops = new ChromeOptions();	
				
				 //ops.addArguments("--disable-notifications");
		        // ops.addArguments("disable-infobars");
		        // ops.addArguments("--start-maximized");
		        
		         driver = new ChromeDriver();
			}else if(browser.equals("IE")){
				driver = new InternetExplorerDriver();
			}else if(browser.equals("Edge")){
				driver = new EdgeDriver();
			}
			
			
			driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(200, TimeUnit.SECONDS);
			driver.get("https://www.rediff.com/");
			driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/a[2]")).click();
			driver.findElement(By.xpath("//*[@id='signin_info']/a[1]")).click();
			driver.findElement(By.xpath("//*[@id='useremail']")).sendKeys("shruthiprakashmailbox@rediffmail.com");
			driver.findElement(By.xpath("//*[@id='emailsubmit']")).click();
			WebDriverWait wait =new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='userpass']"))));
			driver.findElement(By.xpath("//*[@id='userpass']")).sendKeys("shruthi26");
			driver.findElement(By.xpath("//*[@id='userpass']")).sendKeys(Keys.ENTER);
		  // Thread.sleep(10000);
		    //driver.findElement(By.xpath("//*[@id='createPortfolio']")).click();
			clickAndWait("//*[@id='createPortfolio']","//*[@id='createPortfolioButton']",5);
		//	String winHandleBefore = driver.getWindowHandle();
			//waitForPageToLoad();
		    driver.findElement(By.xpath("//*[@id='create']")).clear();
			driver.findElement(By.xpath("//*[@id='create']")).sendKeys("Shruthi1");
		//	for(String winHandle : driver.getWindowHandles()){
			//    driver.switchTo().window(winHandle);
		//	}
			//driver.close();
		//	driver.switchTo().window(winHandleBefore);
			driver.findElement(By.xpath("//*[@id='createPortfolioButton']")).click();
			//waitForPageToLoad();
			
			waitForPageToLoad();
			waitTillSelectionToBe("Shruthi1");
			
		
			
	}
	
	

	@Test(priority=2,dependsOnMethods={"createPortfolio"})
	public void deletePortfolio(){
		
		
		driver.findElement(By.id("deletePortfolio")).click();
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
		
	}
	
	
	public void clickAndWait(String xpathExpTarget, String xpathExpWait, int maxTime){
		for(int i=0;i<maxTime;i++){
			// click
			driver.findElement(By.xpath(xpathExpTarget)).click();
			// check presence of other ele
			if(isElementPresent(xpathExpWait) && driver.findElement(By.xpath(xpathExpWait)).isDisplayed()){
				// if present - success.. return
				return;
			}else{
				// else wait for 1 sec
				wait(1);
			}
			
		}
		// 10 seconds over - for loop - comes here
		Assert.fail("Target element coming after clicking on "+xpathExpTarget );
	}
	
	
	
	public void wait(int time){
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean isElementPresent(String xpathExp){
		int s = driver.findElements(By.xpath(xpathExp)).size();
		if(s==0)
			return false;
		else
			return true;
	}

	
	public void waitForPageToLoad(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i=0;
		
		while(i!=10){
		String state = (String)js.executeScript("return document.readyState;");
		System.out.println(state);

		if(state.equals("complete"))
			break;
		else
			wait(2);

		i++;
		}
		// check for jquery status
		i=0;
		while(i!=10){
	
			Long d= (Long) js.executeScript("return jQuery.active;");
			System.out.println(d);
			if(d.longValue() == 0 )
			 	break;
			else
				 wait(2);
			 i++;
				
			}
		
		}
	
	
	public void waitTillSelectionToBe(String string) {
		int i=0;
		while(i!=10){
			WebElement e= driver.findElement(By.id("portfolioid"));
			Select s=new Select(e);	
			String actual = s.getFirstSelectedOption().getText();
			System.out.println(actual);
			if(actual.equals(string))
				return;
			else
				wait(2);			
				i++;
		
	}
		Assert.fail("Value never changed in Select box");
	
}
	}
