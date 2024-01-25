package com.lld.bookMyShow.model;

import lombok.Data;

import java.util.List;

@Data
public class Hall {
    // Data members
    private String hallId;
    private String name;
    private List<Seat> seats;
    private Cinema cinema;

    // Returns list of shows
    public List<Show> findCurrentShows()
    {
        return null;
    }

    public void addSeats(Seat seat)
    {
        this.seats.add(seat);
    }

}
