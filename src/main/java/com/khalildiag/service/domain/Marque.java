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
 * A Marque.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "marque")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Marque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("label")
    private String label;

    @DBRef
    @Field("model")
    @JsonIgnoreProperties(value = { "marque" }, allowSetters = true)
    private Set<Model> models = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here

}
