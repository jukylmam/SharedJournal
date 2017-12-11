package rtdsd.groupwork.sharedjournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.ArrayList;

import rtdsd.groupwork.sharedjournal.model.Entry;
import rtdsd.groupwork.sharedjournal.model.Session;

/**
 * Created by domuska on 11/9/17.
 */

public class EntriesViewModel extends AndroidViewModel{

    private final EntriesLiveData liveData;

    EntriesViewModel(Application application, String journalId){
        super(application);
        liveData = new EntriesLiveData(application, journalId);
    }

    public LiveData<ArrayList<Entry>> getData(){
        return liveData;
    }
}
