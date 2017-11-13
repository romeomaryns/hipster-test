package eu.maryns.app.oanda;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.primitives.InstrumentName;

public class Config {
        private Config() {}
        public static final String URL = "https://api-fxpractice.oanda.com";
        public static final String TOKEN = "a87a45322aac965d815a76e69eeb1738-991d3a0e05efaf19cc582a08083cfbbd";
        public static final AccountID ACCOUNTID = new AccountID("101-004-6429306-001");
        public static final InstrumentName INSTRUMENT  = new InstrumentName("EUR/USD");
}