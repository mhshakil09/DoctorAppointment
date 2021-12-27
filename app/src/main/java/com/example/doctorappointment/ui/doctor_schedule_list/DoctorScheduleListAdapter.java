package com.example.doctorappointment.ui.doctor_schedule_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointment.R;

import java.util.List;

public class DoctorScheduleListAdapter extends RecyclerView.Adapter<DoctorScheduleListAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    int selectedPosition = -1;

    // data is passed into the constructor
    DoctorScheduleListAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public DoctorScheduleListAdapter() {

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_view_time_slot, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.timeSlotTV.setText(text);

        if (selectedPosition == position) {
            holder.timeSlot.setBackground(ContextCompat.getDrawable(holder.timeSlot.getContext(), R.drawable.bg_option_selected));
        } else {
            holder.timeSlot.setBackground(ContextCompat.getDrawable(holder.timeSlot.getContext(), R.drawable.bg_option_unselected));
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView timeSlotTV;
        View timeSlot;

        ViewHolder(View itemView) {
            super(itemView);
            timeSlotTV = itemView.findViewById(R.id.timeSlotTV);
            timeSlot = itemView.findViewById(R.id.timeSlot);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null){
                mClickListener.onItemClick(view, getAdapterPosition());
                selectedPosition = getAdapterPosition();
                notifyDataSetChanged();
            }
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}