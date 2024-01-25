package com.lld.bookMyShow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private String id;
    private String title;
    private List<Show> shows;

    public void addShow(Show show)
    {
        this.shows.add(show);
    }

}
