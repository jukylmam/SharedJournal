package rtdsd.groupwork.sharedjournal;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.os.FileObserver;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMISTAJA on 10.10.2017.
 */

class JournalLiveData extends LiveData<List<String>> {

    //https://medium.com/google-developers/lifecycle-aware-data-loading-with-android-architecture-components-f95484159de4

    private final String TAG = "JournalLiveData";
    private final Context context;
    private final FileObserver fileObserver;
    FirebaseDatabase database;
    ValueEventListener valueEventListener;

    public JournalLiveData(Context context) {
        this.context = context;
        String path = new File(context.getFilesDir(),
                "downloaded.json").getPath();
        fileObserver = new FileObserver(path) {
            @Override
            public void onEvent(int event, String path) {
                // The file has changed, so let’s reload the data
                loadData();
            }
        };
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                ArrayList<String> strings = new ArrayList<>();
                strings.add(message);
                setValue(strings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled");
            }
        };
        //loadData();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onActive() {
        DatabaseReference reference = database.getReference("message");
        reference.addValueEventListener(valueEventListener);
        //fileObserver.startWatching();
    }

    @Override
    protected void onInactive() {
        DatabaseReference reference = database.getReference("message");
        reference.removeEventListener(valueEventListener);
        //fileObserver.stopWatching();
    }

    private void loadData() {
        /*new AsyncTask<Void, Void, List<String>>(){

            @Override
            protected List<String> doInBackground(Void... params) {
                return null;
            }
        }*/
        new AsyncTask<Void,Void,List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                File jsonFile = new File(context.getFilesDir(),
                        "downloaded.json");
                List<String> data = new ArrayList<>();
                // Parse the JSON using the library of your choice
                return data;
            }
            @Override
            protected void onPostExecute(List<String> data) {
                setValue(data);
            }
        }.execute();
    }
}
