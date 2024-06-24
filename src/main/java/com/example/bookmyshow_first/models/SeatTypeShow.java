package com.example.bookmyshow_first.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "seat_type_shows")
public class SeatTypeShow extends BaseModel{
    private SeatType seatType;
    @ManyToOne
    private Show show;
    private double price;
}
