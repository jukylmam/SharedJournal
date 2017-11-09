package rtdsd.groupwork.sharedjournal.recyclerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by domuska on 11/9/17.
 */

public class EntriesRecyclerAdapter extends RecyclerView.Adapter<JournalsRecyclerAdapter.ViewHolder> {

    @Override
    public void onBindViewHolder(JournalsRecyclerAdapter.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public JournalsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(JournalsRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
