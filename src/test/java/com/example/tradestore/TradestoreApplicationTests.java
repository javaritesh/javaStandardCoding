package com.example.tradestore;

import com.example.tradestore.dto.TradeStoreDto;
import com.example.tradestore.exception.TradeStoreException;
import com.example.tradestore.repository.TradeStoreRepository;
import com.example.tradestore.service.TradeStoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TradestoreApplicationTests {

	@Autowired
	TradeStoreService tradeStoreService;

	@Test()
	void shouldThrowExceptionWhenMaturityDateIsLessThenCurrentDate(){
		TradeStoreDto trade = TradeStoreDto.builder()
				.tradeId("T1").version(1).counterPartyId("CP-1").bookId("B1")
				.maturityDate(LocalDate.of(2020, 5, 20)).createDate(LocalDate.now()).expired("N").build();
                 boolean thrown = false;
		try {
			tradeStoreService.insertTradeData(trade);
		} catch (TradeStoreException e) {
			thrown = true;
		}

		assertTrue(thrown);
	}

	@Test
	void shouldLoadTradeLoadWhenDataIsEmptyAndAllValidationsMatched(){
		TradeStoreDto trade = TradeStoreDto.builder()
				.tradeId("T9").version(1).counterPartyId("CP-1").bookId("B1")
				.maturityDate(LocalDate.of(2023, 5, 20)).createDate(LocalDate.now()).expired("N").build();

		assertEquals(tradeStoreService.insertTradeData(trade), true);
	}

	@Test
	void shouldUpdateRecordWhenVersionIsSame(){
		initialLoadData();
		TradeStoreDto trade = TradeStoreDto.builder()
				.tradeId("T2").version(2).counterPartyId("CP-3").bookId("B2")
				.maturityDate(LocalDate.of(2023, 5, 20)).createDate(LocalDate.now()).expired("N").build();

		assertEquals(tradeStoreService.insertTradeData(trade), true);
	}

	@Test
	void shouldThrowExceptionWhenVersionIsLessThenCurrent(){
		initialLoadData();
		TradeStoreDto trade = TradeStoreDto.builder()
				.tradeId("T2").version(1).counterPartyId("CP-3").bookId("B2")
				.maturityDate(LocalDate.of(2023, 5, 20)).createDate(LocalDate.now()).expired("N").build();

		boolean thrown = false;
		try {
			tradeStoreService.insertTradeData(trade);
		} catch (TradeStoreException e) {
			thrown = true;
		}

		assertTrue(thrown);
	}

	@Test
	void shouldUpdateExpiryFlagIfTradeCrossesTheMaturityDate(){
		initialLoadData();
		assertTrue(tradeStoreService.updateExpiryFlag());
	}

	private void initialLoadData(){
		List<TradeStoreDto> trades = new ArrayList<>();

		TradeStoreDto data = TradeStoreDto.builder()
				.tradeId("T1").version(1).counterPartyId("CP-1").bookId("B1").maturityDate(LocalDate.of(2020, 5, 20)).createDate(LocalDate.now()).expired("N").build();

		TradeStoreDto data2 = TradeStoreDto.builder()
				.tradeId("T2").version(2).counterPartyId("CP-2").bookId("B1").maturityDate(LocalDate.of(2021, 5, 20)).createDate(LocalDate.now()).expired("N").build();

		TradeStoreDto data3 = TradeStoreDto.builder()
				.tradeId("T2").version(1).counterPartyId("CP-1").bookId("B1").maturityDate(LocalDate.of(2021, 5, 20)).createDate(LocalDate.of(2015, 3, 14)).expired("N").build();

		TradeStoreDto data4 = TradeStoreDto.builder()
				.tradeId("T3").version(3).counterPartyId("CP-3").bookId("B2").maturityDate(LocalDate.of(2014, 5, 20)).createDate(LocalDate.now()).expired("Y").build();

		trades.add(data);
		trades.add(data2);
		trades.add(data3);
		trades.add(data4);

		tradeStoreService.loadData(trades);
	}


}
