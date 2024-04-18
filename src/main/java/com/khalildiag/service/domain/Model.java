package com.khalildiag.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Model.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "model")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String label;

    private String marqueId;

    @DBRef
    @Field("marque")
    @JsonIgnoreProperties(value = { "models" }, allowSetters = true)
    private Marque marque;

    @DBRef
    @Field("produit")
    @JsonIgnoreProperties(value = { "model" }, allowSetters = true)
    private Set<Model> produits = new HashSet<>();
}
