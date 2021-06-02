package com.example.league;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileActivity extends AppCompatActivity {
    private String recevierleague;
    private ImageView image;
    private TextView profileleaguename,profilelocation,profileprice,profileemail,profileusername,profilephone;
    private DatabaseReference userref;
    private static final int    REQUEST_CALL=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userref= FirebaseDatabase.getInstance().getReference().child("new league");


        recevierleague=getIntent().getExtras().get("visite_league_details").toString();

        image=findViewById(R.id.leagueimage);
        profilephone=findViewById(R.id.P);
        profileleaguename= findViewById(R.id.LN);
        profilelocation=findViewById(R.id.L);
        profileprice=findViewById(R.id.PTJ);
        profileemail=findViewById(R.id.E);
        profileusername=findViewById(R.id.UN);
        
        
        RetriveInformation();

    }

    private void RetriveInformation() {
        userref.child(recevierleague).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    String LEAGUENAME=snapshot.child("league name").getValue().toString();
                    String LOCATION=snapshot.child("location").getValue().toString();
                    String USERNAME=snapshot.child("person name").getValue().toString();
                    String PHONE=snapshot.child("phone number").getValue().toString();
                    String PRICE=snapshot.child("price").getValue().toString();
                    String EMAIL=snapshot.child("email").getValue().toString();

                    profileemail.setText(EMAIL);
                    profileleaguename.setText(LEAGUENAME);
                    profilelocation.setText(LOCATION);
                    profilephone.setText(PHONE);
                    profileprice.setText(PRICE);
                    profileusername.setText(USERNAME);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.call:
//makephonecall();

                Intent  intent=new Intent();
              intent.setAction(Intent.ACTION_CALL);
              intent.setData(Uri.parse("tel:"+profilephone.getText()));

              startActivity(intent);

                break;
        }



        return super.onOptionsItemSelected(item);

    }

    /*private void makephonecall() {
        if(ContextCompat.checkSelfPermission(profileActivity.this,
                Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(profileActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE} ,REQUEST_CALL);
        }else {
            String dail = "tel"+profilephone.getText();
            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dail)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode== REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makephonecall();
            }else {
                Toast.makeText(this, "permission denayed", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}