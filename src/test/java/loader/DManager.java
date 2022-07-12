package loader;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DManager {
    public WebDriver driver;
    public Wait<WebDriver> wait;
    public Actions act;
    public String downloadDirectory;


    public void setupClass() {

        WebDriverManager.chromedriver().setup();
        //WebDriverManager.firefoxdriver().setup();

        downloadDirectory = createDownloadDirectory();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadDirectory);


        ChromeOptions options = new ChromeOptions();
        options.addArguments("--log-level=3", "--disable-gpu", "--disable-logging", "--output=/dev/null", "--disable-in-process-stack-traces", "--output=/dev/null", "--disable-dev-shm-usage");
        options.addArguments("--disable-setuid-sandbox", "--no-sandbox");
        options.addArguments("--window-size=1325x1200");
        //options.addArguments("start-maximized");

        options.setExperimentalOption("prefs", prefs);

        //options.addArguments("--remote-debugging-port=9222");
        //options.setHeadless(true);
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        act = new Actions(driver);
        //FirefoxOptions options = new FirefoxOptions().addArguments("port:4444");
        //driver = new FirefoxDriver();
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(45))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class);
        System.out.println("Driver started");


    }


    public void addFortiCientExtension() {
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

        try {
            Files.walk(Path.of(downloadDirectory))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            System.out.println("Directory deleted");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int getIntRandom() {

        return ThreadLocalRandom.current().nextInt(100, 1000 + 1);
    }

    private char charReturn() {
        Random random = new Random();
        return  (char) (random.nextInt(26) + 'a');
    }

    private String createDownloadDirectory() {
        String userDir = System.getProperty("user.dir");

        String dir = charReturn() + String.valueOf(getIntRandom());
        String fullPath = userDir +"/"+ dir;
        System.out.println(fullPath);
        Path path = Paths.get(fullPath);

        try {
            Files.createDirectories(path);
            System.out.println("Directory is created!");

        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }

        return fullPath;
    }




}
