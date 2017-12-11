package rtdsd.groupwork.sharedjournal;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import rtdsd.groupwork.sharedjournal.DialogFragments.AddElementDialogFragment;
import rtdsd.groupwork.sharedjournal.DialogFragments.BaseAppDialogFragment;
import rtdsd.groupwork.sharedjournal.DialogFragments.EditDeleteDialogFragment;
import rtdsd.groupwork.sharedjournal.DialogFragments.EditEntryFragment;
import rtdsd.groupwork.sharedjournal.model.Entry;
import rtdsd.groupwork.sharedjournal.model.Session;
import rtdsd.groupwork.sharedjournal.viewmodel.FireBaseEntryCommunication;

public class SessionsActivity extends BaseActivity implements
        SessionsFragment.OnSessionsFragmentInteractionListener,
        EntriesFragment.OnEntriesFragmentInteractionListener,
        BaseAppDialogFragment.OnDialogFragmentInteraction,
        EditDeleteDialogFragment.onEditDeleteFragmentInteraction,
        EditEntryFragment.OnEditFragmentInteraction{

    private final String TAG = "SessionsActivity";
    public static final String EXTRA_JOURNAL_ID = "journalId";

    private static final String SESSIONS_FRAGMENT_TAG = "sessionsFragment";
    private static final String ENTRIES_FRAGMENT_TAG = "entriesFragment";
    private static final String ADD_SESSION_FRAGMENT_TAG = "addSessionFragmentTag";
    private static final String ADD_ENTRY_FRAGMENT_TAG = "addEntryFragmentTag";
    private static final String EDIT_DELETE_FRAGMENT_TAG = "editDeleteFragmentTag";
    private static final String EDIT_ENTRY_FRAGMENT_TAG = "editEntryFragmentTag";

    private String journalId;

    private FloatingActionButton fab;
    private Toolbar toolbar;

    @IntDef({SESSIONS_FRAGMENT_ACTIVE, ENTRIES_FRAGMENT_ACTIVE})
    public @interface ActiveFragment{}
    public static final int SESSIONS_FRAGMENT_ACTIVE = 0;
    public static final int ENTRIES_FRAGMENT_ACTIVE = 1;

    @ActiveFragment
    private int activeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        journalId = getIntent().getStringExtra(EXTRA_JOURNAL_ID);
        activeFragment = ENTRIES_FRAGMENT_ACTIVE;

        Log.d(TAG, "onCreate: extra journal id got:" + journalId);
        fab = findViewById(R.id.fab);

        setCurrentlyActiveFragment(SESSIONS_FRAGMENT_ACTIVE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        SessionsFragment sessionsFragment = SessionsFragment.newInstance(journalId,"");
        transaction.add(R.id.fragmentLayout, sessionsFragment, SESSIONS_FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar = findViewById(R.id.toolbar);
        if(activeFragment == SESSIONS_FRAGMENT_ACTIVE)
            toolbar.setTitle(getString(R.string.sessions_title));
        else
            toolbar.setTitle(getString(R.string.entries_title));


        setSupportActionBar(toolbar);
    }

    @Override
    public void entryFragmentDetaching() {
        setCurrentlyActiveFragment(SESSIONS_FRAGMENT_ACTIVE);
    }


    private void setCurrentlyActiveFragment(@ActiveFragment int activeFragment){
        this.activeFragment = activeFragment;
        if(activeFragment == SESSIONS_FRAGMENT_ACTIVE){
            fab.setOnClickListener(new FabAddSessionClickListener());
            if(getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.sessions_title));
            }
        }
        else{
            fab.setOnClickListener(new FabAddEntryClickListener());
            if(getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.entries_title));
            }
        }
    }

    //interface implementation for SessionsFragment
    @Override
    public void openSession(Session session) {
        Log.d(TAG, "openSession: opening a new session: " + session.toString());

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        EntriesFragment entriesFragment = EntriesFragment.newInstance(session.getId());

        transaction.replace(R.id.fragmentLayout, entriesFragment, ENTRIES_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
        setCurrentlyActiveFragment(ENTRIES_FRAGMENT_ACTIVE);
    }

    //interface implementation for BaseAppDialogFragment
    @Override
    public void onBaseAppDialogOkButtonClicked(String editTextContents) {
        if(activeFragment == SESSIONS_FRAGMENT_ACTIVE) {
            ((SessionsFragment) getSupportFragmentManager()
                    .findFragmentByTag(SESSIONS_FRAGMENT_TAG))
                    .userAddingSession(editTextContents);
        }
        else{
            ((EntriesFragment) getSupportFragmentManager()
                    .findFragmentByTag(ENTRIES_FRAGMENT_TAG))
                    .userAddingEntry(editTextContents);
        }
    }

    //interface implementation for EntriesFragment
    @Override
    public void onElementLongClicked(Entry entry) {
        //launch the dialogfragment to show edit / delete buttons
        Bundle args = new Bundle();
        args.putString(EditDeleteDialogFragment.ELEMENT_INTERACTION_ID, entry.getId());
        args.putString(EditDeleteDialogFragment.ELEMENT_INTERACTION_TITLE, entry.getEntryTitle());
        args.putString(EditDeleteDialogFragment.ELEMENT_INTERACTION_BODY, entry.getEntryBody());
        DialogFragment editDeleteFragment = new EditDeleteDialogFragment();
        editDeleteFragment.setArguments(args);
        editDeleteFragment.show(getSupportFragmentManager(), EDIT_DELETE_FRAGMENT_TAG);
    }

    //interface implementation for EditDeleteDialogFragment
    @Override
    public void onEditEntryClicked(String id, String title, String body) {
        Log.d(TAG, "onEditEntryClicked: you seem to have long clicked element with id " + id);
        dismissEditDeleteFragment();

        //show a new fragment that has the title and body of entry as editable text
        Bundle args = new Bundle();
        args.putString(EditEntryFragment.ARG_ENTRY_BODY, body);
        args.putString(EditEntryFragment.ARG_ENTRY_TITLE, title);
        args.putString(EditEntryFragment.ARG_ENTRY_ID, id);
        DialogFragment editFragment = new EditEntryFragment();
        editFragment.setArguments(args);
        editFragment.show(getSupportFragmentManager(), EDIT_ENTRY_FRAGMENT_TAG);
    }

    private void dismissEditDeleteFragment(){
        //first find the fragment
        Fragment editDeleteFragment =
                getSupportFragmentManager().findFragmentByTag(EDIT_DELETE_FRAGMENT_TAG);
        //if found, remove it
        if(editDeleteFragment != null)
            getSupportFragmentManager().beginTransaction().remove(editDeleteFragment).commit();
    }

    @Override
    public void onDeleteEntryClicked(String id) {
        Log.d(TAG, "onDeleteEntryClicked: you seem to have long clicked element with id " + id);
        //just delete the entry. No need to warn the user of immediate DOOOOOOOM!
        dismissEditDeleteFragment();
        EntriesFragment entriesFragment =
                (EntriesFragment) getSupportFragmentManager().findFragmentByTag(ENTRIES_FRAGMENT_TAG);
        entriesFragment.getFirebaseInstance().deleteEntry(id);

    }

    //OnEditFragmentInteraction interface
    @Override
    public void onEditEntryOkClicked(String id, String newTitle, String newBody) {
        EntriesFragment entriesFragment =
                (EntriesFragment) getSupportFragmentManager().findFragmentByTag(ENTRIES_FRAGMENT_TAG);
        Entry originalEntry = entriesFragment.getEntryById(id);

        if((!originalEntry.getEntryTitle().equals(newTitle)) ||
                !originalEntry.getEntryBody().equals(newBody)){

            //if either body or title have changed, just save both
            originalEntry.setEntryTitle(newTitle);
            originalEntry.setEntryBody(newBody);
            //tell the fragment (that can tell the adapter) that an entry has been changed
            entriesFragment.entryChanged(originalEntry);
            //get firebase instance from the entries fragment
            FireBaseEntryCommunication firebaseInstance = entriesFragment.getFirebaseInstance();
            //pass the instance the edited entry, new title and body
            firebaseInstance.editEntry(originalEntry);
        }


    }



    //fab onClickListener implementations
    class FabAddEntryClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Bundle args = new Bundle();
            args.putString(BaseAppDialogFragment.DIALOG_TITLE_ARG,
                    getString(R.string.add_entry_dialog_title));
            args.putString(BaseAppDialogFragment.DIALOG_EDITTEXT_HINT_ARG,
                    getString(R.string.add_entry_edittext_hint));
            DialogFragment addEntryFragment = new AddElementDialogFragment();
            addEntryFragment.setArguments(args);
            addEntryFragment.show(getSupportFragmentManager(), ADD_ENTRY_FRAGMENT_TAG);
        }
    }



    class FabAddSessionClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {

            //create args for the dialog fragment texts
            Bundle args = new Bundle();
            args.putString(BaseAppDialogFragment.DIALOG_TITLE_ARG,
                    getString(R.string.add_session_dialog_title));
            args.putString(BaseAppDialogFragment.DIALOG_EDITTEXT_HINT_ARG,
                    getString(R.string.add_session_edittext_hint));

            DialogFragment addJournalFragment = new AddElementDialogFragment();
            addJournalFragment.setArguments(args);
            addJournalFragment.show(getSupportFragmentManager(), ADD_SESSION_FRAGMENT_TAG);
        }
    }

}
