package eu.maryns.app.repository;

import eu.maryns.app.domain.OandaAccount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the OandaAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OandaAccountRepository extends JpaRepository<OandaAccount, Long> {

    @Query("select oanda_account from OandaAccount oanda_account where oanda_account.user.login = ?#{principal.username}")
    List<OandaAccount> findByUserIsCurrentUser();

}
