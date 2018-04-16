package eu.maryns.app.service;

import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.CandlestickGranularity;
import com.oanda.v20.primitives.InstrumentName;
import eu.maryns.app.domain.CandleStick;
import eu.maryns.app.domain.CandleStickGranularity;

import java.util.List;

public interface ICandleService {

    CandleStick convertAndSaveCandleStick(Candlestick candle, CandlestickGranularity granularity, InstrumentName instrument);
    CandleStick convertCandleStick(Candlestick candle, CandlestickGranularity granularity, InstrumentName instrument);
    List<CandleStick> findDetail(String from, String to, String instrument, CandleStickGranularity granularity);
}
