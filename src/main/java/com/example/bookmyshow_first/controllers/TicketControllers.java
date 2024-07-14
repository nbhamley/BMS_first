package com.example.bookmyshow_first.controllers;

import com.example.bookmyshow_first.dtos.BookTicketRequestDto;
import com.example.bookmyshow_first.dtos.BookTicketResponseDto;
import com.example.bookmyshow_first.dtos.Response;
import com.example.bookmyshow_first.exceptions.InvalidBookTicketRequestException;
import com.example.bookmyshow_first.models.Ticket;
import com.example.bookmyshow_first.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TicketControllers {

    private TicketService ticketService;

    @Autowired
    public TicketControllers(TicketService ticketService) {
        this.ticketService = ticketService;
    }


@RequestMapping(path = "/bookTicket")
    public BookTicketResponseDto bookTicket(BookTicketRequestDto requestDto) {
        BookTicketResponseDto responseDto = new BookTicketResponseDto();

        try{
            validateBookTicketRequest(requestDto);
            Ticket ticket = ticketService.bookTicket(requestDto.getUserId(), requestDto.getShowId(), requestDto.getShowSeatIds());
            Response response = Response.getSuccessResponse();
            responseDto.setResponse(response);
            responseDto.setTicket(ticket);
            return responseDto;
        }catch (Exception e){
            Response response = Response.getFailureResponse(e.getMessage());
            responseDto.setResponse(response);
        }
        return responseDto;
    }

    private static void validateBookTicketRequest(BookTicketRequestDto RequestDto) throws InvalidBookTicketRequestException {
        if(RequestDto.getShowId() <= 0){
            throw new InvalidBookTicketRequestException("Show id cannot be negative or Zero");
        }
        if(RequestDto.getUserId() <= 0){
            throw new InvalidBookTicketRequestException("User id cannot be negative or Zero");
        }
        if(RequestDto.getShowSeatIds() == null || RequestDto.getShowSeatIds().isEmpty()){
            throw new InvalidBookTicketRequestException("Seat ids should be present");
        }
    }
}
