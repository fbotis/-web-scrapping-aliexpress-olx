package olx;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by florinbotis on 14/03/2016.
 */
public class Test {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/florinbotis/Personal/chromedriver");
        WebDriver driver = new FirefoxDriver();
        driver.get("http://www.olx.ro");
        driver.findElement(By.id("postNewAdLink")).click();
        driver.close();
    }
}
