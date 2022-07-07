package com.example.tradestore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeStoreDto {
    private String tradeId;

    private Integer version;

    private String counterPartyId;

    private String bookId;

    private LocalDate maturityDate;

    private LocalDate createDate;

    private String expired;
}
