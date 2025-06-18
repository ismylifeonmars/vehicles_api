package com.ravemaster.vehicles.services;

import com.ravemaster.vehicles.domain.entity.BrandEntity;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    BrandEntity createBrand(BrandEntity brandEntity);

    List<BrandEntity> findAll();

    Optional<BrandEntity> findbyId(Long id);

    boolean isExists(Long id);

    BrandEntity update(Long id, BrandEntity brandEntity);

    void deleteById(Long id);
}
