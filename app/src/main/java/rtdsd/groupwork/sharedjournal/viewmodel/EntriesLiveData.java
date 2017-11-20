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

        entryListener = new EntryListener();

        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive: attaching firebase reference to session ID: " + sessionId);
        database.getReference(ENTRIES_KEY)
                .child(sessionId)
                .addChildEventListener(entryListener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive: unattaching firebase reference to session ID" + sessionId);
        database.getReference(ENTRIES_KEY)
                .child(sessionId)
                .removeEventListener(entryListener);
    }

    private class EntryListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, "onChildAdded: datasnapshot value: " + dataSnapshot.getValue());
            Entry entry = dataSnapshot.getValue(Entry.class);
            if (entry != null) {
                Log.d(TAG, "onChildAdded: child added: " + entry.getEntryTitle());
                entry.setId(dataSnapshot.getKey());

                ArrayList<Entry> entries = getValue();
                if (entries == null) {
                    entries = new ArrayList<>();
                }

                if (!entries.contains(entry)) {
                    entry.setId(dataSnapshot.getKey());
                    entries.add(entry);
                    setValue(entries);
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, "onChildChanged: snapshot: " + dataSnapshot.getValue());
            Entry entry = dataSnapshot.getValue(Entry.class);

            if (entry != null) {
                ArrayList<Entry> entries = getValue();
                entry.setId(dataSnapshot.getKey());
                if (entries != null) {
                    entries.get(entries.indexOf(entry)).updateValues(entry);
                    setValue(entries);
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Entry entry = dataSnapshot.getValue(Entry.class);
            if (entry != null) {
                entry.setId(dataSnapshot.getKey());
            }

            ArrayList<Entry> entries = getValue();
            if (entries != null) {
                entries.remove(entry);
                setValue(entries);
            }
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }
}
