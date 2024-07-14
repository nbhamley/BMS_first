package com.example.bookmyshow_first.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name ="show_seats")
public class ShowSeat extends BaseModel{
    @ManyToOne
    private Show show;
    @ManyToOne
    private Seat seat;
    @Enumerated(value = EnumType.STRING)
    private SeatStatus  seatStatus;
    @ManyToOne
    private User bookedBy;
}
