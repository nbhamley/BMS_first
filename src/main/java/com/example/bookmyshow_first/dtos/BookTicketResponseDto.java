package com.example.bookmyshow_first.dtos;

import com.example.bookmyshow_first.models.Ticket;
import lombok.Data;

@Data
public class BookTicketResponseDto {
    private Response response;
    private Ticket ticket;
}
