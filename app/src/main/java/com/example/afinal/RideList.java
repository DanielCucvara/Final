package com.example.afinal;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class RideList extends ArrayAdapter<Ride> {

    private Activity context;
    private List<Ride> rideList;

    public RideList (Activity context,List<Ride> rideList) {
        super(context,R.layout.list_layout,rideList);
        this.context = context;
        this.rideList = rideList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView StartTextView = (TextView) listViewItem.findViewById(R.id.startTextView);
        TextView DestinationTextView = (TextView) listViewItem.findViewById(R.id.destinationTextView);

        Ride ride = rideList.get(position);

        StartTextView.setText(ride.getStart());
        DestinationTextView.setText(ride.getDestination());

        return listViewItem;
    }
}
