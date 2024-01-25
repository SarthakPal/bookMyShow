package com.lld.bookMyShow.services;

import com.lld.bookMyShow.exceptions.NotFoundException;
import com.lld.bookMyShow.exceptions.ScreenAlreadyOccupiedException;
import com.lld.bookMyShow.model.Hall;
import com.lld.bookMyShow.model.Movie;
import com.lld.bookMyShow.model.Show;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.*;

public class ShowService {

    private final Map<String, Show> shows;

    public ShowService() {
        this.shows = new HashMap<>();
    }

    public Show getShow(@NonNull final String showId) {
        if (!shows.containsKey(showId)) {
            throw new NotFoundException();
        }
        return shows.get(showId);
    }

    public Show createShow(Show show) {
        if (!checkIfShowCreationAllowed(show.getHall(), show.getStartTime(), show.getDuration())) {
            throw new ScreenAlreadyOccupiedException();
        }
        String showId = UUID.randomUUID().toString();
        show.setShowId(showId);
        shows.put(showId, show);
        show.getMovie().addShow(show);
        return show;
    }

    private List<Show> getShowsForScreen(final Hall hall) {
        final List<Show> response = new ArrayList<>();
        for (Show show : shows.values()) {
            if (show.getHall().equals(hall)) {
                response.add(show);
            }
        }
        return response;
    }

    private boolean checkIfShowCreationAllowed(final Hall hall, final LocalDateTime startTime, final Integer durationInSeconds) {
        // TODO: Implement this. This method will return whether the screen is free at a particular time for
        // specific duration. This function will be helpful in checking whether the show can be scheduled in that slot
        // or not.
        return true;
    }
}
