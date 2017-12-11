package rtdsd.groupwork.sharedjournal.viewmodel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rtdsd.groupwork.sharedjournal.model.Entry;

/**
 * Created by domuska on 11/17/17.
 */

public class FireBaseEntryCommunication {

    DatabaseReference databaseReference;
    private final String DB_REF_ENTRIES = "entries";


    public FireBaseEntryCommunication(String sessionId){
        //the database reference to entries - sessionId
        //the entries are saved under the session ID
        this.databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child(DB_REF_ENTRIES)
                        .child(sessionId);
    }

    public void addEntry(String entryTitle, String entryBody){
        Entry entry = new Entry();
        entry.setEntryTitle(entryTitle);
        entry.setEntryBody(entryBody);
        //push a new reference to the DB and save the key to the entry
        String key = databaseReference.push().getKey();
        entry.setId(key);
        databaseReference.child(key).setValue(entry);
    }

    public void editEntry(Entry entry){
            //save the entry to firebase over the old entry
            databaseReference.child(entry.getId()).setValue(entry);

    }

    public void deleteEntry(String entryId) {
        databaseReference.child(entryId).setValue(null);
    }
}
