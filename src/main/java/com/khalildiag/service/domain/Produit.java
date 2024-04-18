package com.khalildiag.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Produit.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "produit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String ref;

    private String label;

    private String denomination;

    private String etat;

    private String cylindre;

    private String puissance;

    private String year;

    private String carburant;

    private String boiteVitesse;

    private String description;

    private String marqueId;

    private String modelId;

    private String categoryId;

    private List<ImgUrl> imgUrlList;

    private ImgUrl imgProduitUrl;

    private ImgUrl imgTicketUrl;

    @DBRef
    @Field("marque")
    @JsonIgnoreProperties(value = { "produits" }, allowSetters = true)
    private Marque marque;

    @DBRef
    @Field("category")
    @JsonIgnoreProperties(value = { "produits" }, allowSetters = true)
    private Category category;

    @DBRef
    @Field("model")
    @JsonIgnoreProperties(value = { "produits" }, allowSetters = true)
    private Model model;
}
