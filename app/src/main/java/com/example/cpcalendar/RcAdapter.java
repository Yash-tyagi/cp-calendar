package com.example.cpcalendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RcAdapter extends RecyclerView.Adapter<RcAdapter.ViewHolder> {

    private Datum[] mData;
    private OnContestInfoListener mOnContestInfoListener;

    public RcAdapter(Datum[] data,OnContestInfoListener onContestInfoListener) {
        mData = data;
        mOnContestInfoListener=onContestInfoListener;
    }


    @NonNull
    @Override
    // Usually involves inflating a layout from XML and returning the holder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contestView = inflater.inflate(R.layout.custom_item_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contestView,mOnContestInfoListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Datum datum = mData[position];

        String[] date_time_start = datum.getStartTime().split("T");

/*        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat formatter;
        try {
            Date date = format.parse(datum.getStartTime());
            formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
            String strDate = formatter.format(date);
            Log.e("ye hai actual",strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        String[] date_time_end = datum.getEndTime().split("T");

        holder.mTvContestName.setText(datum.getName());
        holder.mTvContestSite.setText(datum.getSite());
        holder.mTvContestStartTime.setText(date_time_start[0]);
        holder.mTvContestEndTime.setText(date_time_end[0]);
        holder.mTvContestStartDate.setText(date_time_start[1]);
        holder.mTvContestEndDate.setText(date_time_end[1]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView mTvContestSite;
        public TextView mTvContestName;
        public TextView mTvContestStartTime;
        public TextView mTvContestEndTime;
        public TextView mTvContestStartDate;
        public TextView mTvContestEndDate;
        OnContestInfoListener onContestInfoListener;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, OnContestInfoListener onContestInfoListener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.onContestInfoListener = onContestInfoListener;
            mTvContestSite = itemView.findViewById(R.id.tv_contest_site);
            mTvContestName = itemView.findViewById(R.id.tv_contest_name);
            mTvContestStartTime = itemView.findViewById(R.id.contest_start_time);
            mTvContestEndTime = itemView.findViewById(R.id.contest_end_time);
            mTvContestStartDate = itemView.findViewById(R.id.contest_start_date);
            mTvContestEndDate = itemView.findViewById(R.id.contest_end_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onContestInfoListener.onContestInfoClick(getBindingAdapterPosition());
        }
    }

    public interface OnContestInfoListener{
        void onContestInfoClick(int position);
    }

}
