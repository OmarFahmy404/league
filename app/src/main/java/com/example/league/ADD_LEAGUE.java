package com.example.league;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ADD_LEAGUE extends AppCompatActivity {
    EditText phoneNo, personeName, leagueName, email, location,price;
    Button save;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_d_d__l_e_a_g_u_e);
        final ActionBar actionBar = getSupportActionBar();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        personeName=findViewById(R.id.personeName);
        phoneNo=findViewById(R.id.phoneNo);
        leagueName=findViewById(R.id.leagueName);
        email=findViewById(R.id.email);
        location=findViewById(R.id.location);
        price=findViewById(R.id.price);
        save=findViewById(R.id.save);
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        //myRef.setValue("Hello, World!");


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


// Write a message to the database
       // database = FirebaseDatabase.getInstance();
          //myRef = database.getReference();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_personName=personeName.getText().toString();
                String txt_phoneNo=phoneNo.getText().toString();
                String txt_location=location.getText().toString();
                String txt_leagueName=leagueName.getText().toString();
                String txt_price=price.getText().toString();
                String txt_email=email.getText().toString();
                if (txt_email.isEmpty() || txt_leagueName.isEmpty() || txt_location.isEmpty() ||txt_personName.isEmpty() ||
                        txt_phoneNo.isEmpty() || txt_price.isEmpty())
                {
                    Toast.makeText(ADD_LEAGUE.this, "Some Detials Missing", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ADD_LEAGUE.this, "Your Detials Add Sucessfully", Toast.LENGTH_SHORT).show();

                    HashMap<String,Object> map=new HashMap<>();
                map.put("person name",txt_personName);
                map.put("league name",txt_leagueName);
                map.put("phone number",txt_phoneNo);
                map.put("location",txt_location);
                map.put("price",txt_price);
                map.put("email",txt_email);
                FirebaseDatabase.getInstance().getReference().child("new league").push().setValue(map);

            }}
        });





        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

