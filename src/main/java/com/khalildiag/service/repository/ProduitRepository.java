package com.khalildiag.service.repository;

import com.khalildiag.service.domain.Produit;
import java.util.List;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends MongoRepository<Produit, String> {
    @Query("?0")
    List<Produit> filter(Document document);

    @Query("?0")
    Page<Produit> filter(Document document, Pageable pageable);
}
