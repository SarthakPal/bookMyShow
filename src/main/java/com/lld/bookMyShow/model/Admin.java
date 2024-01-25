package com.lld.bookMyShow.model;

import com.lld.bookMyShow.services.MovieService;
import com.lld.bookMyShow.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;

public class Admin extends User {
    // show here refers to an instance of the ShowTime class

    @Autowired
    private ShowService showService;

    @Autowired
    private MovieService movieService;

    public Show addShow(Show show)
    {
      return showService.createShow(show);
    }
    public boolean updateShow(Show show)
    {
        return true;
    }
    public boolean deleteShow(Show show)
    {
        return true;
    }
    public Movie addMovie(Movie movie)
    {
        return movieService.createMovie(movie);
    }
    public boolean deleteMovie(Movie movie)
    {
        return true;
    }
}
