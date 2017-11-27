package eu.maryns.app.repository;

import eu.maryns.app.domain.CandleStickGranularity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandleStickGranularity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandleStickGranularityRepository extends JpaRepository<CandleStickGranularity, Long> {

    CandleStickGranularity findByName(String name);
}
