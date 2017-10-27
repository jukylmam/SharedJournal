package rtdsd.groupwork.sharedjournal.model;

import android.util.Log;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by OMISTAJA on 18.10.2017.
 */

public class Session {

    private String id, title, startedOn, endedOn;
    private HashMap<String, Boolean> entryIds;

    public Session (Session s){
        this.id = s.id;
        this.title = s.title;
        this.startedOn = s.startedOn;
        this.endedOn = s.endedOn;
        this.entryIds = s.entryIds;
    }

    public Session(){
        //empty constructor, as needed by Firebase
    }

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

    public HashMap<String, Boolean> getEntryIds() {
        return entryIds;
    }

    public void setEntryIds(HashMap<String, Boolean> entryIds) {
        this.entryIds = entryIds;
    }

    public boolean areContentsSame(Session s){
        return s.title.equals(title)
                && s.startedOn.equals(startedOn)
                && s.endedOn.equals(endedOn)
                && s.id.equals(id);
    }

    public void updateValues(Session s){
        Log.d("Session.java", "updateValues: title: " + s.getTitle()
                + " startedon: " + s.startedOn + " endedon: " + s.endedOn);
        title = s.title;
        startedOn = s.startedOn;
        endedOn = s.endedOn;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass()){
            return false;
        }

        Session s = (Session) obj;
        return Objects.equals(id, s.getId());
    }
}
