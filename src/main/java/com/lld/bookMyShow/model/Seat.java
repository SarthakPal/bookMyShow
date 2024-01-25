package com.lld.bookMyShow.model;

import com.lld.bookMyShow.enums.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    // Data members
    private String seatId;
    private Integer seatNo;
    private Integer rowNo;
    private SeatType seatType;
    private SeatStatus status; // Refers to the SeatStatus enum

    // Member functions
    public boolean isAvailable()
    {
        return true;
    }


}
