package com.example.tradestore.repository;

import com.example.tradestore.dto.TradeStoreDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TradeStoreRepository {
    public static List<TradeStoreDto> trades = new ArrayList<>();

    public static List<TradeStoreDto> loadData(List<TradeStoreDto> tradeList){
        trades.addAll(tradeList);
        return trades;
    }

    public static Optional<TradeStoreDto> getDataByTradeIdAndMaxVersion(String tradeId){
        return trades.stream().filter(x->x.getTradeId().equalsIgnoreCase(tradeId))
                .max(Comparator.comparing(TradeStoreDto::getVersion));
    }

    public static Boolean saveTrade(TradeStoreDto trade){
        trades.add(trade);
        return Boolean.TRUE;
    }

    public static Boolean updateTrade(TradeStoreDto currentTrade) {
        for (int i = 0; i < trades.size(); i++) {
            TradeStoreDto trade = trades.get(i);
            if(trade.getTradeId().equalsIgnoreCase(currentTrade.getTradeId())
            && trade.getVersion().equals(currentTrade.getVersion())){
                trades.set(i, currentTrade);
                break;
            }
        }
        return Boolean.TRUE;
    }

    public static Boolean updateExpiryFlag(){
        for (int i = 0; i < trades.size(); i++) {
            TradeStoreDto trade = trades.get(i);
            if(trade.getMaturityDate().isBefore(LocalDate.now())){
                trades.set(i, TradeStoreDto.builder()
                        .tradeId(trade.getTradeId()).version(trade.getVersion()).counterPartyId(trade.getCounterPartyId())
                        .bookId(trade.getBookId()).maturityDate(trade.getMaturityDate()).createDate(trade.getCreateDate())
                        .expired("Y").build());
            }
        }
        return Boolean.TRUE;
    }
}
