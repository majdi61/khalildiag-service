package com.khalildiag.service.domain;

import java.util.UUID;

public class CategoryTestSamples {

    public static Category getCategorySample1() {
        return new Category().id("id1").label("label1");
    }

    public static Category getCategorySample2() {
        return new Category().id("id2").label("label2");
    }

    public static Category getCategoryRandomSampleGenerator() {
        return new Category().id(UUID.randomUUID().toString()).label(UUID.randomUUID().toString());
    }
}
