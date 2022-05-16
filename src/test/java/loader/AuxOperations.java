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
            value = rawNextLine[1].replaceAll(" ","");
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
    }

}
