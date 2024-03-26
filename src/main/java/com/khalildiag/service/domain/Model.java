package com.khalildiag.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @Field("label")
    private String label;

    @DBRef
    @Field("marque")
    @JsonIgnoreProperties(value = { "models" }, allowSetters = true)
    private Marque marque;
}
