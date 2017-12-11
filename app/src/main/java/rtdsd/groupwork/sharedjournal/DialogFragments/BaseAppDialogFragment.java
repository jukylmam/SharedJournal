package rtdsd.groupwork.sharedjournal.DialogFragments;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.util.Log;

/**
 * Created by domuska on 11/9/17.
 */

public class BaseAppDialogFragment extends DialogFragment {

    protected OnDialogFragmentInteraction mListener;
    private String TAG = "BaseAppDialogFragment";

    public static final String DIALOG_TITLE_ARG = "dialogTitleArg";
    public static final String DIALOG_EDITTEXT_HINT_ARG = "editTextHintArg";

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
    public interface OnDialogFragmentInteraction {
        void onBaseAppDialogOkButtonClicked(String editTextContents);
    }


    public void onOkButtonPressed(String editTextContents) {
        if (mListener != null) {
            Log.d(TAG, "onOkButtonPressed: ok clicked, journal name is " + editTextContents);
            mListener.onBaseAppDialogOkButtonClicked(editTextContents);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogFragmentInteraction) {
            mListener = (OnDialogFragmentInteraction) context;
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
}
