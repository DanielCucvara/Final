package com.example.afinal;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mStart = new ArrayList<>();
    private ArrayList<String> mDestination = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<String> mTimes = new ArrayList<>();
    private ArrayList<Integer> mSeats = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mStart, ArrayList<String> mDestination, ArrayList<String> mImages, ArrayList<String> mDates, ArrayList<String> mTimes, ArrayList<Integer> mSeats, Context mContext) {
        this.mStart = mStart;
        this.mDestination = mDestination;
        this.mImages = mImages;
        this.mDates = mDates;
        this.mTimes = mTimes;
        this.mSeats = mSeats;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");



        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);
        holder.Start.setText(mStart.get(position));
        holder.Destination.setText(mDestination.get(position));
        holder.Date.setText(mDates.get(position));
        holder.Time.setText(mTimes.get(position));
        holder.Seats.setText(mSeats.get(position).toString());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on "+mStart.get(position));

                Toast.makeText(mContext,mStart.get(position),Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mStart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView Start;
        TextView Destination;
        TextView Date;
        TextView Time;
        TextView Seats;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            Start = itemView.findViewById(R.id.recyclerView_start);
            Destination = itemView.findViewById(R.id.recyclerView_destination);
            Date = itemView.findViewById(R.id.recyclerView_date);
            Time = itemView.findViewById(R.id.recyclerView_time);
            Seats = itemView.findViewById(R.id.recyclerView_seats);
            parentLayout = itemView.findViewById(R.id.recyclerView_layout);
        }
    }
}
