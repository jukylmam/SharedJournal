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

    public FireBaseEntryCommunication(String entryId){
        this.databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child(DB_REF_ENTRIES)
                        .child(entryId);
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
}
