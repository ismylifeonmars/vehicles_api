package com.ravemaster.vehicles.repository;

import com.ravemaster.vehicles.domain.entity.BrandEntity;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends CrudRepository<BrandEntity, Long> {
}
