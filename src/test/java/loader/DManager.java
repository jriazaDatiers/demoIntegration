package loader;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class DManager {
    public  WebDriver driver;
    public  Wait<WebDriver> wait;
    public Actions act;


    public void setupClass() {

        //WebDriverManager.chromedriver().setup();
        WebDriverManager.chromedriver().browserVersion("101.0.4951.41").setup();
        WebDriverManager.chromedriver().driverVersion("101.0.4951.41").setup();
        //WebDriverManager.firefoxdriver().setup();

       //ProfilesIni profileIni = new ProfilesIni();
        //FirefoxProfile profile = profileIni.getProfile("default");
        //FirefoxOptions options = new FirefoxOptions();
        //options.setProfile(profile);
        //File extension = new File("C:\\ext\\webfilter.xpi");
        //profile.addExtension(extension);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);


        driver = new ChromeDriver(options);

        act = new Actions(driver);
        //FirefoxOptions options = new FirefoxOptions().addArguments("port:4444");
        //driver = new FirefoxDriver();
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        System.out.println("Driver started");
        //driver.get("https://addons.mozilla.org/en-US/firefox/search/?q=fortiClient");
        //System.out.println("Added extension");
        driver.switchTo().newWindow(WindowType.TAB);
    }


    public void addFortiCientExtension(){
        driver.get("https://addons.mozilla.org/en-US/firefox/search/?q=fortiClient");
        //driver.get("https://chrome.google.com/webstore/detail/forticlient-chromebook-we/igbgpehnbmhgdgjbhkkpedommgmfbeao");
        //WebElement inputExtensions = driver.findElement(By.xpath("//*[@id=\"AutoSearchInput-q\"]"));
        //inputExtensions.sendKeys("fortiClient");
        //inputExtensions.click();
        //WebElement fortiClientExtension = driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/section/div/ul/li[1]/div/div/div[2]/h2/a"));
        //fortiClientExtension.click();
        //WebElement addToFirefox = driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[1]/section[1]/div/header/div[4]/div/div/a"));
        //addToFirefox.click();
        System.out.println("Added extension");

    }

    public void teardown() {
        driver.quit();
        System.out.println("Driver closed");
    }


}
