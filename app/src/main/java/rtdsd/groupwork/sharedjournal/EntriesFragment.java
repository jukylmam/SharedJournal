package rtdsd.groupwork.sharedjournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rtdsd.groupwork.sharedjournal.model.Entry;
import rtdsd.groupwork.sharedjournal.recyclerViewAdapters.EntriesRecyclerAdapter;
import rtdsd.groupwork.sharedjournal.viewmodel.EntriesViewModel;
import rtdsd.groupwork.sharedjournal.viewmodel.EntriesViewModelFactory;
import rtdsd.groupwork.sharedjournal.viewmodel.FireBaseEntryCommunication;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnEntriesFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EntriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntriesFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SESSION_ID = "entryIdParam";

    private String sessionId;

    private FireBaseEntryCommunication fireBaseEntryCommunication;

    EntriesRecyclerAdapter adapter;

    private OnEntriesFragmentInteractionListener mListener;

    public EntriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sessionId ID of the entry that was opened previously
     * @return A new instance of fragment EntriesFragment.
     */
    public static EntriesFragment newInstance(String sessionId) {
        EntriesFragment fragment = new EntriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SESSION_ID, sessionId);
        fragment.setArguments(args);
        return fragment;
    }

    public Entry getEntryById(String id){
        if(adapter!= null){
            return adapter.getEntryById(id);
        }
        else
            return null;
    }

    public void entryChanged(Entry entry) {
        adapter.updateEntry(entry);
    }

    public FireBaseEntryCommunication getFirebaseInstance(){
        return fireBaseEntryCommunication;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sessionId = getArguments().getString(ARG_SESSION_ID);
        }
        fireBaseEntryCommunication = new FireBaseEntryCommunication(sessionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_entries, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.entries_recyclerview);
        adapter = new EntriesRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);

        //GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager
                (2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        //observe the data
        EntriesViewModel model = ViewModelProviders.of(this, new EntriesViewModelFactory(
                this.getActivity().getApplication(), sessionId))
                .get(EntriesViewModel.class);

        model.getData().observe(this, new Observer<ArrayList<Entry>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Entry> entries) {
                if(entries != null){
                    adapter.setEntries(entries);
                }
            }
        });

        //return inflater.inflate(R.layout.fragment_entries, container, false);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.entryFragmentDetaching(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEntriesFragmentInteractionListener) {
            mListener = (OnEntriesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSessionsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.entryFragmentDetaching();
        mListener = null;
    }

    public void userAddingEntry(String entryBody) {
        String entryTitle = "Entry " + (adapter.getItemCount()+1);
        fireBaseEntryCommunication.addEntry(entryTitle, entryBody);
    }

    public void elementLongClicked(Entry entry) {
        //just pass the call to the activity
        mListener.onElementLongClicked(entry);
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnEntriesFragmentInteractionListener {
        void entryFragmentDetaching();
        void onElementLongClicked(Entry entry);
    }
}
