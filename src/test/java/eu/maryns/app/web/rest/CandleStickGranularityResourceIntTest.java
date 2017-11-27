package eu.maryns.app.web.rest;

import eu.maryns.app.HipsterApp;

import eu.maryns.app.domain.CandleStickGranularity;
import eu.maryns.app.repository.CandleStickGranularityRepository;
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
 * Test class for the CandleStickGranularityResource REST controller.
 *
 * @see CandleStickGranularityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipsterApp.class)
public class CandleStickGranularityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CandleStickGranularityRepository candleStickGranularityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandleStickGranularityMockMvc;

    private CandleStickGranularity candleStickGranularity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandleStickGranularityResource candleStickGranularityResource = new CandleStickGranularityResource(candleStickGranularityRepository);
        this.restCandleStickGranularityMockMvc = MockMvcBuilders.standaloneSetup(candleStickGranularityResource)
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
    public static CandleStickGranularity createEntity(EntityManager em) {
        CandleStickGranularity candleStickGranularity = new CandleStickGranularity()
            .name(DEFAULT_NAME);
        return candleStickGranularity;
    }

    @Before
    public void initTest() {
        candleStickGranularity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandleStickGranularity() throws Exception {
        int databaseSizeBeforeCreate = candleStickGranularityRepository.findAll().size();

        // Create the CandleStickGranularity
        restCandleStickGranularityMockMvc.perform(post("/api/candle-stick-granularities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candleStickGranularity)))
            .andExpect(status().isCreated());

        // Validate the CandleStickGranularity in the database
        List<CandleStickGranularity> candleStickGranularityList = candleStickGranularityRepository.findAll();
        assertThat(candleStickGranularityList).hasSize(databaseSizeBeforeCreate + 1);
        CandleStickGranularity testCandleStickGranularity = candleStickGranularityList.get(candleStickGranularityList.size() - 1);
        assertThat(testCandleStickGranularity.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCandleStickGranularityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candleStickGranularityRepository.findAll().size();

        // Create the CandleStickGranularity with an existing ID
        candleStickGranularity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandleStickGranularityMockMvc.perform(post("/api/candle-stick-granularities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candleStickGranularity)))
            .andExpect(status().isBadRequest());

        // Validate the CandleStickGranularity in the database
        List<CandleStickGranularity> candleStickGranularityList = candleStickGranularityRepository.findAll();
        assertThat(candleStickGranularityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCandleStickGranularities() throws Exception {
        // Initialize the database
        candleStickGranularityRepository.saveAndFlush(candleStickGranularity);

        // Get all the candleStickGranularityList
        restCandleStickGranularityMockMvc.perform(get("/api/candle-stick-granularities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candleStickGranularity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCandleStickGranularity() throws Exception {
        // Initialize the database
        candleStickGranularityRepository.saveAndFlush(candleStickGranularity);

        // Get the candleStickGranularity
        restCandleStickGranularityMockMvc.perform(get("/api/candle-stick-granularities/{id}", candleStickGranularity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candleStickGranularity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandleStickGranularity() throws Exception {
        // Get the candleStickGranularity
        restCandleStickGranularityMockMvc.perform(get("/api/candle-stick-granularities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandleStickGranularity() throws Exception {
        // Initialize the database
        candleStickGranularityRepository.saveAndFlush(candleStickGranularity);
        int databaseSizeBeforeUpdate = candleStickGranularityRepository.findAll().size();

        // Update the candleStickGranularity
        CandleStickGranularity updatedCandleStickGranularity = candleStickGranularityRepository.findOne(candleStickGranularity.getId());
        updatedCandleStickGranularity
            .name(UPDATED_NAME);

        restCandleStickGranularityMockMvc.perform(put("/api/candle-stick-granularities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandleStickGranularity)))
            .andExpect(status().isOk());

        // Validate the CandleStickGranularity in the database
        List<CandleStickGranularity> candleStickGranularityList = candleStickGranularityRepository.findAll();
        assertThat(candleStickGranularityList).hasSize(databaseSizeBeforeUpdate);
        CandleStickGranularity testCandleStickGranularity = candleStickGranularityList.get(candleStickGranularityList.size() - 1);
        assertThat(testCandleStickGranularity.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCandleStickGranularity() throws Exception {
        int databaseSizeBeforeUpdate = candleStickGranularityRepository.findAll().size();

        // Create the CandleStickGranularity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandleStickGranularityMockMvc.perform(put("/api/candle-stick-granularities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candleStickGranularity)))
            .andExpect(status().isCreated());

        // Validate the CandleStickGranularity in the database
        List<CandleStickGranularity> candleStickGranularityList = candleStickGranularityRepository.findAll();
        assertThat(candleStickGranularityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandleStickGranularity() throws Exception {
        // Initialize the database
        candleStickGranularityRepository.saveAndFlush(candleStickGranularity);
        int databaseSizeBeforeDelete = candleStickGranularityRepository.findAll().size();

        // Get the candleStickGranularity
        restCandleStickGranularityMockMvc.perform(delete("/api/candle-stick-granularities/{id}", candleStickGranularity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CandleStickGranularity> candleStickGranularityList = candleStickGranularityRepository.findAll();
        assertThat(candleStickGranularityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandleStickGranularity.class);
        CandleStickGranularity candleStickGranularity1 = new CandleStickGranularity();
        candleStickGranularity1.setId(1L);
        CandleStickGranularity candleStickGranularity2 = new CandleStickGranularity();
        candleStickGranularity2.setId(candleStickGranularity1.getId());
        assertThat(candleStickGranularity1).isEqualTo(candleStickGranularity2);
        candleStickGranularity2.setId(2L);
        assertThat(candleStickGranularity1).isNotEqualTo(candleStickGranularity2);
        candleStickGranularity1.setId(null);
        assertThat(candleStickGranularity1).isNotEqualTo(candleStickGranularity2);
    }
}
