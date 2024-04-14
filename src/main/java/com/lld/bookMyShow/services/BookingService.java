package com.lld.bookMyShow.services;

import com.lld.bookMyShow.enums.SeatStatus;
import com.lld.bookMyShow.exceptions.BadRequestException;
import com.lld.bookMyShow.exceptions.NotFoundException;
import com.lld.bookMyShow.exceptions.SeatPermanentlyUnavailableException;
import com.lld.bookMyShow.model.Booking;
import com.lld.bookMyShow.model.Seat;
import com.lld.bookMyShow.model.Show;
import com.lld.bookMyShow.providers.SeatLockProvider;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.*;

public class BookingService {

    private final Map<String, Booking> showBookings;
    private final SeatLockProvider seatLockProvider;
    private final PaymentsService paymentsService;
    private final ScheduledExecutorService expiryTimer = Executors.newScheduledThreadPool(1);

    public BookingService(SeatLockProvider seatLockProvider, PaymentsService paymentsService) {
        this.seatLockProvider = seatLockProvider;
        this.showBookings = new HashMap<>();
        this.paymentsService = paymentsService;
    }

    public Booking getBooking(@NonNull final String bookingId) {
        if (!showBookings.containsKey(bookingId)) {
            throw new NotFoundException();
        }
        return showBookings.get(bookingId);
    }

    public List<Booking> getAllBookings(@NonNull final Show show) {
        List<Booking> response = new ArrayList<>();
        for (Booking booking : showBookings.values()) {
            if (booking.getShow().equals(show)) {
                response.add(booking);
            }
        }

        return response;
    }

    public Booking createBooking(Booking booking) {
        if (isAnySeatAlreadyBooked(booking.getShow(), booking.getSeatsBooked())) {
            throw new SeatPermanentlyUnavailableException();
        }
        for(Seat seat : booking.getSeatsBooked())
        {
            seat.setStatus(SeatStatus.LOCKED);
        }
        seatLockProvider.lockSeats(booking.getShow(), booking.getSeatsBooked(), booking.getCustomerId());
        final String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);
        showBookings.put(bookingId, booking);
        // Schedule booking expiry in, for example, 15 minutes
        scheduleBookingExpiry(booking, 15, TimeUnit.MINUTES);
        return booking;
    }

    private void scheduleBookingExpiry(Booking booking, long delay, TimeUnit timeUnit) {
        expiryTimer.schedule(() -> {
            // Expire the booking logic goes here
            expireBooking(booking);
        }, delay, timeUnit);
    }

    private void expireBooking(Booking booking) {
        // Expire the booking logic, e.g., unlock seats and remove booking from showBookings
        showBookings.remove(booking.getBookingId());
        seatLockProvider.unlockSeats(booking.getShow(), booking.getSeatsBooked(), booking.getCustomerId());
        for(Seat seat : booking.getSeatsBooked())
        {
            seat.setStatus(SeatStatus.AVAILABLE);
        }
    }

    public List<Seat> getBookedSeats(@NonNull final Show show) {
        return getAllBookings(show).stream()
                .filter(Booking::isConfirmed)
                .map(Booking::getSeatsBooked)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public void confirmBooking(@NonNull final Booking booking, @NonNull final String user) {
        if (!booking.getCustomerId().equals(user)) {
            throw new BadRequestException();
        }

        for (Seat seat : booking.getSeatsBooked()) {
            if (!seatLockProvider.validateLock(booking.getShow(), seat, user)) {
                throw new BadRequestException();
            }
        }
        // Assuming makePayment() returns a boolean indicating payment success
        if (booking.getPayment().makePayment()) {
            // Update seat status to "booked"
            for (Seat seat : booking.getSeatsBooked()) {
                seat.setStatus(SeatStatus.BOOKED);
            }

            booking.confirmBooking();
            expiryTimer.shutdown(); // Cancel the expiry timer
        } else {
            paymentsService.processPaymentFailed(booking, user);
        }
    }

    private boolean isAnySeatAlreadyBooked(final Show show, final List<Seat> seats) {
        final List<Seat> bookedSeats = getBookedSeats(show);
        for (Seat seat : seats) {
            if (bookedSeats.contains(seat)) {
                return true;
            }
        }
        return false;
    }
}
