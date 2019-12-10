package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class CreateRideActivity extends AppCompatActivity {
    EditText EtPassengers,EtDestination,EtStart,EtDate;
    Button btnCreateRide;
    DatabaseReference DatRef;
    Ride Ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);

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



    }

}
