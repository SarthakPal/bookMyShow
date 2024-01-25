package com.lld.bookMyShow.services;

import com.lld.bookMyShow.enums.SeatStatus;
import com.lld.bookMyShow.exceptions.NotFoundException;
import com.lld.bookMyShow.model.*;
import lombok.NonNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CinemaService {

    private final Map<String, Cinema> cinemas;
    private final Map<String, Hall> halls;
    private final Map<String, Seat> seats;

    public CinemaService() {
        this.cinemas = new HashMap<>();
        this.halls = new HashMap<>();
        this.seats = new HashMap<>();
    }

    public Seat getSeat(@NonNull final String seatId) {
        if (!seats.containsKey(seatId)) {
            throw new NotFoundException();
        }
        return seats.get(seatId);
    }

    public Cinema getCinema(@NonNull final String theatreId) {
        if (!cinemas.containsKey(theatreId)) {
            throw new NotFoundException();
        }
        return cinemas.get(theatreId);
    }

    public Hall getHall(@NonNull final String hallId) {
        if (!halls.containsKey(hallId)) {
            throw new NotFoundException();
        }
        return halls.get(hallId);
    }

    public Cinema createCinema(@NonNull final String cinemaName, @NonNull final City city) {
        String cinemaId = UUID.randomUUID().toString();
        Cinema cinema = new Cinema(cinemaId, cinemaName, city);
        cinemas.put(cinemaId, cinema);
        return cinema;
    }

    public Hall createHallInCinemas(@NonNull final Hall hall, @NonNull final Cinema cinema) {
        Hall hallInCinema = createHall(hall);
        cinema.addHall(hallInCinema);
        return hall;
    }

    public Seat createSeatInHall(@NonNull final Integer rowNo, @NonNull final Integer seatNo,
                                 @NonNull final Hall hall, @NonNull final SeatType seatType) {
        String seatId = UUID.randomUUID().toString();
        Seat seat = new Seat(seatId, rowNo, seatNo, seatType, SeatStatus.AVAILABLE);
        seats.put(seatId, seat);
        hall.addSeats(seat);
        return seat;
    }

    private Hall createHall(Hall hall) {
        String hallId = UUID.randomUUID().toString();
        hall.setHallId(hallId);
        halls.put(hallId, hall);
        return hall;
    }

}
