package com.lld.bookMyShow.model;

public class TicketAgent extends User {
    // booking here refers to an instance of the Booking class
    public Booking createBooking(Booking booking)
    {
        return new Booking();
    }
}
