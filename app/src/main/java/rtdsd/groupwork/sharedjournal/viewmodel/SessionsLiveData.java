package rtdsd.groupwork.sharedjournal.viewmodel;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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

    public SessionsLiveData(Context context){

        this.context = context;

        sessionListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Session session = dataSnapshot.getValue(Session.class);
                Log.d(TAG, "onChildAdded: got new child " + dataSnapshot.getKey());
                session.setId(dataSnapshot.getKey());

                ArrayList<Session> sessions = getValue();
                if(sessions == null){
                    sessions = new ArrayList<>();
                }
                sessions.add(session);
                setValue(sessions);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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
        database.getReference(DB_SESSIONS_KEY).addChildEventListener(sessionListener);
    }

    @Override
    protected void onInactive() {
        database.getReference(DB_SESSIONS_KEY).removeEventListener(sessionListener);
    }
}
