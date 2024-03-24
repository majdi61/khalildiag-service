package com.khalildiag.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Model.
 */
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Model id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public Model label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Marque getMarque() {
        return this.marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public Model marque(Marque marque) {
        this.setMarque(marque);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Model)) {
            return false;
        }
        return getId() != null && getId().equals(((Model) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Model{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
