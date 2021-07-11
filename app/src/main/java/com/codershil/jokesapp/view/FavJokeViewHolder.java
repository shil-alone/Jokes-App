package com.codershil.jokesapp.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codershil.jokesapp.R;

public class FavJokeViewHolder extends RecyclerView.ViewHolder {
    private TextView txtJokeText;
    private ImageButton btnShare;
    public FavJokeViewHolder(@NonNull View itemView) {
        super(itemView);
        txtJokeText = itemView.findViewById(R.id.txtFavJoke);
        btnShare = itemView.findViewById(R.id.favShareButton);
    }

    public TextView getTxtJokeText() {
        return txtJokeText;
    }

    public ImageButton getBtnShare() {
        return btnShare;
    }
}
