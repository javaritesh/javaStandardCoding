package com.example.tradestore.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
public class TradeStoreException extends RuntimeException {
    private ErrorStatusCode statusCode;

    public TradeStoreException(String errorMessage, ErrorStatusCode statusCode) {
        super(errorMessage);
        this.statusCode = statusCode;
    }

}
