package eu.maryns.app.repository;

import eu.maryns.app.domain.CandleStickGranularity;
import eu.maryns.app.domain.Instrument;
import eu.maryns.app.domain.Stat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Stat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {

    List<Stat> findAllByInstrumentAndGranularity(Instrument instrument, CandleStickGranularity granularity);
}
