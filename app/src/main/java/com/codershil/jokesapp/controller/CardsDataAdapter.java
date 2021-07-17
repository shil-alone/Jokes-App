package com.codershil.jokesapp.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codershil.jokesapp.R;
import com.codershil.jokesapp.model.Joke;
import com.codershil.jokesapp.view.JokeViewHolder;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

public class CardsDataAdapter extends RecyclerView.Adapter<JokeViewHolder> {
    private Context context;
    private List<Joke> jokeList;
    private LayoutInflater layoutInflater;
    private boolean isLiked = true;
    private JokeLikeListener jokeLikeListener;
    private SharedPreferences sharedPreferences;

    public CardsDataAdapter(Context context, List<Joke> jokeList, JokeLikeListener jokeLikeListener) {
        this.context = context;
        this.jokeList = jokeList;
        layoutInflater = LayoutInflater.from(context);
        this.jokeLikeListener = jokeLikeListener;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @NonNull
    @Override
    public JokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.joke_card, parent, false);
        return new JokeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeViewHolder holder, int position) {
        if (sharedPreferences.contains(jokeList.get(position).getJokeText())) {
            holder.getLikeButton().setImageResource(R.drawable.like_filled);
            isLiked = false;
        } else {
            isLiked = true;
        }


        holder.getJokeText().setText(jokeList.get(position).getJokeText());
        holder.getLikeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Joke joke = jokeList.get(position);
                if (isLiked) {
                    holder.getLikeButton().setImageResource(R.drawable.like_filled);
                    joke.setJokeLiked(true);
                    isLiked = false;
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(holder.getLikeButton());

                } else {
                    holder.getLikeButton().setImageResource(R.drawable.like_empty);
                    joke.setJokeLiked(false);
                    isLiked = true;
                }
                jokeLikeListener.jokeIsLiked(joke);
            }
        });

        holder.getShareButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jokeText = jokeList.get(position).getJokeText();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "JOKE");
                intent.putExtra(Intent.EXTRA_TEXT, jokeText);
                context.startActivity(Intent.createChooser(intent, "Share Joke Using..."));
            }
        });
    }

    @Override
    public int getItemCount() {
        return jokeList.size();
    }
}
