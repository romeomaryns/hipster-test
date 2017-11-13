package eu.maryns.app.repository;

import eu.maryns.app.domain.CandleStickData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandleStickData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandleStickDataRepository extends JpaRepository<CandleStickData, Long> {

}
