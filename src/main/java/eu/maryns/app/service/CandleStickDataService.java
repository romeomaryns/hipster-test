package eu.maryns.app.service;

import com.oanda.v20.instrument.CandlestickData;
import eu.maryns.app.domain.CandleStickData;
import eu.maryns.app.repository.CandleStickDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CandleStickDataService implements ICandleStickDataService {

    private final Logger log = LoggerFactory.getLogger(CandleStickDataService.class);

    @Autowired
    private CandleStickDataRepository candleStickDataRepository;


    @Override
    public CandleStickData convertAndSaveCandleStickData(CandlestickData candleStickData) {
        return candleStickDataRepository.save(convertCandleStickData(candleStickData));
    }

    public CandleStickData convertCandleStickData(CandlestickData candleStickData) {
        CandleStickData result = new CandleStickData();
        if(candleStickData != null) {
            log.info(candleStickData.toString());
            if (null != candleStickData.getO())
                result.setO(candleStickData.getO().doubleValue());
            if (null != candleStickData.getL())
                result.setL(candleStickData.getL().doubleValue());
            if (null != candleStickData.getC())
                result.setC(candleStickData.getC().doubleValue());
            if (null != candleStickData.getH())
                result.setH(candleStickData.getH().doubleValue());
        }
        else
        {
            log.info("CandleStickData is NULL");
            return null;
        }
        return result;
    }
}
