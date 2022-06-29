package loader;

import java.util.ArrayList;
import java.util.List;

public class Article {

    public String code;
    public String unidataURLStatus;
    public String unicatURLStatus;
    public String type;
    public String state;
    public String lifeCycleStatus;
    public String standardizationLevel;
    public String unidataURL;
    public String unicatURL;
    public String labelEnglish;
    public String specification;
    public ArrayList<String> modules = new ArrayList<>();

    public Article() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLifeCycleStatus() {
        return lifeCycleStatus;
    }

    public void setLifeCycleStatus(String lifeCycleStatus) {
        this.lifeCycleStatus = lifeCycleStatus;
    }

    public List<String> getModules() {
        return modules;
    }

    public void addModules(String module){
        modules.add(module);
    }

    public void setModulesKADMKLIF08(){
        modules.add("KADMMLIFB08");
        modules.add("KADMMLIFC08");
        modules.add("KADMMLIFF08");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public String getStandardizationLevel() {
        return standardizationLevel;
    }

    public String getUnidataURL() {
        return unidataURL;
    }

    public String getUnicatURL() {
        return unicatURL;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStandardizationLevel(String standardizationLevel) {
        this.standardizationLevel = standardizationLevel;
    }

    public void setUnidataURL(String unidataURL) {
        this.unidataURL = unidataURL;
    }

    public void setUnicatURL(String unicatURL) {
        this.unicatURL = unicatURL;
    }

    public String getUnidataURLStatus() {
        return unidataURLStatus;
    }

    public String getUnicatURLStatus() {
        return unicatURLStatus;
    }

    public void setUnidataURLStatus(String unidataURLStatus) {
        this.unidataURLStatus = unidataURLStatus;
    }

    public void setUnicatURLStatus(String unicatURLStatus) {
        this.unicatURLStatus = unicatURLStatus;
    }

    public String getLabelEnglish() {
        return labelEnglish;
    }

    public void setLabelEnglish(String labelEnglish) {
        this.labelEnglish = labelEnglish;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
