package com.gifmsg.application;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>  {


    private ArrayList<String> mData;
    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;

    MyRecyclerViewAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gif_drawee, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String uri = mData.get(position);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        holder.myDraweeView.setController(controller);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        DraweeView myDraweeView;

        ViewHolder(View itemView) {
            super(itemView);
            myDraweeView = itemView.findViewById(R.id.gifs);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
    }

        String getItem(int id) {
            return mData.get(id);
        }

//        // allows clicks events to be caught
//        void setClickListener(ItemClickListener itemClickListener) {
//            this.mClickListener = itemClickListener;
//        }
//
//        // parent activity will implement this method to respond to click events
//        public interface ItemClickListener {
//            void onItemClick(View view, int position);
//        }
}
