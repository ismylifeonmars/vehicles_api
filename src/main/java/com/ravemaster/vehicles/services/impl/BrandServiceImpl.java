package com.ravemaster.vehicles.services.impl;

import com.ravemaster.vehicles.domain.entity.BrandEntity;
import com.ravemaster.vehicles.repository.BrandRepository;
import com.ravemaster.vehicles.services.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public BrandEntity createBrand(BrandEntity brandEntity) {
        return brandRepository.save(brandEntity);
    }

    @Override
    public Optional<BrandEntity> findbyId(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return brandRepository.existsById(id);
    }

    @Override
    public BrandEntity update(Long id, BrandEntity brandEntity) {
        brandEntity.setId(id);
        return brandRepository.findById(id).map( existingBrand->{
            Optional.ofNullable(brandEntity.getName()).ifPresent(existingBrand::setName);
            Optional.ofNullable(brandEntity.getCountry()).ifPresent(existingBrand::setCountry);
            return brandRepository.save(existingBrand);
        }).orElseThrow(()->new RuntimeException("Brand does not exist"));
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public List<BrandEntity> findAll() {
        return StreamSupport.stream(brandRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
