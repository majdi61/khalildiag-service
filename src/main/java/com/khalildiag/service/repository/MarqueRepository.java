package com.khalildiag.service.repository;

import com.khalildiag.service.domain.Marque;
import java.util.List;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface MarqueRepository extends MongoRepository<Marque, String> {
    @Query("?0")
    List<Marque> filter(Document document);

    @Query("?0")
    Page<Marque> filter(Document document, Pageable pageable);
}
