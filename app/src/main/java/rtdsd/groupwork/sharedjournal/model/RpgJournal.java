package rtdsd.groupwork.sharedjournal.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by OMISTAJA on 17.10.2017.
 */

public class RpgJournal {

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
}
