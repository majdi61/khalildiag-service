package com.khalildiag.service.web.rest;

import com.khalildiag.service.domain.Marque;
import com.khalildiag.service.repository.MarqueRepository;
import com.khalildiag.service.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.khalildiag.service.domain.Marque}.
 */
@RestController
@RequestMapping("/api/marques")
public class MarqueResource {

    private final Logger log = LoggerFactory.getLogger(MarqueResource.class);

    private static final String ENTITY_NAME = "khalildiagMarque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MarqueRepository marqueRepository;

    public MarqueResource(MarqueRepository marqueRepository) {
        this.marqueRepository = marqueRepository;
    }

    /**
     * {@code POST  /marques} : Create a new marque.
     *
     * @param marque the marque to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new marque, or with status {@code 400 (Bad Request)} if the marque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Marque> createMarque(@RequestBody Marque marque) throws URISyntaxException {
        log.debug("REST request to save Marque : {}", marque);
        if (marque.getId() != null) {
            throw new BadRequestAlertException("A new marque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Marque result = marqueRepository.save(marque);
        return ResponseEntity
            .created(new URI("/api/marques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /marques/:id} : Updates an existing marque.
     *
     * @param id the id of the marque to save.
     * @param marque the marque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marque,
     * or with status {@code 400 (Bad Request)} if the marque is not valid,
     * or with status {@code 500 (Internal Server Error)} if the marque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Marque> updateMarque(@PathVariable(value = "id", required = false) final String id, @RequestBody Marque marque)
        throws URISyntaxException {
        log.debug("REST request to update Marque : {}, {}", id, marque);
        if (marque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Marque result = marqueRepository.save(marque);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, marque.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /marques/:id} : Partial updates given fields of an existing marque, field will ignore if it is null
     *
     * @param id the id of the marque to save.
     * @param marque the marque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated marque,
     * or with status {@code 400 (Bad Request)} if the marque is not valid,
     * or with status {@code 404 (Not Found)} if the marque is not found,
     * or with status {@code 500 (Internal Server Error)} if the marque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Marque> partialUpdateMarque(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Marque marque
    ) throws URISyntaxException {
        log.debug("REST request to partial update Marque partially : {}, {}", id, marque);
        if (marque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, marque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!marqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Marque> result = marqueRepository
            .findById(marque.getId())
            .map(existingMarque -> {
                if (marque.getLabel() != null) {
                    existingMarque.setLabel(marque.getLabel());
                }

                return existingMarque;
            })
            .map(marqueRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, marque.getId()));
    }

    /**
     * {@code GET  /marques} : get all the marques.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of marques in body.
     */
    @GetMapping("")
    public List<Marque> getAllMarques() {
        log.debug("REST request to get all Marques");
        return marqueRepository.findAll();
    }

    @GetMapping("/test")
    public String test() {
        log.debug("Working Service");
        return ("Working Service");
    }

    /**
     * {@code GET  /marques/:id} : get the "id" marque.
     *
     * @param id the id of the marque to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the marque, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Marque> getMarque(@PathVariable("id") String id) {
        log.debug("REST request to get Marque : {}", id);
        Optional<Marque> marque = marqueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(marque);
    }

    /**
     * {@code DELETE  /marques/:id} : delete the "id" marque.
     *
     * @param id the id of the marque to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarque(@PathVariable("id") String id) {
        log.debug("REST request to delete Marque : {}", id);
        marqueRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
