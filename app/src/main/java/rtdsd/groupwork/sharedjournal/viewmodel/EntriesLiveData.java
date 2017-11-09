package rtdsd.groupwork.sharedjournal.viewmodel;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import rtdsd.groupwork.sharedjournal.model.Entry;

/**
 * Created by domuska on 11/9/17.
 */

public class EntriesLiveData extends LiveData<ArrayList<Entry>> {

    String sessionId;

    private final String ENTRIES_KEY = "entries";

    private final String TAG = "EntriesLiveData";

    private ChildEventListener entryListener;
    private FirebaseDatabase database;

    public EntriesLiveData(Context context, String sessionId){

        this.sessionId = sessionId;

        entryListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Entry entry = dataSnapshot.getValue(Entry.class);
                if(entry != null) {
                    Log.d(TAG, "onChildAdded: child added: " + entry.getEntryTitle());
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //update this.value here
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //update this.value here
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onActive() {
        database.getReference(ENTRIES_KEY)
                .child(sessionId)
                .addChildEventListener(entryListener);
    }

    @Override
    protected void onInactive() {
        database.getReference(ENTRIES_KEY)
                .child(sessionId)
                .removeEventListener(entryListener);
    }
}
