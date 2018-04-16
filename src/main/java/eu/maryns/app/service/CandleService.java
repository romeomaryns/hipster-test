package eu.maryns.app.service;

import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.CandlestickGranularity;
import com.oanda.v20.primitives.DateTime;
import com.oanda.v20.primitives.InstrumentName;
import eu.maryns.app.domain.CandleStick;
import eu.maryns.app.domain.CandleStickGranularity;
import eu.maryns.app.repository.CandleStickGranularityRepository;
import eu.maryns.app.repository.CandleStickRepository;
import eu.maryns.app.repository.InstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class CandleService implements ICandleService{

    private final Logger log = LoggerFactory.getLogger(CandleService.class);

    @Autowired
    private CandleStickRepository candleStickRepository;

    @Autowired
    private CandleStickGranularityRepository candleStickGranularityRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private ICandleStickDataService candleStickDataService;


    public CandleStick convertCandleStick(Candlestick candle, CandlestickGranularity granularity, InstrumentName instrument)
    {
        CandleStick candleStick = new CandleStick();
        candleStick.setGranularity(candleStickGranularityRepository.findByName(granularity.name()));
        candleStick.setInstrument(instrumentRepository.findByNameIgnoreCase(instrument.toString()));
        candleStick.setComplete(candle.getComplete());
        candleStick.setVolume(candle.getVolume());
        candleStick.setTime(Instant.parse(candle.getTime()));
        candleStick.setMid(candleStickDataService.convertAndSaveCandleStickData(candle.getMid()));
        return candleStick;
    }

    @Override
    public List<CandleStick> findDetail(String from, String to, String instrument, CandleStickGranularity granularity) {
        return this.candleStickRepository.findAllByInstrumentAndGranularityAndTime(Instant.parse(from),Instant.parse(to),instrumentRepository.findByNameIgnoreCase(instrument),granularity);
    }

    @Override
    public CandleStick convertAndSaveCandleStick(Candlestick candle, CandlestickGranularity granularity, InstrumentName instrument) {
        return candleStickRepository.save(convertCandleStick(candle,granularity,instrument));
    }
}
