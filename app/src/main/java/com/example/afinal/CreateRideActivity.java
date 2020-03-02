package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateRideActivity extends AppCompatActivity {
    private DatabaseReference DatRef;
    private String Start;
    private String Destination;
    private String Date;
    private String Time;
    private int Seats;
    private EditText NumberOfSeats;
    private Button CreateRideBtn;
    private TextView TimeTextView;
    private TextView DateTextView;
    private DatePickerDialog.OnDateSetListener mRideDatePickerListener;
    private TimePickerDialog.OnTimeSetListener mRideTimePickerListener;
    PlacesClient placesClient;
    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.ADDRESS);
    AutocompleteSupportFragment places_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);

        DatRef = FirebaseDatabase.getInstance().getReference().child("Ride");
        TimeTextView = findViewById(R.id.TimeTextView);
        DateTextView = findViewById(R.id.textViewDate);
        CreateRideBtn = findViewById(R.id.CreateRideBtn);
        NumberOfSeats = findViewById(R.id.PassengersEditText);

        initPlaces();
        initTimeDateListeners();
        initCreateRideListener();

        setUpPlaceAutoCompleteStart();
        setUpPlaceAutoCompleteDestination();
    }





/*
        EtStart=findViewById(R.id.editTextStart);
        EtDate=findViewById(R.id.editTextDate);
        EtDestination=findViewById(R.id.editTextDestination);
        EtPassengers=findViewById(R.id.editTextPassengers);
        btnCreateRide=findViewById(R.id.btnCreateRide);
        Ride = new Ride();
        DatRef = FirebaseDatabase.getInstance().getReference().child("Ride");
        btnCreateRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int passengers = Integer.parseInt(EtPassengers.getText().toString().trim());
                String start = EtStart.getText().toString().trim();
                String dest = EtDestination.getText().toString().trim();
                String date = EtDate.getText().toString().trim();

                Ride.setDate(date);
                Ride.setDestination(dest);
                Ride.setPassengers(passengers);
                Ride.setStart(start);
                DatRef.push().setValue(Ride);
                Toast.makeText(CreateRideActivity.this,"U made it dog",Toast.LENGTH_LONG).show();

            }
        });


*/
public void initPlaces(){
    Places.initialize(this,getString(R.string.places_api_key));
    placesClient = Places.createClient(this);

}

    public void setUpPlaceAutoCompleteStart(){
        places_fragment = (AutocompleteSupportFragment)getSupportFragmentManager()
                .findFragmentById(R.id.places_autocomplete_fragment_start);
        places_fragment.setPlaceFields(placeFields);
        places_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(CreateRideActivity.this,""+place.getName(),Toast.LENGTH_SHORT).show();
                Start = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(CreateRideActivity.this,""+status.getStatusMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setUpPlaceAutoCompleteDestination(){
        places_fragment = (AutocompleteSupportFragment)getSupportFragmentManager()
                .findFragmentById(R.id.places_autocomplete_fragment_destination);
        places_fragment.setPlaceFields(placeFields);
        places_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(CreateRideActivity.this,""+place.getName(),Toast.LENGTH_SHORT).show();
                Destination = place.getName();
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(CreateRideActivity.this,""+status.getStatusMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initTimeDateListeners(){
        TimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog timeDialog = new TimePickerDialog(
                        CreateRideActivity.this,
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
                        CreateRideActivity.this,
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

    private void initCreateRideListener(){
        CreateRideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seats = Integer.parseInt(NumberOfSeats.getText().toString().trim());
                Ride ride = new Ride();
                ride.setDate(Date);
                ride.setDestination(Destination);
                ride.setPassengers(Seats);
                ride.setStart(Start);
                DatRef.push().setValue(ride);
                Toast.makeText(CreateRideActivity.this,"U made it dog",Toast.LENGTH_LONG).show();
            }
        });
    }




}



