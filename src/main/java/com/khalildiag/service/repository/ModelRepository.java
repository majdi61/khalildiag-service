package com.khalildiag.service.repository;

import com.khalildiag.service.domain.Category;
import com.khalildiag.service.domain.Model;
import java.util.List;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Model entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelRepository extends MongoRepository<Model, String> {
    @Query("?0")
    List<Model> filter(Document document);

    @Query("?0")
    Page<Model> filter(Document document, Pageable pageable);
}
