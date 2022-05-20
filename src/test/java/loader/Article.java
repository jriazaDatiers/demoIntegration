package loader;

import java.util.ArrayList;
import java.util.List;

public class Article {

    public String code;
    public int MSFid;
    public String lifeCycleStatus;
    public ArrayList<String> modules = new ArrayList<>();

    public Article() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMSFid() {
        return MSFid;
    }

    public void setMSFid(int MSFid) {
        this.MSFid = MSFid;
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
}
