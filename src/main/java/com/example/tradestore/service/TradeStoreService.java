package com.example.tradestore.service;

import com.example.tradestore.dto.TradeStoreDto;
import com.example.tradestore.exception.ErrorStatusCode;
import com.example.tradestore.exception.TradeStoreException;
import com.example.tradestore.repository.TradeStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TradeStoreService {

    public Boolean insertTradeData(TradeStoreDto currentTrade){
        log.info(MessageFormat.format("Starting trade Insertion for tradeId: {0}", currentTrade.getTradeId()));
        Boolean isUpdate = Boolean.FALSE;
        Boolean isSaveSuccessfully;

        log.info("Fetching old trades by trade Id and Max Version");
        Optional<TradeStoreDto> oldTrades = TradeStoreRepository.getDataByTradeIdAndMaxVersion(currentTrade.getTradeId());

        if(oldTrades.isPresent()){
            Integer oldVersion = oldTrades.get().getVersion();
            if(currentTrade.getVersion() < oldVersion){
                throw new TradeStoreException("Exception occurred saving the new trade", ErrorStatusCode.LOWER_VERSION);
            } else if(currentTrade.getVersion().equals(oldVersion)){
                log.info("Setting update flag as true in case of same version is found for given trade");
                isUpdate = Boolean.TRUE;
            }
        }

        log.info("Checking trade maturity date comparing to local date");
        if(currentTrade.getMaturityDate().isAfter(LocalDate.now())){
            log.info("Saving/updating the trade");
            if(isUpdate){
                isSaveSuccessfully = TradeStoreRepository.updateTrade(currentTrade);
            } else {
                isSaveSuccessfully = TradeStoreRepository.saveTrade(currentTrade);
            }
        } else {
            throw new TradeStoreException("Exception occurred saving the new trade", ErrorStatusCode.MATURITY_DATE);
        }

        return isSaveSuccessfully;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public Boolean updateExpiryFlag(){
        log.info(MessageFormat.format("Updating the expiry flag for eligible trades: {0}", LocalDate.now()));
        return TradeStoreRepository.updateExpiryFlag();
    }

    public List<TradeStoreDto> loadData(List<TradeStoreDto> trades) {
        return TradeStoreRepository.loadData(trades);
    }
}
