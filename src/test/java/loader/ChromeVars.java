package loader;

import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeVars {
    public ChromeVars() {
    }

    public void pathVars() {
        String chromeDriverFile = "src/main/resources/chromedriver";
        String chromeProperty = "webdriver.chrome.driver";
        System.setProperty(chromeProperty, chromeDriverFile);
    }

    public ChromeOptions options() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("--disable-gpu", "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage");
        return options;
    }
}
