package eu.maryns.app.repository;

import eu.maryns.app.domain.CandleStick;
import eu.maryns.app.domain.CandleStickGranularity;
import eu.maryns.app.domain.Instrument;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data JPA repository for the CandleStick entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandleStickRepository extends JpaRepository<CandleStick, Long> {

    CandleStick[] findAllByInstrumentAndGranularity(Instrument instrument, CandleStickGranularity granularity);

    @Query("select c from CandleStick c where c.instrument = :instrument and c.granularity = :granularity and (c.time between :from and :to)")
    List<CandleStick> findAllByInstrumentAndGranularityAndTime(@Param("from")Instant from,
                                                               @Param("to") Instant to,
                                                               @Param("instrument") Instrument instrument,
                                                               @Param("granularity")CandleStickGranularity granularity);
}
