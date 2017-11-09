package rtdsd.groupwork.sharedjournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rtdsd.groupwork.sharedjournal.model.Session;
import rtdsd.groupwork.sharedjournal.recyclerViewAdapters.SessionsRecyclerAdapter;
import rtdsd.groupwork.sharedjournal.viewmodel.SessionsViewModel;
import rtdsd.groupwork.sharedjournal.viewmodel.SessionsViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SessionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SessionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String JOURNAL_ID_PARAM = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String TAG = "SessionsFragment";

    // TODO: Rename and change types of parameters
    private String journalId;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    SessionsViewModel model;

    public SessionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param journalId Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SessionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SessionsFragment newInstance(String journalId, String param2) {
        SessionsFragment fragment = new SessionsFragment();
        Bundle args = new Bundle();
        args.putString(JOURNAL_ID_PARAM, journalId);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            journalId = getArguments().getString(JOURNAL_ID_PARAM);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sessions, container, false);


        RecyclerView recyclerView = v.findViewById(R.id.sessionsList);
        final SessionsRecyclerAdapter sessionsAdapter = new SessionsRecyclerAdapter();
        recyclerView.setAdapter(sessionsAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        //observer the livedata (firebase data)
        model = ViewModelProviders.of(this, new SessionsViewModelFactory(
                this.getActivity().getApplication(), journalId))
                .get(SessionsViewModel.class);
        //SessionsViewModel model = ViewModelProviders.of(this).get(SessionsViewModel.class);
        model.getData().observe(this, new Observer<ArrayList<Session>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Session> sessions) {
                if(sessions != null){
                    sessionsAdapter.setSessions(sessions);
                }
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public void userAddingSession(String name){
        Log.d(TAG, "userAddingSession: in fragment, got session name" + name);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
