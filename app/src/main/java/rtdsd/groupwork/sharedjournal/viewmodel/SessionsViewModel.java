package rtdsd.groupwork.sharedjournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.ArrayList;

import rtdsd.groupwork.sharedjournal.model.Session;

/**
 * Created by OMISTAJA on 18.10.2017.
 */

public class SessionsViewModel extends AndroidViewModel{

    private final SessionsLiveData liveData;

    public SessionsViewModel(Application application){
        super(application);
        liveData = new SessionsLiveData(application);
    }

    public LiveData<ArrayList<Session>> getData(){
        return liveData;
    }


}
