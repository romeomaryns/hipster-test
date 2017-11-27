package eu.maryns.app.service;

import eu.maryns.app.domain.CandleStick;
import eu.maryns.app.domain.CandleStickGranularity;
import eu.maryns.app.domain.Instrument;
import eu.maryns.app.domain.Stat;
import eu.maryns.app.repository.CandleStickGranularityRepository;
import eu.maryns.app.repository.CandleStickRepository;
import eu.maryns.app.repository.InstrumentRepository;
import eu.maryns.app.repository.StatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StatService implements IStatService{

    private final Logger log = LoggerFactory.getLogger(StatService.class);

    @Autowired
    private StatRepository statRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private CandleStickRepository candlestickRepository;

    @Autowired
    private CandleStickGranularityRepository candlestickGranularityRepository;

    @Override
    public List<Stat> loadAll() {
        List<Stat> stats = new ArrayList<Stat>();
        try
        {
            stats.addAll(statRepository.findAll());
        }
        catch(Exception e)
        {
            log.error("Error occurred during fetching of Stats");
        }
        return stats;
    }

    @Override
    public List<Stat> recalculateAll() {
        List<Stat> stats = new ArrayList<Stat>();
        stats = statRepository.findAll();
            for (Instrument instrument : instrumentRepository.findAll())
            {
                for(CandleStickGranularity granularity : candlestickGranularityRepository.findAll()) {
                    Stat stat = new Stat();
                    List<Stat> temp = statRepository.findAllByInstrumentAndGranularity(instrument,granularity);
                    if(!temp.isEmpty())
                    {
                        stat = temp.get(0);
                    }
                    stat.setInstrument(instrument);
                    stat.setGranularity(granularity);
                    stat.setLastUpdated(Instant.now());
                    int count = 0;
                    Instant first = null;
                    Instant last = null;
                    for (CandleStick candleStick: candlestickRepository.findAllByInstrumentAndGranularity(instrument,granularity)) {
                        count++;
                        Instant time = candleStick.getTime();
                        if(null == first || time.isBefore(first))
                        {
                            first = time;
                        }
                        if(null == last || time.isAfter(last))
                        {
                            last = time;
                        }
                    }
                    stat.setNumberOfCandles(count);
                    stat.setFirst(first);
                    stat.setLast(last);
                    if(count >0) {
                        stats.add(stat);
                    }
                }
            }
        statRepository.save(stats);
        return stats;
    }
}
