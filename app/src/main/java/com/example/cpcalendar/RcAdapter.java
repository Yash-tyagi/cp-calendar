package com.example.cpcalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class RcAdapter extends RecyclerView.Adapter<RcAdapter.ViewHolder> {

    private Datum[] mData;
    private OnContestInfoListener mOnContestInfoListener;
    private ProgressBar progressBar;

    private static SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
    private static SimpleDateFormat sdfOutput = new SimpleDateFormat("dd MMM yyyy','HH:mm");

    public RcAdapter(Datum[] data, OnContestInfoListener onContestInfoListener, ProgressBar progressBar) {
        mData = data;
        mOnContestInfoListener=onContestInfoListener;
        this.progressBar = progressBar;
    }

    static String[] getDateAndTime(String datetime){

        String[] date_time = new String[0];

        sdfInput.setTimeZone(TimeZone.getTimeZone("UTC"));


        try {
            // parse is used to convert String to Date
            Date date = sdfInput.parse(datetime);

            // format is used to change the format of the current date in string...
            String date_output = sdfOutput.format(date);

            date_time= date_output.split(",");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date_time;
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

        String[] date_time_start = getDateAndTime(datum.getStartTime());
        String[] date_time_end = getDateAndTime(datum.getEndTime());


        holder.mTvContestName.setText(datum.getName());
        holder.mTvContestSite.setText(datum.getSite());
        holder.mTvContestStartTime.setText(date_time_start[0]);
        holder.mTvContestEndTime.setText(date_time_end[0]);
        holder.mTvContestStartDate.setText(date_time_start[1]);
        holder.mTvContestEndDate.setText(date_time_end[1]);

        int color_id = R.color.white;
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
        Date startDate = null;
        Date endDate = null;
        
        try{
            startDate = sdf2.parse(date_time_start[0]);
            String startDateFormatted = sdf1.format(startDate);

            startDate = sdf1.parse(startDateFormatted);

//            endDate = sdf2.parse(date_time_start[0]);
//            String endDateFormatted = sdf1.format(endDate);

//            endDate = sdf1.parse(endDateFormatted);

            if (System.currentTimeMillis() >= startDate.getTime() ) {
                color_id = R.color.active_contests;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        int color = holder.mRecyclerViewRowLayout.getContext().getResources().getColor(color_id);
        holder.mRecyclerViewRowLayout.setCardBackgroundColor(color);

        progressBar.setVisibility(View.INVISIBLE);

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
        private CardView mRecyclerViewRowLayout;
        OnContestInfoListener onContestInfoListener;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, OnContestInfoListener onContestInfoListener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mRecyclerViewRowLayout = itemView.findViewById(R.id.recycler_view_row_layout);
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
