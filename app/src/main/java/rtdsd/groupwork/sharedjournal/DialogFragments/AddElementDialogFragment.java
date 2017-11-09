package rtdsd.groupwork.sharedjournal.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;

import rtdsd.groupwork.sharedjournal.R;


/**
 * A simple DialogFragment to be shown in the MainActivity.
 * This will be used to get the new journal name from the user.
 * Use the default empty constructor when creating a new fragment
 * and call show(FragmentManager, fragmentTag) to show the fragment.
 * Calling acitivty needs to implement the OnDialogFragmentInteraction
 * interface or application will crash at runtime. This interface
 * is used for communication between the fragment and the activity.
 */
public class AddElementDialogFragment extends BaseAppDialogFragment {

    private final String TAG = "AddElementDialogFragment";

    public AddElementDialogFragment() {
        // Required empty public constructor
    }

    //public AddElementDialogFragment(String title, String editTextHint){

    //}


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogfragment_addelement, null);

        final TextInputEditText editText = view.findViewById(R.id.edittext_addName);

        String dialogTitle, editTextHint;

        if(getArguments() != null){
            dialogTitle = getArguments().getString(DIALOG_TITLE_ARG);
            editTextHint = getArguments().getString(DIALOG_EDITTEXT_HINT_ARG);
        }
        else{
            dialogTitle = "default title";
            editTextHint = "default edit text hint";
        }

        editText.setHint(editTextHint);

        builder.setView(view)
                .setMessage(dialogTitle)
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
                        AddElementDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    /*@Override
    public void onOkButtonPressed(String editTextContents) {
        if (mListener != null) {
            Log.d(TAG, "onOkButtonPressed: ok clicked, journal name is " + editTextContents);
            mListener.onDialogOkButtonClicked(editTextContents);
        }
    }*/

}
