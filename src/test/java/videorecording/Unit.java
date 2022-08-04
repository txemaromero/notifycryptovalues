package videorecording;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Unit {

    public static void main(String[] args) {
    	
  		WebDriverManager.chromedriver().setup();
  		ArrayList<String> optionsList = new ArrayList<String>();
		ChromeOptions chromeOptions = new ChromeOptions();
		optionsList.add("--start-maximized");
		optionsList.add("--incognito");
		optionsList.add("disable-notifications");
		chromeOptions.addArguments(optionsList);
		chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
  		chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		
  		WebDriver driver = new ChromeDriver(chromeOptions);
    	
        driver.get("https://github.com/txemaromero");
        driver.manage().window().maximize();
        
        VideoRecord videoRecord = new VideoRecord();
        try
        {
			videoRecord.startRecording();
		}
        catch (Exception e)
        {
			e.printStackTrace();
		}
        try
        {
			Thread.sleep(5000);
		}
        catch (InterruptedException ie)
        {
			ie.printStackTrace();
		}
        System.out.println("Page title is: " + driver.getTitle());
        try
        {
			videoRecord.stopRecording();
		}
        catch (Exception e)
        {
			e.printStackTrace();
		}
        driver.quit();
    }

}
