package com.khalildiag.service.web.rest;

import com.khalildiag.service.domain.Produit;
import com.khalildiag.service.domain.Produit;
import com.khalildiag.service.repository.ProduitRepository;
import com.khalildiag.service.service.ProduitService;
import com.khalildiag.service.web.rest.errors.BadRequestAlertException;
import com.turkraft.springfilter.boot.Filter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.khalildiag.service.domain.Produit}.
 */
@RestController
@RequestMapping("/api/produits")
public class ProduitResource {

    private final Logger log = LoggerFactory.getLogger(ProduitResource.class);

    private static final String ENTITY_NAME = "khalildiagProduit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitService produitService;

    public ProduitResource(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("")
    public Page<Produit> getLinksPage(@Filter(entityClass = Produit.class) Document document, Pageable pageable) {
        return produitService.getProduitsPage(document, pageable);
    }

    @PostMapping("")
    public ResponseEntity<Produit> saveProduit(@RequestBody Produit produit) throws URISyntaxException {
        Produit result = produitService.save(produit);
        return ResponseEntity
            .created(new URI("/api/produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable("id") String id) {
        log.debug("REST request to get Produit : {}", id);
        Optional<Produit> produit = produitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable("id") String id) {
        log.debug("REST request to delete Produit : {}", id);
        produitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
