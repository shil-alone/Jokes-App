package com.codershil.jokesapp.controller;

import com.codershil.jokesapp.model.Joke;

public interface JokeLikeListener {
    void jokeIsLiked(Joke joke);
}
