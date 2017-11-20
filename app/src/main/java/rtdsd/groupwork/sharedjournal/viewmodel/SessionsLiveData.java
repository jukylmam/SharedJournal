package rtdsd.groupwork.sharedjournal.viewmodel;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import rtdsd.groupwork.sharedjournal.model.Session;

/**
 * Created by OMISTAJA on 18.10.2017.
 */

public class SessionsLiveData extends LiveData<ArrayList<Session>> {

    Context context;

    private final String TAG = "SessionsLiveData";

    private ChildEventListener sessionListener;
    private FirebaseDatabase database;

    private final String DB_SESSIONS_KEY = "sessions";

    private final String journalId;


    public SessionsLiveData(Context context, String journalId){

        this.context = context;
        this.journalId = journalId;

        sessionListener = new SessionsListener();

        database = FirebaseDatabase.getInstance();
    }

    /**
     * Get a session from a snapshot, initialize the session with ID
     * @param snapshot a snapshot gotten from Firebase database
     * @return an initialized session
     */
    private Session getSessionFromSnapshot(DataSnapshot snapshot){
        Session session = snapshot.getValue(Session.class);
        if (session != null) {
            session.setId(snapshot.getKey());
        }
        return session;
    }

    @Override
    protected void onActive() {
        database.getReference(DB_SESSIONS_KEY)
                .child(journalId)
                .addChildEventListener(sessionListener);
    }

    @Override
    protected void onInactive() {
        database.getReference(DB_SESSIONS_KEY)
                .child(journalId)
                .removeEventListener(sessionListener);
    }

    private class SessionsListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            Session session = getSessionFromSnapshot(dataSnapshot);
            if(session.getEntryIds() == null){
                session.setEntryIds(new HashMap<String, Boolean>());
            }

            ArrayList<Session> sessions = getValue();
            if(sessions == null){
                sessions = new ArrayList<>();
            }
            if(!sessions.contains(session)){
                Log.d(TAG, "onChildAdded: got new child " + dataSnapshot.getKey());
                sessions.add(session);
                setValue(sessions);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Session changedSession = getSessionFromSnapshot(dataSnapshot);
            if(changedSession.getEntryIds() == null){
                changedSession.setEntryIds(new HashMap<String, Boolean>());
            }

            ArrayList<Session> sessions = getValue();

            if (sessions != null) {
                sessions.get(sessions.indexOf(changedSession)).updateValues(changedSession);
                setValue(sessions);
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

            Session removableSession = getSessionFromSnapshot(dataSnapshot);

            ArrayList<Session> sessions = getValue();
            if (sessions != null) {
                sessions.remove(removableSession);
            }
            setValue(sessions);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, "onChildMoved: session moved, should maybe do something");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "onCancelled: cancelled database call: " + databaseError);
        }
    }
}
