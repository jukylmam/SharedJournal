package rtdsd.groupwork.sharedjournal.viewmodel;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rtdsd.groupwork.sharedjournal.model.JournalEntry;
import rtdsd.groupwork.sharedjournal.model.RpgJournal;

/**
 * Created by OMISTAJA on 10.10.2017.
 */

class JournalLiveData extends LiveData<ArrayList<RpgJournal>> {

    //https://medium.com/google-developers/lifecycle-aware-data-loading-with-android-architecture-components-f95484159de4

    private final String TAG = "JournalLiveData";
    private final Context context;
    private FirebaseDatabase database;
    private ChildEventListener childEventListener;

    public JournalLiveData(Context context) {
        this.context = context;

        childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //here we get singular Journal entries, this will be called multiple times, once per each entry
                RpgJournal journal = getJournalFromSnapshot(dataSnapshot);
                ArrayList<RpgJournal> journals = getValue();
                if(journals == null) {
                    Log.d(TAG, "error adding entry to journals, getValue() returned null. " +
                            "Creating new 'journals'");
                    journals = new ArrayList<>();
                }
                if(journals.contains(journal)){
                    journals.remove(journal);
                }
                journals.add(journal);
                //set value for this LiveData object - model will know stuff has changed
                //and call observers' onChanged method with the new data
                setValue(journals);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                RpgJournal journal = getJournalFromSnapshot(dataSnapshot);

                ArrayList<RpgJournal> journals = getValue();
                if(journals != null){
                    journals.remove(journal);
                }
                setValue(journals);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                RpgJournal journal = getJournalFromSnapshot(dataSnapshot);
                ArrayList<RpgJournal> journals = getValue();
                if (journals != null) {
                    journals.get(journals.indexOf(journal)).updateValues(journal);
                    setValue(journals);
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled");
            }
        };
        database = FirebaseDatabase.getInstance();
    }

    private RpgJournal getJournalFromSnapshot(DataSnapshot snapshot){
        RpgJournal journal = snapshot.getValue(RpgJournal.class);
        if (journal != null) {
            Log.d(TAG, "onDataChange: got new journal with id:" + snapshot.getKey()
                    + "name: " + journal.getName());
            journal.setId(snapshot.getKey());
        }

        return journal;
    }

    @Override
    protected void onActive() {
        DatabaseReference reference = database.getReference("journals");
        reference.addChildEventListener(childEventListener);
    }

    @Override
    protected void onInactive() {
        DatabaseReference reference = database.getReference("journals");
        reference.removeEventListener(childEventListener);
    }
}
