package com.khalildiag.service.repository;

import com.khalildiag.service.domain.Produit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends MongoRepository<Produit, String> {}
