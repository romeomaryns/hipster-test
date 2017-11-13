package eu.maryns.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.maryns.app.domain.CandleStick;

import eu.maryns.app.repository.CandleStickRepository;
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
 * REST controller for managing CandleStick.
 */
@RestController
@RequestMapping("/api")
public class CandleStickResource {

    private final Logger log = LoggerFactory.getLogger(CandleStickResource.class);

    private static final String ENTITY_NAME = "candleStick";

    private final CandleStickRepository candleStickRepository;

    public CandleStickResource(CandleStickRepository candleStickRepository) {
        this.candleStickRepository = candleStickRepository;
    }

    /**
     * POST  /candle-sticks : Create a new candleStick.
     *
     * @param candleStick the candleStick to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candleStick, or with status 400 (Bad Request) if the candleStick has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candle-sticks")
    @Timed
    public ResponseEntity<CandleStick> createCandleStick(@RequestBody CandleStick candleStick) throws URISyntaxException {
        log.debug("REST request to save CandleStick : {}", candleStick);
        if (candleStick.getId() != null) {
            throw new BadRequestAlertException("A new candleStick cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandleStick result = candleStickRepository.save(candleStick);
        return ResponseEntity.created(new URI("/api/candle-sticks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candle-sticks : Updates an existing candleStick.
     *
     * @param candleStick the candleStick to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candleStick,
     * or with status 400 (Bad Request) if the candleStick is not valid,
     * or with status 500 (Internal Server Error) if the candleStick couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candle-sticks")
    @Timed
    public ResponseEntity<CandleStick> updateCandleStick(@RequestBody CandleStick candleStick) throws URISyntaxException {
        log.debug("REST request to update CandleStick : {}", candleStick);
        if (candleStick.getId() == null) {
            return createCandleStick(candleStick);
        }
        CandleStick result = candleStickRepository.save(candleStick);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candleStick.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candle-sticks : get all the candleSticks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of candleSticks in body
     */
    @GetMapping("/candle-sticks")
    @Timed
    public ResponseEntity<List<CandleStick>> getAllCandleSticks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CandleSticks");
        Page<CandleStick> page = candleStickRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/candle-sticks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /candle-sticks/:id : get the "id" candleStick.
     *
     * @param id the id of the candleStick to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candleStick, or with status 404 (Not Found)
     */
    @GetMapping("/candle-sticks/{id}")
    @Timed
    public ResponseEntity<CandleStick> getCandleStick(@PathVariable Long id) {
        log.debug("REST request to get CandleStick : {}", id);
        CandleStick candleStick = candleStickRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candleStick));
    }

    /**
     * DELETE  /candle-sticks/:id : delete the "id" candleStick.
     *
     * @param id the id of the candleStick to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candle-sticks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandleStick(@PathVariable Long id) {
        log.debug("REST request to delete CandleStick : {}", id);
        candleStickRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
