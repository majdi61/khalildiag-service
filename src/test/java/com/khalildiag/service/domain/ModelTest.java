package com.khalildiag.service.domain;

import static com.khalildiag.service.domain.MarqueTestSamples.*;
import static com.khalildiag.service.domain.ModelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.khalildiag.service.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Model.class);
        Model model1 = getModelSample1();
        Model model2 = new Model();
        assertThat(model1).isNotEqualTo(model2);

        model2.setId(model1.getId());
        assertThat(model1).isEqualTo(model2);

        model2 = getModelSample2();
        assertThat(model1).isNotEqualTo(model2);
    }

    @Test
    void marqueTest() throws Exception {
        Model model = getModelRandomSampleGenerator();
        Marque marqueBack = getMarqueRandomSampleGenerator();

        model.setMarque(marqueBack);
        assertThat(model.getMarque()).isEqualTo(marqueBack);

        model.marque(null);
        assertThat(model.getMarque()).isNull();
    }
}
