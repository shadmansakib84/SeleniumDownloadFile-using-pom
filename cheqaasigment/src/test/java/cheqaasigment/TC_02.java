package cheqaasigment;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

public class TC_02 {
	public WebDriver driver;

	@Test

	public void seleniumDownloadFile() throws Exception {
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", System.getProperty("user.dir"));
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.navigate().to("https://intellipaat.com/blog/wp-content/uploads/2022/10/Selenium-Cheat-Sheet-2022.pdf");
		// We find the download
		List<WebElement> list = driver.findElements(By.cssSelector("div[id='maskedImage']"));
    	// Click to 5MB web element
		WebElement el = list.get(list.size() - 1);
		el.click();
		Thread.sleep(500);
		// Popup
		js.executeScript("document.querySelector(\"html > ins\").style.display='none'");
		// Again click to 5MB web element
		el.click();
		Thread.sleep(200);
		// Get the user.dir folder
		File folder = new File(System.getProperty("user.dir"));
		// List the files on that folder
		File[] listOfFiles = folder.listFiles();
		boolean found = false;
		File f = null;
		for (File listOfFile : listOfFiles) {
			if (listOfFile.isFile()) {
				String fileName = listOfFile.getName();
				System.out.println("File " + listOfFile.getName());
				if (fileName.matches("5MB")) {
					f = new File(fileName);
					found = true;
				}

			}
		}
		Assertion assertion = new Assertion();
		assertion.assertTrue(found, "Downloaded document is not found");
		f.deleteOnExit();
		driver.close();
	}
}
