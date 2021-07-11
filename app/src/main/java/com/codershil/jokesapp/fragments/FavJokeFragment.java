package com.codershil.jokesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codershil.jokesapp.R;
import com.codershil.jokesapp.controller.FavJokeListAdapter;
import com.codershil.jokesapp.model.Joke;
import com.codershil.jokesapp.model.JokeManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FavJokeFragment extends Fragment {


    RecyclerView recyclerView;
    FavJokeListAdapter favJokeListAdapter;
    JokeManager jokeManager;
    List<Joke> jokeList = new ArrayList<>();

    private Joke deletedJoke;

    public FavJokeFragment() {
        // Required empty public constructor
    }

    public static FavJokeFragment newInstance() {
        FavJokeFragment fragment = new FavJokeFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        jokeManager = new JokeManager(getContext());
        jokeList.clear();
        if (jokeManager.retrieveJokes().size() > 0) {
            jokeList.addAll(jokeManager.retrieveJokes());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_fav_joke, container, false);
        if (view != null) {
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            favJokeListAdapter = new FavJokeListAdapter(jokeList, getContext());
            recyclerView.setAdapter(favJokeListAdapter);

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);


        }
        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                case ItemTouchHelper.RIGHT:

                    deletedJoke = jokeList.get(position);
                    jokeManager.deleteJoke(jokeList.get(position));
                    jokeList.remove(position);
                    favJokeListAdapter.notifyItemRemoved(position);

                    Snackbar.make(recyclerView, "Joke is\"Removed\"", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    jokeList.add(position, deletedJoke);
                                    jokeManager.saveJoke(deletedJoke);
                                    favJokeListAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;
            }
        }
    };
}