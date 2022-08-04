package sendmessage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WhatsApp {
	
	private static WebDriver driver = null;
	private static final int MAXTIMEOUTS = 45;
	
	public static void configure() {
		
		WebDriverManager.chromedriver().setup();
  		ArrayList<String> optionsList = new ArrayList<String>();
		ChromeOptions chromeOptions = new ChromeOptions();
		optionsList.add("--start-maximized");
		optionsList.add("--incognito");
		optionsList.add("disable-notifications");
		chromeOptions.addArguments(optionsList);
		chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
  		chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		
  		driver = new ChromeDriver(chromeOptions);
		
	}

	public static boolean send(String chatName, String text) {
		
		if (Objects.isNull(driver))		configure();
		
        //Navigate to WhatsApp Web URL
        driver.get("https://web.whatsapp.com/");
        
        try {
  		
        	System.out.println("Scan WhatsApp QR code ...");
        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(MAXTIMEOUTS));
        	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("side")));
        	
        }
        catch (TimeoutException e) {
        	
        	System.err.println("WhatsApp logging failed: timeout");;
        	driver.quit();
        	driver = null;
        	return false;
        	
        }
        
        System.out.println("WhatsApp logged in ...");
        
        //Enter the name of the friend or group you wish to send message
        driver.findElement(By.xpath("//div[contains(@title,'Cuadro de texto para ingresar la búsqueda')]")).sendKeys(chatName);
        
        //Enter on the friend chat window        
        driver.findElement(By.xpath("//div[contains(@title,'Cuadro de texto para ingresar la búsqueda')]")).sendKeys(Keys.ENTER);
        
        //Enter the message and hit enter
        driver.findElement(By.xpath("//div[contains(@title,'Escribe un mensaje aquí')]")).click();
        driver.findElement(By.xpath("//div[contains(@title,'Escribe un mensaje aquí')]")).sendKeys(text);
        driver.findElement(By.xpath("//div[contains(@title,'Escribe un mensaje aquí')]")).sendKeys(Keys.ENTER);
        
        //WaitUtils.sleep(10000);
        //quitDriver();
        //System.out.println("WhatsApp log out");
        
        return true;
        
    }
	
	public static void quitDriver() {
		
		if (Objects.nonNull(driver))
        {
			driver.close();
			driver.quit();
			driver = null;
        }	
        
	}

}
