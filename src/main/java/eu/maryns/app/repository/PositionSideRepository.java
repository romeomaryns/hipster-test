package eu.maryns.app.repository;

import eu.maryns.app.domain.PositionSide;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PositionSide entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PositionSideRepository extends JpaRepository<PositionSide, Long> {

}
