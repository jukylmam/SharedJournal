package rtdsd.groupwork.sharedjournal.DialogFragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import rtdsd.groupwork.sharedjournal.R;

/**
 * Created by domuska on 11/20/17.
 */

public class EditDeleteDialogFragment extends DialogFragment {

    private onEditDeleteFragmentInteraction mListener;
    public static final String ELEMENT_INTERACTION_ID = "elementInteractionId";
    public static final String ELEMENT_INTERACTION_TITLE = "elementInteractionTitle";
    public static final String ELEMENT_INTERACTION_BODY = "elementInteractionBody";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(getArguments() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();

            //we are creating a custom dialog fragment so passing null as root is okay
            View dialogLayout = inflater.inflate(R.layout.dialogfragment_edit_element, null);

            final String elementId = getArguments().getString(ELEMENT_INTERACTION_ID);
            final String elementTitle = getArguments().getString(ELEMENT_INTERACTION_TITLE);
            final String elementBody = getArguments().getString(ELEMENT_INTERACTION_BODY);

            Button editButton = dialogLayout.findViewById(R.id.dialogfragment_edit_element);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onEditEntryClicked(elementId, elementTitle, elementBody);
                }
            });

            Button deleteButton = dialogLayout.findViewById(R.id.dialogfragment_delete_element);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onDeleteEntryClicked(elementId);
                }
            });

            builder.setView(dialogLayout);
            return builder.create();
        }
        else
            throw new RuntimeException(getContext().toString()
                + "must pass arguments to EditDeleteDialogFragment: " +
                    "ID of the element to be edited/deleted");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onEditDeleteFragmentInteraction) {
            mListener = (onEditDeleteFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDialogFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface for communication between the dialog fragment and
     * the activity where it's hosted
     */
    public interface onEditDeleteFragmentInteraction{
        void onEditEntryClicked(String id, String title, String body);
        void onDeleteEntryClicked(String id);
    }

}
