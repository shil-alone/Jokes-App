package com.codershil.jokesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.codershil.jokesapp.controller.CardsDataAdapter;
import com.codershil.jokesapp.controller.JokeLikeListener;
import com.codershil.jokesapp.fragments.FavJokeFragment;
import com.codershil.jokesapp.model.Joke;
import com.codershil.jokesapp.model.JokeManager;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JokeLikeListener {


    CardStackView cardStackView;
    CardsDataAdapter adapter;
    ArrayList<Joke> jokesList = new ArrayList<>();
    JokeManager jokeManager;

    // following are used for shake detection
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jokeManager = new JokeManager(this);
        cardStackView = findViewById(R.id.myCardStackView);
        cardStackView.setLayoutManager(new CardStackLayoutManager(this));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer  = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                handleShakeEvent();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject rootJsonObject = new JSONObject(loadJSONFromAsset());
                    String[] jokesCategories = {"fat", "stupid", "ugly", "nasty",
                            "hairy", "bald", "old", "poor", "short", "skinny", "tall", "like"};
                    ArrayList<String> jokeCategoryList = new ArrayList<>(Arrays.asList(jokesCategories));
                    for (int i = 0; i < jokeCategoryList.size(); i++) {
                        JSONArray jokesArray = rootJsonObject.getJSONArray(jokeCategoryList.get(i));
                        addJokesToArrayList(jokesArray, jokesList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Collections.shuffle(jokesList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new CardsDataAdapter(MainActivity.this, jokesList, MainActivity.this);
                        cardStackView.setAdapter(adapter);
                    }
                });
            }
        }).start();

    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(shakeDetector,accelerometer,SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    private void handleShakeEvent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               Collections.shuffle(jokesList);
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       cardStackView.setAdapter(new CardsDataAdapter(MainActivity.this, jokesList, MainActivity.this));
                       adapter.notifyDataSetChanged();
                   }
               });
            }
        }).start();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("jokes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void addJokesToArrayList(JSONArray jsonArray, List<Joke> arrayList) {
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    arrayList.add(new Joke(jsonArray.getString(i), false));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void jokeIsLiked(Joke joke) {
        // if joke is already liked then delete this joke from database otherwise add new joke to database
        if (joke.isJokeLiked()) {
            jokeManager.saveJoke(joke);
        } else {
            jokeManager.deleteJoke(joke);
        }
    }

    // menu options

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(MainActivity.this, FavJokeActivity.class));
        return super.onOptionsItemSelected(item);
    }
}