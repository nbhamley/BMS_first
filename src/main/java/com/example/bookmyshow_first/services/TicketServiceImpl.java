package com.example.bookmyshow_first.services;

import com.example.bookmyshow_first.exceptions.InvalidBookTicketRequestException;
import com.example.bookmyshow_first.exceptions.SeatsUnavailableException;
import com.example.bookmyshow_first.models.*;
import com.example.bookmyshow_first.repositories.ShowRepository;
import com.example.bookmyshow_first.repositories.ShowSeatRepository;
import com.example.bookmyshow_first.repositories.TicketRepository;
import com.example.bookmyshow_first.repositories.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Ticket bookTicket(int userId, int showId, List<Integer> showSeatsIds) throws Exception {

        /*
           1. Check if userid is valid
           2. show_id in showSeatIds and given show_id should match
           3. start transaction(SERIALIZABLE)
           4. select * from show_seats where id in (showSeatIds) and seat_status = 'Available' FOR UPDATE
           5. if all seats are not available
           6. throw error and rollback the transaction
           7. update show_seats set seat_Status = 'BLOCKED' where id in (ShowSeatIds)
           8. Generate ticket object
           9. Store ticket object in DB and return
         */

//        Optional<User> userOptional = this.userRepository.findById(userId);
//        User user;
//        if(userOptional.isPresent()) {
//            user = userOptional.get();
//        }   else {
//            throw new InvalidBookTicketRequestException("User is invalid");
//        }

       User user = this.userRepository.findById(userId).orElseThrow(() -> new InvalidBookTicketRequestException("User is invalid."));
       Show show = this.showRepository.findById(showId).orElseThrow(() -> new InvalidBookTicketRequestException("Show id is invalid."));
       ShowSeat showSeat= this.showSeatRepository.findById(showSeatsIds.get(0)).orElseThrow(() -> new InvalidBookTicketRequestException("Show Seat id is invalid."));

       if(showSeat.getShow().getId() != showId){
           throw new InvalidBookTicketRequestException("Given seats dont belong to the same show.");
       }

        List<ShowSeat> showSeats = this.showSeatRepository.findShowSeatsByIdInAndSeatStatus_AvailableAndAAndShow(showSeatsIds, show);

       // check if show seat size is equal
        if(showSeats.size() != showSeatsIds.size()){
            throw new SeatsUnavailableException("Selected seats are not available");
        }

        for(ShowSeat ss: showSeats){
            ss.setBookedBy(user);
            ss.setSeatStatus(SeatStatus.BLOCKED);
          //  showSeatRepository.save(ss); this will update one by one
        }

        showSeatRepository.saveAll(showSeats);

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setShowSeats(showSeats);
        ticket.setMovie(show.getMovie());

        return ticketRepository.save(ticket);

       // return null;
    }
}
