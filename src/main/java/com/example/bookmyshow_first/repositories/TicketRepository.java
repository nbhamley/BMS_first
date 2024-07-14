package com.example.bookmyshow_first.repositories;

import com.example.bookmyshow_first.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
