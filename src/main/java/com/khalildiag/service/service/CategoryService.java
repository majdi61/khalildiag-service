package com.khalildiag.service.service;

import com.khalildiag.service.domain.Category;
import com.khalildiag.service.repository.CategoryRepository;
import java.util.Optional;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category category) {
        log.debug("Request to save Category : {}", category);
        return categoryRepository.save(category);
    }

    public Page<Category> getCategorysPage(Document document, Pageable pageable) {
        //        document = Optional.ofNullable(document).orElse(new Document());
        //        log.debug("doc: {}", document);
        //        // add removed = false by default. removed links should be returned in
        //        document.putIfAbsent("removed", false);
        //        log.debug("doc: {}", document);
        return categoryRepository.filter(document, pageable);
    }

    public Optional<Category> findOne(String id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id);
    }

    public void delete(String id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
