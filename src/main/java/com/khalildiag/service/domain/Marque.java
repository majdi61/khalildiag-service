package com.khalildiag.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Marque.
 */
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

    public String getId() {
        return this.id;
    }

    public Marque id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public Marque label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Model> getModels() {
        return this.models;
    }

    public void setModels(Set<Model> models) {
        if (this.models != null) {
            this.models.forEach(i -> i.setMarque(null));
        }
        if (models != null) {
            models.forEach(i -> i.setMarque(this));
        }
        this.models = models;
    }

    public Marque models(Set<Model> models) {
        this.setModels(models);
        return this;
    }

    public Marque addModel(Model model) {
        this.models.add(model);
        model.setMarque(this);
        return this;
    }

    public Marque removeModel(Model model) {
        this.models.remove(model);
        model.setMarque(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Marque)) {
            return false;
        }
        return getId() != null && getId().equals(((Marque) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Marque{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
