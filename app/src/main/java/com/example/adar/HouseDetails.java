package com.example.adar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import io.reactivex.rxjava3.annotations.NonNull;

public class HouseDetails extends AppCompatActivity {

    private ImageView mDetailsHousingImage;
    private TextView mDetailsHousingLocation, mDetailsHousingDescription, mDetailsHousingPrice;
    private Button mDetailsHousingBookNow, mDetailsHousingEdit,mDetailsHousingDelete;
    private LinearLayout mDetailsHousingYourHouse,mDetailsHousingNotYourHouse;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);

        mDetailsHousingBookNow=findViewById(R.id.detailsHousingBookNow);
        mDetailsHousingDelete=findViewById(R.id.detailsHousingDelete);
        mDetailsHousingEdit=findViewById(R.id.detailsHousingEdit);
        mDetailsHousingDescription=findViewById(R.id.detailsHousingDescription);
        mDetailsHousingImage=findViewById(R.id.detailsHousingImage);
        mDetailsHousingLocation=findViewById(R.id.detailsHousingLocation);
        mDetailsHousingPrice=findViewById(R.id.detailsHousingPrice);
        mDetailsHousingYourHouse=findViewById(R.id.detailsHousingYourHouse);
        mDetailsHousingNotYourHouse=findViewById(R.id.detailsHousingNotYourHouse);

        mStore = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();

        if(data.getStringExtra("isOwnHouse").equals("false")){
            mDetailsHousingYourHouse.setVisibility(View.GONE);
        }else{
            mDetailsHousingNotYourHouse.setVisibility(View.GONE);
        }

        mDetailsHousingEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditHousing.class);
                intent.putExtra("houseId", data.getStringExtra("houseId"));
                intent.putExtra("city",data.getStringExtra("city") );
                intent.putExtra("country",data.getStringExtra("country"));
                intent.putExtra("price",data.getStringExtra("price"));
                intent.putExtra("description",data.getStringExtra("description"));
                intent.putExtra("phone",data.getStringExtra("phone"));
                intent.putExtra("imageUrl", data.getStringExtra("imageUrl"));

                view.getContext().startActivity(intent);
            }
        });

        mDetailsHousingDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = mStore.collection("houses").document(mUser.getUid()).collection("myHomes").document(data.getStringExtra("houseId"));
                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "House is Deleted.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to delete house.",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mDetailsHousingBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+data.getStringExtra("phone")));//change the number
                startActivity(callIntent);
            }
        });

        mDetailsHousingLocation.setText(data.getStringExtra("city") + " - "+data.getStringExtra("country"));
        mDetailsHousingDescription.setText(data.getStringExtra("description"));
        mDetailsHousingPrice.setText("$"+data.getStringExtra("price")+" /night");
        Picasso.get().load(data.getStringExtra("imageUrl")).into(mDetailsHousingImage);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}