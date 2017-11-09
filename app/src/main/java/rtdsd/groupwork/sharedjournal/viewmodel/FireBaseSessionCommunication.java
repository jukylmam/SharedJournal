package rtdsd.groupwork.sharedjournal.viewmodel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import rtdsd.groupwork.sharedjournal.model.Session;

/**
 * Created by domuska on 11/9/17.
 */

public class FireBaseSessionCommunication {

    private DatabaseReference databaseReference;
    private final String DB_REF_SESSIONS = "sessions";

    public FireBaseSessionCommunication(){
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void addSession(String journalId, String sessionName){
        Session s = new Session();
        s.setTitle(sessionName);

        //set initial startedOn and endedOn dates to now, endedOn updated as entries added to session
        String startDate = LocalDate.now().toString();
        String startTime = LocalTime.now().toString();
        String timeStamp = startDate + " - "+ startTime;
        s.setStartedOn(timeStamp);
        s.setEndedOn(timeStamp);
        databaseReference.child(DB_REF_SESSIONS).child(journalId).push().setValue(s);
    }
}
