package eu.maryns.app.web.rest;

import eu.maryns.app.HipsterApp;

import eu.maryns.app.domain.Instrument;
import eu.maryns.app.repository.InstrumentRepository;
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
import java.util.List;

import static eu.maryns.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InstrumentResource REST controller.
 *
 * @see InstrumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipsterApp.class)
public class InstrumentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PIP_LOCATION = 1;
    private static final Integer UPDATED_PIP_LOCATION = 2;

    private static final Integer DEFAULT_DISPLAY_PRECISION = 1;
    private static final Integer UPDATED_DISPLAY_PRECISION = 2;

    private static final Integer DEFAULT_TRADE_UNITS_PRECISION = 1;
    private static final Integer UPDATED_TRADE_UNITS_PRECISION = 2;

    private static final Double DEFAULT_MINIMUM_TRADE_SIZE = 1D;
    private static final Double UPDATED_MINIMUM_TRADE_SIZE = 2D;

    private static final Double DEFAULT_MAXIMUM_TRAILING_STOP_DISTANCE = 1D;
    private static final Double UPDATED_MAXIMUM_TRAILING_STOP_DISTANCE = 2D;

    private static final Double DEFAULT_MINIMUM_TRAILING_STOP_DISTANCE = 1D;
    private static final Double UPDATED_MINIMUM_TRAILING_STOP_DISTANCE = 2D;

    private static final Double DEFAULT_MAXIMUM_POSITION_SIZE = 1D;
    private static final Double UPDATED_MAXIMUM_POSITION_SIZE = 2D;

    private static final Double DEFAULT_MAXIMUM_ORDER_UNITS = 1D;
    private static final Double UPDATED_MAXIMUM_ORDER_UNITS = 2D;

    private static final Double DEFAULT_MARGIN_RATE = 1D;
    private static final Double UPDATED_MARGIN_RATE = 2D;

    private static final Double DEFAULT_COMMISSION = 1D;
    private static final Double UPDATED_COMMISSION = 2D;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInstrumentMockMvc;

    private Instrument instrument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstrumentResource instrumentResource = new InstrumentResource(instrumentRepository);
        this.restInstrumentMockMvc = MockMvcBuilders.standaloneSetup(instrumentResource)
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
    public static Instrument createEntity(EntityManager em) {
        Instrument instrument = new Instrument()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .displayName(DEFAULT_DISPLAY_NAME)
            .pipLocation(DEFAULT_PIP_LOCATION)
            .displayPrecision(DEFAULT_DISPLAY_PRECISION)
            .tradeUnitsPrecision(DEFAULT_TRADE_UNITS_PRECISION)
            .minimumTradeSize(DEFAULT_MINIMUM_TRADE_SIZE)
            .maximumTrailingStopDistance(DEFAULT_MAXIMUM_TRAILING_STOP_DISTANCE)
            .minimumTrailingStopDistance(DEFAULT_MINIMUM_TRAILING_STOP_DISTANCE)
            .maximumPositionSize(DEFAULT_MAXIMUM_POSITION_SIZE)
            .maximumOrderUnits(DEFAULT_MAXIMUM_ORDER_UNITS)
            .marginRate(DEFAULT_MARGIN_RATE)
            .commission(DEFAULT_COMMISSION);
        return instrument;
    }

    @Before
    public void initTest() {
        instrument = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstrument() throws Exception {
        int databaseSizeBeforeCreate = instrumentRepository.findAll().size();

        // Create the Instrument
        restInstrumentMockMvc.perform(post("/api/instruments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isCreated());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeCreate + 1);
        Instrument testInstrument = instrumentList.get(instrumentList.size() - 1);
        assertThat(testInstrument.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstrument.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInstrument.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testInstrument.getPipLocation()).isEqualTo(DEFAULT_PIP_LOCATION);
        assertThat(testInstrument.getDisplayPrecision()).isEqualTo(DEFAULT_DISPLAY_PRECISION);
        assertThat(testInstrument.getTradeUnitsPrecision()).isEqualTo(DEFAULT_TRADE_UNITS_PRECISION);
        assertThat(testInstrument.getMinimumTradeSize()).isEqualTo(DEFAULT_MINIMUM_TRADE_SIZE);
        assertThat(testInstrument.getMaximumTrailingStopDistance()).isEqualTo(DEFAULT_MAXIMUM_TRAILING_STOP_DISTANCE);
        assertThat(testInstrument.getMinimumTrailingStopDistance()).isEqualTo(DEFAULT_MINIMUM_TRAILING_STOP_DISTANCE);
        assertThat(testInstrument.getMaximumPositionSize()).isEqualTo(DEFAULT_MAXIMUM_POSITION_SIZE);
        assertThat(testInstrument.getMaximumOrderUnits()).isEqualTo(DEFAULT_MAXIMUM_ORDER_UNITS);
        assertThat(testInstrument.getMarginRate()).isEqualTo(DEFAULT_MARGIN_RATE);
        assertThat(testInstrument.getCommission()).isEqualTo(DEFAULT_COMMISSION);
    }

    @Test
    @Transactional
    public void createInstrumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instrumentRepository.findAll().size();

        // Create the Instrument with an existing ID
        instrument.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstrumentMockMvc.perform(post("/api/instruments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isBadRequest());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInstruments() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get all the instrumentList
        restInstrumentMockMvc.perform(get("/api/instruments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instrument.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME.toString())))
            .andExpect(jsonPath("$.[*].pipLocation").value(hasItem(DEFAULT_PIP_LOCATION)))
            .andExpect(jsonPath("$.[*].displayPrecision").value(hasItem(DEFAULT_DISPLAY_PRECISION)))
            .andExpect(jsonPath("$.[*].tradeUnitsPrecision").value(hasItem(DEFAULT_TRADE_UNITS_PRECISION)))
            .andExpect(jsonPath("$.[*].minimumTradeSize").value(hasItem(DEFAULT_MINIMUM_TRADE_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].maximumTrailingStopDistance").value(hasItem(DEFAULT_MAXIMUM_TRAILING_STOP_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].minimumTrailingStopDistance").value(hasItem(DEFAULT_MINIMUM_TRAILING_STOP_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].maximumPositionSize").value(hasItem(DEFAULT_MAXIMUM_POSITION_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].maximumOrderUnits").value(hasItem(DEFAULT_MAXIMUM_ORDER_UNITS.doubleValue())))
            .andExpect(jsonPath("$.[*].marginRate").value(hasItem(DEFAULT_MARGIN_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION.doubleValue())));
    }

    @Test
    @Transactional
    public void getInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get the instrument
        restInstrumentMockMvc.perform(get("/api/instruments/{id}", instrument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(instrument.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME.toString()))
            .andExpect(jsonPath("$.pipLocation").value(DEFAULT_PIP_LOCATION))
            .andExpect(jsonPath("$.displayPrecision").value(DEFAULT_DISPLAY_PRECISION))
            .andExpect(jsonPath("$.tradeUnitsPrecision").value(DEFAULT_TRADE_UNITS_PRECISION))
            .andExpect(jsonPath("$.minimumTradeSize").value(DEFAULT_MINIMUM_TRADE_SIZE.doubleValue()))
            .andExpect(jsonPath("$.maximumTrailingStopDistance").value(DEFAULT_MAXIMUM_TRAILING_STOP_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.minimumTrailingStopDistance").value(DEFAULT_MINIMUM_TRAILING_STOP_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.maximumPositionSize").value(DEFAULT_MAXIMUM_POSITION_SIZE.doubleValue()))
            .andExpect(jsonPath("$.maximumOrderUnits").value(DEFAULT_MAXIMUM_ORDER_UNITS.doubleValue()))
            .andExpect(jsonPath("$.marginRate").value(DEFAULT_MARGIN_RATE.doubleValue()))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstrument() throws Exception {
        // Get the instrument
        restInstrumentMockMvc.perform(get("/api/instruments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);
        int databaseSizeBeforeUpdate = instrumentRepository.findAll().size();

        // Update the instrument
        Instrument updatedInstrument = instrumentRepository.findOne(instrument.getId());
        updatedInstrument
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .displayName(UPDATED_DISPLAY_NAME)
            .pipLocation(UPDATED_PIP_LOCATION)
            .displayPrecision(UPDATED_DISPLAY_PRECISION)
            .tradeUnitsPrecision(UPDATED_TRADE_UNITS_PRECISION)
            .minimumTradeSize(UPDATED_MINIMUM_TRADE_SIZE)
            .maximumTrailingStopDistance(UPDATED_MAXIMUM_TRAILING_STOP_DISTANCE)
            .minimumTrailingStopDistance(UPDATED_MINIMUM_TRAILING_STOP_DISTANCE)
            .maximumPositionSize(UPDATED_MAXIMUM_POSITION_SIZE)
            .maximumOrderUnits(UPDATED_MAXIMUM_ORDER_UNITS)
            .marginRate(UPDATED_MARGIN_RATE)
            .commission(UPDATED_COMMISSION);

        restInstrumentMockMvc.perform(put("/api/instruments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstrument)))
            .andExpect(status().isOk());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeUpdate);
        Instrument testInstrument = instrumentList.get(instrumentList.size() - 1);
        assertThat(testInstrument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstrument.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInstrument.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testInstrument.getPipLocation()).isEqualTo(UPDATED_PIP_LOCATION);
        assertThat(testInstrument.getDisplayPrecision()).isEqualTo(UPDATED_DISPLAY_PRECISION);
        assertThat(testInstrument.getTradeUnitsPrecision()).isEqualTo(UPDATED_TRADE_UNITS_PRECISION);
        assertThat(testInstrument.getMinimumTradeSize()).isEqualTo(UPDATED_MINIMUM_TRADE_SIZE);
        assertThat(testInstrument.getMaximumTrailingStopDistance()).isEqualTo(UPDATED_MAXIMUM_TRAILING_STOP_DISTANCE);
        assertThat(testInstrument.getMinimumTrailingStopDistance()).isEqualTo(UPDATED_MINIMUM_TRAILING_STOP_DISTANCE);
        assertThat(testInstrument.getMaximumPositionSize()).isEqualTo(UPDATED_MAXIMUM_POSITION_SIZE);
        assertThat(testInstrument.getMaximumOrderUnits()).isEqualTo(UPDATED_MAXIMUM_ORDER_UNITS);
        assertThat(testInstrument.getMarginRate()).isEqualTo(UPDATED_MARGIN_RATE);
        assertThat(testInstrument.getCommission()).isEqualTo(UPDATED_COMMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingInstrument() throws Exception {
        int databaseSizeBeforeUpdate = instrumentRepository.findAll().size();

        // Create the Instrument

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInstrumentMockMvc.perform(put("/api/instruments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(instrument)))
            .andExpect(status().isCreated());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);
        int databaseSizeBeforeDelete = instrumentRepository.findAll().size();

        // Get the instrument
        restInstrumentMockMvc.perform(delete("/api/instruments/{id}", instrument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Instrument.class);
        Instrument instrument1 = new Instrument();
        instrument1.setId(1L);
        Instrument instrument2 = new Instrument();
        instrument2.setId(instrument1.getId());
        assertThat(instrument1).isEqualTo(instrument2);
        instrument2.setId(2L);
        assertThat(instrument1).isNotEqualTo(instrument2);
        instrument1.setId(null);
        assertThat(instrument1).isNotEqualTo(instrument2);
    }
}
