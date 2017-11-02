package rtdsd.groupwork.sharedjournal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Factory for creating new SessionViewModels. Will pass the
 * journalID (that is passed in this class' constructor) to the constructor of the
 * SessionLiveData class.
 */

public class SessionsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application application;
    private String journalId;

    /**
     *
     * @param app Instance of the application context running in the application now
     * @param journalId ID of a journal in the Firebase database
     */
    public SessionsViewModelFactory(Application app, String journalId){
        application = app;
        this.journalId = journalId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new SessionsViewModel(application, journalId);
    }
}
