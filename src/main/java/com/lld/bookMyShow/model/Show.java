package com.lld.bookMyShow.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class Show {
    // Data members
    private String showId;
    private Movie movie;
    private Hall hall;

    // The Date datatype represents and deals with both date and time
    private LocalDateTime startTime;
    private Date date;
    private int duration;

    public Show(String showId, Movie movie, Hall hall, LocalDateTime startTime, int duration)
    {
        this.showId = showId;
        this.movie = movie;
        this.hall = hall;
        this.startTime = startTime;
        this.duration = duration;
    }

    // Displays the list of available seats
    public void showAvailableSeats()
    {

    }

}
