package eu.maryns.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.maryns.app.domain.CandleStickData;

import eu.maryns.app.repository.CandleStickDataRepository;
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
 * REST controller for managing CandleStickData.
 */
@RestController
@RequestMapping("/api")
public class CandleStickDataResource {

    private final Logger log = LoggerFactory.getLogger(CandleStickDataResource.class);

    private static final String ENTITY_NAME = "candleStickData";

    private final CandleStickDataRepository candleStickDataRepository;

    public CandleStickDataResource(CandleStickDataRepository candleStickDataRepository) {
        this.candleStickDataRepository = candleStickDataRepository;
    }

    /**
     * POST  /candle-stick-data : Create a new candleStickData.
     *
     * @param candleStickData the candleStickData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candleStickData, or with status 400 (Bad Request) if the candleStickData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candle-stick-data")
    @Timed
    public ResponseEntity<CandleStickData> createCandleStickData(@RequestBody CandleStickData candleStickData) throws URISyntaxException {
        log.debug("REST request to save CandleStickData : {}", candleStickData);
        if (candleStickData.getId() != null) {
            throw new BadRequestAlertException("A new candleStickData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandleStickData result = candleStickDataRepository.save(candleStickData);
        return ResponseEntity.created(new URI("/api/candle-stick-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candle-stick-data : Updates an existing candleStickData.
     *
     * @param candleStickData the candleStickData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candleStickData,
     * or with status 400 (Bad Request) if the candleStickData is not valid,
     * or with status 500 (Internal Server Error) if the candleStickData couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candle-stick-data")
    @Timed
    public ResponseEntity<CandleStickData> updateCandleStickData(@RequestBody CandleStickData candleStickData) throws URISyntaxException {
        log.debug("REST request to update CandleStickData : {}", candleStickData);
        if (candleStickData.getId() == null) {
            return createCandleStickData(candleStickData);
        }
        CandleStickData result = candleStickDataRepository.save(candleStickData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candleStickData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candle-stick-data : get all the candleStickData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of candleStickData in body
     */
    @GetMapping("/candle-stick-data")
    @Timed
    public ResponseEntity<List<CandleStickData>> getAllCandleStickData(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CandleStickData");
        Page<CandleStickData> page = candleStickDataRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/candle-stick-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /candle-stick-data/:id : get the "id" candleStickData.
     *
     * @param id the id of the candleStickData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candleStickData, or with status 404 (Not Found)
     */
    @GetMapping("/candle-stick-data/{id}")
    @Timed
    public ResponseEntity<CandleStickData> getCandleStickData(@PathVariable Long id) {
        log.debug("REST request to get CandleStickData : {}", id);
        CandleStickData candleStickData = candleStickDataRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candleStickData));
    }

    /**
     * DELETE  /candle-stick-data/:id : delete the "id" candleStickData.
     *
     * @param id the id of the candleStickData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candle-stick-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandleStickData(@PathVariable Long id) {
        log.debug("REST request to delete CandleStickData : {}", id);
        candleStickDataRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
