package rtdsd.groupwork.sharedjournal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by OMISTAJA on 17.10.2017.
 */

public class RpgJournal {

    public RpgJournal(){
        //empty journal for firebase
    }

    public RpgJournal(RpgJournal journal){
        //for deep copying a journal
        this.name = journal.name;
        this.id = journal.id;
    }

    private String name, id;
    //to be changed to Sessions objects when its done
    private HashMap<String, Boolean> sessions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, Boolean> getSessions() {
        return sessions;
    }

    public void setSessions(HashMap<String, Boolean> sessions) {
        this.sessions = sessions;
    }

    public void updateValues(RpgJournal journal){
        this.name = journal.name;
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

        RpgJournal journal = (RpgJournal) obj;
        return journal.getId().equals(this.id);
    }
}
