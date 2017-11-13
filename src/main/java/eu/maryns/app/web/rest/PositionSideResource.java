package eu.maryns.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.maryns.app.domain.PositionSide;

import eu.maryns.app.repository.PositionSideRepository;
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
 * REST controller for managing PositionSide.
 */
@RestController
@RequestMapping("/api")
public class PositionSideResource {

    private final Logger log = LoggerFactory.getLogger(PositionSideResource.class);

    private static final String ENTITY_NAME = "positionSide";

    private final PositionSideRepository positionSideRepository;

    public PositionSideResource(PositionSideRepository positionSideRepository) {
        this.positionSideRepository = positionSideRepository;
    }

    /**
     * POST  /position-sides : Create a new positionSide.
     *
     * @param positionSide the positionSide to create
     * @return the ResponseEntity with status 201 (Created) and with body the new positionSide, or with status 400 (Bad Request) if the positionSide has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/position-sides")
    @Timed
    public ResponseEntity<PositionSide> createPositionSide(@RequestBody PositionSide positionSide) throws URISyntaxException {
        log.debug("REST request to save PositionSide : {}", positionSide);
        if (positionSide.getId() != null) {
            throw new BadRequestAlertException("A new positionSide cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PositionSide result = positionSideRepository.save(positionSide);
        return ResponseEntity.created(new URI("/api/position-sides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /position-sides : Updates an existing positionSide.
     *
     * @param positionSide the positionSide to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated positionSide,
     * or with status 400 (Bad Request) if the positionSide is not valid,
     * or with status 500 (Internal Server Error) if the positionSide couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/position-sides")
    @Timed
    public ResponseEntity<PositionSide> updatePositionSide(@RequestBody PositionSide positionSide) throws URISyntaxException {
        log.debug("REST request to update PositionSide : {}", positionSide);
        if (positionSide.getId() == null) {
            return createPositionSide(positionSide);
        }
        PositionSide result = positionSideRepository.save(positionSide);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, positionSide.getId().toString()))
            .body(result);
    }

    /**
     * GET  /position-sides : get all the positionSides.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of positionSides in body
     */
    @GetMapping("/position-sides")
    @Timed
    public ResponseEntity<List<PositionSide>> getAllPositionSides(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PositionSides");
        Page<PositionSide> page = positionSideRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/position-sides");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /position-sides/:id : get the "id" positionSide.
     *
     * @param id the id of the positionSide to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the positionSide, or with status 404 (Not Found)
     */
    @GetMapping("/position-sides/{id}")
    @Timed
    public ResponseEntity<PositionSide> getPositionSide(@PathVariable Long id) {
        log.debug("REST request to get PositionSide : {}", id);
        PositionSide positionSide = positionSideRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(positionSide));
    }

    /**
     * DELETE  /position-sides/:id : delete the "id" positionSide.
     *
     * @param id the id of the positionSide to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/position-sides/{id}")
    @Timed
    public ResponseEntity<Void> deletePositionSide(@PathVariable Long id) {
        log.debug("REST request to delete PositionSide : {}", id);
        positionSideRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
