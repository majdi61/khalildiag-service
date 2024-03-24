package com.khalildiag.service.repository;

import com.khalildiag.service.domain.Model;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Model entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelRepository extends MongoRepository<Model, String> {}
