package eu.maryns.app.service;

import eu.maryns.app.domain.Position;
import eu.maryns.app.repository.OandaAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oanda.v20.account.Account;
import eu.maryns.app.domain.OandaAccount;

import java.time.Instant;
import java.util.ArrayList;

@Service
@Transactional
public class AccountService implements IAccountService{

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    OandaAccountRepository oandaAccountRepository;

    public OandaAccount convertAndSaveAccountInfo(Account account){
        OandaAccount oandaAccount = new OandaAccount();
        try{
            oandaAccount = convertAccount(account);
            return oandaAccountRepository.save(oandaAccount);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return oandaAccount;
    }


    private static OandaAccount convertAccount(Account account)
    {
        OandaAccount oandaAccount = new OandaAccount();
        oandaAccount.setAlias(account.getAlias());
        oandaAccount.setCurrency(account.getCurrency().toString());
        oandaAccount.setBalance(account.getBalance().doubleValue());
        if (account.getCreatedByUserID() != null)
        {
            oandaAccount.setCreatedByUserID(new Integer(account.getCreatedByUserID()));
        }
        oandaAccount.setCreatedTime(Instant.parse(account.getCreatedTime()));
        oandaAccount.setPl(account.getPl().doubleValue());
        oandaAccount.setResettablePL(account.getResettablePL().doubleValue());
        if(account.getResettabledPLTime() != null) {
            oandaAccount.setResettabledPLTime(Instant.parse(account.getResettabledPLTime()));
        }
        oandaAccount.setCommission(account.getCommission().doubleValue());
        oandaAccount.setMarginRate(account.getMarginRate().doubleValue());
        if(account.getMarginCallEnterTime() != null) {
            oandaAccount.setMarginCallEnterTime(Instant.parse(account.getMarginCallEnterTime()));
        }
        if (account.getMarginCallExtensionCount() != null)
        {
            oandaAccount.setMarginCallExtensionCount(new Integer(account.getMarginCallExtensionCount()));
        }
        if(account.getLastMarginCallExtensionTime() != null) {
            oandaAccount.setLastMarginCallExtensionTime(Instant.parse(account.getLastMarginCallExtensionTime()));
        }
        if (account.getOpenTradeCount() != null)
        {
            oandaAccount.setOpenTradeCount(new Integer(account.getOpenTradeCount()));
        }
        if (account.getOpenPositionCount() != null)
        {
            oandaAccount.setOpenPositionCount(new Integer(account.getOpenPositionCount()));
        }
        if (account.getPendingOrderCount() != null)
        {
            oandaAccount.setPendingOrderCount(new Integer(account.getPendingOrderCount()));
        }
        if (account.getHedgingEnabled() != null)
        {
            oandaAccount.setHedgingEnabled(new Boolean(account.getHedgingEnabled()));
        }
        oandaAccount.setUnrealizedPL(account.getUnrealizedPL().doubleValue());
        oandaAccount.setnAV(account.getNAV().doubleValue());
        oandaAccount.setMarginUsed(account.getMarginUsed().doubleValue());
        oandaAccount.setMarginAvailable(account.getMarginAvailable().doubleValue());
        oandaAccount.setPositionValue(account.getPositionValue().doubleValue());
        oandaAccount.setMarginCloseoutUnrealizedPL(account.getMarginCloseoutUnrealizedPL().doubleValue());
        oandaAccount.setMarginCloseoutNAV(account.getMarginCloseoutNAV().doubleValue());
        oandaAccount.setMarginCloseoutMarginUsed(account.getMarginCloseoutMarginUsed().doubleValue());
        oandaAccount.setMarginCloseoutPercent(account.getMarginCloseoutPercent().doubleValue());
        oandaAccount.setMarginCloseoutPositionValue(account.getMarginCloseoutPositionValue().doubleValue());
        oandaAccount.setWithdrawalLimit(account.getWithdrawalLimit().doubleValue());
        oandaAccount.setMarginCallMarginUsed(account.getMarginCallMarginUsed().doubleValue());
        oandaAccount.setMarginCallPercent(account.getMarginCallPercent().doubleValue());
        oandaAccount.setLastTransactionID(account.getLastTransactionID().toString());
        /*if (account.getPositions() != null)
        {
            oandaAccount.positions = new ArrayList<Position>();
            for (com.oanda.v20.position.Position position: account.getPositions()) {
                oandaAccount.positions.add(new Position(position));
            }
        }*/
        return oandaAccount;
    }

}
