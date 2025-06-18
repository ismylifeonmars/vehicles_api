package com.ravemaster.vehicles.repository;

import com.ravemaster.vehicles.domain.entity.ModelEntity;
import org.springframework.data.repository.CrudRepository;

public interface ModelRepository extends CrudRepository<ModelEntity, Long> {
}
