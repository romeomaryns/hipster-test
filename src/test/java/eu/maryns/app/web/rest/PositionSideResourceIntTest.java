package eu.maryns.app.web.rest;

import eu.maryns.app.HipsterApp;

import eu.maryns.app.domain.PositionSide;
import eu.maryns.app.repository.PositionSideRepository;
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
 * Test class for the PositionSideResource REST controller.
 *
 * @see PositionSideResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipsterApp.class)
public class PositionSideResourceIntTest {

    private static final Double DEFAULT_UNITS = 1D;
    private static final Double UPDATED_UNITS = 2D;

    private static final Double DEFAULT_AVERAGE_PRICE = 1D;
    private static final Double UPDATED_AVERAGE_PRICE = 2D;

    private static final Double DEFAULT_PL = 1D;
    private static final Double UPDATED_PL = 2D;

    private static final Double DEFAULT_UNREALIZED_PL = 1D;
    private static final Double UPDATED_UNREALIZED_PL = 2D;

    private static final Double DEFAULT_RESETTABLE_PL = 1D;
    private static final Double UPDATED_RESETTABLE_PL = 2D;

    @Autowired
    private PositionSideRepository positionSideRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPositionSideMockMvc;

    private PositionSide positionSide;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PositionSideResource positionSideResource = new PositionSideResource(positionSideRepository);
        this.restPositionSideMockMvc = MockMvcBuilders.standaloneSetup(positionSideResource)
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
    public static PositionSide createEntity(EntityManager em) {
        PositionSide positionSide = new PositionSide()
            .units(DEFAULT_UNITS)
            .averagePrice(DEFAULT_AVERAGE_PRICE)
            .pl(DEFAULT_PL)
            .unrealizedPL(DEFAULT_UNREALIZED_PL)
            .resettablePL(DEFAULT_RESETTABLE_PL);
        return positionSide;
    }

    @Before
    public void initTest() {
        positionSide = createEntity(em);
    }

    @Test
    @Transactional
    public void createPositionSide() throws Exception {
        int databaseSizeBeforeCreate = positionSideRepository.findAll().size();

        // Create the PositionSide
        restPositionSideMockMvc.perform(post("/api/position-sides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(positionSide)))
            .andExpect(status().isCreated());

        // Validate the PositionSide in the database
        List<PositionSide> positionSideList = positionSideRepository.findAll();
        assertThat(positionSideList).hasSize(databaseSizeBeforeCreate + 1);
        PositionSide testPositionSide = positionSideList.get(positionSideList.size() - 1);
        assertThat(testPositionSide.getUnits()).isEqualTo(DEFAULT_UNITS);
        assertThat(testPositionSide.getAveragePrice()).isEqualTo(DEFAULT_AVERAGE_PRICE);
        assertThat(testPositionSide.getPl()).isEqualTo(DEFAULT_PL);
        assertThat(testPositionSide.getUnrealizedPL()).isEqualTo(DEFAULT_UNREALIZED_PL);
        assertThat(testPositionSide.getResettablePL()).isEqualTo(DEFAULT_RESETTABLE_PL);
    }

    @Test
    @Transactional
    public void createPositionSideWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = positionSideRepository.findAll().size();

        // Create the PositionSide with an existing ID
        positionSide.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPositionSideMockMvc.perform(post("/api/position-sides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(positionSide)))
            .andExpect(status().isBadRequest());

        // Validate the PositionSide in the database
        List<PositionSide> positionSideList = positionSideRepository.findAll();
        assertThat(positionSideList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPositionSides() throws Exception {
        // Initialize the database
        positionSideRepository.saveAndFlush(positionSide);

        // Get all the positionSideList
        restPositionSideMockMvc.perform(get("/api/position-sides?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(positionSide.getId().intValue())))
            .andExpect(jsonPath("$.[*].units").value(hasItem(DEFAULT_UNITS.doubleValue())))
            .andExpect(jsonPath("$.[*].averagePrice").value(hasItem(DEFAULT_AVERAGE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].pl").value(hasItem(DEFAULT_PL.doubleValue())))
            .andExpect(jsonPath("$.[*].unrealizedPL").value(hasItem(DEFAULT_UNREALIZED_PL.doubleValue())))
            .andExpect(jsonPath("$.[*].resettablePL").value(hasItem(DEFAULT_RESETTABLE_PL.doubleValue())));
    }

    @Test
    @Transactional
    public void getPositionSide() throws Exception {
        // Initialize the database
        positionSideRepository.saveAndFlush(positionSide);

        // Get the positionSide
        restPositionSideMockMvc.perform(get("/api/position-sides/{id}", positionSide.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(positionSide.getId().intValue()))
            .andExpect(jsonPath("$.units").value(DEFAULT_UNITS.doubleValue()))
            .andExpect(jsonPath("$.averagePrice").value(DEFAULT_AVERAGE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.pl").value(DEFAULT_PL.doubleValue()))
            .andExpect(jsonPath("$.unrealizedPL").value(DEFAULT_UNREALIZED_PL.doubleValue()))
            .andExpect(jsonPath("$.resettablePL").value(DEFAULT_RESETTABLE_PL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPositionSide() throws Exception {
        // Get the positionSide
        restPositionSideMockMvc.perform(get("/api/position-sides/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePositionSide() throws Exception {
        // Initialize the database
        positionSideRepository.saveAndFlush(positionSide);
        int databaseSizeBeforeUpdate = positionSideRepository.findAll().size();

        // Update the positionSide
        PositionSide updatedPositionSide = positionSideRepository.findOne(positionSide.getId());
        updatedPositionSide
            .units(UPDATED_UNITS)
            .averagePrice(UPDATED_AVERAGE_PRICE)
            .pl(UPDATED_PL)
            .unrealizedPL(UPDATED_UNREALIZED_PL)
            .resettablePL(UPDATED_RESETTABLE_PL);

        restPositionSideMockMvc.perform(put("/api/position-sides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPositionSide)))
            .andExpect(status().isOk());

        // Validate the PositionSide in the database
        List<PositionSide> positionSideList = positionSideRepository.findAll();
        assertThat(positionSideList).hasSize(databaseSizeBeforeUpdate);
        PositionSide testPositionSide = positionSideList.get(positionSideList.size() - 1);
        assertThat(testPositionSide.getUnits()).isEqualTo(UPDATED_UNITS);
        assertThat(testPositionSide.getAveragePrice()).isEqualTo(UPDATED_AVERAGE_PRICE);
        assertThat(testPositionSide.getPl()).isEqualTo(UPDATED_PL);
        assertThat(testPositionSide.getUnrealizedPL()).isEqualTo(UPDATED_UNREALIZED_PL);
        assertThat(testPositionSide.getResettablePL()).isEqualTo(UPDATED_RESETTABLE_PL);
    }

    @Test
    @Transactional
    public void updateNonExistingPositionSide() throws Exception {
        int databaseSizeBeforeUpdate = positionSideRepository.findAll().size();

        // Create the PositionSide

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPositionSideMockMvc.perform(put("/api/position-sides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(positionSide)))
            .andExpect(status().isCreated());

        // Validate the PositionSide in the database
        List<PositionSide> positionSideList = positionSideRepository.findAll();
        assertThat(positionSideList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePositionSide() throws Exception {
        // Initialize the database
        positionSideRepository.saveAndFlush(positionSide);
        int databaseSizeBeforeDelete = positionSideRepository.findAll().size();

        // Get the positionSide
        restPositionSideMockMvc.perform(delete("/api/position-sides/{id}", positionSide.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PositionSide> positionSideList = positionSideRepository.findAll();
        assertThat(positionSideList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PositionSide.class);
        PositionSide positionSide1 = new PositionSide();
        positionSide1.setId(1L);
        PositionSide positionSide2 = new PositionSide();
        positionSide2.setId(positionSide1.getId());
        assertThat(positionSide1).isEqualTo(positionSide2);
        positionSide2.setId(2L);
        assertThat(positionSide1).isNotEqualTo(positionSide2);
        positionSide1.setId(null);
        assertThat(positionSide1).isNotEqualTo(positionSide2);
    }
}
