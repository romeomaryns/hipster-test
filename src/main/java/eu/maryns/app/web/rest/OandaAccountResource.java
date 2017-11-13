package eu.maryns.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.maryns.app.domain.OandaAccount;

import eu.maryns.app.repository.OandaAccountRepository;
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
 * REST controller for managing OandaAccount.
 */
@RestController
@RequestMapping("/api")
public class OandaAccountResource {

    private final Logger log = LoggerFactory.getLogger(OandaAccountResource.class);

    private static final String ENTITY_NAME = "oandaAccount";

    private final OandaAccountRepository oandaAccountRepository;

    public OandaAccountResource(OandaAccountRepository oandaAccountRepository) {
        this.oandaAccountRepository = oandaAccountRepository;
    }

    /**
     * POST  /oanda-accounts : Create a new oandaAccount.
     *
     * @param oandaAccount the oandaAccount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new oandaAccount, or with status 400 (Bad Request) if the oandaAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oanda-accounts")
    @Timed
    public ResponseEntity<OandaAccount> createOandaAccount(@RequestBody OandaAccount oandaAccount) throws URISyntaxException {
        log.debug("REST request to save OandaAccount : {}", oandaAccount);
        if (oandaAccount.getId() != null) {
            throw new BadRequestAlertException("A new oandaAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OandaAccount result = oandaAccountRepository.save(oandaAccount);
        return ResponseEntity.created(new URI("/api/oanda-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oanda-accounts : Updates an existing oandaAccount.
     *
     * @param oandaAccount the oandaAccount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated oandaAccount,
     * or with status 400 (Bad Request) if the oandaAccount is not valid,
     * or with status 500 (Internal Server Error) if the oandaAccount couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oanda-accounts")
    @Timed
    public ResponseEntity<OandaAccount> updateOandaAccount(@RequestBody OandaAccount oandaAccount) throws URISyntaxException {
        log.debug("REST request to update OandaAccount : {}", oandaAccount);
        if (oandaAccount.getId() == null) {
            return createOandaAccount(oandaAccount);
        }
        OandaAccount result = oandaAccountRepository.save(oandaAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, oandaAccount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oanda-accounts : get all the oandaAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of oandaAccounts in body
     */
    @GetMapping("/oanda-accounts")
    @Timed
    public ResponseEntity<List<OandaAccount>> getAllOandaAccounts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of OandaAccounts");
        Page<OandaAccount> page = oandaAccountRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/oanda-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /oanda-accounts/:id : get the "id" oandaAccount.
     *
     * @param id the id of the oandaAccount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the oandaAccount, or with status 404 (Not Found)
     */
    @GetMapping("/oanda-accounts/{id}")
    @Timed
    public ResponseEntity<OandaAccount> getOandaAccount(@PathVariable Long id) {
        log.debug("REST request to get OandaAccount : {}", id);
        OandaAccount oandaAccount = oandaAccountRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(oandaAccount));
    }

    /**
     * DELETE  /oanda-accounts/:id : delete the "id" oandaAccount.
     *
     * @param id the id of the oandaAccount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oanda-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteOandaAccount(@PathVariable Long id) {
        log.debug("REST request to delete OandaAccount : {}", id);
        oandaAccountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
