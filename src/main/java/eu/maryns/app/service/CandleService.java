package eu.maryns.app.service;

import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.CandlestickGranularity;
import com.oanda.v20.primitives.InstrumentName;
import eu.maryns.app.domain.CandleStick;
import eu.maryns.app.domain.CandleStickData;
import eu.maryns.app.domain.enumeration.CandleStickGranularity;
import eu.maryns.app.repository.CandleStickRepository;
import eu.maryns.app.repository.InstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class CandleService implements ICandleService{

    private final Logger log = LoggerFactory.getLogger(CandleService.class);

    @Autowired
    private CandleStickRepository candleStickRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private ICandleStickDataService candleStickDataService;


    public CandleStick convertCandleStick(Candlestick candle, CandlestickGranularity granularity, InstrumentName instrument)
    {
        CandleStick candleStick = new CandleStick();
        candleStick.setGranularity(CandleStickGranularity.valueOf(granularity.name()));
        candleStick.setInstrument(instrumentRepository.findByNameIgnoreCase(instrument.toString()));
        candleStick.setComplete(candle.getComplete());
        candleStick.setVolume(candle.getVolume());
        candleStick.setTime(Instant.parse(candle.getTime()));
        candleStick.setMid(candleStickDataService.convertAndSaveCandleStickData(candle.getMid()));
        return candleStick;
    }

    @Override
    public CandleStick convertAndSaveCandleStick(Candlestick candle, CandlestickGranularity granularity, InstrumentName instrument) {
        return candleStickRepository.save(convertCandleStick(candle,granularity,instrument));
    }
}
