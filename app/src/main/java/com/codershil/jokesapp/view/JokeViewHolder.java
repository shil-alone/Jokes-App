package com.codershil.jokesapp.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codershil.jokesapp.R;


public class JokeViewHolder extends RecyclerView.ViewHolder {
    private TextView jokeText;
    private ImageButton likeButton, shareButton;

    public JokeViewHolder(@NonNull View itemView) {
        super(itemView);
        jokeText = itemView.findViewById(R.id.jokeContent);
        likeButton = itemView.findViewById(R.id.likeButton);
        shareButton = itemView.findViewById(R.id.shareButton);
    }

    public TextView getJokeText() {
        return jokeText;
    }

    public ImageButton getLikeButton() {
        return likeButton;
    }

    public ImageButton getShareButton() {
        return shareButton;
    }
}
