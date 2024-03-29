package com.example.jewellery_shop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.jewellery_shop.R;
import com.example.jewellery_shop.fragments.home.HomeFragment;

public class MainActivity2 extends AppCompatActivity {
    Fragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        homeFragment=new HomeFragment();
        loadFragment(homeFragment);

    }

    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.commit();
    }
}