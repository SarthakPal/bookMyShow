package com.lld.bookMyShow.model;

import com.lld.bookMyShow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Customer extends User {

    @Autowired
    private BookingService bookingService;
    private List<Booking> bookings; // List of bookings

    // booking here refers to an instance of the Booking class
    public Booking createBooking(Booking booking)
    {
        return bookingService.createBooking(booking);
    }
    public boolean updateBooking(Booking booking)
    {
        return true;
    }
    public boolean deleteBooking(Booking booking)
    {
        return true;
    }
}
