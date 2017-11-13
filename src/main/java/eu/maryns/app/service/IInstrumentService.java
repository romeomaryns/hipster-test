package eu.maryns.app.service;

import eu.maryns.app.domain.Instrument;

public interface IInstrumentService {
    Instrument convertAndSaveInstrumentInfo(com.oanda.v20.primitives.Instrument instrumentSource);
}
