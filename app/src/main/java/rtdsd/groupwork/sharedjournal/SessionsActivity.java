package rtdsd.groupwork.sharedjournal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import rtdsd.groupwork.sharedjournal.DialogFragments.AddElementDialogFragment;
import rtdsd.groupwork.sharedjournal.DialogFragments.BaseAppDialogFragment;
import rtdsd.groupwork.sharedjournal.model.Session;
import rtdsd.groupwork.sharedjournal.nearbyFragment.JournalSharingFragment;

public class SessionsActivity extends BaseActivity implements
        SessionsFragment.OnFragmentInteractionListener,
        BaseAppDialogFragment.OnDialogFragmentInteraction {

    private final String TAG = "SessionsActivity";
    public static final String EXTRA_JOURNAL_ID = "journalId";
    public static final String EXTRA_JOURNAL_TITLE = "journalTitle";

    private static final String SESSIONS_FRAGMENT_TAG = "sessionsFragment";
    private static final String ADD_SESSION_FRAGMENT_TAG = "addSessionFragmentTag";

    private String journalId;
    private String journalTitle;
    private Button shareJournalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shareJournalButton = findViewById(R.id.journal_share_button);

        journalId = getIntent().getStringExtra(EXTRA_JOURNAL_ID);
        journalTitle = getIntent().getStringExtra(EXTRA_JOURNAL_TITLE);

        Log.d(TAG, "onCreate: extra journal id got:" + journalId);
        Log.d(TAG, "onCreate: extra journal title got:" + journalTitle);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new FabSessionsOnClickListener());

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        SessionsFragment sessionsFragment = SessionsFragment.newInstance(journalId, journalTitle);
        transaction.add(R.id.fragmentLayout, sessionsFragment, SESSIONS_FRAGMENT_TAG);
        transaction.commit();


        shareJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment sharingFragment = JournalSharingFragment.newInstance(journalId, journalTitle);
                FragmentTransaction shareButtonTransaction = getSupportFragmentManager().beginTransaction();
                shareButtonTransaction.replace(R.id.fragmentLayout, sharingFragment);
                shareButtonTransaction.addToBackStack(null);
                shareButtonTransaction.commit();
                dismissEditDeleteFragment();
            }
        });
    }

    @Override
    public void openSession(Session session) {
        //// TODO: 18.10.2017 implement
        Log.d(TAG, "openSession: opening a new session: " + session.toString());

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

    private void dismissEditDeleteFragment(){
        //first find the fragment
        Fragment editDeleteFragment =
                getSupportFragmentManager().findFragmentByTag(SESSIONS_FRAGMENT_TAG);
        //if found, remove it
        if(editDeleteFragment != null)
            getSupportFragmentManager().beginTransaction().remove(editDeleteFragment).commit();
    }
}
