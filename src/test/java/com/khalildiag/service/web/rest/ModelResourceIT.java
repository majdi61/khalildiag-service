package com.khalildiag.service.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khalildiag.service.IntegrationTest;
import com.khalildiag.service.domain.Model;
import com.khalildiag.service.repository.ModelRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ModelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModelResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/models";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private MockMvc restModelMockMvc;

    private Model model;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Model createEntity() {
        Model model = new Model().label(DEFAULT_LABEL);
        return model;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Model createUpdatedEntity() {
        Model model = new Model().label(UPDATED_LABEL);
        return model;
    }

    @BeforeEach
    public void initTest() {
        modelRepository.deleteAll();
        model = createEntity();
    }

    @Test
    void createModel() throws Exception {
        int databaseSizeBeforeCreate = modelRepository.findAll().size();
        // Create the Model
        restModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(model)))
            .andExpect(status().isCreated());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeCreate + 1);
        Model testModel = modelList.get(modelList.size() - 1);
        assertThat(testModel.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    void createModelWithExistingId() throws Exception {
        // Create the Model with an existing ID
        model.setId("existing_id");

        int databaseSizeBeforeCreate = modelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(model)))
            .andExpect(status().isBadRequest());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllModels() throws Exception {
        // Initialize the database
        modelRepository.save(model);

        // Get all the modelList
        restModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(model.getId())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    void getModel() throws Exception {
        // Initialize the database
        modelRepository.save(model);

        // Get the model
        restModelMockMvc
            .perform(get(ENTITY_API_URL_ID, model.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(model.getId()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    void getNonExistingModel() throws Exception {
        // Get the model
        restModelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingModel() throws Exception {
        // Initialize the database
        modelRepository.save(model);

        int databaseSizeBeforeUpdate = modelRepository.findAll().size();

        // Update the model
        Model updatedModel = modelRepository.findById(model.getId()).orElseThrow();
        updatedModel.label(UPDATED_LABEL);

        restModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedModel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedModel))
            )
            .andExpect(status().isOk());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
        Model testModel = modelList.get(modelList.size() - 1);
        assertThat(testModel.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    void putNonExistingModel() throws Exception {
        int databaseSizeBeforeUpdate = modelRepository.findAll().size();
        model.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, model.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(model))
            )
            .andExpect(status().isBadRequest());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchModel() throws Exception {
        int databaseSizeBeforeUpdate = modelRepository.findAll().size();
        model.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(model))
            )
            .andExpect(status().isBadRequest());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamModel() throws Exception {
        int databaseSizeBeforeUpdate = modelRepository.findAll().size();
        model.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(model)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateModelWithPatch() throws Exception {
        // Initialize the database
        modelRepository.save(model);

        int databaseSizeBeforeUpdate = modelRepository.findAll().size();

        // Update the model using partial update
        Model partialUpdatedModel = new Model();
        partialUpdatedModel.setId(model.getId());

        partialUpdatedModel.label(UPDATED_LABEL);

        restModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModel))
            )
            .andExpect(status().isOk());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
        Model testModel = modelList.get(modelList.size() - 1);
        assertThat(testModel.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    void fullUpdateModelWithPatch() throws Exception {
        // Initialize the database
        modelRepository.save(model);

        int databaseSizeBeforeUpdate = modelRepository.findAll().size();

        // Update the model using partial update
        Model partialUpdatedModel = new Model();
        partialUpdatedModel.setId(model.getId());

        partialUpdatedModel.label(UPDATED_LABEL);

        restModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModel))
            )
            .andExpect(status().isOk());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
        Model testModel = modelList.get(modelList.size() - 1);
        assertThat(testModel.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    void patchNonExistingModel() throws Exception {
        int databaseSizeBeforeUpdate = modelRepository.findAll().size();
        model.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, model.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(model))
            )
            .andExpect(status().isBadRequest());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchModel() throws Exception {
        int databaseSizeBeforeUpdate = modelRepository.findAll().size();
        model.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(model))
            )
            .andExpect(status().isBadRequest());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamModel() throws Exception {
        int databaseSizeBeforeUpdate = modelRepository.findAll().size();
        model.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(model)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Model in the database
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteModel() throws Exception {
        // Initialize the database
        modelRepository.save(model);

        int databaseSizeBeforeDelete = modelRepository.findAll().size();

        // Delete the model
        restModelMockMvc
            .perform(delete(ENTITY_API_URL_ID, model.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Model> modelList = modelRepository.findAll();
        assertThat(modelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
