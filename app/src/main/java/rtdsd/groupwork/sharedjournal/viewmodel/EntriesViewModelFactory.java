package rtdsd.groupwork.sharedjournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by domuska on 11/9/17.
 */

public class EntriesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application application;
    private String entryId;

    /**
     *
     * @param app Instance of the application context running in the application now
     * @param entryId ID of an entry in the Firebase database
     */
    public EntriesViewModelFactory(Application app, String entryId){
        application = app;
        this.entryId = entryId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new EntriesViewModel(application, entryId);
    }
}
