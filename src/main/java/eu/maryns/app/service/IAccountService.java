package eu.maryns.app.service;

import com.oanda.v20.account.Account;
import eu.maryns.app.domain.OandaAccount;

public interface IAccountService {
    OandaAccount convertAndSaveAccountInfo(Account account);
}
