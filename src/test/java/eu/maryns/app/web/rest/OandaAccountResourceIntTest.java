package eu.maryns.app.web.rest;

import eu.maryns.app.HipsterApp;

import eu.maryns.app.domain.OandaAccount;
import eu.maryns.app.repository.OandaAccountRepository;
import eu.maryns.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static eu.maryns.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OandaAccountResource REST controller.
 *
 * @see OandaAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipsterApp.class)
public class OandaAccountResourceIntTest {

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREATED_BY_USER_ID = 1;
    private static final Integer UPDATED_CREATED_BY_USER_ID = 2;

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PL = 1D;
    private static final Double UPDATED_PL = 2D;

    private static final Double DEFAULT_RESETTABLE_PL = 1D;
    private static final Double UPDATED_RESETTABLE_PL = 2D;

    private static final Instant DEFAULT_RESETTABLED_PL_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESETTABLED_PL_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_COMMISSION = 1D;
    private static final Double UPDATED_COMMISSION = 2D;

    private static final Double DEFAULT_MARGIN_RATE = 1D;
    private static final Double UPDATED_MARGIN_RATE = 2D;

    private static final Instant DEFAULT_MARGIN_CALL_ENTER_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MARGIN_CALL_ENTER_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_MARGIN_CALL_EXTENSION_COUNT = 1;
    private static final Integer UPDATED_MARGIN_CALL_EXTENSION_COUNT = 2;

    private static final Instant DEFAULT_LAST_MARGIN_CALL_EXTENSION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MARGIN_CALL_EXTENSION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_OPEN_TRADE_COUNT = 1;
    private static final Integer UPDATED_OPEN_TRADE_COUNT = 2;

    private static final Integer DEFAULT_OPEN_POSITION_COUNT = 1;
    private static final Integer UPDATED_OPEN_POSITION_COUNT = 2;

    private static final Integer DEFAULT_PENDING_ORDER_COUNT = 1;
    private static final Integer UPDATED_PENDING_ORDER_COUNT = 2;

    private static final Boolean DEFAULT_HEDGING_ENABLED = false;
    private static final Boolean UPDATED_HEDGING_ENABLED = true;

    private static final Double DEFAULT_UNREALIZED_PL = 1D;
    private static final Double UPDATED_UNREALIZED_PL = 2D;

    private static final Double DEFAULT_N_AV = 1D;
    private static final Double UPDATED_N_AV = 2D;

    private static final Double DEFAULT_MARGIN_USED = 1D;
    private static final Double UPDATED_MARGIN_USED = 2D;

    private static final Double DEFAULT_MARGIN_AVAILABLE = 1D;
    private static final Double UPDATED_MARGIN_AVAILABLE = 2D;

    private static final Double DEFAULT_POSITION_VALUE = 1D;
    private static final Double UPDATED_POSITION_VALUE = 2D;

    private static final Double DEFAULT_MARGIN_CLOSEOUT_UNREALIZED_PL = 1D;
    private static final Double UPDATED_MARGIN_CLOSEOUT_UNREALIZED_PL = 2D;

    private static final Double DEFAULT_MARGIN_CLOSEOUT_NAV = 1D;
    private static final Double UPDATED_MARGIN_CLOSEOUT_NAV = 2D;

    private static final Double DEFAULT_MARGIN_CLOSEOUT_MARGIN_USED = 1D;
    private static final Double UPDATED_MARGIN_CLOSEOUT_MARGIN_USED = 2D;

    private static final Double DEFAULT_MARGIN_CLOSEOUT_PERCENT = 1D;
    private static final Double UPDATED_MARGIN_CLOSEOUT_PERCENT = 2D;

    private static final Double DEFAULT_MARGIN_CLOSEOUT_POSITION_VALUE = 1D;
    private static final Double UPDATED_MARGIN_CLOSEOUT_POSITION_VALUE = 2D;

    private static final Double DEFAULT_WITHDRAWAL_LIMIT = 1D;
    private static final Double UPDATED_WITHDRAWAL_LIMIT = 2D;

    private static final Double DEFAULT_MARGIN_CALL_MARGIN_USED = 1D;
    private static final Double UPDATED_MARGIN_CALL_MARGIN_USED = 2D;

    private static final Double DEFAULT_MARGIN_CALL_PERCENT = 1D;
    private static final Double UPDATED_MARGIN_CALL_PERCENT = 2D;

    private static final String DEFAULT_LAST_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_LAST_TRANSACTION_ID = "BBBBBBBBBB";

    @Autowired
    private OandaAccountRepository oandaAccountRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOandaAccountMockMvc;

    private OandaAccount oandaAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OandaAccountResource oandaAccountResource = new OandaAccountResource(oandaAccountRepository);
        this.restOandaAccountMockMvc = MockMvcBuilders.standaloneSetup(oandaAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OandaAccount createEntity(EntityManager em) {
        OandaAccount oandaAccount = new OandaAccount()
            .alias(DEFAULT_ALIAS)
            .balance(DEFAULT_BALANCE)
            .currency(DEFAULT_CURRENCY)
            .createdByUserID(DEFAULT_CREATED_BY_USER_ID)
            .createdTime(DEFAULT_CREATED_TIME)
            .pl(DEFAULT_PL)
            .resettablePL(DEFAULT_RESETTABLE_PL)
            .resettabledPLTime(DEFAULT_RESETTABLED_PL_TIME)
            .commission(DEFAULT_COMMISSION)
            .marginRate(DEFAULT_MARGIN_RATE)
            .marginCallEnterTime(DEFAULT_MARGIN_CALL_ENTER_TIME)
            .marginCallExtensionCount(DEFAULT_MARGIN_CALL_EXTENSION_COUNT)
            .lastMarginCallExtensionTime(DEFAULT_LAST_MARGIN_CALL_EXTENSION_TIME)
            .openTradeCount(DEFAULT_OPEN_TRADE_COUNT)
            .openPositionCount(DEFAULT_OPEN_POSITION_COUNT)
            .pendingOrderCount(DEFAULT_PENDING_ORDER_COUNT)
            .hedgingEnabled(DEFAULT_HEDGING_ENABLED)
            .unrealizedPL(DEFAULT_UNREALIZED_PL)
            .nAV(DEFAULT_N_AV)
            .marginUsed(DEFAULT_MARGIN_USED)
            .marginAvailable(DEFAULT_MARGIN_AVAILABLE)
            .positionValue(DEFAULT_POSITION_VALUE)
            .marginCloseoutUnrealizedPL(DEFAULT_MARGIN_CLOSEOUT_UNREALIZED_PL)
            .marginCloseoutNAV(DEFAULT_MARGIN_CLOSEOUT_NAV)
            .marginCloseoutMarginUsed(DEFAULT_MARGIN_CLOSEOUT_MARGIN_USED)
            .marginCloseoutPercent(DEFAULT_MARGIN_CLOSEOUT_PERCENT)
            .marginCloseoutPositionValue(DEFAULT_MARGIN_CLOSEOUT_POSITION_VALUE)
            .withdrawalLimit(DEFAULT_WITHDRAWAL_LIMIT)
            .marginCallMarginUsed(DEFAULT_MARGIN_CALL_MARGIN_USED)
            .marginCallPercent(DEFAULT_MARGIN_CALL_PERCENT)
            .lastTransactionID(DEFAULT_LAST_TRANSACTION_ID);
        return oandaAccount;
    }

    @Before
    public void initTest() {
        oandaAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createOandaAccount() throws Exception {
        int databaseSizeBeforeCreate = oandaAccountRepository.findAll().size();

        // Create the OandaAccount
        restOandaAccountMockMvc.perform(post("/api/oanda-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oandaAccount)))
            .andExpect(status().isCreated());

        // Validate the OandaAccount in the database
        List<OandaAccount> oandaAccountList = oandaAccountRepository.findAll();
        assertThat(oandaAccountList).hasSize(databaseSizeBeforeCreate + 1);
        OandaAccount testOandaAccount = oandaAccountList.get(oandaAccountList.size() - 1);
        assertThat(testOandaAccount.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testOandaAccount.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testOandaAccount.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testOandaAccount.getCreatedByUserID()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testOandaAccount.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testOandaAccount.getPl()).isEqualTo(DEFAULT_PL);
        assertThat(testOandaAccount.getResettablePL()).isEqualTo(DEFAULT_RESETTABLE_PL);
        assertThat(testOandaAccount.getResettabledPLTime()).isEqualTo(DEFAULT_RESETTABLED_PL_TIME);
        assertThat(testOandaAccount.getCommission()).isEqualTo(DEFAULT_COMMISSION);
        assertThat(testOandaAccount.getMarginRate()).isEqualTo(DEFAULT_MARGIN_RATE);
        assertThat(testOandaAccount.getMarginCallEnterTime()).isEqualTo(DEFAULT_MARGIN_CALL_ENTER_TIME);
        assertThat(testOandaAccount.getMarginCallExtensionCount()).isEqualTo(DEFAULT_MARGIN_CALL_EXTENSION_COUNT);
        assertThat(testOandaAccount.getLastMarginCallExtensionTime()).isEqualTo(DEFAULT_LAST_MARGIN_CALL_EXTENSION_TIME);
        assertThat(testOandaAccount.getOpenTradeCount()).isEqualTo(DEFAULT_OPEN_TRADE_COUNT);
        assertThat(testOandaAccount.getOpenPositionCount()).isEqualTo(DEFAULT_OPEN_POSITION_COUNT);
        assertThat(testOandaAccount.getPendingOrderCount()).isEqualTo(DEFAULT_PENDING_ORDER_COUNT);
        assertThat(testOandaAccount.isHedgingEnabled()).isEqualTo(DEFAULT_HEDGING_ENABLED);
        assertThat(testOandaAccount.getUnrealizedPL()).isEqualTo(DEFAULT_UNREALIZED_PL);
        assertThat(testOandaAccount.getnAV()).isEqualTo(DEFAULT_N_AV);
        assertThat(testOandaAccount.getMarginUsed()).isEqualTo(DEFAULT_MARGIN_USED);
        assertThat(testOandaAccount.getMarginAvailable()).isEqualTo(DEFAULT_MARGIN_AVAILABLE);
        assertThat(testOandaAccount.getPositionValue()).isEqualTo(DEFAULT_POSITION_VALUE);
        assertThat(testOandaAccount.getMarginCloseoutUnrealizedPL()).isEqualTo(DEFAULT_MARGIN_CLOSEOUT_UNREALIZED_PL);
        assertThat(testOandaAccount.getMarginCloseoutNAV()).isEqualTo(DEFAULT_MARGIN_CLOSEOUT_NAV);
        assertThat(testOandaAccount.getMarginCloseoutMarginUsed()).isEqualTo(DEFAULT_MARGIN_CLOSEOUT_MARGIN_USED);
        assertThat(testOandaAccount.getMarginCloseoutPercent()).isEqualTo(DEFAULT_MARGIN_CLOSEOUT_PERCENT);
        assertThat(testOandaAccount.getMarginCloseoutPositionValue()).isEqualTo(DEFAULT_MARGIN_CLOSEOUT_POSITION_VALUE);
        assertThat(testOandaAccount.getWithdrawalLimit()).isEqualTo(DEFAULT_WITHDRAWAL_LIMIT);
        assertThat(testOandaAccount.getMarginCallMarginUsed()).isEqualTo(DEFAULT_MARGIN_CALL_MARGIN_USED);
        assertThat(testOandaAccount.getMarginCallPercent()).isEqualTo(DEFAULT_MARGIN_CALL_PERCENT);
        assertThat(testOandaAccount.getLastTransactionID()).isEqualTo(DEFAULT_LAST_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void createOandaAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oandaAccountRepository.findAll().size();

        // Create the OandaAccount with an existing ID
        oandaAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOandaAccountMockMvc.perform(post("/api/oanda-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oandaAccount)))
            .andExpect(status().isBadRequest());

        // Validate the OandaAccount in the database
        List<OandaAccount> oandaAccountList = oandaAccountRepository.findAll();
        assertThat(oandaAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOandaAccounts() throws Exception {
        // Initialize the database
        oandaAccountRepository.saveAndFlush(oandaAccount);

        // Get all the oandaAccountList
        restOandaAccountMockMvc.perform(get("/api/oanda-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oandaAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].createdByUserID").value(hasItem(DEFAULT_CREATED_BY_USER_ID)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].pl").value(hasItem(DEFAULT_PL.doubleValue())))
            .andExpect(jsonPath("$.[*].resettablePL").value(hasItem(DEFAULT_RESETTABLE_PL.doubleValue())))
            .andExpect(jsonPath("$.[*].resettabledPLTime").value(hasItem(DEFAULT_RESETTABLED_PL_TIME.toString())))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].marginRate").value(hasItem(DEFAULT_MARGIN_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].marginCallEnterTime").value(hasItem(DEFAULT_MARGIN_CALL_ENTER_TIME.toString())))
            .andExpect(jsonPath("$.[*].marginCallExtensionCount").value(hasItem(DEFAULT_MARGIN_CALL_EXTENSION_COUNT)))
            .andExpect(jsonPath("$.[*].lastMarginCallExtensionTime").value(hasItem(DEFAULT_LAST_MARGIN_CALL_EXTENSION_TIME.toString())))
            .andExpect(jsonPath("$.[*].openTradeCount").value(hasItem(DEFAULT_OPEN_TRADE_COUNT)))
            .andExpect(jsonPath("$.[*].openPositionCount").value(hasItem(DEFAULT_OPEN_POSITION_COUNT)))
            .andExpect(jsonPath("$.[*].pendingOrderCount").value(hasItem(DEFAULT_PENDING_ORDER_COUNT)))
            .andExpect(jsonPath("$.[*].hedgingEnabled").value(hasItem(DEFAULT_HEDGING_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].unrealizedPL").value(hasItem(DEFAULT_UNREALIZED_PL.doubleValue())))
            .andExpect(jsonPath("$.[*].nAV").value(hasItem(DEFAULT_N_AV.doubleValue())))
            .andExpect(jsonPath("$.[*].marginUsed").value(hasItem(DEFAULT_MARGIN_USED.doubleValue())))
            .andExpect(jsonPath("$.[*].marginAvailable").value(hasItem(DEFAULT_MARGIN_AVAILABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].positionValue").value(hasItem(DEFAULT_POSITION_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].marginCloseoutUnrealizedPL").value(hasItem(DEFAULT_MARGIN_CLOSEOUT_UNREALIZED_PL.doubleValue())))
            .andExpect(jsonPath("$.[*].marginCloseoutNAV").value(hasItem(DEFAULT_MARGIN_CLOSEOUT_NAV.doubleValue())))
            .andExpect(jsonPath("$.[*].marginCloseoutMarginUsed").value(hasItem(DEFAULT_MARGIN_CLOSEOUT_MARGIN_USED.doubleValue())))
            .andExpect(jsonPath("$.[*].marginCloseoutPercent").value(hasItem(DEFAULT_MARGIN_CLOSEOUT_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].marginCloseoutPositionValue").value(hasItem(DEFAULT_MARGIN_CLOSEOUT_POSITION_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].withdrawalLimit").value(hasItem(DEFAULT_WITHDRAWAL_LIMIT.doubleValue())))
            .andExpect(jsonPath("$.[*].marginCallMarginUsed").value(hasItem(DEFAULT_MARGIN_CALL_MARGIN_USED.doubleValue())))
            .andExpect(jsonPath("$.[*].marginCallPercent").value(hasItem(DEFAULT_MARGIN_CALL_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastTransactionID").value(hasItem(DEFAULT_LAST_TRANSACTION_ID.toString())));
    }

    @Test
    @Transactional
    public void getOandaAccount() throws Exception {
        // Initialize the database
        oandaAccountRepository.saveAndFlush(oandaAccount);

        // Get the oandaAccount
        restOandaAccountMockMvc.perform(get("/api/oanda-accounts/{id}", oandaAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oandaAccount.getId().intValue()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.createdByUserID").value(DEFAULT_CREATED_BY_USER_ID))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.pl").value(DEFAULT_PL.doubleValue()))
            .andExpect(jsonPath("$.resettablePL").value(DEFAULT_RESETTABLE_PL.doubleValue()))
            .andExpect(jsonPath("$.resettabledPLTime").value(DEFAULT_RESETTABLED_PL_TIME.toString()))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.marginRate").value(DEFAULT_MARGIN_RATE.doubleValue()))
            .andExpect(jsonPath("$.marginCallEnterTime").value(DEFAULT_MARGIN_CALL_ENTER_TIME.toString()))
            .andExpect(jsonPath("$.marginCallExtensionCount").value(DEFAULT_MARGIN_CALL_EXTENSION_COUNT))
            .andExpect(jsonPath("$.lastMarginCallExtensionTime").value(DEFAULT_LAST_MARGIN_CALL_EXTENSION_TIME.toString()))
            .andExpect(jsonPath("$.openTradeCount").value(DEFAULT_OPEN_TRADE_COUNT))
            .andExpect(jsonPath("$.openPositionCount").value(DEFAULT_OPEN_POSITION_COUNT))
            .andExpect(jsonPath("$.pendingOrderCount").value(DEFAULT_PENDING_ORDER_COUNT))
            .andExpect(jsonPath("$.hedgingEnabled").value(DEFAULT_HEDGING_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.unrealizedPL").value(DEFAULT_UNREALIZED_PL.doubleValue()))
            .andExpect(jsonPath("$.nAV").value(DEFAULT_N_AV.doubleValue()))
            .andExpect(jsonPath("$.marginUsed").value(DEFAULT_MARGIN_USED.doubleValue()))
            .andExpect(jsonPath("$.marginAvailable").value(DEFAULT_MARGIN_AVAILABLE.doubleValue()))
            .andExpect(jsonPath("$.positionValue").value(DEFAULT_POSITION_VALUE.doubleValue()))
            .andExpect(jsonPath("$.marginCloseoutUnrealizedPL").value(DEFAULT_MARGIN_CLOSEOUT_UNREALIZED_PL.doubleValue()))
            .andExpect(jsonPath("$.marginCloseoutNAV").value(DEFAULT_MARGIN_CLOSEOUT_NAV.doubleValue()))
            .andExpect(jsonPath("$.marginCloseoutMarginUsed").value(DEFAULT_MARGIN_CLOSEOUT_MARGIN_USED.doubleValue()))
            .andExpect(jsonPath("$.marginCloseoutPercent").value(DEFAULT_MARGIN_CLOSEOUT_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.marginCloseoutPositionValue").value(DEFAULT_MARGIN_CLOSEOUT_POSITION_VALUE.doubleValue()))
            .andExpect(jsonPath("$.withdrawalLimit").value(DEFAULT_WITHDRAWAL_LIMIT.doubleValue()))
            .andExpect(jsonPath("$.marginCallMarginUsed").value(DEFAULT_MARGIN_CALL_MARGIN_USED.doubleValue()))
            .andExpect(jsonPath("$.marginCallPercent").value(DEFAULT_MARGIN_CALL_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.lastTransactionID").value(DEFAULT_LAST_TRANSACTION_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOandaAccount() throws Exception {
        // Get the oandaAccount
        restOandaAccountMockMvc.perform(get("/api/oanda-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOandaAccount() throws Exception {
        // Initialize the database
        oandaAccountRepository.saveAndFlush(oandaAccount);
        int databaseSizeBeforeUpdate = oandaAccountRepository.findAll().size();

        // Update the oandaAccount
        OandaAccount updatedOandaAccount = oandaAccountRepository.findOne(oandaAccount.getId());
        updatedOandaAccount
            .alias(UPDATED_ALIAS)
            .balance(UPDATED_BALANCE)
            .currency(UPDATED_CURRENCY)
            .createdByUserID(UPDATED_CREATED_BY_USER_ID)
            .createdTime(UPDATED_CREATED_TIME)
            .pl(UPDATED_PL)
            .resettablePL(UPDATED_RESETTABLE_PL)
            .resettabledPLTime(UPDATED_RESETTABLED_PL_TIME)
            .commission(UPDATED_COMMISSION)
            .marginRate(UPDATED_MARGIN_RATE)
            .marginCallEnterTime(UPDATED_MARGIN_CALL_ENTER_TIME)
            .marginCallExtensionCount(UPDATED_MARGIN_CALL_EXTENSION_COUNT)
            .lastMarginCallExtensionTime(UPDATED_LAST_MARGIN_CALL_EXTENSION_TIME)
            .openTradeCount(UPDATED_OPEN_TRADE_COUNT)
            .openPositionCount(UPDATED_OPEN_POSITION_COUNT)
            .pendingOrderCount(UPDATED_PENDING_ORDER_COUNT)
            .hedgingEnabled(UPDATED_HEDGING_ENABLED)
            .unrealizedPL(UPDATED_UNREALIZED_PL)
            .nAV(UPDATED_N_AV)
            .marginUsed(UPDATED_MARGIN_USED)
            .marginAvailable(UPDATED_MARGIN_AVAILABLE)
            .positionValue(UPDATED_POSITION_VALUE)
            .marginCloseoutUnrealizedPL(UPDATED_MARGIN_CLOSEOUT_UNREALIZED_PL)
            .marginCloseoutNAV(UPDATED_MARGIN_CLOSEOUT_NAV)
            .marginCloseoutMarginUsed(UPDATED_MARGIN_CLOSEOUT_MARGIN_USED)
            .marginCloseoutPercent(UPDATED_MARGIN_CLOSEOUT_PERCENT)
            .marginCloseoutPositionValue(UPDATED_MARGIN_CLOSEOUT_POSITION_VALUE)
            .withdrawalLimit(UPDATED_WITHDRAWAL_LIMIT)
            .marginCallMarginUsed(UPDATED_MARGIN_CALL_MARGIN_USED)
            .marginCallPercent(UPDATED_MARGIN_CALL_PERCENT)
            .lastTransactionID(UPDATED_LAST_TRANSACTION_ID);

        restOandaAccountMockMvc.perform(put("/api/oanda-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOandaAccount)))
            .andExpect(status().isOk());

        // Validate the OandaAccount in the database
        List<OandaAccount> oandaAccountList = oandaAccountRepository.findAll();
        assertThat(oandaAccountList).hasSize(databaseSizeBeforeUpdate);
        OandaAccount testOandaAccount = oandaAccountList.get(oandaAccountList.size() - 1);
        assertThat(testOandaAccount.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testOandaAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testOandaAccount.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testOandaAccount.getCreatedByUserID()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testOandaAccount.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testOandaAccount.getPl()).isEqualTo(UPDATED_PL);
        assertThat(testOandaAccount.getResettablePL()).isEqualTo(UPDATED_RESETTABLE_PL);
        assertThat(testOandaAccount.getResettabledPLTime()).isEqualTo(UPDATED_RESETTABLED_PL_TIME);
        assertThat(testOandaAccount.getCommission()).isEqualTo(UPDATED_COMMISSION);
        assertThat(testOandaAccount.getMarginRate()).isEqualTo(UPDATED_MARGIN_RATE);
        assertThat(testOandaAccount.getMarginCallEnterTime()).isEqualTo(UPDATED_MARGIN_CALL_ENTER_TIME);
        assertThat(testOandaAccount.getMarginCallExtensionCount()).isEqualTo(UPDATED_MARGIN_CALL_EXTENSION_COUNT);
        assertThat(testOandaAccount.getLastMarginCallExtensionTime()).isEqualTo(UPDATED_LAST_MARGIN_CALL_EXTENSION_TIME);
        assertThat(testOandaAccount.getOpenTradeCount()).isEqualTo(UPDATED_OPEN_TRADE_COUNT);
        assertThat(testOandaAccount.getOpenPositionCount()).isEqualTo(UPDATED_OPEN_POSITION_COUNT);
        assertThat(testOandaAccount.getPendingOrderCount()).isEqualTo(UPDATED_PENDING_ORDER_COUNT);
        assertThat(testOandaAccount.isHedgingEnabled()).isEqualTo(UPDATED_HEDGING_ENABLED);
        assertThat(testOandaAccount.getUnrealizedPL()).isEqualTo(UPDATED_UNREALIZED_PL);
        assertThat(testOandaAccount.getnAV()).isEqualTo(UPDATED_N_AV);
        assertThat(testOandaAccount.getMarginUsed()).isEqualTo(UPDATED_MARGIN_USED);
        assertThat(testOandaAccount.getMarginAvailable()).isEqualTo(UPDATED_MARGIN_AVAILABLE);
        assertThat(testOandaAccount.getPositionValue()).isEqualTo(UPDATED_POSITION_VALUE);
        assertThat(testOandaAccount.getMarginCloseoutUnrealizedPL()).isEqualTo(UPDATED_MARGIN_CLOSEOUT_UNREALIZED_PL);
        assertThat(testOandaAccount.getMarginCloseoutNAV()).isEqualTo(UPDATED_MARGIN_CLOSEOUT_NAV);
        assertThat(testOandaAccount.getMarginCloseoutMarginUsed()).isEqualTo(UPDATED_MARGIN_CLOSEOUT_MARGIN_USED);
        assertThat(testOandaAccount.getMarginCloseoutPercent()).isEqualTo(UPDATED_MARGIN_CLOSEOUT_PERCENT);
        assertThat(testOandaAccount.getMarginCloseoutPositionValue()).isEqualTo(UPDATED_MARGIN_CLOSEOUT_POSITION_VALUE);
        assertThat(testOandaAccount.getWithdrawalLimit()).isEqualTo(UPDATED_WITHDRAWAL_LIMIT);
        assertThat(testOandaAccount.getMarginCallMarginUsed()).isEqualTo(UPDATED_MARGIN_CALL_MARGIN_USED);
        assertThat(testOandaAccount.getMarginCallPercent()).isEqualTo(UPDATED_MARGIN_CALL_PERCENT);
        assertThat(testOandaAccount.getLastTransactionID()).isEqualTo(UPDATED_LAST_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingOandaAccount() throws Exception {
        int databaseSizeBeforeUpdate = oandaAccountRepository.findAll().size();

        // Create the OandaAccount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOandaAccountMockMvc.perform(put("/api/oanda-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oandaAccount)))
            .andExpect(status().isCreated());

        // Validate the OandaAccount in the database
        List<OandaAccount> oandaAccountList = oandaAccountRepository.findAll();
        assertThat(oandaAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOandaAccount() throws Exception {
        // Initialize the database
        oandaAccountRepository.saveAndFlush(oandaAccount);
        int databaseSizeBeforeDelete = oandaAccountRepository.findAll().size();

        // Get the oandaAccount
        restOandaAccountMockMvc.perform(delete("/api/oanda-accounts/{id}", oandaAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OandaAccount> oandaAccountList = oandaAccountRepository.findAll();
        assertThat(oandaAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OandaAccount.class);
        OandaAccount oandaAccount1 = new OandaAccount();
        oandaAccount1.setId(1L);
        OandaAccount oandaAccount2 = new OandaAccount();
        oandaAccount2.setId(oandaAccount1.getId());
        assertThat(oandaAccount1).isEqualTo(oandaAccount2);
        oandaAccount2.setId(2L);
        assertThat(oandaAccount1).isNotEqualTo(oandaAccount2);
        oandaAccount1.setId(null);
        assertThat(oandaAccount1).isNotEqualTo(oandaAccount2);
    }
}
