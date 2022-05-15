package StepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import loader.AuxOperations;
import loader.DManager;
import loader.Unidata;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;

import java.io.IOException;

public class StepDefinitions extends AuxOperations {
    DManager driverManager;
    Unidata testUnidata;
    static WebDriver driver;
    static Wait<WebDriver> wait;
    static Actions act;
    private String user;
    private String password;
    private String url;

    //@Before
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
        //driverManager.teardown();
        System.out.println("End demo");
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

    @When("I do demo")
    public void iDoDemo() {
        System.out.println("Demo");
        String role = System.getProperty("role");
        String environment = System.getProperty("environment");

        System.out.println(role + " " + environment);
    }

    @When("I am a precondition")
    public void iAmAPrecondition() {
        System.out.println("Precondition done");
    }

    @When("I log in")
    public void iLogIn() {
    }

    @Then("I Open the feedback at the top")
    public void iOpenTheFeedbackAtTheTop() {
    }

    @Then("I Add an answering message")
    public void iAddAnAnsweringMessage() {
    }

    @Then("I Add a new addressee in the Feedback")
    public void iAddANewAddresseeInTheFeedback() {
    }

    @Then("I Save and close")
    public void iSaveAndClose() {
    }

    @Then("I go to the menu item Feedback addressed to me")
    public void iGoToTheMenuItemFeedbackAddressedToMe() {
    }

    @Then("I go to the menu item Feedback initiated by me")
    public void iGoToTheMenuItemFeedbackInitiatedByMe() {
    }

    @Then("I Set the status of the Feedback message to Closed")
    public void iSetTheStatusOfTheFeedbackMessageToClosed() {
    }

    @Then("I go to an article record")
    public void iGoToAnArticleRecord() {
    }

    @Then("I go to the Feedback tab")
    public void iGoToTheFeedbackTab() {
    }

    @Then("I create a Feedback with the + button")
    public void iCreateAFeedbackWithTheButton() {
    }

    @Then("I address to a user")
    public void iAddressToAUser() {
    }

    @Then("I add a Message")
    public void iAddAMessage() {
    }

    @Then("I set a priority")
    public void iSetAPriority() {
    }

    @When("I go to url")
    public void iGoToUrl() {
        String role = System.getProperty("role");
        String environment = System.getProperty("url");
        System.out.println(role + " " + environment);
        AuxOperations operations = new AuxOperations();
        operations.performUnidata(role,environment);

        user = operations.roleDataMap.get("user");
        password = operations.roleDataMap.get("password");
        url = operations.environmentsDataMap.get("environment");


        testUnidata.iGoTo(url);
    }

    @Then("I log in with user and password")
    public void iLogInWithUserAndPassword() {
        testUnidata.login(user,password);
    }
}
