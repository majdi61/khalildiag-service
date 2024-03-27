package com.khalildiag.service.web.rest;

import com.khalildiag.service.domain.Model;
import com.khalildiag.service.domain.Model;
import com.khalildiag.service.repository.ModelRepository;
import com.khalildiag.service.service.ModelService;
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
 * REST controller for managing {@link com.khalildiag.service.domain.Model}.
 */
@RestController
@RequestMapping("/api/models")
public class ModelResource {

    private final Logger log = LoggerFactory.getLogger(ModelResource.class);

    private static final String ENTITY_NAME = "khalildiagModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModelService modelService;

    public ModelResource(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("")
    public Page<Model> getLinksPage(@Filter(entityClass = Model.class) Document document, Pageable pageable) {
        return modelService.getModelsPage(document, pageable);
    }

    @PostMapping("")
    public ResponseEntity<Model> saveModel(@RequestBody Model model) throws URISyntaxException {
        log.debug("REST request to save Model : {}", model);
        if (model.getId() != null) {
            throw new BadRequestAlertException("A new model cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Model result = modelService.save(model);
        return ResponseEntity
            .created(new URI("/api/models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable("id") String id) {
        log.debug("REST request to get Model : {}", id);
        Optional<Model> model = modelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable("id") String id) {
        log.debug("REST request to delete Model : {}", id);
        modelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
