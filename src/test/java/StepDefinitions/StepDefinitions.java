package StepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
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

public class StepDefinitions {
    DManager driverManager;
    Unidata testUnidata;
    static WebDriver driver;
    static Wait<WebDriver> wait;
    static Actions act;
    final AuxOperations operations = new AuxOperations();


    @Before
    public void i_connect() {
        driverManager = new DManager();
        driverManager.setupClass();
        driver = driverManager.driver;
        wait = driverManager.wait;
        act = driverManager.act;
        testUnidata = new Unidata(wait,driver, act, operations);
        //String role = System.getProperty("role");
        String role = "UD_dataOwner";
        //String environment = System.getProperty("omgeving");
        String environment = "STAGING";
        //System.out.println(role + " " + environment);

        operations.performUnidata(role,environment);

    }

    //To deprecate?
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

    @Then("I go to Articles")
    public void iGotoArticles() {
        testUnidata.goToArticles();
    }

    @Then("I go to Article Lists")
    public void iGoToArticleLists() {
        testUnidata.clickOnArticleList();
    }

    @Then("I click on create list from Excel")
    public void iClickOnCreateListFromExcelName() {
        testUnidata.creteListFromExcel(operations.getName());
    }

    @Then("I delete an article list {string}")
    public void iDeleteAnArticleListName(String name, String hasToSucced) {
        testUnidata.selectElementOnList(name);
        testUnidata.clickOnActionsMenu();
        testUnidata.deleteArticleList(hasToSucced);
    }

    @Then("I delete an article list but fail not owner")
    public void iDeleteAnArticleListButFailNotOwner() {
        testUnidata.selectElementOnList(operations.getName());
        testUnidata.clickOnActionsMenu();
        testUnidata.deleteArticleList(operations.getHasToSucceed());
    }

    @Then("I add a participant in my list")
    public void iAddAParticipantInMyListNameParticipant() {
        testUnidata.addParticipantOnList(operations.getName(), operations.getParticipant());
    }

    @Then("I add an article to existing List")
    public void iAddAnArticleToExistingList() {
        testUnidata.addArticleOnExistingList();
    }

    @Then("I detach an article from existing List")
    public void iDetachAnArticleFromExistingList() {
        testUnidata.detachElementsFromList();
    }

    @Then("I go to Feedback Menu")
    public void iGoToFeedbackMenu() {
        testUnidata.goToFeedback();
    }

    @Then("I create a new feedback addressing to addressee")
    public void iCreateANewFeedbackAddressingToAssignee() {
        testUnidata.createFeedbackToAddressee(operations.getAddressee());
    }

    @Then("I select the Biomed Articles view")
    public void iSelectTheBiomedArticlesView() {
        testUnidata.selectBiomedArticlesView();
    }

    @Then("I export a number of articles with the service code-label-image")
    public void iExportANumberOfArticlesWithTheServiceCodeLabelImage() {
        testUnidata.exportArticlesBiomedExcel();
    }

    @Then("I check if the results are sorted alphabetically on code")
    public void iCheckIfTheResultsAreSortedAlphabeticallyOnCode() {
        testUnidata.checkResultsExcelAlphaOrder();
    }

    @Then("I go to Descriptions Menu")
    public void iGoToDescriptionsMenu() {
        //testUnidata.goToDescriptions();
    }

    @Then("I click on a Description")
    public void iClickOnADescription() {
        //testUnidata.clickOnDescriptionElement();
    }

    @Then("I close the record")
    public void iCloseTheRecord() {
        //testUnidata.closeRecord();
    }

    @Then("I validate I am on the description table again")
    public void iValidateIAmOnTheDescriptionTableAgain() {
        //testUnidata.validateIamOnDescriptions();
    }

    @Then("I delete an article list")
    public void iDeleteAnArticleList( ) {
        String status = "true";
        testUnidata.selectElementOnList(operations.getParticipant());
        testUnidata.clickOnActionsMenu();
        testUnidata.deleteArticleList(status);
    }

    @Then("I find the lists where I am participant")
    public void iFindTheListsWhereIAmParticipant() {
        testUnidata.filterAsParticipant();

        }

    @Then("I add an article to existing List as Participant")
    public void iAddAnArticleToExistingListAsParticipant() {
        testUnidata.addArticleOnExistingListAsParticipant();
    }

    @Then("I delete an article list as Participant")
    public void iDeleteAnArticleListAsParticipant() {
        testUnidata.detachElementsFromListAsParticipant();
    }

    @Then("I Change the name of the Article list fail due rights")
    public void iChangeTheNameOfTheArticleListFailDueRights() {
        testUnidata.changeListNameFail();
    }


    @Then("I Delete the article list fail due rights")
    public void iDeleteTheArticleListFailDueRights() {
        testUnidata.deleteArticleList("false");
    }

    @When("I go to url")
    public void iGoToUrl() {
        testUnidata.iGoTo(operations.getUrl());
    }

    @Then("I log in with user and password")
    public void iLogInWithUserAndPassword() {
        testUnidata.login(operations.getUser(),operations.getPassword());
    }


    @Then("I update the email with newEmail")
    public void iUpdateTheEmailWithNewEmail() {
        testUnidata.updateEmail(operations.getNewEmail());
    }

    @Then("I check the email newEmail")
    public void iCheckTheEmailNewEmail() {
        testUnidata.checkEmail(operations.getNewEmail());
    }

    @Then("I update the email with oldEmail")
    public void iUpdateTheEmailWithOldEmail() {
        testUnidata.updateEmail(operations.getOldEmail());
    }

    @Then("I check the email oldEmail")
    public void iCheckTheEmailOldEmail() {
        testUnidata.checkEmail(operations.getOldEmail());
    }

    @Then("I go to Kit Compositions")
    public void iGoToKitCompositions() {
        testUnidata.iGoToKitCompositions();
    }

    @Then("I click on TreeView")
    public void iClickOnTreeView() {
        testUnidata.iClickOnTreeView();
    }

    @Then("I click on a Tree View branch")
    public void iClickOnATreeViewBranch() {
        testUnidata.iClickOnTreeViewBranch();
    }

    @Then("I click on a Kit Article")
    public void iClickOnAKitArticle() {
        testUnidata.iClickOnKitArticle();
    }

    @Then("I click on Kit Article tabs")
    public void iClickOnKitArticleTabs() {
        testUnidata.iClickOnKitArticleTabs();
    }


    @Then("I collect Article information")
    public void iCollectArticleInformation() {
        testUnidata.iCollectArticleData();
    }

    @Then("I go to Feedback Addressed to me")
    public void iGoToFeedbackAddressedToMe() {
        testUnidata.goToFeedBackAddressedToMe();
    }

    @Then("I type a new message Feedback")
    public void iTypeANewMessageFeedback() {
        testUnidata.addNewMessageFeedback();
    }

    @Then("I add and addressee")
    public void iAddAndAddressee() {
        testUnidata.addAddressee();
    }

    @Then("I change the priority")
    public void iChangeThePriority() {
        testUnidata.addPriorityToMessage();
    }

    @Then("I save and close the feedback")
    public void iSaveAndCloseTheFeedback() {
        testUnidata.saveAndClose();
    }

    @Then("I click on specific article")
    public void iClickOnSpecificArticle() {
        testUnidata.clickSpecificArticle();
    }

    @Then("I validate the content of the product in Unicat")
    public void iValidateTheContentOfTheProductInUnicat() {
        testUnidata.validateOpenTabUnicat();
    }

    @Then("I select a feedback addressed to me")
    public void iSelectAFeedbackAddressedToMe() {
        testUnidata.iSelectFeedbacckAddressedToMe();
    }

    @Then("I create a NST Article")
    public void iCreateANSTArticle() {
        testUnidata.createNSTArticle();
    }
}


