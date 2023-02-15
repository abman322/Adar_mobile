package com.example.adar;

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

import io.reactivex.rxjava3.annotations.NonNull;

public class EditHousing extends AppCompatActivity {

    Intent data;
    private EditText mEditHousingCity, mEditHousingCountry,mEditHousingPhone,mEditHousingDescription,mEditHousingPrice,mEditHousingImageURL;
    private Button mEditHousingSubmit;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_housing);

        mEditHousingCity=findViewById(R.id.editHousingCity);
        mEditHousingCountry=findViewById(R.id.editHousingCountry);
        mEditHousingDescription=findViewById(R.id.editHousingDescription);
        mEditHousingPhone=findViewById(R.id.editHousingPhone);
        mEditHousingPrice=findViewById(R.id.editHousingPrice);
        mEditHousingImageURL=findViewById(R.id.editHousingImageURL);
        mEditHousingSubmit=findViewById(R.id.editHousingSubmit);
        data=getIntent();

        mStore = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mEditHousingSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newCity = mEditHousingCity.getText().toString();
                String newCountry  = mEditHousingCountry.getText().toString();
                String newDescription = mEditHousingDescription.getText().toString();
                String newPhone = mEditHousingPhone.getText().toString();
                String newPrice = mEditHousingPrice.getText().toString();
                String newImageURL = mEditHousingImageURL.getText().toString();

                if(newCity.isEmpty() || newCountry.isEmpty() || newImageURL.isEmpty() || newDescription.isEmpty() || newPhone.isEmpty() || newPrice.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required!",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    DocumentReference documentReference = mStore.collection("houses").document(mUser.getUid()).collection("myHomes").document(data.getStringExtra("houseId"));
                    Map<String,Object> home = new HashMap<>();
                    home.put("city", newCity);
                    home.put("country",newCountry);
                    home.put("price",newPrice);
                    home.put("imageUrl",newImageURL);
                    home.put("description",newDescription);
                    home.put("phone",newPhone);

                    documentReference.set(home).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "House is Updated.",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@androidx.annotation.NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to Update.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


        mEditHousingCity.setText(data.getStringExtra("city"));
        mEditHousingCountry.setText(data.getStringExtra("country"));
        mEditHousingDescription.setText(data.getStringExtra("description"));
        mEditHousingPhone.setText(data.getStringExtra("phone"));
        mEditHousingPrice.setText(data.getStringExtra("price"));
        mEditHousingImageURL.setText(data.getStringExtra("imageUrl"));



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}