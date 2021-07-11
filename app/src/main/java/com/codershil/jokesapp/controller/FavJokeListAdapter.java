package com.codershil.jokesapp.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codershil.jokesapp.R;
import com.codershil.jokesapp.model.Joke;
import com.codershil.jokesapp.view.FavJokeViewHolder;

import java.util.List;

public class FavJokeListAdapter extends RecyclerView.Adapter<FavJokeViewHolder> {
    private List<Joke> jokesList;
    private Context context;

    public Context getContext(){
        return context;
    }

    public FavJokeListAdapter(List<Joke> jokesList, Context context) {
        this.jokesList = jokesList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavJokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_joke_item,parent,false);
        return new FavJokeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavJokeViewHolder holder, int position) {
        String jokeText = jokesList.get(position).getJokeText();
        holder.getTxtJokeText().setText(jokeText);

        holder.getBtnShare().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jokeText = jokesList.get(position).getJokeText();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"JOKE");
                intent.putExtra(Intent.EXTRA_TEXT,jokeText);
                context.startActivity(Intent.createChooser(intent,"Share Joke Using..."));
            }
        });
    }

    @Override
    public int getItemCount() {
        return jokesList.size();
    }
}
