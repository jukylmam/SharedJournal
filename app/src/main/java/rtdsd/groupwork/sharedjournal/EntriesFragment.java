package rtdsd.groupwork.sharedjournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
    private static final String ARG_ENTRY_ID = "entryIdParam";

    private String entryId;

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
     * @param entryId Parameter 1.
     * @return A new instance of fragment EntriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntriesFragment newInstance(String entryId) {
        EntriesFragment fragment = new EntriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ENTRY_ID, entryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            entryId = getArguments().getString(ARG_ENTRY_ID);
        }
        fireBaseEntryCommunication = new FireBaseEntryCommunication(entryId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_entries, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.entries_recyclerview);
        adapter = new EntriesRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        //GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager
                (2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        //observe the data
        EntriesViewModel model = ViewModelProviders.of(this, new EntriesViewModelFactory(
                this.getActivity().getApplication(), entryId))
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
            mListener.onFragmentInteraction(uri);
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
        mListener = null;
    }

    public void userAddingEntry(String entryBody) {
        String entryTitle = "Entry " + (adapter.getItemCount()+1);
        fireBaseEntryCommunication.addEntry(entryTitle, entryBody);
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
    public interface OnEntriesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
