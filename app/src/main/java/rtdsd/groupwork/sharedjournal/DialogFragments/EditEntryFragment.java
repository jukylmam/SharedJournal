package rtdsd.groupwork.sharedjournal.DialogFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import rtdsd.groupwork.sharedjournal.R;

/**
 * Created by domuska on 12/11/17.
 */

public class EditEntryFragment extends DialogFragment {


    OnEditFragmentInteraction listener;
    public static final String ARG_ENTRY_TITLE = "entryTitle";
    public static final String ARG_ENTRY_BODY = "entryBody";
    public static final String ARG_ENTRY_ID = "entryId";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String entryTitle = getArguments().getString(ARG_ENTRY_TITLE);
        final String entryBody = getArguments().getString(ARG_ENTRY_BODY);
        final String entryId = getArguments().getString(ARG_ENTRY_ID);

        if(entryTitle != null && entryBody != null && entryId != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View fragmentLayout = inflater.inflate(R.layout.dialogfragment_edit_entry, null);

            final TextInputEditText title = fragmentLayout.findViewById(R.id.entryTitleEditText);
            title.setText(entryTitle);

            final TextInputEditText body = fragmentLayout.findViewById(R.id.entryBodyEditText);
            body.setText(entryBody);

            builder.setView(fragmentLayout);
            builder.setTitle(getString(R.string.edit_entry_dialog_title))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onEditEntryOkClicked(
                                    entryId,
                                    title.getText().toString(),
                                    body.getText().toString());
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    });
            return builder.create();
        }
        else{
            throw new Error(
                    "must supply arguments to EditEntryFragment : title, body and ID of entry");
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditEntryFragment.OnEditFragmentInteraction) {
            listener = (EditEntryFragment.OnEditFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDialogFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * Interface for communication between the dialog fragment and
     * the activity where it's hosted
     */
    public interface OnEditFragmentInteraction{
        void onEditEntryOkClicked(String id, String newTitle, String newBody);
    }
}
