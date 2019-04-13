package rediffTests;

import java.util.concurrent.TimeUnit;



import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;




public class LoginTest {
	
	static WebDriver driver = null;
	@Test
	public void loginTest(){
		String browser = "Mozilla";// xls, xml
		
		// script
		if(browser.equals("Mozilla")){
			driver = new FirefoxDriver();
		}else if(browser.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Downloads\\Shruthi Installers\\chromedriver_win32\\chromedriver.exe");
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
		boolean result = isElementPresent("//*[@id='username']/a");
		Assert.assertTrue(result, "Not able to login");	
        
		
		
	}
	
	
	public boolean isElementPresent(String xpathExp){
		int s = driver.findElements(By.xpath(xpathExp)).size();
		if(s==0)
			return false;
		else
			return true;
	}
	
	
	public void wait(int time){
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		
	}


