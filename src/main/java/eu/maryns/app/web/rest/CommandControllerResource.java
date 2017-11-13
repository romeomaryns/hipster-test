package eu.maryns.app.web.rest;

import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.*;
import eu.maryns.app.domain.Instrument;
import eu.maryns.app.domain.OandaAccount;
import eu.maryns.app.service.AccountService;
import eu.maryns.app.service.InstrumentService;
import org.h2.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import eu.maryns.app.oanda.Config;
import com.oanda.v20.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * CommandController controller
 */
@RestController
@RequestMapping("/api/command-controller")
public class CommandControllerResource {

    private final Logger log = LoggerFactory.getLogger(CommandControllerResource.class);

    private final AccountService accountService;
    private final InstrumentService instrumentService;

    Context ctx = new Context(Config.URL, Config.TOKEN);
    AccountID accountId = Config.ACCOUNTID;


    public CommandControllerResource(InstrumentService instrumentService,
                                     AccountService accountService){
        this.accountService=accountService;
        this.instrumentService=instrumentService;
    }

    /**
     * GET loadAccounts
     */
    @GetMapping("/load-accounts")
    public List<OandaAccount> loadAccounts() {
        List<OandaAccount> accounts = new ArrayList<OandaAccount>();
        AccountListResponse listResponse  = null;
        try {
            listResponse = ctx.account.list();
        } catch (RequestException e) {
            e.printStackTrace();
        } catch (ExecuteException e) {
            e.printStackTrace();
        }
        for(AccountProperties prop : listResponse.getAccounts())
        {
            try {
                Account account = ctx.account.get(prop.getId()).getAccount();
                OandaAccount oandaAccount = accountService.convertAndSaveAccountInfo(account);
                accounts.add(oandaAccount);
            } catch (RequestException e) {
                e.printStackTrace();
            } catch (ExecuteException e) {
                e.printStackTrace();
            }
        }
        return accounts;
    }

    /**
     * GET loadInstruments
     */
    @GetMapping("/load-instruments")
    public List<Instrument> loadInstruments() {
        List<Instrument> instruments = new ArrayList<Instrument>();
        try {
            AccountInstrumentsResponse instrumentsResponse = ctx.account.instruments(accountId);
            for(com.oanda.v20.primitives.Instrument instrument : instrumentsResponse.getInstruments())
            {
                Instrument instrumentTarget = instrumentService.convertAndSaveInstrumentInfo(instrument);
                instruments.add(instrumentTarget);
            }
        } catch (RequestException e) {
            e.printStackTrace();
        } catch (ExecuteException e) {
            e.printStackTrace();
        }

        return instruments;
    }



}
