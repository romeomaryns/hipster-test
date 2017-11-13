package eu.maryns.app.web.rest;

import eu.maryns.app.HipsterApp;

import eu.maryns.app.domain.CandleStickData;
import eu.maryns.app.repository.CandleStickDataRepository;
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
 * Test class for the CandleStickDataResource REST controller.
 *
 * @see CandleStickDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipsterApp.class)
public class CandleStickDataResourceIntTest {

    private static final Double DEFAULT_O = 1D;
    private static final Double UPDATED_O = 2D;

    private static final Double DEFAULT_H = 1D;
    private static final Double UPDATED_H = 2D;

    private static final Double DEFAULT_L = 1D;
    private static final Double UPDATED_L = 2D;

    private static final Double DEFAULT_C = 1D;
    private static final Double UPDATED_C = 2D;

    @Autowired
    private CandleStickDataRepository candleStickDataRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandleStickDataMockMvc;

    private CandleStickData candleStickData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandleStickDataResource candleStickDataResource = new CandleStickDataResource(candleStickDataRepository);
        this.restCandleStickDataMockMvc = MockMvcBuilders.standaloneSetup(candleStickDataResource)
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
    public static CandleStickData createEntity(EntityManager em) {
        CandleStickData candleStickData = new CandleStickData()
            .o(DEFAULT_O)
            .h(DEFAULT_H)
            .l(DEFAULT_L)
            .c(DEFAULT_C);
        return candleStickData;
    }

    @Before
    public void initTest() {
        candleStickData = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandleStickData() throws Exception {
        int databaseSizeBeforeCreate = candleStickDataRepository.findAll().size();

        // Create the CandleStickData
        restCandleStickDataMockMvc.perform(post("/api/candle-stick-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candleStickData)))
            .andExpect(status().isCreated());

        // Validate the CandleStickData in the database
        List<CandleStickData> candleStickDataList = candleStickDataRepository.findAll();
        assertThat(candleStickDataList).hasSize(databaseSizeBeforeCreate + 1);
        CandleStickData testCandleStickData = candleStickDataList.get(candleStickDataList.size() - 1);
        assertThat(testCandleStickData.getO()).isEqualTo(DEFAULT_O);
        assertThat(testCandleStickData.getH()).isEqualTo(DEFAULT_H);
        assertThat(testCandleStickData.getL()).isEqualTo(DEFAULT_L);
        assertThat(testCandleStickData.getC()).isEqualTo(DEFAULT_C);
    }

    @Test
    @Transactional
    public void createCandleStickDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candleStickDataRepository.findAll().size();

        // Create the CandleStickData with an existing ID
        candleStickData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandleStickDataMockMvc.perform(post("/api/candle-stick-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candleStickData)))
            .andExpect(status().isBadRequest());

        // Validate the CandleStickData in the database
        List<CandleStickData> candleStickDataList = candleStickDataRepository.findAll();
        assertThat(candleStickDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCandleStickData() throws Exception {
        // Initialize the database
        candleStickDataRepository.saveAndFlush(candleStickData);

        // Get all the candleStickDataList
        restCandleStickDataMockMvc.perform(get("/api/candle-stick-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candleStickData.getId().intValue())))
            .andExpect(jsonPath("$.[*].o").value(hasItem(DEFAULT_O.doubleValue())))
            .andExpect(jsonPath("$.[*].h").value(hasItem(DEFAULT_H.doubleValue())))
            .andExpect(jsonPath("$.[*].l").value(hasItem(DEFAULT_L.doubleValue())))
            .andExpect(jsonPath("$.[*].c").value(hasItem(DEFAULT_C.doubleValue())));
    }

    @Test
    @Transactional
    public void getCandleStickData() throws Exception {
        // Initialize the database
        candleStickDataRepository.saveAndFlush(candleStickData);

        // Get the candleStickData
        restCandleStickDataMockMvc.perform(get("/api/candle-stick-data/{id}", candleStickData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candleStickData.getId().intValue()))
            .andExpect(jsonPath("$.o").value(DEFAULT_O.doubleValue()))
            .andExpect(jsonPath("$.h").value(DEFAULT_H.doubleValue()))
            .andExpect(jsonPath("$.l").value(DEFAULT_L.doubleValue()))
            .andExpect(jsonPath("$.c").value(DEFAULT_C.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCandleStickData() throws Exception {
        // Get the candleStickData
        restCandleStickDataMockMvc.perform(get("/api/candle-stick-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandleStickData() throws Exception {
        // Initialize the database
        candleStickDataRepository.saveAndFlush(candleStickData);
        int databaseSizeBeforeUpdate = candleStickDataRepository.findAll().size();

        // Update the candleStickData
        CandleStickData updatedCandleStickData = candleStickDataRepository.findOne(candleStickData.getId());
        updatedCandleStickData
            .o(UPDATED_O)
            .h(UPDATED_H)
            .l(UPDATED_L)
            .c(UPDATED_C);

        restCandleStickDataMockMvc.perform(put("/api/candle-stick-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandleStickData)))
            .andExpect(status().isOk());

        // Validate the CandleStickData in the database
        List<CandleStickData> candleStickDataList = candleStickDataRepository.findAll();
        assertThat(candleStickDataList).hasSize(databaseSizeBeforeUpdate);
        CandleStickData testCandleStickData = candleStickDataList.get(candleStickDataList.size() - 1);
        assertThat(testCandleStickData.getO()).isEqualTo(UPDATED_O);
        assertThat(testCandleStickData.getH()).isEqualTo(UPDATED_H);
        assertThat(testCandleStickData.getL()).isEqualTo(UPDATED_L);
        assertThat(testCandleStickData.getC()).isEqualTo(UPDATED_C);
    }

    @Test
    @Transactional
    public void updateNonExistingCandleStickData() throws Exception {
        int databaseSizeBeforeUpdate = candleStickDataRepository.findAll().size();

        // Create the CandleStickData

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandleStickDataMockMvc.perform(put("/api/candle-stick-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candleStickData)))
            .andExpect(status().isCreated());

        // Validate the CandleStickData in the database
        List<CandleStickData> candleStickDataList = candleStickDataRepository.findAll();
        assertThat(candleStickDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandleStickData() throws Exception {
        // Initialize the database
        candleStickDataRepository.saveAndFlush(candleStickData);
        int databaseSizeBeforeDelete = candleStickDataRepository.findAll().size();

        // Get the candleStickData
        restCandleStickDataMockMvc.perform(delete("/api/candle-stick-data/{id}", candleStickData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CandleStickData> candleStickDataList = candleStickDataRepository.findAll();
        assertThat(candleStickDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandleStickData.class);
        CandleStickData candleStickData1 = new CandleStickData();
        candleStickData1.setId(1L);
        CandleStickData candleStickData2 = new CandleStickData();
        candleStickData2.setId(candleStickData1.getId());
        assertThat(candleStickData1).isEqualTo(candleStickData2);
        candleStickData2.setId(2L);
        assertThat(candleStickData1).isNotEqualTo(candleStickData2);
        candleStickData1.setId(null);
        assertThat(candleStickData1).isNotEqualTo(candleStickData2);
    }
}
