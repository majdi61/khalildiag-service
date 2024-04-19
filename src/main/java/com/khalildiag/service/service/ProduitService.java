package com.khalildiag.service.service;

import com.khalildiag.service.domain.Produit;
import com.khalildiag.service.repository.ProduitRepository;
import java.util.Optional;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProduitService {

    private final Logger log = LoggerFactory.getLogger(ProduitService.class);

    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public Produit save(Produit produit) {
        log.debug("Request to save Produit : {}", produit);
        produit.setCategoryId(produit.getCategory().getId());
        produit.setMarqueId(produit.getMarque().getId());
        produit.setModelId(produit.getModel().getId());
        produit.setCategoryLabel(produit.getCategory().getLabel());
        produit.setMarqueLabel(produit.getMarque().getLabel());
        produit.setModelLabel(produit.getModel().getLabel());
        return produitRepository.save(produit);
    }

    public Page<Produit> getProduitsPage(Document document, Pageable pageable) {
        //        document = Optional.ofNullable(document).orElse(new Document());
        //        log.debug("doc: {}", document);
        //        // add removed = false by default. removed links should be returned in
        //        document.putIfAbsent("removed", false);
        //        log.debug("doc: {}", document);
        return produitRepository.filter(document, pageable);
    }

    public Optional<Produit> findOne(String id) {
        log.debug("Request to get Produit : {}", id);
        return produitRepository.findById(id);
    }

    public void delete(String id) {
        log.debug("Request to delete Produit : {}", id);
        produitRepository.deleteById(id);
    }
}
