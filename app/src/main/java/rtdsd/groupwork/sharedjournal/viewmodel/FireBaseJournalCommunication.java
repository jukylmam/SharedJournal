package rtdsd.groupwork.sharedjournal.viewmodel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rtdsd.groupwork.sharedjournal.model.RpgJournal;
import rtdsd.groupwork.sharedjournal.model.Session;

/**
 * Created by domuska on 11/2/17.
 */

public class FireBaseJournalCommunication {

    private DatabaseReference databaseReference;

    private final String DB_REF_JOURNALS = "journals";

    public FireBaseJournalCommunication(){
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void addJournal(String journalName){
        RpgJournal journal = new RpgJournal();
        journal.setName(journalName);

        databaseReference.child(DB_REF_JOURNALS).push().setValue(journal);
    }


}
