package eu.maryns.app.repository;

import eu.maryns.app.domain.CandleStick;
import eu.maryns.app.domain.CandleStickGranularity;
import eu.maryns.app.domain.Instrument;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandleStick entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandleStickRepository extends JpaRepository<CandleStick, Long> {

    CandleStick[] findAllByInstrumentAndGranularity(Instrument instrument, CandleStickGranularity granularity);

}
