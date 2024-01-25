package com.lld.bookMyShow.model;

import com.lld.bookMyShow.enums.BookingStatus;
import com.lld.bookMyShow.exceptions.InvalidStateException;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Booking {
    // Data members
    private String bookingId;
    private String customerId;
    private int amount;
    private int totalSeats;

    // The Date datatype represents and deals with both date and time.
    private Date createdOn;

    // BookingStatus enum
    private BookingStatus bookingStatus;

    // Instances of classes
    private Payment payment;
    private Show show;
    private List<MovieTicket> tickets;
    private List<Seat> seatsBooked;

    public boolean isConfirmed() {
        return this.bookingStatus == BookingStatus.CONFIRMED;
    }

    public void confirmBooking() {
        if (this.bookingStatus != BookingStatus.CREATED) {
            throw new InvalidStateException();
        }
        this.bookingStatus = BookingStatus.CONFIRMED;
    }

    public void expireBooking() {
        if (this.bookingStatus != BookingStatus.CREATED) {
            throw new InvalidStateException();
        }
        this.bookingStatus = BookingStatus.EXPIRED;
    }

}
