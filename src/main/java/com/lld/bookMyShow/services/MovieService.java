package com.lld.bookMyShow.services;

import com.lld.bookMyShow.exceptions.NotFoundException;
import com.lld.bookMyShow.model.Movie;
import lombok.NonNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MovieService {

    private final Map<String, Movie> movies;

    public MovieService() {
        this.movies = new HashMap<>();
    }

    public Movie getMovie(@NonNull final String movieId) {
        if (!movies.containsKey(movieId)) {
            throw new NotFoundException();
        }
        return movies.get(movieId);
    }

    public Movie createMovie(Movie movie) {
        String movieId = UUID.randomUUID().toString();
        movie.setId(movieId);
        movies.put(movieId, movie);
        return movie;
    }

}
