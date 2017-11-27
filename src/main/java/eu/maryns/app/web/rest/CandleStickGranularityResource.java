package eu.maryns.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.maryns.app.domain.CandleStickGranularity;

import eu.maryns.app.repository.CandleStickGranularityRepository;
import eu.maryns.app.web.rest.errors.BadRequestAlertException;
import eu.maryns.app.web.rest.util.HeaderUtil;
import eu.maryns.app.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CandleStickGranularity.
 */
@RestController
@RequestMapping("/api")
public class CandleStickGranularityResource {

    private final Logger log = LoggerFactory.getLogger(CandleStickGranularityResource.class);

    private static final String ENTITY_NAME = "candleStickGranularity";

    private final CandleStickGranularityRepository candleStickGranularityRepository;

    public CandleStickGranularityResource(CandleStickGranularityRepository candleStickGranularityRepository) {
        this.candleStickGranularityRepository = candleStickGranularityRepository;
    }

    /**
     * POST  /candle-stick-granularities : Create a new candleStickGranularity.
     *
     * @param candleStickGranularity the candleStickGranularity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candleStickGranularity, or with status 400 (Bad Request) if the candleStickGranularity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candle-stick-granularities")
    @Timed
    public ResponseEntity<CandleStickGranularity> createCandleStickGranularity(@RequestBody CandleStickGranularity candleStickGranularity) throws URISyntaxException {
        log.debug("REST request to save CandleStickGranularity : {}", candleStickGranularity);
        if (candleStickGranularity.getId() != null) {
            throw new BadRequestAlertException("A new candleStickGranularity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandleStickGranularity result = candleStickGranularityRepository.save(candleStickGranularity);
        return ResponseEntity.created(new URI("/api/candle-stick-granularities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candle-stick-granularities : Updates an existing candleStickGranularity.
     *
     * @param candleStickGranularity the candleStickGranularity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candleStickGranularity,
     * or with status 400 (Bad Request) if the candleStickGranularity is not valid,
     * or with status 500 (Internal Server Error) if the candleStickGranularity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candle-stick-granularities")
    @Timed
    public ResponseEntity<CandleStickGranularity> updateCandleStickGranularity(@RequestBody CandleStickGranularity candleStickGranularity) throws URISyntaxException {
        log.debug("REST request to update CandleStickGranularity : {}", candleStickGranularity);
        if (candleStickGranularity.getId() == null) {
            return createCandleStickGranularity(candleStickGranularity);
        }
        CandleStickGranularity result = candleStickGranularityRepository.save(candleStickGranularity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candleStickGranularity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candle-stick-granularities : get all the candleStickGranularities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of candleStickGranularities in body
     */
    @GetMapping("/candle-stick-granularities")
    @Timed
    public ResponseEntity<List<CandleStickGranularity>> getAllCandleStickGranularities(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CandleStickGranularities");
        Page<CandleStickGranularity> page = candleStickGranularityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/candle-stick-granularities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /candle-stick-granularities/:id : get the "id" candleStickGranularity.
     *
     * @param id the id of the candleStickGranularity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candleStickGranularity, or with status 404 (Not Found)
     */
    @GetMapping("/candle-stick-granularities/{id}")
    @Timed
    public ResponseEntity<CandleStickGranularity> getCandleStickGranularity(@PathVariable Long id) {
        log.debug("REST request to get CandleStickGranularity : {}", id);
        CandleStickGranularity candleStickGranularity = candleStickGranularityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candleStickGranularity));
    }

    /**
     * DELETE  /candle-stick-granularities/:id : delete the "id" candleStickGranularity.
     *
     * @param id the id of the candleStickGranularity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candle-stick-granularities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandleStickGranularity(@PathVariable Long id) {
        log.debug("REST request to delete CandleStickGranularity : {}", id);
        candleStickGranularityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
