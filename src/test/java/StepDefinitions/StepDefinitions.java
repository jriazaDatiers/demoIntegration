package StepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import loader.ChromeVars;
import loader.Unidata;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.IOException;
import java.time.Duration;

public class StepDefinitions extends ChromeVars {
    //DManager driverManager;
    Unidata testUnidata;
    static WebDriver driver;
    static Wait<WebDriver> wait;
    static Actions act;

    @Before
    public void i_connect() {
        pathVars();
        driver = new ChromeDriver( options());
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        act = new Actions(driver);
        testUnidata = new Unidata(wait,driver, act);

    }


    @When("I log in with user {string} and password {string}")
    public void i_log_in_with_user_and_password(String userName, String password) {

        testUnidata.login(userName,password);
    }

    @Then("I am able to update the email with {string}")
    public void i_am_able_to_update_the_email_with(String newEmail) {
        testUnidata.updateEmail(newEmail);

    }
    @Then("I am able to check the email {string}")
    public void i_am_able_to_check_the_email(String emailToCheck) {
        testUnidata.checkEmail(emailToCheck);
    }

    @Then("I go to My Unidata")
    public void iGoToMyUnidata() {
        testUnidata.unidataMSFME();
        testUnidata.myUnidata();
    }

    @Then("I go to My Unidata and inside")
    public void iGoToMyUnidataAndInside() {
        testUnidata.unidataMSFME();
        testUnidata.myUnidata();
        testUnidata.goToMydataIFrame();
    }

    @After
    @Then("I close the driver")
    public void iCloseTheDriver(){
        driver.quit();
    }


    @Then("I click on article {string}")
    public void iClickOnArticleArticleId(String articleId) {
        testUnidata.chooseArticle();

    }

    @Then("I click on article")
    public void iClickOnArticle() {
        testUnidata.chooseArticle();

    }

    @When("I go to {string}")
    public void iGoToUrl(String url) {
        testUnidata.iGoTo(url);
    }

    @Then("I check the links in Unidata Home")
    public void iCheckTheLinksInUnidataHome() {
        testUnidata.checkUnidataHomeLinks();

    }

    @Then("I go to Composition Lists")
    public void iGoToCompositionLists() {
        testUnidata.iGoToCompositionLists();
    }

    @Then("I select kit articles")
    public void iSelectKitArticles() {
        //testUnidata.iSelectKitArticles();
        testUnidata.iSelectKitArticlesAndextractData();
    }

    @Then("I export them grouped")
    public void iExportThemGrouped() {
        testUnidata.iExportGrouped();
    }

    @Then("I download the excel")
    public void iDownloadTheExcel() {
        testUnidata.iDownloadExcel();
    }


    @Then("I check the excel")
    public void iCheckTheExcel() throws IOException {
        testUnidata.iCheckExcel();
    }

}
