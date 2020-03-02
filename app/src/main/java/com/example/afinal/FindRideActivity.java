package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.List;

public class FindRideActivity extends AppCompatActivity {

    private ListView listViewRides;
    private String Start;
    private String Destination;
    private String Time;
    private String Date;
    private int FreeSeats;
    private DatabaseReference DatRef;
    private List<Ride> rideList;
    private ArrayList<String> mStarts;
    private ArrayList<String> mDestinations;
    private ArrayList<String> mImages;
    private TextView DateTextView;
    private TextView TimeTextView;
    private EditText PassengersEditText;
    private Button FindRideButton;
    private DatePickerDialog.OnDateSetListener mRideDatePickerListener;
    private TimePickerDialog.OnTimeSetListener mRideTimePickerListener;
    private Boolean RidesFound;
    PlacesClient placesClient;
    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.ADDRESS);
    AutocompleteSupportFragment places_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);
        DateTextView = findViewById(R.id.find_ride_date);
        TimeTextView = findViewById(R.id.find_ride_time);
        PassengersEditText = findViewById(R.id.find_ride_passengers);
        FindRideButton = findViewById(R.id.find_ride_btn);


        DatRef = FirebaseDatabase.getInstance().getReference().child("Ride");
        //listViewRides = findViewById(R.id.listViewRides);
        rideList = new ArrayList<>();
        mStarts = new ArrayList<>();
        mDestinations = new ArrayList<>();
        mImages = new ArrayList<>();

        setUpPlaceAutoCompleteDestination();
        setUpPlaceAutoCompleteStart();

        initPlaces();
        initmRides();
        initFindRideListener();
        initTimeDatePicker();

    }


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

    private void initFindRideListener(){
        FindRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        RidesFound = false;
                        mDestinations.clear();
                        mStarts.clear();
                        mImages.clear();
                        //FreeSeats = Integer.parseInt(PassengersEditText.getText().toString());
                        for (DataSnapshot ridesSnapshot : dataSnapshot.getChildren()) {
                            Ride ride = ridesSnapshot.getValue(Ride.class);
                            if (ride.getStart().equals(Start) && ride.getDestination().equals(Destination)) {
                                mStarts.add(ride.getStart());
                                mDestinations.add(ride.getDestination());
                                mImages.add("https://i.gifer.com/fetch/w300-preview/4d/4da3190067177e522e7771c66cf3c25d.gif");
                                RidesFound = true;

                            }

                        }
                        if (RidesFound == true) {
                            initRecyclerView(mStarts, mDestinations, mImages);
                        } else
                            Toast.makeText(FindRideActivity.this, "Nenasli sa ziadne jazdy", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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



    private void initTimeDatePicker(){
        TimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog timeDialog = new TimePickerDialog(
                        FindRideActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mRideTimePickerListener,
                        hour,minute,true);
                timeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialog.show();
            }
        });

        mRideTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay+":"+minute;
                TimeTextView.setText(time);
                Time = time;
            }
        };

        DateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month =  cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(
                        FindRideActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mRideDatePickerListener,
                        year,month,day);
                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();
            }
        });

        mRideDatePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth+"/"+(month+1)+"/"+year;
                DateTextView.setText(date);
                Date = date;
            }
        };

    }



}
