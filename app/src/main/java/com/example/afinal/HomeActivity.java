package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Button btnLogOut;
    ImageView imgFindRide;
    ImageView imgCreateRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toast.makeText(HomeActivity.this,"Database connection succes",Toast.LENGTH_LONG).show();
        imgCreateRide = findViewById(R.id.add_ride_image);
        imgFindRide = findViewById(R.id.find_ride_image);
        btnLogOut = findViewById(R.id.btnLogOut);

        imgFindRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,FindRideActivity.class);
                startActivity(i);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        imgCreateRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,CreateRideActivity.class);
                startActivity(i);
            }
        });


    }
}
