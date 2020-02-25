package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindRideActivity extends AppCompatActivity {

    private ListView listViewRides;
    private String Start;
    private String Destination;
    private DatabaseReference DatRef;
    private List<Ride> rideList;
    private ArrayList<String> mStarts;
    private ArrayList<String> mDestinations;
    private ArrayList<String> mImages;
    PlacesClient placesClient;
    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.ADDRESS);
    AutocompleteSupportFragment places_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);
        initPlaces();
        setUpPlaceAutoCompleteDestination();
        setUpPlaceAutoCompleteStart();

        DatRef = FirebaseDatabase.getInstance().getReference().child("Ride");
        //listViewRides = findViewById(R.id.listViewRides);
        rideList = new ArrayList<>();
        mStarts = new ArrayList<>();
        mDestinations = new ArrayList<>();
        mImages = new ArrayList<>();

        initmRides();

    }
    /*@Override
    protected void onStart() {
        super.onStart();

        DatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rideList.clear();
                for (DataSnapshot ridesSnapshot : dataSnapshot.getChildren()){
                    Ride ride = ridesSnapshot.getValue(Ride.class);
                    rideList.add(ride);
                }
                RideList adapter = new RideList(FindRideActivity.this,rideList);
                listViewRides.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    private void initmRides(){
        DatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDestinations.clear();
                mStarts.clear();
                mImages.clear();
                for (DataSnapshot ridesSnapshot : dataSnapshot.getChildren()){
                    Ride ride = ridesSnapshot.getValue(Ride.class);
                    mStarts.add(ride.getStart());
                    mDestinations.add(ride.getDestination());
                    mImages.add("https://i.gifer.com/fetch/w300-preview/4d/4da3190067177e522e7771c66cf3c25d.gif");

                }
                initRecyclerView(mStarts,mDestinations,mImages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void initRecyclerView(ArrayList<String> mStarts,ArrayList<String> mDestinations,ArrayList<String> mImages){
        RecyclerView recyclerView = findViewById(R.id.recyclerView_findRide);
        RecyclerViewAdapter adapter= new RecyclerViewAdapter(mStarts,mDestinations,mImages,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initPlaces(){
        Places.initialize(this,getString(R.string.places_api_key));
        placesClient = Places.createClient(this);

    }

    public void setUpPlaceAutoCompleteStart(){
        places_fragment = (AutocompleteSupportFragment)getSupportFragmentManager()
                .findFragmentById(R.id.findStart_autocomplete);
        places_fragment.setPlaceFields(placeFields);
        places_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(FindRideActivity.this,""+place.getName(),Toast.LENGTH_SHORT).show();
                Start = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(FindRideActivity.this,""+status.getStatusMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setUpPlaceAutoCompleteDestination(){
        places_fragment = (AutocompleteSupportFragment)getSupportFragmentManager()
                .findFragmentById(R.id.findDestination_autocomplete);
        places_fragment.setPlaceFields(placeFields);
        places_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(FindRideActivity.this,""+place.getName(),Toast.LENGTH_SHORT).show();
                Destination = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(FindRideActivity.this,""+status.getStatusMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
