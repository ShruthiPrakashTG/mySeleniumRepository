package rediffTests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import org.testng.annotations.Test;

public class buyOrSellStockTest {
	
	static WebDriver driver = null;
	
	@Test 
	public void buySellStock() throws InterruptedException{
		String browser = "Chrome";// xls, xml
		
		// script
		if(browser.equals("Mozilla")){
			driver = new FirefoxDriver();
		}else if(browser.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\Downloads\\Shruthi Installers\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
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
		waitForPageToLoad();
		WebElement e= driver.findElement(By.id("portfolioid"));
		waitForPageToLoad();
		Select s=new Select(e);
		s.selectByVisibleText("Shruthi1");
		waitForPageToLoad();
		int rNum=getRowWithCellData("Tata Steel");
		System.out.println("Row "+ rNum);
		driver.findElement(By.xpath("//table[@id='stock']/tbody/tr["+rNum+"]/td[1]")).click();
		driver.findElements(By.xpath("//input[@class='buySell']")).get(rNum-1).click();
		
		driver.findElement(By.name("buySellStockDate")).sendKeys("01-04-2019");
		//Thread.sleep(100);
		//driver.findElement(By.cssSelector("button.dpTodayButton:nth-child(2)")).click();
		driver.findElement(By.id("buysellqty")).sendKeys("200");
		driver.findElement(By.id("buysellprice")).sendKeys("500");
		driver.findElement(By.id("buySellStockButton")).click();
		waitForPageToLoad();
	}

	
	@Test
	public void checkTransactionHistory(){
		int rNum=getRowWithCellData("Tata Steel");
		System.out.println("Row "+ rNum);
		driver.findElement(By.xpath("//table[@id='stock']/tbody/tr["+rNum+"]/td[1]")).click();
		driver.findElements(By.xpath("//input[@class='equityTransaction']")).get(rNum-1).click();
		String actual=driver.findElement(By.xpath("//table[@id='stock']/tbody/tr["+rNum+"]/td[5]")).getText();
		List<WebElement> shares = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[3]"));
		List<WebElement> prices = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[4]"));
		
		int totalShares=0;
		int totalAmount=0;
		for(int i=0;i<prices.size();i++){
			int share = Integer.parseInt(shares.get(i).getText());
			int price = Integer.parseInt(prices.get(i).getText());
			totalShares = share + totalShares;
			totalAmount=totalAmount + (share*price);
		}
		System.out.println("Total shares - "+totalShares );
		System.out.println("Total Amount spent "+totalAmount );
		double average = Double.valueOf(totalAmount)/Double.valueOf(totalShares);
		System.out.println("Average - "+average );
		Assert.assertEquals(actual, average);
		
	}
	public void selectDate(String d) {
		Date current = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date selected = sd.parse(d);
			String day = new SimpleDateFormat("dd").format(selected);
			String month = new SimpleDateFormat("MMMM").format(selected);
			String year = new SimpleDateFormat("yyyy").format(selected);
			System.out.println(day+" --- "+month+" --- "+ year);
			String desiredMonthYear=month+" "+year;
			
			while(true){
				String displayedMonthYear=driver.findElement(By.cssSelector(".dpTitleText")).getText();
				if(desiredMonthYear.equals(displayedMonthYear)){
					// select the day
					driver.findElement(By.linkText(day)).click();
					break;
					
				}else{
					
					if(selected.compareTo(current) > 0)
						driver.findElement(By.xpath("//*[@id='datepicker']/table/tbody/tr[1]/td[4]/button")).click();
					else if(selected.compareTo(current) < 0)
						driver.findElement(By.xpath("//*[@id='datepicker']/table/tbody/tr[1]/td[2]/button")).click();
					
				}
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			}
	
	public void wait(int time){
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		wait(2);// wait of 2 sec between page status and jquery
		// check for jquery status
		i=0;
		while(i!=10){
	
			Boolean result= (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			System.out.println(result);
			if(result )
			 	break;
			else
				 wait(2);
			 i++;
				
			}
		
		}

	public int getRowWithCellData(String data){
		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='stock']/tbody/tr"));
		for(int rNum=0;rNum<rows.size();rNum++){
			WebElement row = rows.get(rNum);
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for(int cNum=0;cNum<cells.size();cNum++){
				WebElement cell = cells.get(cNum);
				if(!cell.getText().trim().equals("") && data.contains(cell.getText()))
					return ++rNum;
			}
		}
		
		return -1;// not found
	}
	
}
