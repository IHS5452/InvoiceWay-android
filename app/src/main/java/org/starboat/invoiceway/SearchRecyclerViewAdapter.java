package org.starboat.invoiceway;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchRecyclerViewHolder> {

    private ArrayList<row> mRow = new ArrayList<row>();
    private onRowListener mOnRowList;




    public static class SearchRecyclerViewHolder extends ViewHolder implements View.OnClickListener {
    public TextView itemName;
    public onRowListener onRowListener;

        public SearchRecyclerViewHolder(@NonNull View itemView, onRowListener onRowListener) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemname);
        this.onRowListener = onRowListener;


    itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
        onRowListener.onRowClicked(getAdapterPosition());
        }
    }

    public SearchRecyclerViewAdapter(ArrayList<row> itemList, onRowListener onRowListener) {
        this.mOnRowList = onRowListener;
        mRow = itemList;
    }

    @NonNull
    @Override
    public SearchRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvrow, parent, false);
        SearchRecyclerViewHolder vh = new SearchRecyclerViewHolder(v, mOnRowList);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewHolder holder, final int position) {
    final row currentItem =  mRow.get(position);
        holder.itemName.setText(currentItem.getmText());


    }

    @Override
    public int getItemCount() {
        return mRow.size();
    }

    public interface onRowListener{
        void onRowClicked(int position);
    }

}




