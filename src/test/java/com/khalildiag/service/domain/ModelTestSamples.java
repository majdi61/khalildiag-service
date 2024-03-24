package com.khalildiag.service.domain;

import java.util.UUID;

public class ModelTestSamples {

    public static Model getModelSample1() {
        return new Model().id("id1").label("label1");
    }

    public static Model getModelSample2() {
        return new Model().id("id2").label("label2");
    }

    public static Model getModelRandomSampleGenerator() {
        return new Model().id(UUID.randomUUID().toString()).label(UUID.randomUUID().toString());
    }
}
