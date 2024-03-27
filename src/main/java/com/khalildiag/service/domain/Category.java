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
 * A Category.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "category")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String label;

    @DBRef
    @Field("produit")
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private Set<Model> produits = new HashSet<>();
}
