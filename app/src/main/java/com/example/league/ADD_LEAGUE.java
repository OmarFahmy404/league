package com.example.league;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class ADD_LEAGUE extends AppCompatActivity {
    EditText phoneNo, personeName, leagueName, email, location,price;
    Button save;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    FirebaseDatabase database;
    ImageView imageView;
    private DatabaseReference myRef=FirebaseDatabase.getInstance().getReference().child("new league");
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    private Uri imageuri;

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
        imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosepic();
            }
        });

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
                    Toast.makeText(ADD_LEAGUE.this, "Some Detials Missed", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ADD_LEAGUE.this, "Your Detials Add Sucessfully", Toast.LENGTH_SHORT).show();

                    StorageReference fileref=storageReference.child(System.currentTimeMillis() + "." + getFileExtention(imageuri));
                    fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                          public void onSuccess(Uri uri) {

                            HashMap<String,Object> map=new HashMap<>();
                            map.put("person name",txt_personName);
                            map.put("league name",txt_leagueName);
                            map.put("phone number",txt_phoneNo);
                            map.put("location",txt_location);
                            map.put("price",txt_price);
                            map.put("email",txt_email);
                            String image=uri.toString();
                            map.put("image",image);
                            myRef.push().setValue(map);
                           // FirebaseDatabase.getInstance().getReference().child("new league").push().setValue(map);
                        }
                        });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                    Intent ii= new Intent(ADD_LEAGUE.this,MainActivity.class);
                    startActivity(ii);






                    // FirebaseDatabase.getInstance().getReference().child("new ground").push().setValue(map);

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


    private void choosepic() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,101);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode==101 && resultCode==RESULT_OK && data!=null)){
            imageuri=data.getData();
            imageView.setImageURI(imageuri);
        }
    }
    private String getFileExtention(Uri mUri){
        ContentResolver cr= getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(mUri));

    }
}

