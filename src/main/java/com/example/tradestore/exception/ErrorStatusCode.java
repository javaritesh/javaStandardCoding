package com.example.tradestore.exception;

public enum ErrorStatusCode {
    LOWER_VERSION("01", "Lower Version is being received by the store."),
    MATURITY_DATE("02", "Trade is having less maturity date then today's date."),
    UNEXPECTED("100", "Unexpected error occurred.");

    private final String code;
    private final String description;

    ErrorStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {return this.code;}
    public String getDescription() {return this.description;}
}
