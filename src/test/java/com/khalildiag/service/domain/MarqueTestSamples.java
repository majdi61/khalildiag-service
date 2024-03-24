package com.khalildiag.service.domain;

import java.util.UUID;

public class MarqueTestSamples {

    public static Marque getMarqueSample1() {
        return new Marque().id("id1").label("label1");
    }

    public static Marque getMarqueSample2() {
        return new Marque().id("id2").label("label2");
    }

    public static Marque getMarqueRandomSampleGenerator() {
        return new Marque().id(UUID.randomUUID().toString()).label(UUID.randomUUID().toString());
    }
}
