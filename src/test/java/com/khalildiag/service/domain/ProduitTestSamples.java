package com.khalildiag.service.domain;

import java.util.UUID;

public class ProduitTestSamples {

    public static Produit getProduitSample1() {
        return new Produit().id("id1").label("label1");
    }

    public static Produit getProduitSample2() {
        return new Produit().id("id2").label("label2");
    }

    public static Produit getProduitRandomSampleGenerator() {
        return new Produit().id(UUID.randomUUID().toString()).label(UUID.randomUUID().toString());
    }
}
