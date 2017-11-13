package eu.maryns.app.web.rest;

import eu.maryns.app.HipsterApp;

import eu.maryns.app.domain.CandleStick;
import eu.maryns.app.repository.CandleStickRepository;
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

import eu.maryns.app.domain.enumeration.CandleStickGranularity;
/**
 * Test class for the CandleStickResource REST controller.
 *
 * @see CandleStickResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipsterApp.class)
public class CandleStickResourceIntTest {

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_VOLUME = 1;
    private static final Integer UPDATED_VOLUME = 2;

    private static final Boolean DEFAULT_COMPLETE = false;
    private static final Boolean UPDATED_COMPLETE = true;

    private static final CandleStickGranularity DEFAULT_GRANULARITY = CandleStickGranularity.S5;
    private static final CandleStickGranularity UPDATED_GRANULARITY = CandleStickGranularity.S10;

    @Autowired
    private CandleStickRepository candleStickRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandleStickMockMvc;

    private CandleStick candleStick;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandleStickResource candleStickResource = new CandleStickResource(candleStickRepository);
        this.restCandleStickMockMvc = MockMvcBuilders.standaloneSetup(candleStickResource)
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
    public static CandleStick createEntity(EntityManager em) {
        CandleStick candleStick = new CandleStick()
            .time(DEFAULT_TIME)
            .volume(DEFAULT_VOLUME)
            .complete(DEFAULT_COMPLETE)
            .granularity(DEFAULT_GRANULARITY);
        return candleStick;
    }

    @Before
    public void initTest() {
        candleStick = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandleStick() throws Exception {
        int databaseSizeBeforeCreate = candleStickRepository.findAll().size();

        // Create the CandleStick
        restCandleStickMockMvc.perform(post("/api/candle-sticks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candleStick)))
            .andExpect(status().isCreated());

        // Validate the CandleStick in the database
        List<CandleStick> candleStickList = candleStickRepository.findAll();
        assertThat(candleStickList).hasSize(databaseSizeBeforeCreate + 1);
        CandleStick testCandleStick = candleStickList.get(candleStickList.size() - 1);
        assertThat(testCandleStick.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testCandleStick.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testCandleStick.isComplete()).isEqualTo(DEFAULT_COMPLETE);
        assertThat(testCandleStick.getGranularity()).isEqualTo(DEFAULT_GRANULARITY);
    }

    @Test
    @Transactional
    public void createCandleStickWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candleStickRepository.findAll().size();

        // Create the CandleStick with an existing ID
        candleStick.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandleStickMockMvc.perform(post("/api/candle-sticks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candleStick)))
            .andExpect(status().isBadRequest());

        // Validate the CandleStick in the database
        List<CandleStick> candleStickList = candleStickRepository.findAll();
        assertThat(candleStickList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCandleSticks() throws Exception {
        // Initialize the database
        candleStickRepository.saveAndFlush(candleStick);

        // Get all the candleStickList
        restCandleStickMockMvc.perform(get("/api/candle-sticks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candleStick.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME)))
            .andExpect(jsonPath("$.[*].complete").value(hasItem(DEFAULT_COMPLETE.booleanValue())))
            .andExpect(jsonPath("$.[*].granularity").value(hasItem(DEFAULT_GRANULARITY.toString())));
    }

    @Test
    @Transactional
    public void getCandleStick() throws Exception {
        // Initialize the database
        candleStickRepository.saveAndFlush(candleStick);

        // Get the candleStick
        restCandleStickMockMvc.perform(get("/api/candle-sticks/{id}", candleStick.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candleStick.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME))
            .andExpect(jsonPath("$.complete").value(DEFAULT_COMPLETE.booleanValue()))
            .andExpect(jsonPath("$.granularity").value(DEFAULT_GRANULARITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandleStick() throws Exception {
        // Get the candleStick
        restCandleStickMockMvc.perform(get("/api/candle-sticks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandleStick() throws Exception {
        // Initialize the database
        candleStickRepository.saveAndFlush(candleStick);
        int databaseSizeBeforeUpdate = candleStickRepository.findAll().size();

        // Update the candleStick
        CandleStick updatedCandleStick = candleStickRepository.findOne(candleStick.getId());
        updatedCandleStick
            .time(UPDATED_TIME)
            .volume(UPDATED_VOLUME)
            .complete(UPDATED_COMPLETE)
            .granularity(UPDATED_GRANULARITY);

        restCandleStickMockMvc.perform(put("/api/candle-sticks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandleStick)))
            .andExpect(status().isOk());

        // Validate the CandleStick in the database
        List<CandleStick> candleStickList = candleStickRepository.findAll();
        assertThat(candleStickList).hasSize(databaseSizeBeforeUpdate);
        CandleStick testCandleStick = candleStickList.get(candleStickList.size() - 1);
        assertThat(testCandleStick.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testCandleStick.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testCandleStick.isComplete()).isEqualTo(UPDATED_COMPLETE);
        assertThat(testCandleStick.getGranularity()).isEqualTo(UPDATED_GRANULARITY);
    }

    @Test
    @Transactional
    public void updateNonExistingCandleStick() throws Exception {
        int databaseSizeBeforeUpdate = candleStickRepository.findAll().size();

        // Create the CandleStick

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandleStickMockMvc.perform(put("/api/candle-sticks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candleStick)))
            .andExpect(status().isCreated());

        // Validate the CandleStick in the database
        List<CandleStick> candleStickList = candleStickRepository.findAll();
        assertThat(candleStickList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandleStick() throws Exception {
        // Initialize the database
        candleStickRepository.saveAndFlush(candleStick);
        int databaseSizeBeforeDelete = candleStickRepository.findAll().size();

        // Get the candleStick
        restCandleStickMockMvc.perform(delete("/api/candle-sticks/{id}", candleStick.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CandleStick> candleStickList = candleStickRepository.findAll();
        assertThat(candleStickList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandleStick.class);
        CandleStick candleStick1 = new CandleStick();
        candleStick1.setId(1L);
        CandleStick candleStick2 = new CandleStick();
        candleStick2.setId(candleStick1.getId());
        assertThat(candleStick1).isEqualTo(candleStick2);
        candleStick2.setId(2L);
        assertThat(candleStick1).isNotEqualTo(candleStick2);
        candleStick1.setId(null);
        assertThat(candleStick1).isNotEqualTo(candleStick2);
    }
}
