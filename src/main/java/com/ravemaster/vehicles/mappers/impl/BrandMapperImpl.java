package com.ravemaster.vehicles.mappers.impl;

import com.ravemaster.vehicles.domain.dto.BrandDto;
import com.ravemaster.vehicles.domain.entity.BrandEntity;
import com.ravemaster.vehicles.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BrandMapperImpl implements Mapper<BrandEntity, BrandDto> {

    private final ModelMapper modelMapper;

    public BrandMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BrandDto mapTo(BrandEntity brandEntity) {
        return modelMapper.map(brandEntity, BrandDto.class);
    }

    @Override
    public BrandEntity mapFrom(BrandDto brandDto) {
        return modelMapper.map(brandDto, BrandEntity.class);
    }
}
