package com.example.bookmyshow_first.dtos;


public class Response {
    private ResponseStatus responseStatus;
    private String error;

    public Response(ResponseStatus responseStatus, String error) {
        this.responseStatus = responseStatus;
        this.error = error;
    }

    public static Response getFailureResponse(String error) {
        return new Response(ResponseStatus.FAILURE, error);
    }

    public static Response getSuccessResponse() {
        return new Response(ResponseStatus.SUCCESS, null);
    }
}
