package com.khalildiag.service.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khalildiag.service.IntegrationTest;
import com.khalildiag.service.domain.Marque;
import com.khalildiag.service.repository.MarqueRepository;
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
 * Integration tests for the {@link MarqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MarqueResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/marques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MarqueRepository marqueRepository;

    @Autowired
    private MockMvc restMarqueMockMvc;

    private Marque marque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Marque createEntity() {
        Marque marque = new Marque().label(DEFAULT_LABEL);
        return marque;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Marque createUpdatedEntity() {
        Marque marque = new Marque().label(UPDATED_LABEL);
        return marque;
    }

    @BeforeEach
    public void initTest() {
        marqueRepository.deleteAll();
        marque = createEntity();
    }

    @Test
    void createMarque() throws Exception {
        int databaseSizeBeforeCreate = marqueRepository.findAll().size();
        // Create the Marque
        restMarqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marque)))
            .andExpect(status().isCreated());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeCreate + 1);
        Marque testMarque = marqueList.get(marqueList.size() - 1);
        assertThat(testMarque.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    void createMarqueWithExistingId() throws Exception {
        // Create the Marque with an existing ID
        marque.setId("existing_id");

        int databaseSizeBeforeCreate = marqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marque)))
            .andExpect(status().isBadRequest());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMarques() throws Exception {
        // Initialize the database
        marqueRepository.save(marque);

        // Get all the marqueList
        restMarqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marque.getId())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    void getMarque() throws Exception {
        // Initialize the database
        marqueRepository.save(marque);

        // Get the marque
        restMarqueMockMvc
            .perform(get(ENTITY_API_URL_ID, marque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(marque.getId()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    void getNonExistingMarque() throws Exception {
        // Get the marque
        restMarqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMarque() throws Exception {
        // Initialize the database
        marqueRepository.save(marque);

        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();

        // Update the marque
        Marque updatedMarque = marqueRepository.findById(marque.getId()).orElseThrow();
        updatedMarque.label(UPDATED_LABEL);

        restMarqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMarque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMarque))
            )
            .andExpect(status().isOk());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
        Marque testMarque = marqueList.get(marqueList.size() - 1);
        assertThat(testMarque.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    void putNonExistingMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, marque.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marque))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(marque))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(marque)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMarqueWithPatch() throws Exception {
        // Initialize the database
        marqueRepository.save(marque);

        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();

        // Update the marque using partial update
        Marque partialUpdatedMarque = new Marque();
        partialUpdatedMarque.setId(marque.getId());

        restMarqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarque))
            )
            .andExpect(status().isOk());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
        Marque testMarque = marqueList.get(marqueList.size() - 1);
        assertThat(testMarque.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    void fullUpdateMarqueWithPatch() throws Exception {
        // Initialize the database
        marqueRepository.save(marque);

        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();

        // Update the marque using partial update
        Marque partialUpdatedMarque = new Marque();
        partialUpdatedMarque.setId(marque.getId());

        partialUpdatedMarque.label(UPDATED_LABEL);

        restMarqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMarque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMarque))
            )
            .andExpect(status().isOk());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
        Marque testMarque = marqueList.get(marqueList.size() - 1);
        assertThat(testMarque.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    void patchNonExistingMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, marque.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marque))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(marque))
            )
            .andExpect(status().isBadRequest());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMarque() throws Exception {
        int databaseSizeBeforeUpdate = marqueRepository.findAll().size();
        marque.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMarqueMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(marque)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Marque in the database
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMarque() throws Exception {
        // Initialize the database
        marqueRepository.save(marque);

        int databaseSizeBeforeDelete = marqueRepository.findAll().size();

        // Delete the marque
        restMarqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, marque.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Marque> marqueList = marqueRepository.findAll();
        assertThat(marqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
