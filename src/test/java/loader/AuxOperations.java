package loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class AuxOperations {


    private String fileRolePath;
    private final static String fileEnvironmentPath ="src/test/resources/environments.txt";
    private final static String COMMA_DELIMETER = ",";
    public HashMap<String, String> roleDataMap = new HashMap<>();
    public HashMap<String, String> environmentsDataMap = new HashMap<>();

    public AuxOperations() {
    }

    private Scanner openFile(String filePath){

        Scanner scanner = null;
        try{
            scanner = new Scanner(new File(filePath));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return scanner;
    }

    private void closeFile(Scanner scanner){
        try{
            scanner.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void composeFileRolePath(String role){
        String basePath = "src/test/resources/";

        switch (role) {
            case "UD_dataOwner":
                fileRolePath = basePath + "dataOwnerData.txt";
                break;
            case "UD_dataSteward":
                fileRolePath = basePath + "dataStewardData.txt";
                break;
            case "UD_readOnly":
                fileRolePath = basePath + "readOnlyData.txt";
                break;
        }
    }

    private void loadRoleData(){
        Scanner scanner = openFile(fileRolePath);
        String[] rawNextLine;
        String key;
        String value;

        while (scanner.hasNextLine()){
            rawNextLine = scanner.nextLine().split(COMMA_DELIMETER);
            key = rawNextLine[0].replaceAll(" ","");
            if (key.contains("addressee") || key.contains("participant") ) {
                value = rawNextLine[1];
            } else {
                value = rawNextLine[1].replaceAll(" ","");
            }
            roleDataMap.put(key,value);
        }
        closeFile(scanner);
    }

    private void loadEnvironmentsData(){
        Scanner scanner = openFile(fileEnvironmentPath);
        String[] rawNextLine;
        String key;
        String value;

        while (scanner.hasNextLine()){
            rawNextLine = scanner.nextLine().split(COMMA_DELIMETER);
            key = rawNextLine[0].replaceAll(" ","");
            value = rawNextLine[1].replaceAll(" ","");
            environmentsDataMap.put(key,value);
        }
        closeFile(scanner);
    }

    public void performUnidata(String role, String environment){
        composeFileRolePath(role);
        loadRoleData();
        loadEnvironmentsData();
        setUrl(environment);
    }

    private void setUrl(String environment){
        roleDataMap.put("url",environmentsDataMap.get(environment));
    }

    public String getUser() {
        return roleDataMap.get("user");
    }

    public String getPassword() {
        return roleDataMap.get("password");
    }

    public String getUrl() {
        return roleDataMap.get("url");
    }

    public String getName() {
        return roleDataMap.get("name");
    }

    public String getAddressee() {
        return roleDataMap.get("addressee");
    }

    public String getParticipant() {
        return roleDataMap.get("participant");
    }

    public String getHasToSucceed() {
        return roleDataMap.get("hasToSucceed");
    }

    public String getNewEmail() {
        return roleDataMap.get("newEmail");
    }

    public String getOldEmail() {
        return roleDataMap.get("oldEmail");
    }



}