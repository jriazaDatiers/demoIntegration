package loader;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Unidata {

    Wait<WebDriver> wait;
    WebDriver driver;
    Actions act;
    ArrayList<String> articleList = new ArrayList<>();

    public Unidata(Wait<WebDriver> wait, WebDriver driver, Actions act) {
        this.wait = wait;
        this.driver = driver;
        this.act = act;
    }

    public void firstSteps(){

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://staging.unidata.msf.org/");

        unidataMSFME();
        myUnidata();

        int size = driver.findElements(By.tagName("iframe")).size();
        System.out.println(size);
        goToMydataIFrame();
        checkEmail("test.ignore@msf.org");
        updateEmail("juan.moron-consultant@msf.org");
        checkEmail("juan.moron-consultant@msf.org");
        updateEmail("test.ignore@msf.org");
        checkEmail("test.ignore@msf.org");


    }

    public void iGoTo(String url){
        driver.get(url);
    }

    public void login(String userName, String password){

        WebElement element = driver.findElement(By.xpath("//*[contains(text(),'here')]"));
        element.click();
        WebElement userField = driver.findElement(By.id("login"));
        userField.sendKeys(userName);
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);
        WebElement submit = driver.findElement(By.id("edit-submit"));
        submit.click();
        System.out.println("Logged in");
    }

    public void unidataMSFME(){
/*        Function<WebDriver,WebElement> function;
        function = arg0 ->
        {
            WebElement buttonMe = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[1]/div/div[2]/div/div[1]/div/div/div/ul/li[2]/div[2]/div/div/div[2]/span"));
            buttonMe.click();
            return buttonMe;
        };
        wait.until(function);*/
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div/div[2]/div/div[1]/div/div[2]/div/div[1]/div/div/div/ul/li[2]/div[2]/div/div/div[2]/span")));
        WebElement buttonMe = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[1]/div/div[2]/div/div[1]/div/div/div/ul/li[2]/div[2]/div/div/div[2]/span"));
        buttonMe.click();
    }

    public void myUnidata(){
/*        Function<WebDriver,WebElement> function;
        function = arg0 ->
        {
            WebElement myUnidata = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[1]/div/div[2]/div/div[1]/div/div/div/ul/ul[1]/li[1]/div[2]/a/span"));
            myUnidata.click();
            return myUnidata;
        };
        wait.until(function);*/

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div/div[2]/div/div[1]/div/div[2]/div/div[1]/div/div/div/ul/ul[1]/li[1]/div[2]/a/span")));
        WebElement myUnidata = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[1]/div/div[2]/div/div[1]/div/div/div/ul/ul[1]/li[1]/div[2]/a/span"));

        myUnidata.click();

    }

    public void updateEmail(String emailComtent){
        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"___40_cfvAO__email\"]"));
        emailField.clear();
        emailField.sendKeys(emailComtent);
        WebElement saveButton = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div[2]/form/div[3]/div[1]/button[2]"));
        saveButton.click();
    }

    public void goToMydataIFrame(){

        driver.switchTo().frame("ebx-legacy-component");
        /*Function<WebDriver,WebElement> function1;
        function1 = arg0 ->
        {
            WebElement divEbxLegacy = driver.findElement(By.xpath("//*[@id=\"ebx_Workspace\"]"));
            divEbxLegacy.isDisplayed();
            return divEbxLegacy;
        };
        wait.until(function1);*/
        //WebElement divEbxLegacy = driver.findElement(By.xpath("//*[@id=\"ebx_Workspace\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ebx_Workspace\"]")));
        driver.switchTo().frame("myUnidataFrame");
        /*Function<WebDriver,WebElement> function2;
        function2 = arg0 ->
        {
            WebElement divmyUnidataFrame = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div[1]/div[1]/div/div/div[1]/div/div/div/div/h2[1]"));
            divmyUnidataFrame.isDisplayed();
            return divmyUnidataFrame;
        };
        wait.until(function2);*/
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div/div/div/div/div[1]/div[1]/div/div/div[1]/div/div/div/div/h2[1]")));
        //WebElement divmyUnidataFrame = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div[1]/div[1]/div/div/div[1]/div/div/div/div/h2[1]"));

        driver.switchTo().frame("ebx-legacy-component");
    }

    public void checkEmail(String emailComtent){

        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"___40_cfvAO__email\"]"));
        Assert.assertTrue(emailField.getAttribute("value").contains( emailComtent));
        System.out.println("Email field content: "+ emailField.getAttribute("value")+ " ,Checked");
    }

    public void goToArticles(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Articles')]")));
        String pathArticles = "//*[contains(text(),'Articles')]";
        WebElement articles = driver.findElement(By.xpath(pathArticles));

        if(articles.getAttribute("title").equals("Collapsed")){
            articles.click();
        }

        System.out.println("Articles");
    }

    public void goToFeedback(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Feedback')]")));
        String pathFeedback = "//*[@id=\"_ebx-root\"]/div/div/div[2]/div/div[1]/div/div[2]/div/div[1]/div/div/div/ul/li[9]/div[2]/div/button";
        WebElement feedbackMenu = driver.findElement(By.xpath(pathFeedback));

        if(feedbackMenu.getAttribute("title").equals("Collapsed")){
            feedbackMenu.click();
        }

        System.out.println("Feedback Menu");
    }

    public void creteListFromExcel(String name){
        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Create List')]")));
        WebElement createList = driver.findElement(By.xpath("//*[contains(text(),'Create List')]"));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Create List')]")));
        createList.click();
        act.click(createList).perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='file']")));
        WebElement chooseFile = driver.findElement(By.cssSelector("input[type='file']"));
        chooseFile.sendKeys("C:\\Users\\jmr\\IdeaProjects\\msf_demo\\src\\test\\resources\\Article-Belongs-to-List.xlsx");

        WebElement listName = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div/form/div[3]/table/tbody/tr[1]/td[3]/div/input"));
        listName.sendKeys(name + " list " + getIntRandom());
        System.out.println("createList");

        WebElement upload = driver.findElement(By.xpath("//*[contains(text(),'Upload')]"));
        upload.click();
        System.out.println("List uploaded");
    }

    public void chooseArticle(){
        Function<WebDriver,WebElement> function;
        function = arg0 ->
        {
        WebElement allArticlesMenu = driver.findElement(By.xpath("//*[@id=\"_ebx-root\"]/div/div/div[2]/div/div[1]/div/div[2]/div/div[1]/div/div/div/ul/ul[2]/li[1]/div[2]/a"));
        allArticlesMenu.click();
            return allArticlesMenu;
        };
        wait.until(function);

        driver.switchTo().frame("ebx-legacy-component");
        //WebElement article = driver.findElement(By.xpath("//*[contains(., '"+id+"')]"));
        Function<WebDriver,WebElement> function2;
        function2 = arg0 ->
        {
        WebElement articleUnicat = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div[2]/div/div[1]/div/div/div/div[2]/div/table/tbody/tr[4]/td[3]/div/div/button"));
        articleUnicat.click();
            return articleUnicat;
        };
        wait.until(function2);

        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        driver.switchTo().frame("ebx-legacy-component");
        WebElement article = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div[2]/div/div[1]/div/div/div/div[2]/div/table/tbody/tr[4]/td[3]/div/div"));
        act.doubleClick(article).perform();
        //article.click();


        System.out.println("Article clicked");
        WebElement closeButton = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div[2]/form/div[3]/div[1]/button[3]"));
        closeButton.click();
        System.out.println("Close article");

    }

    public void clickOnArticleList(){

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Article Lists')]")));
        WebElement articleLists = driver.findElement(By.xpath("//*[contains(text(),'Article Lists')]"));
        articleLists.click();
        System.out.println("Article List");

    }

    public void checkUnidataHomeLinks(){
        //TODO revisar los indices de los gets para hacerlo dinamico
        WebElement unicatLink = driver.findElement(By.xpath("/html/body/div[4]/div[7]/span/a/img"));
        unicatLink.click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        checkTitle("Unicat");
        checkTitle("Home");
        driver.close();
        System.out.println("tab closed");
        driver.switchTo().window(tabs.get(0));

        WebElement supportLink = driver.findElement(By.xpath("/html/body/div[4]/div[8]/a"));
        supportLink.click();
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(tabs.size()-1));
        checkTitle("Service Desk - Jira Service Management");
        driver.close();
        System.out.println("tab closed");
        driver.switchTo().window(tabs.get(tabs.size()-2));

        WebElement spincoLink = driver.findElement(By.xpath("/html/body/div[4]/div[10]/a/img"));
        spincoLink.click();
        ArrayList<String> tabs3 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs3.get(1));
        checkTitle("SPINCO – Coordination & Source of Product Information");
        driver.close();
        System.out.println("tab closed");
        driver.switchTo().window(tabs.get(0));

        System.out.println("Checked links on unidata Home");

    }

    public void checkTitle(String requiredTitle){
        assertTrue(driver.getTitle().contains(requiredTitle));
        System.out.println("Title :" + requiredTitle + ", checked");
    }

    public void iGoToCompositionLists(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'STD')]")));
        WebElement std = driver.findElement(By.xpath("//*[contains(text(),'STD')]"));
        std.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Composition Lists')]")));
        WebElement compositionLists = driver.findElement(By.xpath("//*[contains(text(),'Composition Lists')]"));
        compositionLists.click();

        System.out.println("Composition");

    }

    public void iSelectKitArticles(){
        driver.switchTo().frame("ebx-legacy-component");
        Function<WebDriver,WebElement> function;
        function = arg0 ->
        {
            WebElement checkbox1 = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[2]/td[1]/label/input"));
            checkbox1.click();
            return checkbox1;
        };
        wait.until(function);

        WebElement checkbox2 = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[4]/td[1]/label/input"));
        checkbox2.click();

        WebElement checkbox3 = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[1]/td[1]/label/input"));
        checkbox3.click();

        System.out.println("Kit articles selected");
    }

    public void iSelectKitArticlesAndextractData(){
        driver.switchTo().frame("ebx-legacy-component");
        Function<WebDriver,WebElement> function;
        function = arg0 ->
        {
            WebElement checkbox1 = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[2]/td[1]/label/input"));
            checkbox1.click();
            return checkbox1;
        };
        wait.until(function);
        WebElement article1 = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_mainScroller\"]/table/tbody/tr[4]/td[1]/table/tbody/tr/td[1]"));
        articleList.add(iExtractArticleFromWeb(article1.getText()));


        WebElement checkbox2 = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[4]/td[1]/label/input"));
        checkbox2.click();
        WebElement article2 = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_mainScroller\"]/table/tbody/tr[2]/td[1]/table/tbody/tr/td[1]"));
        articleList.add(iExtractArticleFromWeb(article2.getText()));

        WebElement checkbox3 = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[1]/td[1]/label/input"));
        checkbox3.click();
        WebElement article3 = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_mainScroller\"]/table/tbody/tr[1]/td[1]/table/tbody/tr/td[1]"));
        articleList.add(iExtractArticleFromWeb(article3.getText()));
        System.out.println("Kit articles selected");

        WebElement contains = driver.findElement(By.xpath("//*[contains(text(),'KADMKADM1EX')]"));
        System.out.println(contains.getText());
        System.out.println("Done");
    }

    public void iExportGrouped(){
        WebElement exportButton = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div[1]/div[1]/div[2]/button"));
        exportButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div/form/div[4]/div[1]/button[2]")));
        WebElement lauchExport = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div/form/div[4]/div[1]/button[2]"));
        lauchExport.click();

        System.out.println("Exported selected");
    }

    public void iDownloadExcel(){
        Function<WebDriver,WebElement> function;
        function = arg0 ->
        {
        WebElement downloadButton = driver.findElement(By.xpath("//*[@id=\"download\"]"));
        downloadButton.click();
            return downloadButton;
        };
        wait.until(function);
        long timetoWait = 5000;


        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Excel downloaded");
    }

    public String iExtractDataFromExcel(String rawString){

        String cleanString = rawString.replaceAll("\\s+","");
        return cleanString.replaceAll("-","");
    }

    public String iExtractArticleFromWeb(String rawString){

        return rawString.substring(0,rawString.indexOf('-')).replaceAll("\\s+","");
    }

    public void iCheckExcel() throws IOException {
        ArrayList<String> fromExcel = new ArrayList<>();

        String lastFile = String.valueOf(findLast("C:\\Users\\jmr\\Downloads"));

        FileInputStream fs = new FileInputStream(lastFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowItr = sheet.rowIterator();

        while ( rowItr.hasNext() ) {
            XSSFRow row = (XSSFRow) rowItr.next();
            XSSFCell cell;

            Iterator<Cell> cellItr = row.cellIterator();

            if(cellItr.hasNext()) {
                cell = (XSSFCell) cellItr.next();
                fromExcel.add(iExtractDataFromExcel(cell.toString()));
            }
        }

        boolean var  = fromExcel.containsAll(articleList);
        assertTrue(var);
        System.out.println("Excel checked");
    }

    public File findLast(String sdir) {
        File dir = new File(sdir);
        if (dir.isDirectory()) {
            File[] dirFiles = dir.listFiles((FileFilter) FileFilterUtils.fileFileFilter());
            if (dirFiles != null && dirFiles.length > 0) {
                Arrays.sort(dirFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                return dirFiles[0];
            }
        }
        return null;
    }

    private int getIntRandom(){

        return ThreadLocalRandom.current().nextInt(100, 1000 + 1);
    }

    public void selectElementOnList(String name){
        driver.switchTo().frame("ebx-legacy-component");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div[2]/div/div[1]/div/div/div/div[3]/table/tbody/tr[1]/td[2]/table/tbody/tr/td[2]/button")));
        String preExpr = "//div/table/tbody/tr/td[1][contains(text(),'";
        String expr = preExpr + name + " list')]";
        //String expr = "//*[contains(text(),'Escobar list 683')]";

        WebElement rowToDelete = driver.findElement(By.xpath(expr));
        //TODO obtener el texto para mostrarlo en el log
        String listToDelete = rowToDelete.getText();
        act.doubleClick(rowToDelete).perform();

    }

    public void clickOnActionsMenu(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Actions')]")));
        WebElement actionsButton = driver.findElement(By.xpath("//button[contains(text(),'Actions')]"));
        actionsButton.click();
    }

    public void deleteArticleList(String status){
        boolean hasToSucceed = Boolean.parseBoolean(status);

        if (hasToSucceed) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Delete')]")));
            WebElement deleteButton = driver.findElement(By.xpath("//*[contains(text(),'Delete')]"));
            deleteButton.click();

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'OK')]")));
            WebElement okButton = driver.findElement(By.xpath("//*[contains(text(),'OK')]"));
            okButton.click();
            System.out.println("List deleted");
        } else {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Delete')]")));
                fail("Error, Delete option available");
                } catch (Exception e) {
                    System.out.println("Delete option not present, test OK");
                }
        }
    }

    public void addParticipantOnList(String name, String participant){
        selectElementOnList(name);
        long timetoWait = 5000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String addParticpantButton = "//button[@title = 'Add an occurrence']";
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(addParticpantButton))));
        WebElement addParticipantButton = driver.findElement(By.xpath(addParticpantButton));
        addParticipantButton.click();

        boolean participantEmpty = false;
        int iterator = 0;

        do {
            WebElement inputParticipant = driver.findElement(By.xpath("//*[@id=\"___40_cfvAO__participants_5b_"+iterator+"_5d_\"]"));
            System.out.println(inputParticipant.getAttribute("value"));
            if(inputParticipant.getAttribute("value").contains("not defined")){
                inputParticipant.sendKeys(participant);

                long timetoWait2 = 3000;

                try {
                    Thread.sleep(timetoWait2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                WebElement includeIcon = driver.findElement(By.xpath("//*[@id=\"ebx_ISS_Item_0\"]"));
                includeIcon.click();
                participantEmpty = true;
            }
            if(iterator>=20){
                participantEmpty=true;
                fail("We reach 20 participant tries, something is wrong");
            }
            iterator++;
        } while (!participantEmpty);


        WebElement saveCloseButton = driver.findElement(By.xpath("//button[contains(text(),'Save and close')]"));
        saveCloseButton.click();
        System.out.println("Participant added");

    }

    public void addArticleOnExistingList(){
        selectElementOnList("Escobar");
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[contains(text(),'Contains (Articles)')]"))));
        WebElement containButton = driver.findElement(By.xpath("//*[contains(text(),'Contains (Articles)')]"));
        act.click(containButton).perform();

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Attach')]"))));
        WebElement attachButton = driver.findElement(By.xpath("//button[contains(text(),'Attach')]"));
        act.click(attachButton).perform();

        long timetoWait = 5000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.switchTo().frame("ebx_InternalPopup_frame");

        WebElement article1InputButton = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[16]/td[1]/label/input"));
        act.click(article1InputButton).perform();
        WebElement article2InputButton = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[20]/td[1]/label/input"));
        act.click(article2InputButton).perform();

        WebElement associateButton = driver.findElement(By.xpath("//button[contains(text(),'Associate')]"));
        act.click(associateButton).perform();

        WebElement okButton = driver.findElement(By.xpath("//button[contains(text(),'OK')]"));
        act.click(okButton).perform();

        WebElement saveCloseButton = driver.findElement(By.xpath("//button[contains(text(),'Save and close')]"));
        saveCloseButton.click();

        driver.switchTo().defaultContent();
        System.out.println("Added Article");
    }

    public void addArticleOnExistingListAsParticipant(){
        //wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@id=\"FILTER_0_applyButton\"]"))));
        long timetoWait = 2000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement element = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_mainScroller\"]/table/tbody/tr/td[1]"));
        act.doubleClick(element).perform();

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[contains(text(),'Contains (Articles)')]"))));
        WebElement containButton = driver.findElement(By.xpath("//*[contains(text(),'Contains (Articles)')]"));
        act.click(containButton).perform();

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Attach')]"))));
        WebElement attachButton = driver.findElement(By.xpath("//button[contains(text(),'Attach')]"));
        act.click(attachButton).perform();

        long timetoWait2 = 3000;

        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.switchTo().frame("ebx_InternalPopup_frame");
        WebElement article1InputButton = driver.findElement(By.xpath("(//input[@type='checkbox'])[19]"));
        act.click(article1InputButton).perform();
        WebElement article2InputButton = driver.findElement(By.xpath("(//input[@type='checkbox'])[18]"));
        act.click(article2InputButton).perform();

        WebElement associateButton = driver.findElement(By.xpath("//button[contains(text(),'Associate')]"));
        act.click(associateButton).perform();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'OK')]")));
        WebElement okButton = driver.findElement(By.xpath("//button[contains(text(),'OK')]"));
        act.click(okButton).perform();

        driver.switchTo().defaultContent();
        System.out.println("Add article");

    }

    public void detachElementsFromList() {
        selectElementOnList("Escobar");

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[contains(text(),'Contains (Articles)')]"))));
        WebElement containButton = driver.findElement(By.xpath("//*[contains(text(),'Contains (Articles)')]"));
        act.click(containButton).perform();

        long timetoWait = 5000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement article1InputButton = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div[2]/form/div[2]/div[1]/div/div[2]/div[2]/table/tbody/tr[1]/td[3]/div/div/div[2]/div[3]/div/table/tbody/tr[1]/td[1]/label/input"));
        act.click(article1InputButton).perform();

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Detach')]"))));
        WebElement detachButton = driver.findElement(By.xpath("//button[contains(text(),'Detach')]"));
        act.click(detachButton).perform();

        WebElement okButton = driver.findElement(By.xpath("//button[contains(text(),'OK')]"));
        act.click(okButton).perform();

        WebElement saveCloseButton = driver.findElement(By.xpath("//button[contains(text(),'Save and close')]"));
        saveCloseButton.click();

        System.out.println("Detached Article from list");

    }

    public void detachElementsFromListAsParticipant() {

        long timetoWait = 5000;
        long timetoWait2 = 3000;

        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.switchTo().frame("ebx-legacy-component");
        //wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//li[2]/a/em/span/span/b"))));
        WebElement containButton = driver.findElement(By.xpath("//li[2]/a/em/span/span/b"));
        act.click(containButton).perform();

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement article1InputButton = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div[2]/form/div[2]/div[1]/div/div[2]/div[2]/table/tbody/tr[1]/td[3]/div/div/div[2]/div[3]/div/table/tbody/tr[1]/td[1]/label/input"));
        act.click(article1InputButton).perform();

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Detach')]"))));
        WebElement detachButton = driver.findElement(By.xpath("//button[contains(text(),'Detach')]"));
        act.click(detachButton).perform();

        WebElement okButton = driver.findElement(By.xpath("//button[contains(text(),'OK')]"));
        act.click(okButton).perform();

        WebElement saveCloseButton = driver.findElement(By.xpath("//button[contains(text(),'Close')]"));
        saveCloseButton.click();

        System.out.println("Detached Article from list");

    }

    public void createFeedbackToAssignee(String assignee){

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Articles (All)')]")));
        String pathArticlesAll = "//*[contains(text(),'Articles (All)')]";
        WebElement articlesAllMenu = driver.findElement(By.xpath(pathArticlesAll));
        articlesAllMenu.click();

        driver.switchTo().frame("ebx-legacy-component");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/div/div/div[2]/div[2]/div/div[1]/div/div/div/div[3]/table/tbody/tr[14]/td[1]/table/tbody/tr/td[2]/button")));
        String pathArticleToSelect = "//div/table/tbody/tr/td[contains(text(),'ADAPADAP2I')]";
        WebElement articleToSelect = driver.findElement(By.xpath(pathArticleToSelect));
        act.doubleClick(articleToSelect).perform();

        //wait.until(ExpectedConditions.)
        WebElement feedbackTabButton = driver.findElement(By.xpath("//*[@id=\"ebx_WorkspaceFormTabviewTabs\"]/li[8]/a"));
        act.doubleClick(feedbackTabButton).perform();

        WebElement addFeedbackButton = driver.findElement(By.xpath("//button[@title='Create']"));
        addFeedbackButton.click();
        //act.click(addFeedbackButton).perform();

        System.out.println("Feedback Menu");

    }

    public void selectBiomedArticlesView(){

        driver.switchTo().frame("ebx-legacy-component");
        WebElement viewButton = driver.findElement(By.xpath("//button[contains(text(),'View')]"));
        act.click(viewButton).perform();

        WebElement biomedOption = driver.findElement(By.xpath("//a[contains(text(),'Biomed Articles')]"));
        act.click(biomedOption).perform();

        WebElement firstElement = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[1]/td[1]/label/input"));
        act.click(firstElement).perform();
        WebElement secondElement = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[1]/td[1]/label/input"));
        act.click(secondElement).perform();
        WebElement thirdElement = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[1]/td[1]/label/input"));
        act.click(thirdElement).perform();

        WebElement servicesButton = driver.findElement(By.xpath("//button[contains(text(),'Services')]"));
        act.click(servicesButton).perform();

        WebElement exportExcelOption = driver.findElement(By.xpath("//*[contains(text(),'Export to Excel')]"));
        act.click(exportExcelOption).perform();

        WebElement launchExport = driver.findElement(By.xpath("//*[contains(text(),'Launch export')]"));
        act.click(launchExport).perform();

        System.out.println("View BioMed");
    }

    public void filterAsParticipant(){
        driver.switchTo().frame("ebx-legacy-component");

        long timetoWait2 = 2000;
        driver.findElement(By.xpath("//button[@id='ebx_filtersButton']/span")).click();
        driver.findElement(By.id("ebx_SimpleSearchFilterNodeSelectorList_FILTER_0")).click();
        {
            WebElement dropdown = driver.findElement(By.id("ebx_SimpleSearchFilterNodeSelectorList_FILTER_0"));
            dropdown.findElement(By.xpath("//option[. = 'Participants']")).click();
        }
        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//div[2]/input[2]")).sendKeys("Mr Escobar Datamaster");
        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//div[2]/input[2]")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("FILTER_0_applyButton")).click();

        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Filter as participant added");
    }

    public void changeListNameFail(){
        long timetoWait2 = 2000;
        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement element = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_mainScroller\"]/table/tbody/tr/td[1]"));
        act.doubleClick(element).perform();
        driver.findElement(By.xpath("//*[@id=\"ebx_TabTitle____homeGroup\"]/a")).click();
        boolean found = false;
        try {
            driver.findElement(By.xpath("//*[@id=\"___40_cfvAO__listName\"]"));
        } catch (Exception e) {
            found = true;
            System.out.println("Unable to change name due lack of rights. Correct");
        }

        assertTrue(found);
    }




}
