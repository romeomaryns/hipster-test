package eu.maryns.app.service;

import eu.maryns.app.domain.CandleStickGranularity;
import eu.maryns.app.repository.CandleStickGranularityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GranularityService implements IGranularityService{

    private final Logger log = LoggerFactory.getLogger(GranularityService.class);

    @Autowired
    private CandleStickGranularityRepository candleStickGranularityRepository;

    @Override
    public CandleStickGranularity save(CandleStickGranularity granularity) {
        return candleStickGranularityRepository.save(granularity);
    }

    @Override
    public CandleStickGranularity findByName(String h1) {
        return candleStickGranularityRepository.findByName(h1);
    }
}
