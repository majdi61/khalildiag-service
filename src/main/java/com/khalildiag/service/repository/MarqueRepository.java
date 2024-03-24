package com.khalildiag.service.repository;

import com.khalildiag.service.domain.Marque;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Marque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarqueRepository extends MongoRepository<Marque, String> {}
