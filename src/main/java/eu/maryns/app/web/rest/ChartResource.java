package eu.maryns.app.web.rest;

import com.oanda.v20.Context;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.*;
import com.oanda.v20.instrument.CandlestickGranularity;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.primitives.DateTime;
import com.oanda.v20.primitives.InstrumentName;
import eu.maryns.app.domain.*;
import eu.maryns.app.oanda.Config;
import eu.maryns.app.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Charting controller
 */
@RestController
@RequestMapping("/api/chart")
public class ChartResource {

    private final Logger log = LoggerFactory.getLogger(ChartResource.class);

    @Autowired
    private ICandleService candleService;

    @Autowired
    private IGranularityService granularityService;

    Context ctx = new Context(Config.URL, Config.TOKEN);
    AccountID accountId = Config.ACCOUNTID;



    /**
     * GET generateChartingData
     */
    @GetMapping("/generate")
    public List<CandleStick> generateCharts(@RequestParam(name = "from") String from,
                                            @RequestParam(name ="to") String to,
                                            @RequestParam(name = "instrument") String instrument,
                                            @RequestParam(name = "granularity") String granularity) {
        log.info("From: " + from + "\tTo: " + to + "\tInstrument: " +instrument + "\tGranularity: " + granularity);
        List<CandleStick> candleSticks = new ArrayList<CandleStick>();
        try {
            InstrumentCandlesResponse response = ctx.instrument.candles(
                new InstrumentCandlesRequest(new InstrumentName(instrument))
                    .setGranularity(CandlestickGranularity.valueOf(granularity))
                    .setFrom(new DateTime(from))
                    .setTo(new DateTime(to))
                    );
            log.info(String.valueOf(response.getCandles().size()));
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
        return candleService.findDetail(from,to,instrument,granularityService.findByName(granularity));
    }
}
