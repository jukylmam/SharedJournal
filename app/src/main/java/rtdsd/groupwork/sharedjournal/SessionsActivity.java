package rtdsd.groupwork.sharedjournal;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import rtdsd.groupwork.sharedjournal.DialogFragments.AddElementDialogFragment;
import rtdsd.groupwork.sharedjournal.DialogFragments.BaseAppDialogFragment;
import rtdsd.groupwork.sharedjournal.model.Session;

public class SessionsActivity extends BaseActivity implements
        SessionsFragment.OnSessionsFragmentInteractionListener,
        EntriesFragment.OnEntriesFragmentInteractionListener,
        BaseAppDialogFragment.OnDialogFragmentInteraction {

    private final String TAG = "SessionsActivity";
    public static final String EXTRA_JOURNAL_ID = "journalId";

    private static final String SESSIONS_FRAGMENT_TAG = "sessionsFragment";
    private static final String ENTRIES_FRAGMENT_TAG = "entriesFragment";
    private static final String ADD_SESSION_FRAGMENT_TAG = "addSessionFragmentTag";


    private String journalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        journalId = getIntent().getStringExtra(EXTRA_JOURNAL_ID);

        Log.d(TAG, "onCreate: extra journal id got:" + journalId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new FabSessionsOnClickListener());

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        SessionsFragment sessionsFragment = SessionsFragment.newInstance(journalId,"");
        transaction.add(R.id.fragmentLayout, sessionsFragment, SESSIONS_FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public void openSession(Session session) {
        Log.d(TAG, "openSession: opening a new session: " + session.toString());

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        EntriesFragment entriesFragment = EntriesFragment.newInstance(session.getId());

        transaction.replace(R.id.fragmentLayout, entriesFragment, ENTRIES_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDialogOkButtonClicked(String editTextContents) {
        ((SessionsFragment)getSupportFragmentManager()
                .findFragmentByTag(SESSIONS_FRAGMENT_TAG))
                .userAddingSession(editTextContents);
    }

    class FabSessionsOnClickListener implements View.OnClickListener{
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
