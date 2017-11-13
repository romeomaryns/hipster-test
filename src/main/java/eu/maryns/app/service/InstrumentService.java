package eu.maryns.app.service;

import com.oanda.v20.account.Account;
import com.oanda.v20.primitives.InstrumentCommission;
import eu.maryns.app.domain.Instrument;
import eu.maryns.app.domain.OandaAccount;
import eu.maryns.app.domain.enumeration.InstrumentType;
import eu.maryns.app.repository.InstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class InstrumentService {

    private final Logger log = LoggerFactory.getLogger(InstrumentService.class);


    @Autowired
    InstrumentRepository instrumentRepository;

    public Instrument convertAndSaveInstrumentInfo(com.oanda.v20.primitives.Instrument instrumentSource){
        Instrument instrument = new Instrument();
        try{
            instrument = convertInstrument(instrumentSource);
            instrument = instrumentRepository.save(instrument);
            return instrument;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return instrument;
    }


    private static Instrument convertInstrument(com.oanda.v20.primitives.Instrument instrumentSource)
    {
        Instrument instrument = new Instrument();
        instrument.setName(instrumentSource.getName().toString());
        instrument.setType(instrumentSource.getType().name());
        instrument.setDisplayName(instrumentSource.getDisplayName());
        if (instrumentSource.getPipLocation() != null)
        {
            instrument.setPipLocation(instrumentSource.getPipLocation());
        }
        if (instrumentSource.getDisplayPrecision() != null)
        {
            instrument.setDisplayPrecision(instrumentSource.getDisplayPrecision());
        }
        if (instrumentSource.getTradeUnitsPrecision() != null)
        {
            instrument.setTradeUnitsPrecision(instrumentSource.getTradeUnitsPrecision());
        }
        instrument.setMinimumTradeSize(instrumentSource.getMinimumTradeSize().doubleValue());
        instrument.setMaximumTrailingStopDistance(instrumentSource.getMaximumTrailingStopDistance().doubleValue());
        instrument.setMinimumTrailingStopDistance(instrumentSource.getMinimumTrailingStopDistance().doubleValue());
        instrument.setMaximumPositionSize(instrumentSource.getMaximumPositionSize().doubleValue());
        instrument.setMaximumOrderUnits(instrumentSource.getMaximumOrderUnits().doubleValue());
        instrument.setMarginRate(instrumentSource.getMarginRate().doubleValue());
        if (instrumentSource.getCommission() != null)
        {
            instrument.setCommission(instrumentSource.getCommission().getCommission().doubleValue());
        }
        return instrument;
    }


}
