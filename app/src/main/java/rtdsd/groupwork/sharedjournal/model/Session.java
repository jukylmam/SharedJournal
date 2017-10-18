package rtdsd.groupwork.sharedjournal.model;

import java.util.HashMap;

/**
 * Created by OMISTAJA on 18.10.2017.
 */

public class Session {

    String id, title, startedOn, endedOn;
    HashMap<String, Boolean> listEntries;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    public String getEndedOn() {
        return endedOn;
    }

    public void setEndedOn(String endedOn) {
        this.endedOn = endedOn;
    }

    public HashMap<String, Boolean> getListEntries() {
        return listEntries;
    }

    public void setListEntries(HashMap<String, Boolean> listEntries) {
        this.listEntries = listEntries;
    }
}
