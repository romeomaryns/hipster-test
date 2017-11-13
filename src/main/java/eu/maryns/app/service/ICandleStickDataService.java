package eu.maryns.app.service;

import com.oanda.v20.instrument.CandlestickData;
import eu.maryns.app.domain.CandleStickData;

public interface ICandleStickDataService {
    CandleStickData convertAndSaveCandleStickData(CandlestickData candleStickData);
    CandleStickData convertCandleStickData(CandlestickData candleStickData);
}
