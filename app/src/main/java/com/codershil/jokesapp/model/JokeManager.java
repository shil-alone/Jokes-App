package com.codershil.jokesapp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JokeManager {

    private Context context;
    SharedPreferences sharedPreferences;

    public JokeManager(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveJoke(Joke joke) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(joke.getJokeText(), joke.isJokeLiked());
        editor.apply();
    }

    public void deleteJoke(Joke joke) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(joke.getJokeText());
        editor.apply();
    }

    public List<Joke> retrieveJokes() {
        Map<String, ?> data = sharedPreferences.getAll();
        List<Joke> jokeList = new ArrayList<>();

        for (Map.Entry<String, ?> entry : data.entrySet()) {
            Joke joke = new Joke(entry.getKey(), (boolean) entry.getValue());

            if (entry.getKey().matches("variations_seed_native_stored")) {
                continue;
            }
            jokeList.add(joke);
        }

        return jokeList;
    }


}
