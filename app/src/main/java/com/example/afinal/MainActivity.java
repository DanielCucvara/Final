package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText emailEt;
    EditText password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mAuth = FirebaseAuth.getInstance();
        emailEt=findViewById(R.id.editTextMail);
        password=findViewById(R.id.editText2);
        btnSignUp=findViewById(R.id.button);
        tvSignIn=findViewById(R.id.textView);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()){
                    emailEt.setError("Please  enter valid emial");
                    emailEt.requestFocus();

                }
                else if(pwd.isEmpty()){
                    password.setError("Please enter valid passowrd");
                    password.requestFocus();
                }
                else if (pwd.isEmpty() && email.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields are empty !!!",Toast.LENGTH_SHORT).show();
                }
                else if (!(pwd.isEmpty() && email.isEmpty())){
                    mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Registration unsuccessful !!!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }

                        }
                    });


                }
                else Toast.makeText(MainActivity.this,"Error ocurred",Toast.LENGTH_SHORT).show();

            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }
}
