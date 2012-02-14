package jlm.gae;

import com.google.appengine.api.datastore.*;

public class Exercise {
    
    private Key key;
    private String userName;
    private String exoName;
    private String exoLang;
    private boolean success;

    public Exercise(String userName, String exoName, String exoLang, boolean success) {
        this.userName = userName;
        this.exoName = exoName;
        this.exoLang = exoLang;
        this.success = success;

        key = KeyFactory.createKey("Exercise", exoName);

    }

    public void save() {

        Entity exercise = new Entity("Exercise", key);
        exercise.setProperty("username", userName);
        exercise.setProperty("exoname", exoName);
        exercise.setProperty("exolang", exoLang);
        exercise.setProperty("success", success);

        DatastoreService datastore =
                DatastoreServiceFactory.getDatastoreService();
        datastore.put(exercise);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExoName() {
        return exoName;
    }

    public void setExoName(String exoName) {
        this.exoName = exoName;
    }

    public String getExoLang() {
        return exoLang;
    }

    public void setExoLang(String exoLang) {
        this.exoLang = exoLang;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
