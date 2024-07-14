package com.example.bookmyshow_first.exceptions;

public class InvalidBookTicketRequestException extends Exception {
    public InvalidBookTicketRequestException(String message) {
        super(message);
    }
}
