package StepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import loader.DManager;
import loader.Unidata;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import java.io.IOException;

public class StepDefinitions {
    DManager driverManager;
    Unidata testUnidata;
    static WebDriver driver;
    static Wait<WebDriver> wait;
    static Actions act;

    @Before
    public void i_connect() {
        driverManager = new DManager();
        driverManager.setupClass();
        driver = driverManager.driver;
        wait = driverManager.wait;
        act = driverManager.act;
        testUnidata = new Unidata(wait,driver, act);
    }

    //todo I need a given/before each personalized by type of test
    @Given("I can connect")
    public void i_can_connect() {
        driverManager = new DManager();
        driverManager.setupClass();
        driver = driverManager.driver;
        wait = driverManager.wait;
        act = driverManager.act;

    }
    @When("I install the FortiClient")
    public void i_install_the_forti_client() {
        driverManager.addFortiCientExtension();
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
        driverManager.teardown();
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

    @Then("I go Articles")
    public void iGoArticles() {
        testUnidata.goToArticles();
    }

    @Then("I go to Article Lists")
    public void iGoToArticleLists() {
        testUnidata.clickOnArticleList();
    }

    @Then("I click on create list from Excel {string}")
    public void iClickOnCreateListFromExcelName(String name) {
        testUnidata.creteListFromExcel(name);
    }

    @Then("I delete an article list {string}")
    public void iDeleteAnArticleListName(String name, String hasToSucced) {
        testUnidata.deleteArticleList(name,hasToSucced);
    }

    @Then("I delete an article list but fail not owner {string}")
    public void iDeleteAnArticleListButFailNotOwnerName() {
    }

    @Then("I delete an article list but fail not owner {string} {string}")
    public void iDeleteAnArticleListButFailNotOwnerNameHasToSucceed(String name, String hasToSucceed) {
        testUnidata.deleteArticleList(name,hasToSucceed);
    }

}
