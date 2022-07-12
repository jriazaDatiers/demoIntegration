package loader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;

public class MSL {

    Wait<WebDriver> wait;
    WebDriver driver;
    Actions act;
    String downloadDirectory;
    ArrayList<String> articleList = new ArrayList<>();
    Article article = new Article();
    final AuxOperations operations;
    int common2Random;

    public MSL(Wait<WebDriver> wait, WebDriver driver, Actions act, AuxOperations operations, String downloadDirectory) {
        this.wait = wait;
        this.driver = driver;
        this.act = act;
        this.operations = operations;
        this.downloadDirectory = downloadDirectory;
    }

    public void login(String userName, String password){

        WebElement element = driver.findElement(By.xpath("//a[contains(text(),'here')]"));
        element.click();
        WebElement userField = driver.findElement(By.id("login"));
        userField.sendKeys(userName);
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);
        WebElement submit = driver.findElement(By.id("edit-submit"));
        submit.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'UniData, MSF & Me')]")));
        System.out.println("Logged in");
    }


}
