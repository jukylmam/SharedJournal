package rtdsd.groupwork.sharedjournal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

/**
 * Created by OMISTAJA on 10.10.2017.
 */

public class JournalViewModel extends AndroidViewModel {

    /*private final MutableLiveData<List<String>> data =
            new MutableLiveData<>();*/

    private final JournalLiveData data;

    public JournalViewModel (Application app){
        super(app);
        data = new JournalLiveData(app);
    }

    public LiveData<List<String>> getData(){
        return data;
    }


}
