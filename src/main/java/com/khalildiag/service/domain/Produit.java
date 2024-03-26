package com.khalildiag.service.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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

    @Field("label")
    private String label;

    @Field("description")
    private String description;

    @Field("imgUrl")
    private String imgUrl;
}
