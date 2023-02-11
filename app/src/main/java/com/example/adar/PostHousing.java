package com.example.adar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostHousing extends AppCompatActivity {

    private EditText mPostHousingCity, mPostHousingCountry, mPostHousingPrice, mPostHousingImageURL, mPostHousingDescription;
    private Button mPostHousingSubmit;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_housing);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPostHousingCity = findViewById(R.id.postHousingCity);
        mPostHousingCountry = findViewById(R.id.postHousingCountry);
        mPostHousingPrice = findViewById(R.id.postHousingPrice);
        mPostHousingImageURL = findViewById(R.id.postHousingImageURL);
        mPostHousingDescription = findViewById(R.id.postHousingDescription);
        mPostHousingSubmit = findViewById(R.id.postHousingSubmit);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mPostHousingSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = mPostHousingCity.getText().toString();
                String country = mPostHousingCountry.getText().toString();
                String price = mPostHousingPrice.getText().toString();
                String imageUrl = mPostHousingImageURL.getText().toString();
                String description = mPostHousingDescription.getText().toString();

                if (city.isEmpty() || country.isEmpty() || price.isEmpty() || imageUrl.isEmpty() || description.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
                }else{
                    DocumentReference documentReference = mStore.collection("houses").document(mUser.getUid()).collection("myHomes").document();
                    Map<String, Object> Home = new HashMap<>();
                    Home.put("city", city);
                    Home.put("country",country);
                    Home.put("price",price);
                    Home.put("imageUrl",imageUrl);
                    Home.put("description",description);

                    documentReference.set(Home).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "House Posted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PostHousing.this, HomePage.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to post house!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}