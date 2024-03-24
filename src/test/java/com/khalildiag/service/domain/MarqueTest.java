package com.khalildiag.service.domain;

import static com.khalildiag.service.domain.MarqueTestSamples.*;
import static com.khalildiag.service.domain.ModelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.khalildiag.service.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MarqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Marque.class);
        Marque marque1 = getMarqueSample1();
        Marque marque2 = new Marque();
        assertThat(marque1).isNotEqualTo(marque2);

        marque2.setId(marque1.getId());
        assertThat(marque1).isEqualTo(marque2);

        marque2 = getMarqueSample2();
        assertThat(marque1).isNotEqualTo(marque2);
    }

    @Test
    void modelTest() throws Exception {
        Marque marque = getMarqueRandomSampleGenerator();
        Model modelBack = getModelRandomSampleGenerator();

        marque.addModel(modelBack);
        assertThat(marque.getModels()).containsOnly(modelBack);
        assertThat(modelBack.getMarque()).isEqualTo(marque);

        marque.removeModel(modelBack);
        assertThat(marque.getModels()).doesNotContain(modelBack);
        assertThat(modelBack.getMarque()).isNull();

        marque.models(new HashSet<>(Set.of(modelBack)));
        assertThat(marque.getModels()).containsOnly(modelBack);
        assertThat(modelBack.getMarque()).isEqualTo(marque);

        marque.setModels(new HashSet<>());
        assertThat(marque.getModels()).doesNotContain(modelBack);
        assertThat(modelBack.getMarque()).isNull();
    }
}
