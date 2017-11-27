package eu.maryns.app.service;


import eu.maryns.app.domain.CandleStickGranularity;

public interface IGranularityService {


    CandleStickGranularity save(CandleStickGranularity granularity);

    CandleStickGranularity findByName(String h1);
}
