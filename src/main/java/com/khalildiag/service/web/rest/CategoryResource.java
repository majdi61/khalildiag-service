package com.khalildiag.service.web.rest;

import com.khalildiag.service.domain.Category;
import com.khalildiag.service.domain.Category;
import com.khalildiag.service.repository.CategoryRepository;
import com.khalildiag.service.service.CategoryService;
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
 * REST controller for managing {@link com.khalildiag.service.domain.Category}.
 */
@RestController
@RequestMapping("/api/category")
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "khalildiagCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public Page<Category> getLinksPage(@Filter(entityClass = Category.class) Document document, Pageable pageable) {
        return categoryService.getCategorysPage(document, pageable);
    }

    @PostMapping("")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) throws URISyntaxException {
        log.debug("REST request to save Category : {}", category);

        Category result = categoryService.save(category);
        return ResponseEntity
            .created(new URI("/api/categorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") String id) {
        log.debug("REST request to get Category : {}", id);
        Optional<Category> category = categoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String id) {
        log.debug("REST request to delete Category : {}", id);
        categoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
