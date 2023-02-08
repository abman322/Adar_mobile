package com.example.adar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    MyHomesFragment myHomesFragment = new MyHomesFragment();
    RentedHomesFragment rentedHomesFragment = new RentedHomesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar().hide(); // Hides the action bar

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
               switch (item.getItemId()){
                   case R.id.home:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                       return true;
                   case R.id.rentedHomes:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container, rentedHomesFragment).commit();
                       return true;
                   case R.id.yourHomes:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container, myHomesFragment).commit();
                       return true;
                   case R.id.profile:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                       return true;

               }
               return false;
            }
        });
    }
}