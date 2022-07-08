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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class Unidata {

    Wait<WebDriver> wait;
    WebDriver driver;
    Actions act;
    String downloadDirectory;
    ArrayList<String> articleList = new ArrayList<>();
    Article article = new Article();
    final AuxOperations operations;
    int common2Random;

    public Unidata(Wait<WebDriver> wait, WebDriver driver, Actions act, AuxOperations operations, String downloadDirectory) {
        this.wait = wait;
        this.driver = driver;
        this.act = act;
        this.operations = operations;
        this.downloadDirectory = downloadDirectory;
    }

    public void iGoTo(String url){
        driver.get(url);
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

    public void unidataMSFME(){

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'UniData, MSF & Me')]")));
        WebElement buttonMe = driver.findElement(By.xpath("//*[contains(text(),'UniData, MSF & Me')]"));
        act.click(buttonMe).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'My UniData')]")));
        WebElement buttonMyUnidata = driver.findElement(By.xpath("//*[contains(text(),'My UniData')]"));
        act.click(buttonMyUnidata).perform();

        driver.switchTo().frame("ebx-legacy-component");
        long timetoWait = 2000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().frame("myUnidataFrame");
        driver.switchTo().frame("ebx-legacy-component");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'E-Mail')]")));
        WebElement email = driver.findElement(By.xpath("//*[contains(text(),'E-Mail')]"));

        System.out.println("My Unidata");
    }

    public void updateEmail(String emailComtent){
        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"___40_cfvAO__email\"]"));
        emailField.clear();
        emailField.sendKeys(emailComtent);
        WebElement saveButton = driver.findElement(By.xpath("//button[contains(text(),'Save')]"));
        saveButton.click();
    }

    public void checkEmail(String emailComtent){

        WebElement emailField = driver.findElement(By.xpath("//*[@id=\"___40_cfvAO__email\"]"));
        Assert.assertTrue(emailField.getAttribute("value").contains( emailComtent));
        System.out.println("Email field content: "+ emailField.getAttribute("value")+ " ,Checked");
    }

    public void goToArticles(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Articles')]")));
        String pathArticles = "//*[contains(text(),'Articles')]";
        WebElement articles = driver.findElement(By.xpath(pathArticles));

        if(articles.getAttribute("title").equals("Collapsed")){
            articles.click();
        }

        System.out.println("Articles");
    }

    public void goToFeedback(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Feedback')]")));
        String pathFeedback = "//*[@id=\"_ebx-root\"]/div/div/div[2]/div/div[1]/div/div[2]/div/div[1]/div/div/div/ul/li[9]/div[2]/div/button";
        WebElement feedbackMenu = driver.findElement(By.xpath("//*[contains(text(),'Feedback')]"));
        act.click(feedbackMenu).perform();

        System.out.println("Feedback Menu");
    }

    public void creteListFromExcel(String name){

        long timetoWait = 2000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String dir = System.getProperty("user.dir");
        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Create List with')]")));
        WebElement createList = driver.findElement(By.xpath("//button[contains(text(),'Create List with')]"));

        createList.click();
        //createList.click();
        //act.click(createList).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        WebElement chooseFile = driver.findElement(By.cssSelector("input[type='file']"));

        chooseFile.sendKeys(dir + "/target/test-classes/Article-Belongs-to-List.xlsx");


        WebElement listName = driver.findElement(By.xpath("//html/body/div[1]/div/div/div/div/div[2]/div/form/div[3]/table/tbody/tr[1]/td[3]/div/input"));
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
        WebElement closeButton = driver.findElement(By.xpath("//button[contains(text(),'Close')]"));
        closeButton.click();
        System.out.println("Close article");

    }

    public void clickOnArticleList(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Article Lists')]")));

        WebElement articleLists = driver.findElement(By.xpath("//*[contains(text(),'Article Lists')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", articleLists);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Article Lists')]")));

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

        WebElement supportLink = driver.findElement(By.xpath("//html/body/div[4]/div[8]/a"));
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

    public void checkUnicatPage(Article article){
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        checkTitle(article.getCode());
    }

    public void checkTitle(String requiredTitle){
        assertTrue(driver.getTitle().contains(requiredTitle));
        System.out.println("Title :" + requiredTitle + ", checked");
    }

    public void iGoToKitCompositions(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'STD')]")));
        WebElement std = driver.findElement(By.xpath("//*[contains(text(),'STD')]"));
        act.click(std).perform();
    }

    public void iClickOnTreeView(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Tree View')]")));
        WebElement std = driver.findElement(By.xpath("//*[contains(text(),'Tree View')]"));
        //std.click();
        act.click(std).perform();
    }

    public void iGoToCompositionLists(){

        long timetoWait = 2000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Composition Lists')]")));
        WebElement compositionLists = driver.findElement(By.xpath("//*[contains(text(),'Composition Lists')]"));
        //compositionLists.click();
        act.click(compositionLists).perform();

        System.out.println("Composition");

    }

    public void iClickOnKitArticle(){
        driver.switchTo().frame("ebx-legacy-component");
        Function<WebDriver,WebElement> function;
        function = arg0 ->
        {
            WebElement checkbox1 = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[4]/td[1]/label/input"));
            checkbox1.click();
            return checkbox1;
        };
        wait.until(function);

        WebElement article = driver.findElement(By.xpath("//*[contains(text(),'KADMKLIF08')]"));
        //compositionLists.click();
        //act.click(article);
        act.doubleClick(article).perform();
        System.out.println("Click on Kit Article");
    }

    public void iClickOnKitArticleTabs(){

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//html/body/div[1]/div/div/div/div/div[2]/div[2]/form/div[2]/div[1]/div/div[1]/ul/li[4]/a")));
        WebElement containsModulesButton = driver.findElement(By.xpath("//*[contains(text(),'Contains (Modules)')]"));
        containsModulesButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Close')]")));

        iReviewArticleModules();

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
        //TODO to review
        WebElement contains = driver.findElement(By.xpath("//*[contains(text(),'KADMKADM1EX')]"));
        System.out.println(contains.getText());
        System.out.println("Kits selected");
    }

    public void iExportGrouped(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Export')]")));
        WebElement exportButton = driver.findElement(By.xpath("//button[contains(text(),'Export')]"));
        exportButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Launch export')]")));
        WebElement lauchExport = driver.findElement(By.xpath("//button[contains(text(),'Launch export')]"));
        lauchExport.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"download\"]")));
        System.out.println("Exported selected");
    }

    public void iDownloadExcel(){
        //Todo Remember to place a destiny
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

    public void iClickOnTreeViewBranch(){
        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'KADMKLIF08')]")));
        WebElement KADMKLIF08 = driver.findElement(By.xpath("//*[contains(text(),'KADMKLIF08')]"));

        long timetoWait = 2000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement clickStarButton = driver.findElement(with(By.className("ygtvspacer")).toLeftOf(KADMKLIF08));
        clickStarButton.click();


        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement clickStarButtonBelow = driver.findElement(with(By.className("ygtvspacer")).toLeftOf(By.xpath("//span/a/span[3][contains(text(),'KADMKLIF08')]")));
        clickStarButtonBelow.click();

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        article.setModulesKADMKLIF08();
        iReviewArticleModules();

        KADMKLIF08.click();
        System.out.println("Branch");
        driver.switchTo().parentFrame();
        }

    public void iReviewArticleModules(){

        for (String module : article.getModules()) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'"+module+"')]")));
            WebElement moduleToCheck = driver.findElement(By.xpath("//*[contains(text(),'"+module+"')]"));
            System.out.println("module to check: "+ moduleToCheck.getText() + " , module: " + module);
            assertTrue(moduleToCheck.getText().contains(module));
        }

        System.out.println("Modules checked");
    }

    public void iCollectArticleData(){

        driver.switchTo().frame("ebx-legacy-component");
        driver.switchTo().frame("ebx_SubSessioniFrame");

        long timetoWait = 2000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement status = driver.findElement(By.xpath("//div[2]/div[1]/table/tbody/tr[12]/td[3]/div/div"));
        article.setCode(status.getText().replaceAll("[^\\d]", ""));
        System.out.println("Collected");

        WebElement buttonClose = driver.findElement(By.xpath("//button[contains(text(),'Close')]"));
        //buttonClose.click();
        act.click(buttonClose).perform();
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("Alert not shown");
        }
        driver.switchTo().parentFrame();
        driver.switchTo().parentFrame();
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

        String lastFile = String.valueOf(findLast(downloadDirectory));

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

        workbook.close();

        boolean var  = fromExcel.containsAll(articleList);
        assertTrue(var);
        System.out.println("Excel checked successfully");
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

        return ThreadLocalRandom.current().nextInt(100, 10000+ 1);
    }

    private int getInt2Random(){

        return ThreadLocalRandom.current().nextInt(5, 99+ 1);
    }

    public void selectElementOnList(String name){
        driver.switchTo().frame("ebx-legacy-component");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr[1]/td[2]/table/tbody/tr/td[2]/button")));
        String preExpr = "//div/table/tbody/tr/td[1][contains(text(),'";

        String toSearch = "//*[contains(text(),'"+ name + "')]";

        WebElement rowToDelete = driver.findElement(By.xpath(toSearch));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", rowToDelete);
        rowToDelete.click();
        act.doubleClick(rowToDelete).perform();

        long timetoWait2 = 5000;

        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



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

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Active')]")));
        String addParticpantButton = "//button[@title = 'Add an occurrence']";

        long timetoWait2 = 2000;

        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement addParticipantButton = driver.findElement(By.xpath(addParticpantButton));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", addParticipantButton);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(addParticpantButton))));
        addParticipantButton.click();

        findLastspotForParticipant(participant);


        WebElement saveCloseButton = driver.findElement(By.xpath("//button[contains(text(),'Save and close')]"));
        saveCloseButton.click();
        System.out.println("Participant added");

    }

    public void findLastspotForParticipant(String participant){
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
    }

    public void addArticleOnExistingList(){
        selectElementOnList(operations.getName());
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[contains(text(),'Contains (Articles)')]"))));
        WebElement containButton = driver.findElement(By.xpath("//*[contains(text(),'Contains (Articles)')]"));
        act.click(containButton).perform();

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'Attach')]"))));
        WebElement attachButton = driver.findElement(By.xpath("//button[contains(text(),'Attach')]"));
        act.click(attachButton).perform();

        long timetoWait = 6000;

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

        driver.switchTo().parentFrame();
        //driver.switchTo().frame("ebx-legacy-component");
        WebElement saveCloseButton = driver.findElement(By.xpath("//button[contains(text(),'Save and close')]"));
        //saveCloseButton.click();
        act.click(saveCloseButton).perform();

        driver.switchTo().defaultContent();
        System.out.println("Added Article");
    }

    public void addArticleOnExistingListAsParticipant(){
        long timetoWait = 3000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"ebx_workspaceTable_mainScroller\"]/table/tbody/tr/td[1]")));
        WebElement element = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_mainScroller\"]/table/tbody/tr/td[1]"));
        act.doubleClick(element).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Contains (Articles)')]")));
        WebElement containButton = driver.findElement(By.xpath("//*[contains(text(),'Contains (Articles)')]"));
        act.click(containButton).perform();

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Attach')]")));
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
        selectElementOnList(operations.getName());

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

    public void iGotoArticlesAll(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Articles (All)')]")));
        String pathArticlesAll = "//*[contains(text(),'Articles (All)')]";
        WebElement articlesAllMenu = driver.findElement(By.xpath(pathArticlesAll));
        articlesAllMenu.click();
    }

    public void createFeedback(){

        driver.switchTo().frame("ebx-legacy-component");

        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//html/body/div[1]/div/div/div/div/div[2]/div[2]/div/div[1]/div/div/div/div[3]/table/tbody/tr[14]/td[1]/table/tbody/tr/td[2]/button")));
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//html/body/div[1]/div/div/div/div/div[2]/div[2]/div/div[1]/div/div/div/div[3]/table/tbody/tr[14]/td[1]/table/tbody/tr/td[2]/button")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/table/tbody/tr/td[contains(text(),'ADAPADAP2I')]")));

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String pathArticleToSelect = "//div/table/tbody/tr/td[contains(text(),'ADAPADAP2I')]";
        WebElement articleToSelect = driver.findElement(By.xpath(pathArticleToSelect));
        act.doubleClick(articleToSelect).perform();

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebElement feedbackTabButton = driver.findElement(By.xpath("//*[@id=\"ebx_WorkspaceFormTabviewTabs\"]/li[8]/a"));
        WebElement feedbackTabButton = driver.findElement(By.xpath("//*[contains(text(),'Feedback')]"));

        act.doubleClick(feedbackTabButton).perform();

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//div[9]/table/tbody/tr[1]/td[3]/div/div/div[1]/div/div[1]/div[1]/button"))));
        //WebElement addFeedbackButton = driver.findElement(By.xpath("//div[9]/table/tbody/tr[1]/td[3]/div/div/div[1]/div/div[1]/div[1]/button"));


        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@title ='Create']")));
        WebElement addFeedbackButton = driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]/div/div/div[1]/div/div[1]/div[1]/button[@title ='Create']"));

        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].scrollIntoView();", addFeedbackButton);
        //act.moveToElement(addFeedbackButton).build().perform();
        act.click(addFeedbackButton).perform();
        //addFeedbackButton.click();

        driver.switchTo().frame("ebx_InternalPopup_frame");

        System.out.println("Feedback Menu");

    }

    public void selectBiomedArticlesView(){

        driver.switchTo().frame("ebx-legacy-component");

        WebElement viewButton = driver.findElement(By.xpath("//button[contains(text(),'View')]"));
        act.click(viewButton).perform();

        WebElement biomedOption = driver.findElement(By.xpath("//a[contains(text(),'Biomed Articles')]"));
        act.click(biomedOption).perform();

        System.out.println("View BioMed");
    }

    public void exportArticlesBiomedExcel(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[3]/table/tbody/tr[1]/td[1]/table/tbody/tr/td[2]/button")));

        WebElement firstElement = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[1]/td[1]/label/input"));
        act.click(firstElement).perform();
        WebElement secondElement = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[2]/td[1]/label/input"));
        act.click(secondElement).perform();
        WebElement thirdElement = driver.findElement(By.xpath("//*[@id=\"ebx_workspaceTable_fixedScroller\"]/table/tbody/tr[4]/td[1]/label/input"));
        act.click(thirdElement).perform();

        WebElement servicesButton = driver.findElement(By.xpath("//button[contains(text(),'Services')]"));
        act.click(servicesButton).perform();

        WebElement exportExcelOption = driver.findElement(By.xpath("//*[contains(text(),'Export to Excel')]"));
        act.click(exportExcelOption).perform();

        WebElement launchExport = driver.findElement(By.xpath("//*[contains(text(),'Launch export')]"));
        act.click(launchExport).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Click here to download the excel')]")));
        WebElement downloadExcel = driver.findElement(By.xpath("//*[contains(text(),'Click here to download the excel')]"));
        act.click(downloadExcel).perform();

        long timetoWait=7000;
        try {

            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement buttonClose = driver.findElement(By.xpath("//*[contains(text(),'Close')]"));
        act.click(buttonClose).perform();

        System.out.println("Export excel Biomed");
    }

    public void checkResultsExcelAlphaOrder(){
        boolean isEqual = false;
        ArrayList<String> fromExcel = new ArrayList<>();
        List<String> sortedList;

        String lastFile = String.valueOf(findLast(downloadDirectory));

        try {
            FileInputStream fs = new FileInputStream(lastFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowItr = sheet.rowIterator();
            int count = 1;

            while ( rowItr.hasNext() ) {

                XSSFRow row = (XSSFRow) rowItr.next();
                if (count >1) {
                    XSSFCell cell;

                    Iterator<Cell> cellItr = row.cellIterator();

                    if(cellItr.hasNext()) {
                        cell = (XSSFCell) cellItr.next();
                        fromExcel.add(iExtractDataFromExcel(cell.toString()));
                    }
                }
                count ++;
            }

            sortedList = fromExcel.stream().sorted().collect(Collectors.toList());

            isEqual = fromExcel.equals(sortedList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(isEqual);
        assertTrue(isEqual);
        System.out.println("Excel checked");
    }

    public void filterAsParticipant(){
        driver.switchTo().frame("ebx-legacy-component");

        long timetoWait2 = 3000;
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
        driver.findElement(By.xpath("//div[2]/input[2]")).sendKeys(operations.getparticipantMe());
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

    public void addAddressee(){

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[4]/tbody/tr/td/div/div[2]/div[1]/table/tbody/tr[1]/td[3]/div/div/div/div/div/div/button")));
        WebElement addAddressee = driver.findElement(By.xpath("//table[4]/tbody/tr/td/div/div[2]/div[1]/table/tbody/tr[1]/td[3]/div/div/div/div/div/div/button"));
        WebElement anchorToMove = driver.findElement(By.xpath("//*[contains(text(),'Addressed to UniData User')]"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", addAddressee);

        addAddressee.click();

        try {
            Thread.sleep(5500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[1]/td[3]/div/div/div/div/div/ol/li[1]/div/div/div/div/input[2]")));
        driver.findElement(By.xpath("//tr[1]/td[3]/div/div/div/div/div/ol/li[1]/div/div/div/div/input[2]")).sendKeys(operations.getAddressee());
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//tr[1]/td[3]/div/div/div/div/div/ol/li[1]/div/div/div/div/input[2]")).sendKeys(Keys.ENTER);
        WebElement toHook = driver.findElement(By.xpath("//*[contains(text(),'Addressed to MSF Entity')]"));
        toHook.click();

        System.out.println("Added addressee");
    }

    public void addPriorityToMessage(){
        long timetoWait2 = 1000;
        //WebElement clearPriority = driver.findElement(By.xpath("//table[5]/tbody/tr/td/div/div[2]/div[1]/table/tbody/tr[1]/td[3]/div/div/div/div/div/button[1]"));
        //act.click(clearPriority).perform();
        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement priorityMenu = driver.findElement(By.xpath("//table[5]/tbody/tr/td/div/div[2]/div[1]/table/tbody/tr[1]/td[3]/div/div/div/div/div/input[2]"));

        try {
            WebElement findPriority = driver.findElement(By.xpath("//*[contains(text(),'!! High Priority')]"));
            priorityMenu.sendKeys("! Normal Priority");

        } catch (Exception e) {
            priorityMenu.sendKeys("!! High Priority");
        }


        act.click(priorityMenu).sendKeys(Keys.ENTER).perform();

        driver.findElement(By.xpath("//tr[1]/td[3]/div/div/div/div/div/ol/li[1]/div/div/div/div/input[2]")).sendKeys(Keys.ENTER);

        System.out.println("Select Priority");
    }

    public void goToFeedBackAddressedToMe(){
        goToFeedback();

        long timetoWait = 1000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Feedback (Addressed to Me)')]")));
        WebElement feedBackMeMenu = driver.findElement(By.xpath("//*[contains(text(),'Feedback (Addressed to Me)')]"));
        act.click(feedBackMeMenu).perform();

        long timetoWait2 = 2000;

        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.switchTo().frame("ebx-legacy-component");
        driver.switchTo().frame("feedbackFrame");
        driver.switchTo().frame("ebx-legacy-component");

        WebElement feedbackTextHook = driver.findElement(By.xpath("//*[contains(text(),'Feedback (Addressed to Me)')]"));

    }

    public void addNewMessageFeedback(){
        WebElement priorityHook = driver.findElement(By.xpath("//*[contains(text(),'Priority')]"));
        WebElement messagesHook = driver.findElement(By.xpath("//*[contains(text(),'Messages')]"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[5]/tbody/tr/td/div/div[2]/div[1]/table/tbody/tr[2]/td[3]/div/div/div/div/div/div/button")));
        WebElement addNewMessage = driver.findElement(By.xpath("//table[5]/tbody/tr/td/div/div[2]/div[1]/table/tbody/tr[2]/td[3]/div/div/div/div/div/div/button"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", addNewMessage);
        addNewMessage.click();
        WebElement newMessageTextArea = findNewTextAreaMessageFeedback();

        newMessageTextArea.sendKeys("Text answer");

        System.out.println("Feedback message");

    }

    private WebElement findNewTextAreaMessageFeedback(){
        WebElement newMessageTextArea;
        int iteration=1;
        boolean newTextArea;
        String composedXpath;

        do{
            composedXpath = "//tr[2]/td[3]/div/div/div/div/div/ol/li["+iteration+"]/div/div/table/tbody/tr[4]/td[3]/div/div/div/textarea";
            try {
                newMessageTextArea = driver.findElement(By.xpath(composedXpath));
                newTextArea = false;
            } catch (Exception e) {
                System.out.println("Text area used");
                newMessageTextArea = driver.findElement(By.xpath("//tr[2]/td[3]/div/div/div/div/div/ol/li["+(iteration-1)+"]/div/div/table/tbody/tr[4]/td[3]/div/div/div/textarea"));
                newTextArea = true;
            }
            iteration++;
        }while(newTextArea);

        return newMessageTextArea;
    }

    public void saveAndClose(){
        WebElement saveCloseButton = driver.findElement(By.xpath("//button[contains(text(),'Save and close')]"));
        saveCloseButton.click();
    }

    public void clickSpecificArticle(){
        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/table/tbody/tr/td[contains(text(),'ADAPADAP2I')]")));
        WebElement elementADAPADAP2I = driver.findElement(By.xpath("//*[contains(text(),'ADAPADAP2I')]"));
        act.click(elementADAPADAP2I).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Standardization')]")));
    }

    public void clickShareLink(){
        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/table/tbody/tr/td[contains(text(),'ADAPADAP2I')]")));

        WebElement elementADAPADAP2I = driver.findElement(By.xpath("//*[contains(text(),'ADAPADAP2I')]"));
        WebElement shareLinkButton = driver.findElement(with(By.xpath("(//button[@type='button'])")).toLeftOf(elementADAPADAP2I));

        shareLinkButton.click();

    }

    public void validateOpenTabUnicat(){
        long timetoWait2 = 2000;

        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        checkTitle("Unicat");
        checkTitle("ADAPTER USB");
        driver.findElements(By.xpath("//*[contains(text(),'ADAPADAP2I')]"));
        driver.close();
        System.out.println("tab closed");
        driver.switchTo().window(tabs.get(0));
        System.out.println("Link content validated");
    }

    public void iSelectFeedbacckAddressedToMe(){

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Open')]")));

        WebElement feedbackElement = driver.findElement(By.xpath("//*[contains(text(),'Open')]"));
        act.doubleClick(feedbackElement).perform();

        long timetoWait2 = 2000;

        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Feedback selected");

    }

    public void clickOnCreateArticle(){
        long timetoWait = 2000;
        try {

            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[2]/div[1]/div[1]/div[1]/button")));
        WebElement addArticleButton = driver.findElement(By.xpath("//div[2]/div[1]/div[1]/div[1]/button"));
        act.click(addArticleButton).perform();
    }

    public void addGroupToArticle(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[3]/td[3]/div/div/div/div/div/button[2]")));
        WebElement groupDropDown = driver.findElement(By.xpath("//tr[3]/td[3]/div/div/div/div/div/button[2]"));
        act.click(groupDropDown).perform();
        WebElement groupInput = driver.findElement(By.xpath("//tr[3]/td[3]/div/div/div/div/div/input[2]"));
        groupInput.sendKeys("N - Nutrition");
        long timetoWait = 2000;
        try {

            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        act.sendKeys(Keys.ENTER);

        WebElement anchor = driver.findElement(By.xpath("//*[contains(text(),'Group')]"));
        act.click(anchor).perform();
        System.out.println("Group");

    }

    public void addFamilyArticle(){

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[4]/td[3]/div/div/div/div/div/input[2]")));
        WebElement groupDropDown = driver.findElement(By.xpath("//tr[4]/td[3]/div/div/div/div/div/button[2]"));
        act.click(groupDropDown).perform();
        WebElement groupInput = driver.findElement(By.xpath("//tr[4]/td[3]/div/div/div/div/div/input[2]"));
        groupInput.sendKeys("NFOO - Food commodities");
        long timetoWait = 2000;
        try {

            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        act.sendKeys(Keys.ENTER);

        WebElement anchor = driver.findElement(By.xpath("//*[contains(text(),'Family')]"));
        act.click(anchor).perform();
        System.out.println("Family");
    }

    public void addSpecificationArticle(){
        WebElement specificationInput = driver.findElement(By.xpath("//div[1]/table/tbody/tr[6]/td[3]/div/div/div/div/div/input"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", specificationInput);

        specificationInput.sendKeys("TRY" + getInt2Random());
    }

    public void goToDescriptions() {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Descriptions')]")));
        WebElement descriptionMenu = driver.findElement(By.xpath("//*[contains(text(),'Descriptions')]"));
        act.click(descriptionMenu).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Descriptions (All)')]")));
        System.out.println("Descriptions");

    }

    public void clickAddDescription(){
        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title = 'Create']")));
        WebElement addButton = driver.findElement(By.xpath("//button[@title = 'Create']"));
        addButton.click();
    }

    public void changeTypeFFF(){
        long timetoWait = 2000;
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Publication Definition')]")));
        WebElement typeInput = driver.findElement(By.xpath("//div[1]/table/tbody/tr[5]/td[3]/div/div/div/div/div/input[2]"));
        typeInput.sendKeys("FFF");
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        typeInput.sendKeys(Keys.ENTER);
        System.out.println("Type");
    }

    public void changeFamilyCWAT(){
        long timetoWait = 2000;
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Publication Definition')]")));
        WebElement typeInput = driver.findElement(By.xpath("//table/tbody/tr[6]/td[3]/div/div/div/div/div/div/input[2]"));
        typeInput.sendKeys("CWAT - Water and sanitation");
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        typeInput.sendKeys(Keys.ENTER);
        System.out.println("Family");
    }

    public void upluadDefinitionImage(){
        long timetoWait = 3500;
        long timetoWait2 = 1500;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Unvalidated Changes')]")));

        WebElement hook = driver.findElement(By.xpath("//*[contains(text(),'Unvalidated Changes')]"));
        js.executeScript("arguments[0].scrollIntoView();", hook);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title = 'Upload new digital asset(s)']")));
        WebElement uploadButton = driver.findElement(By.xpath("//button[@title = 'Upload new digital asset(s)']"));
        //uploadButton.click();
        act.click(uploadButton).perform();
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.switchTo().frame("ebx_InternalPopup_frame");
        driver.switchTo().frame("serviceIframe");
        String userDir = System.getProperty("user.dir");
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(userDir + "/src/test/resources/LEVO.jpg");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title = 'Attach and upload']")));
        WebElement attachUploadButton = driver.findElement(By.xpath("//button[@title = 'Attach and upload']"));
        //attachUploadButton.click();
        act.click(attachUploadButton).perform();
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Image uploaded");
        driver.switchTo().parentFrame();
        driver.switchTo().parentFrame();
    }

    public void clickOnDescriptionElement() {
        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'FFF')]")));
        WebElement record = driver.findElement(By.xpath("//*[contains(text(),'FFF')]"));
        act.doubleClick(record).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'URL to View Only')]")));

        System.out.println("Record");

    }

    public void closeRecord() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Close')]")));
        WebElement buttonClose = driver.findElement(By.xpath("//button[contains(text(),'Close')]"));
        act.click(buttonClose).perform();

        System.out.println("Close record");

    }

    public void validateIamOnDescriptions() {
        driver.switchTo().parentFrame();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Descriptions (All)')]")));
        System.out.println("Descriptions All");
    }

    public void launchAPICall(APICalls call){


        try {
            call.httpGetRequest();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void iSelectSpecificSTDArticleToDuplicate(){

        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Actions')]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[14]/td[1]/table/tbody/tr/td[2]/button")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'ADAPADAPDV-')]")));
        WebElement ADAPADAPDV = driver.findElement(By.xpath("//*[contains(text(),'ADAPCABL1P-')]"));
        //System.out.println(ADAPADAPDV.getText());
        String content = ADAPADAPDV.getAttribute("innerHTML");
        //System.out.println(content);
        WebElement checkbox = driver.findElement(with(By.className("ebx_checkboxCustom")).toLeftOf(ADAPADAPDV));
        //WebElement checkbox = driver.findElement(By.xpath("//tr[17]/td[1]/label/input"));
        act.click(checkbox).perform();
        WebElement actionsButton = driver.findElement(By.xpath("//*[contains(text(),'Actions')]"));
        actionsButton.click();
        WebElement duplicateAction = driver.findElement(By.xpath("//*[contains(text(),'Duplicate this record')]"));
        duplicateAction.click();

        System.out.println("Actions duplicate");


    }

    public void iSelectSpecificNSTArticleToDuplicate(){

        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Actions')]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[14]/td[1]/table/tbody/tr/td[2]/button")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'ADAPADAPDV-')]")));
        WebElement ADAPADAPDV = driver.findElement(By.xpath("//*[contains(text(),'ADAPCABL1P-')]"));
        //System.out.println(ADAPADAPDV.getText());
        String content = ADAPADAPDV.getAttribute("innerHTML");
        //System.out.println(content);
        WebElement checkbox = driver.findElement(with(By.className("ebx_checkboxCustom")).toLeftOf(ADAPADAPDV));
        //WebElement checkbox = driver.findElement(By.xpath("//tr[17]/td[1]/label/input"));
        act.click(checkbox).perform();
        WebElement actionsButton = driver.findElement(By.xpath("//*[contains(text(),'Actions')]"));
        actionsButton.click();
        WebElement duplicateAction = driver.findElement(By.xpath("//*[contains(text(),'Duplicate this record')]"));
        duplicateAction.click();

        System.out.println("Actions duplicate");


    }

    public void iUpdateSpecification(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'MSF Id')]")));
        WebElement specificationInput = driver.findElement(By.xpath("//div[1]/table/tbody/tr[6]/td[3]/div/div/div/div/div/input"));
        specificationInput.sendKeys(changeSpecification());
        System.out.println("Specification changed");
    }

    public String changeSpecification(){

        WebElement specificationInput = driver.findElement(By.xpath("//div[1]/table/tbody/tr[6]/td[3]/div/div/div/div/div/input"));
        String specificationContent = specificationInput.getAttribute("value");
        specificationInput.clear();

        if (specificationContent.contains("-")) {
            /*if(contentSplit.length>1){
                incremental = Integer.parseInt(contentSplit[1])+1;
                specificationContent = contentSplit[0]+"-"+incremental;
            }else{
                specificationContent = specificationContent + 1;
            }*/
            specificationContent = specificationContent + getInt2Random();
        } else {
            //specificationContent = specificationContent + "-" + 1;
            specificationContent = specificationContent + "-" + getInt2Random();
        }
        article.setSpecification(specificationContent);
        return specificationContent;
    }

    public void iGotoMainTab(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Main')]")));
        WebElement mainTabButton = driver.findElement(By.xpath("//*[contains(text(),'Main')]"));
        mainTabButton.click();
    }

    public void changeStandardizationToSTD(){

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[1]/table/tbody/tr[8]/td[3]/div/div/div/div/div/button[1]")));
        WebElement cleanStandarization = driver.findElement(By.xpath("//div[1]/table/tbody/tr[8]/td[3]/div/div/div/div/div/button[1]"));
        cleanStandarization.click();
        long timetoWait = 3000;

        WebElement standarizationArrowButton = driver.findElement(By.xpath("//div[1]/table/tbody/tr[8]/td[3]/div/div/div/div/div/button[2]"));
        standarizationArrowButton.click();
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement hook = driver.findElement(By.xpath("//*[contains(text(),'Unidata Lifecycle Status')]"));
        WebElement standarizationOption = driver.findElement(By.xpath("//*[contains(text(),'STD')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", hook);
        standarizationOption.click();

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //WebElement msfid = driver.findElement(By.xpath("//*[contains(text(),'MSF Id')]"));
        //msfid.click();

        System.out.println("Standardization updated");
    }

    public void changeStandardizationToNST(){

        try {
            driver.switchTo().frame("ebx-legacy-component");
        } catch (Exception ignored) {
            System.out.println("Frame change not needed, flow continues");
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[contains(text(),'Standardization')]")));
        WebElement standardizationText = driver.findElement(By.xpath("//label[contains(text(),'Standardization')]"));

/*        WebElement cleanstandardizationText = driver.findElement(with(By.xpath("//button[@title = 'Clear field']")).toRightOf(standardizationText));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", cleanstandardizationText);
        cleanstandardizationText.click();*/


        long timetoWait = 3000;

        WebElement hook = driver.findElement(By.xpath("//*[contains(text(),'Standardization')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", hook);
        WebElement standardizationTextArrowButton = driver.findElement(with(By.xpath("//button[@title = 'Open drop-down list']")).toRightOf(standardizationText));

        standardizationTextArrowButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("___40_cfvAO__standardizationLevel")));
        WebElement standarizationOption = driver.findElement(By.id("___40_cfvAO__standardizationLevel"));
        standarizationOption.sendKeys("NST");
        standarizationOption.sendKeys(Keys.ENTER);
        //act.click(standarizationOption).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Label (English)')]")));
        WebElement label = driver.findElement(By.xpath("//*[contains(text(),'Label (English)')]"));
        act.click(label).perform();

        System.out.println("Standardization updated");
    }

    public void changeStandardizationToNSL(){

        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Main')]")));
        //WebElement mainTabButton = driver.findElement(By.xpath("//*[contains(text(),'Main')]"));
        //mainTabButton.click()

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[1]/table/tbody/tr[8]/td[3]/div/div/div/div/div/button[1]")));
        WebElement cleanStandard = driver.findElement(By.xpath("//div[1]/table/tbody/tr[8]/td[3]/div/div/div/div/div/button[1]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", cleanStandard);
        cleanStandard.click();
        long timetoWait = 3000;

        WebElement standarizationArrowButton = driver.findElement(By.xpath("//div[1]/table/tbody/tr[8]/td[3]/div/div/div/div/div/button[2]"));
        standarizationArrowButton.click();
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement hook = driver.findElement(By.xpath("//*[contains(text(),'Unidata Lifecycle Status')]"));
        WebElement standarizationOption = driver.findElement(By.xpath("//*[contains(text(),'NSL')]"));
        js.executeScript("arguments[0].scrollIntoView();", hook);
        standarizationOption.click();

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement family = driver.findElement(By.xpath("//*[contains(text(),'Family')]"));
        family.click();

        System.out.println("Standardization updated");
    }

    public void updateLabelEnglish(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[9]/td[3]/div/div/div/div/div/div/div/input")));
        WebElement labelEnglishInput = driver.findElement(By.xpath("//tr[9]/td[3]/div/div/div/div/div/div/div/input"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", labelEnglishInput);
        String contentLabelEnglish = labelEnglishInput.getAttribute("value") + getInt2Random();
        labelEnglishInput.clear();
        labelEnglishInput.sendKeys(contentLabelEnglish);
        System.out.println("Label updated");

    }

    public void updateLabelFrench(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[12]/td[3]/div/div/div/div/div/div/div/input")));
        WebElement labelFrenchInput = driver.findElement(By.xpath("//tr[12]/td[3]/div/div/div/div/div/div/div/input"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", labelFrenchInput);
        String contentLabelEnglish = labelFrenchInput.getAttribute("value") + getInt2Random();
        labelFrenchInput.clear();
        labelFrenchInput.sendKeys(contentLabelEnglish);
        System.out.println("Label updated");

    }

    public void changeLyfecycleStatus(){
        WebElement cleanLyfecycle = driver.findElement(By.xpath("//div[1]/table/tbody/tr[12]/td[3]/div/div/div/div/div/div/button[1]"));
        cleanLyfecycle.click();

        long timetoWait = 2000;
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement lyfeCycleArrow = driver.findElement(By.xpath("//div[1]/table/tbody/tr[12]/td[3]/div/div/div/div/div/div/button[2]"));
        lyfeCycleArrow.click();
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement lyfeCycleUpdate = driver.findElement(By.xpath("//*[contains(text(),'Valid Articles')]"));
        lyfeCycleUpdate.click();
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Lifecycle updated");

    }

    public void checkManufacturer(){
        WebElement hook = driver.findElement(By.xpath("//*[contains(text(),'External Client Info')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", hook);

        WebElement manufacturerTextArea = driver.findElement(By.xpath("//div[1]/table/tbody/tr[19]/td[4]/div/div/div/div/div/textarea"));
        String textAreaContent = manufacturerTextArea.getAttribute("value");
        assertEquals(0, textAreaContent.length());
        System.out.println("Manufacturer checked");

    }

    public void addArticlePicture(){
        long timetoWait = 3500;
        long timetoWait2 = 1500;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            Thread.sleep(timetoWait2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Supply')]")));
        WebElement supplyTab = driver.findElement(By.xpath("//*[contains(text(),'Supply')]"));
        act.click(supplyTab).perform();

        WebElement hook = driver.findElement(By.xpath("//*[contains(text(),'Main Article Picture')]"));
        js.executeScript("arguments[0].scrollIntoView();", hook);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title = 'Upload new digital asset(s)']")));
        WebElement uploadButton = driver.findElement(By.xpath("//button[@title = 'Upload new digital asset(s)']"));
        //uploadButton.click();
        act.click(uploadButton).perform();
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.switchTo().frame("ebx_InternalPopup_frame");
        driver.switchTo().frame("serviceIframe");
        String userDir = System.getProperty("user.dir");
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(userDir + "/src/test/resources/LEVO.jpg");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title = 'Attach and upload']")));
        WebElement attachUploadButton = driver.findElement(By.xpath("//button[@title = 'Attach and upload']"));
        //attachUploadButton.click();
        act.click(attachUploadButton).perform();
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Image uploaded");
        driver.switchTo().parentFrame();
        driver.switchTo().parentFrame();

    }

    public void iCheckArticleDuplicated(){
        System.out.println(article.getSpecification());
        long timetoWait = 2500;
        boolean created = false;
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'"+ article.getSpecification()+"')]")));

        try {
            WebElement articleCreated = driver.findElement(By.xpath("//*[contains(text(),'"+ article.getSpecification()+"')]"));
            created = true;
            System.out.println("Article duplicated confirmed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Article not duplicated, ERROR ");
        }
        assertTrue(created);
    }

    public void checkUnmatchedStatus(){
        long timetoWait = 2000;
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().parentFrame();
        boolean matched = false;
        try {
            WebElement unmatchedText = driver.findElement(By.xpath("//*[contains(text(),'[Unmatched]')]"));
            matched = true;
            System.out.println("Unmatched status confirmed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Article not in unmatched status, ERROR");
        }
        assertTrue(matched);
    }

    public void iSaveTheArticle(){
        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Save']"));
        saveButton.click();
        System.out.println("Article saved");
        long timetoWait = 2000;
        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'MSF Id')]")));
        WebElement msfId = driver.findElement(By.xpath("//*[contains(text(),'MSF Id')]"));
        WebElement idText = driver.findElement(with(By.xpath("//div/div[@class = 'ebx_FieldDecorator']")).toRightOf(msfId));

        try {
            System.out.println("Article with MSF Id: " + idText.getText());
            article.setMsfId(idText.getText());
        } catch (Exception e) {
            System.out.println("Error handling the MSF Id");
        }

    }

    public void iCloseTheArticle(){
        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Close']")));
        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Close']"));
        saveButton.click();
        System.out.println("Article saved");
    }

    public void fillFormEnglish(){
        WebElement formEnglish = driver.findElement(By.xpath("//tr[59]/td[3]/div/div/div[1]/div/table/tbody/tr[5]/td[1]/div/div/div[1]"));
        act.doubleClick(formEnglish).perform();
        //ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        //driver.switchTo().window(tabs2.get(1));
        //System.out.println(tabs.size());
        System.out.println("Form English");
    }

    public void fillArticleLabelEng(){

        WebElement labelEng = driver.findElement(By.xpath("//tr[9]/td[3]/div/div/div/div/div/div/div/input"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", labelEng);

        common2Random = getInt2Random();
        String randomSuffix = "Label ENG test " + common2Random;
        labelEng.sendKeys(randomSuffix);
        System.out.println("Label ENG");
    }

    public void fillArticleLabelFR(){
        WebElement labelEng = driver.findElement(By.xpath("//tr[10]/td[3]/div/div/div/div/div/div/div/input"));
        String randomSuffix = "Label FR test " + common2Random;
        labelEng.sendKeys(randomSuffix);
        System.out.println("Label FR");
    }

    public void fillLabelEng(){

        WebElement labelEng = driver.findElement(By.xpath("//div[1]/table/tbody/tr[11]/td[3]/div/div/div/div/input"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", labelEng);

        common2Random = getInt2Random();
        String randomSuffix = "Label ENG test " + common2Random;
        labelEng.sendKeys(randomSuffix);
        System.out.println("Label ENG");
    }

    public void checkErrorArticle(){
        boolean successful = true;
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'MSF Id')]")));
        WebElement msfId = driver.findElement(By.xpath("//*[contains(text(),'MSF Id')]"));
        try {
            WebElement error = driver.findElement(By.xpath("//*[contains(text(),'error')]"));
        } catch (Exception e) {
            successful = false;
            System.out.println("Article created with errors");
        }

        assertTrue(successful);
        System.out.println("Article created successfully");
    }

    public void fillLabelFR(){
        WebElement labelEng = driver.findElement(By.xpath("//div[1]/table/tbody/tr[12]/td[3]/div/div/div/div/input"));
        String randomSuffix = "Label ENG test " + common2Random;
        labelEng.sendKeys(randomSuffix);
        System.out.println("Label FR");
    }

    public void saveDescription(){
        WebElement saveButton = driver.findElement(By.xpath("//button[text()='Save']"));
        saveButton.click();
        System.out.println("Save description");
    }

    public void checkidentifier(){
        boolean successful = false;

        long timetoWait = 3000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Identifier')]")));
        WebElement identifier = driver.findElement(By.xpath("//*[contains(text(),'Identifier')]"));
        WebElement identifierId = driver.findElement(with(By.xpath("//td[3]/div/div[@class = 'ebx_FieldDecorator']")).toRightOf(identifier));
        String identifierContent = identifierId.getText();
        String numberClean = identifierContent.replaceAll(",","").replaceAll(" ","");
        int convertedString = Integer.parseInt(numberClean);

        try {
            if(identifierContent.contains("auto")||(identifierContent.contains("auto-incremented value"))){
                successful=false;
            }else{
                if(convertedString>0){
                    successful=true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error checking msfIdentifier, wrong format, maybe empty/null");
        }

        assertTrue(successful);
        System.out.println("Identifier checked");
    }

    public void addRootArticle(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[5]/td[3]/div/div/div/div/div/div/input[2]")));
        WebElement rootInput = driver.findElement(By.xpath("//tr[5]/td[3]/div/div/div/div/div/div/input[2]"));
        rootInput.sendKeys("NFOOBISC - Biscuits");
        long timetoWait = 2000;

        try {
            Thread.sleep(timetoWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rootInput.sendKeys(Keys.ENTER);
        WebElement rootText = driver.findElement(By.xpath("//*[contains(text(),'Root')]"));
        rootText.click();
        System.out.println("Root");
    }

    public void selectSpecificDescription(){

        boolean found = false;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'ADAPTER')]")));
            WebElement adapter = driver.findElement(By.xpath("//*[contains(text(),'ADAPTER')]"));
            WebElement checkBok = driver.findElement(with(By.xpath("//*[@type ='checkbox']")).toLeftOf(adapter));
            checkBok.click();
            found = true;
        } catch (Exception e) {
            WebElement adapter = driver.findElement(By.xpath("//*[contains(text(),'ETHERNET')]"));
            WebElement checkBok = driver.findElement(with(By.xpath("//*[@type ='checkbox']")).toLeftOf(adapter));
            checkBok.click();
            found = true;

        } finally {
            System.out.println("Unable to find ADAPTER, neither ETHERNET, test failed, please check the data");
            assertTrue(found);
        }

        System.out.println("Search criteria placed");

    }

    public void filterValidDescription(){
        driver.switchTo().frame("ebx-legacy-component");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[2]/div/table/tbody/tr[11]/td[1]/label/input")));
        WebElement filtersButton = driver.findElement(By.id("ebx_filtersButton"));
        act.click(filtersButton).perform();

        WebElement selectMenu = driver.findElement(By.id("ebx_SimpleSearchFilterNodeSelectorList_FILTER_0"));
        Select filterDropDown = new Select(selectMenu);
        selectMenu.click();
        filterDropDown.selectByVisibleText("Status");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@title = 'Open drop-down list']")));
        WebElement selectMenu2 = driver.findElement(By.xpath("//*[@title = 'Open drop-down list']"));
        act.click(selectMenu2).perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Valid Description')]")));
        WebElement validOption = driver.findElement(By.xpath("//*[contains(text(),'Valid Description')]"));
        validOption.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ebx_SimpleSearchFilterNodeSelectorList_FILTER_0")));
        WebElement selectMenu3 = driver.findElement(By.id("ebx_SimpleSearchFilterNodeSelectorList_FILTER_0"));
        Select filterDropDown3 = new Select(selectMenu3);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Publish on Web')]")));
        filterDropDown3.selectByVisibleText("Publish on Web");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value = 'true']")));
        WebElement checkBoxHasValue = driver.findElement(By.xpath("//input[@value = 'true']"));
        act.click(checkBoxHasValue).perform();

        WebElement applyButton = driver.findElement(By.id("FILTER_0_applyButton"));
        act.click(applyButton).perform();
    }

    public void performDuplicateAction(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Actions')]")));
        WebElement actionsButton = driver.findElement(By.xpath("//*[contains(text(),'Actions')]"));
        actionsButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Duplicate this record')]")));
        WebElement duplicateAction = driver.findElement(By.xpath("//*[contains(text(),'Duplicate this record')]"));
        duplicateAction.click();

        System.out.println("Actions duplicate");
    }

    public void updateArticleCode(){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[1]/table/tbody/tr[8]/td[3]/div/div/div/div/input")));
        WebElement codeInput = driver.findElement(By.xpath("//div[1]/table/tbody/tr[8]/td[3]/div/div/div/div/input"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", codeInput);
        String inputContent = codeInput.getAttribute("value");
        String newCode = inputContent + getInt2Random();
        codeInput.clear();
        codeInput.sendKeys(newCode);

    }



}
