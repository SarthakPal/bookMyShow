package com.lld.bookMyShow.model;

import java.util.List;

public class Cinema {
    // Data members
    private String cinemaId;
    private String cinemaName;
    private List<Hall> halls;
    private City city;

    public Cinema(String cinemaId, String cinemaName, City city)
    {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.city = city;
    }

    public void addHall(Hall hall)
    {
        this.halls.add(hall);
    }

}
