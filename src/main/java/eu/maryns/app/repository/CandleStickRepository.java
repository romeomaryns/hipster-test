package eu.maryns.app.repository;

import eu.maryns.app.domain.CandleStick;
import eu.maryns.app.domain.Instrument;
import eu.maryns.app.domain.enumeration.CandleStickGranularity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the CandleStick entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandleStickRepository extends JpaRepository<CandleStick, Long> {

    List<CandleStick> findByInstrument(Instrument instrument);
    List<CandleStick> findAllByInstrumentAndGranularity(Instrument instrument, CandleStickGranularity granularity);

}
