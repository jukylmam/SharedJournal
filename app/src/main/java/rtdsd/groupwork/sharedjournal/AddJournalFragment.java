package rtdsd.groupwork.sharedjournal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple DialogFragment to be shown in the MainActivity.
 * This will be used to get the new journal name from the user.
 * Use the default empty constructor when creating a new fragment
 * and call show(FragmentManager, fragmentTag) to show the fragment.
 * Calling acitivty needs to implement the OnFragmentInteraction
 * interface or application will crash at runtime. This interface
 * is used for communication between the fragment and the activity.
 */
public class AddJournalFragment extends DialogFragment {

    private final String TAG = "AddJournalFragment";
    private OnFragmentInteraction mListener;

    public AddJournalFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_journal, null);

        final TextInputEditText editText = view.findViewById(R.id.edittext_journalname);

        builder.setView(view)
                .setMessage(R.string.add_journal_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //pass the text in the edit text to the activity
                        onOkButtonPressed(editText.getText().toString());
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialog
                        AddJournalFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


    public void onOkButtonPressed(String journalName) {
        if (mListener != null) {
            Log.d(TAG, "onOkButtonPressed: ok clicked, journal name is " + journalName);
            mListener.onOkButtonClicked(journalName);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteraction) {
            mListener = (OnFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteraction {
        void onOkButtonClicked(String journalName);
    }
}
