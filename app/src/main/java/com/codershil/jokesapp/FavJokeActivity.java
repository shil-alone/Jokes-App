package com.codershil.jokesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.codershil.jokesapp.fragments.FavJokeFragment;

public class FavJokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_joke);

        FavJokeFragment favJokeFragment = FavJokeFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fav_joke_container, favJokeFragment).commit();
    }
}