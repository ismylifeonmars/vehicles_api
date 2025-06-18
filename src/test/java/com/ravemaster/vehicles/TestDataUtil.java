package com.ravemaster.vehicles;

import com.ravemaster.vehicles.domain.entity.BrandEntity;
import com.ravemaster.vehicles.domain.entity.ModelEntity;

public class TestDataUtil {

    public TestDataUtil() {
    }

    public static BrandEntity createBrandEntityA(){
        return BrandEntity.builder()
                .name("BMW")
                .country("Germany")
                .build();
    }

    public static BrandEntity createBrandEntityB(){
        return BrandEntity.builder()
                .name("Mitsubishi")
                .country("Japan")
                .build();
    }

    public static BrandEntity createBrandEntityC(){
        return BrandEntity.builder()
                .name("Dodge")
                .country("USA")
                .build();
    }

    public static ModelEntity createModelEntityA(final BrandEntity brandEntity){
        return ModelEntity.builder()
                .modelName("5 series")
                .brandEntity(brandEntity)
                .build();
    }

    public static ModelEntity createModelEntityB(final BrandEntity brandEntity){
        return ModelEntity.builder()
                .modelName("Outlander")
                .brandEntity(brandEntity)
                .build();
    }

    public static ModelEntity createModelEntityC(final BrandEntity brandEntity){
        return ModelEntity.builder()
                .modelName("challenger")
                .brandEntity(brandEntity)
                .build();
    }

}
