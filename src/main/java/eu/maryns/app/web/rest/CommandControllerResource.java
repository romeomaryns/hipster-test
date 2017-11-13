package eu.maryns.app.web.rest;

import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.*;
import com.oanda.v20.instrument.CandlestickGranularity;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.primitives.DateTime;
import com.oanda.v20.primitives.InstrumentName;
import eu.maryns.app.domain.CandleStick;
import eu.maryns.app.domain.Instrument;
import eu.maryns.app.domain.OandaAccount;
import eu.maryns.app.service.*;
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

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IInstrumentService instrumentService;
    @Autowired
    private ICandleService candleService;

    Context ctx = new Context(Config.URL, Config.TOKEN);
    AccountID accountId = Config.ACCOUNTID;


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

    /**
     * GET loadInstruments
     */
    @GetMapping("/load-candleSticks")
    public List<CandleStick> loadCandleSticks(String instrument) {
        List<CandleStick> candleSticks = new ArrayList<CandleStick>();
        try {
            InstrumentCandlesResponse response = ctx.instrument.candles(
                new InstrumentCandlesRequest(new InstrumentName(instrument))
                    .setGranularity(CandlestickGranularity.H1)
                    .setFrom(new DateTime("2017-10-01T10:12:35Z"))
                    .setTo(new DateTime("2017-11-01T10:12:35Z"))
                    );
            //InstrumentCandlesRequest request = new InstrumentCandlesRequest(new InstrumentName(instrument));
            //request.setGranularity(CandlestickGranularity.H1);
           // request.setCount(100);
            //InstrumentCandlesResponse response = ctx.instrument.candles(request);

            for(com.oanda.v20.instrument.Candlestick candle : response.getCandles())
            {
                CandleStick candleStick = candleService.convertAndSaveCandleStick(candle,response.getGranularity(),response.getInstrument());
                candleSticks.add(candleStick);
            }
        } catch (RequestException e) {
            e.printStackTrace();
        } catch (ExecuteException e) {
            e.printStackTrace();
        }

        return candleSticks;
    }



}
