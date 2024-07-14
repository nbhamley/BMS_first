package com.example.bookmyshow_first.services;

import com.example.bookmyshow_first.models.Ticket;
import java.util.List;

public interface TicketService {
    Ticket bookTicket(int userId, int showId, List<Integer> showSeatsIds) throws Exception;

}
