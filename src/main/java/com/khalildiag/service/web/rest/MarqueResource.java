package com.khalildiag.service.web.rest;

import com.khalildiag.service.domain.Marque;
import com.khalildiag.service.service.MarqueService;
import com.khalildiag.service.web.rest.errors.BadRequestAlertException;
import com.turkraft.springfilter.boot.Filter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.swing.text.html.parser.Entity;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
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

    private final MarqueService marqueService;

    public MarqueResource(MarqueService marqueService) {
        this.marqueService = marqueService;
    }

    @CrossOrigin(origins = "https://khalildiag-web-admin.web.app/")
    @GetMapping("")
    public Page<Marque> getLinksPage(@Filter(entityClass = Marque.class) Document document, Pageable pageable) {
        return marqueService.getMarquesPage(document, pageable);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("")
    public ResponseEntity<Marque> saveMarque(@RequestBody Marque marque) throws URISyntaxException {
        log.debug("REST request to save Marque : {}", marque);
        if (marque.getId() != null) {
            throw new BadRequestAlertException("A new marque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Marque result = marqueService.save(marque);
        return ResponseEntity
            .created(new URI("/api/marques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @CrossOrigin(origins = "https://khalildiag-web-admin.web.app/")
    @GetMapping("/{id}")
    public ResponseEntity<Marque> getMarqueById(@PathVariable("id") String id) {
        log.debug("REST request to get Marque : {}", id);
        Optional<Marque> marque = marqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marque);
    }

    @CrossOrigin(origins = "https://khalildiag-web-admin.web.app/")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarque(@PathVariable("id") String id) {
        log.debug("REST request to delete Marque : {}", id);
        marqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
