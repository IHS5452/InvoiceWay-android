package org.starboat.invoiceway;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import org.starboat.invoiceway.row;
import org.starboat.invoiceway.modifyCart;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {

    private ArrayList<row> mRow = new ArrayList<row>();
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public static class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView itemName;
//, final OnItemClickListener listener
        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemname);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
        }

    }

    public MyRecyclerViewAdapter(ArrayList<row> itemList) {
        mRow = itemList;
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvrow, parent, false);
        MyRecyclerViewHolder vh = new MyRecyclerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, final int position) {
    final row currentItem =  mRow.get(position);
        holder.itemName.setText(currentItem.getmText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         modifyCart.removeFromCart(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mRow.size();
    }


}




