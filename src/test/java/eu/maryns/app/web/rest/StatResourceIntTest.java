package eu.maryns.app.web.rest;

import eu.maryns.app.HipsterApp;

import eu.maryns.app.domain.Stat;
import eu.maryns.app.repository.StatRepository;
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
 * Test class for the StatResource REST controller.
 *
 * @see StatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipsterApp.class)
public class StatResourceIntTest {

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final CandleStickGranularity DEFAULT_GRANULARITY = CandleStickGranularity.S5;
    private static final CandleStickGranularity UPDATED_GRANULARITY = CandleStickGranularity.S10;

    private static final Integer DEFAULT_NUMBER_OF_CANDLES = 1;
    private static final Integer UPDATED_NUMBER_OF_CANDLES = 2;

    private static final Instant DEFAULT_FIRST = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRST = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StatRepository statRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStatMockMvc;

    private Stat stat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatResource statResource = new StatResource(statRepository);
        this.restStatMockMvc = MockMvcBuilders.standaloneSetup(statResource)
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
    public static Stat createEntity(EntityManager em) {
        Stat stat = new Stat()
            .lastUpdated(DEFAULT_LAST_UPDATED)
            .granularity(DEFAULT_GRANULARITY)
            .numberOfCandles(DEFAULT_NUMBER_OF_CANDLES)
            .first(DEFAULT_FIRST)
            .last(DEFAULT_LAST);
        return stat;
    }

    @Before
    public void initTest() {
        stat = createEntity(em);
    }

    @Test
    @Transactional
    public void createStat() throws Exception {
        int databaseSizeBeforeCreate = statRepository.findAll().size();

        // Create the Stat
        restStatMockMvc.perform(post("/api/stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stat)))
            .andExpect(status().isCreated());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeCreate + 1);
        Stat testStat = statList.get(statList.size() - 1);
        assertThat(testStat.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
        assertThat(testStat.getGranularity()).isEqualTo(DEFAULT_GRANULARITY);
        assertThat(testStat.getNumberOfCandles()).isEqualTo(DEFAULT_NUMBER_OF_CANDLES);
        assertThat(testStat.getFirst()).isEqualTo(DEFAULT_FIRST);
        assertThat(testStat.getLast()).isEqualTo(DEFAULT_LAST);
    }

    @Test
    @Transactional
    public void createStatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statRepository.findAll().size();

        // Create the Stat with an existing ID
        stat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatMockMvc.perform(post("/api/stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stat)))
            .andExpect(status().isBadRequest());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStats() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        // Get all the statList
        restStatMockMvc.perform(get("/api/stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stat.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].granularity").value(hasItem(DEFAULT_GRANULARITY.toString())))
            .andExpect(jsonPath("$.[*].numberOfCandles").value(hasItem(DEFAULT_NUMBER_OF_CANDLES)))
            .andExpect(jsonPath("$.[*].first").value(hasItem(DEFAULT_FIRST.toString())))
            .andExpect(jsonPath("$.[*].last").value(hasItem(DEFAULT_LAST.toString())));
    }

    @Test
    @Transactional
    public void getStat() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);

        // Get the stat
        restStatMockMvc.perform(get("/api/stats/{id}", stat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stat.getId().intValue()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()))
            .andExpect(jsonPath("$.granularity").value(DEFAULT_GRANULARITY.toString()))
            .andExpect(jsonPath("$.numberOfCandles").value(DEFAULT_NUMBER_OF_CANDLES))
            .andExpect(jsonPath("$.first").value(DEFAULT_FIRST.toString()))
            .andExpect(jsonPath("$.last").value(DEFAULT_LAST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStat() throws Exception {
        // Get the stat
        restStatMockMvc.perform(get("/api/stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStat() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);
        int databaseSizeBeforeUpdate = statRepository.findAll().size();

        // Update the stat
        Stat updatedStat = statRepository.findOne(stat.getId());
        updatedStat
            .lastUpdated(UPDATED_LAST_UPDATED)
            .granularity(UPDATED_GRANULARITY)
            .numberOfCandles(UPDATED_NUMBER_OF_CANDLES)
            .first(UPDATED_FIRST)
            .last(UPDATED_LAST);

        restStatMockMvc.perform(put("/api/stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStat)))
            .andExpect(status().isOk());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate);
        Stat testStat = statList.get(statList.size() - 1);
        assertThat(testStat.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
        assertThat(testStat.getGranularity()).isEqualTo(UPDATED_GRANULARITY);
        assertThat(testStat.getNumberOfCandles()).isEqualTo(UPDATED_NUMBER_OF_CANDLES);
        assertThat(testStat.getFirst()).isEqualTo(UPDATED_FIRST);
        assertThat(testStat.getLast()).isEqualTo(UPDATED_LAST);
    }

    @Test
    @Transactional
    public void updateNonExistingStat() throws Exception {
        int databaseSizeBeforeUpdate = statRepository.findAll().size();

        // Create the Stat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStatMockMvc.perform(put("/api/stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stat)))
            .andExpect(status().isCreated());

        // Validate the Stat in the database
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStat() throws Exception {
        // Initialize the database
        statRepository.saveAndFlush(stat);
        int databaseSizeBeforeDelete = statRepository.findAll().size();

        // Get the stat
        restStatMockMvc.perform(delete("/api/stats/{id}", stat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stat> statList = statRepository.findAll();
        assertThat(statList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stat.class);
        Stat stat1 = new Stat();
        stat1.setId(1L);
        Stat stat2 = new Stat();
        stat2.setId(stat1.getId());
        assertThat(stat1).isEqualTo(stat2);
        stat2.setId(2L);
        assertThat(stat1).isNotEqualTo(stat2);
        stat1.setId(null);
        assertThat(stat1).isNotEqualTo(stat2);
    }
}
