package rtdsd.groupwork.sharedjournal;

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
                RpgJournal journal = dataSnapshot.getValue(RpgJournal.class);
                Log.d(TAG, "onDataChange: got new journal with id:" + dataSnapshot.getKey()
                        + "name: " + journal.getName());
                journal.setId(dataSnapshot.getKey());
                ArrayList<RpgJournal> journals = getValue();
                if(journals == null) {
                    Log.d(TAG, "error adding entry to journals, getValue() returned null. " +
                            "Creating new 'journals'");
                    journals = new ArrayList<>();
                }
                journals.add(journal);
                //set value for this LiveData object - model will know stuff has changed
                //and call observers' onChanged method with the new data
                setValue(journals);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
