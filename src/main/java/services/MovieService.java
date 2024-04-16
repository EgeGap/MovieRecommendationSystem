package com.ege.app.rest.services;

import com.ege.app.rest.model.Movie;
import com.ege.app.rest.repositories.MovieRepository;

import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void saveMovieSelection(Movie movie) {
        movieRepository.save(movie);
    }
}