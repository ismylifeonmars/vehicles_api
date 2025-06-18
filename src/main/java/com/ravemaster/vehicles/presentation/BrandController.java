package com.ravemaster.vehicles.presentation;

import com.ravemaster.vehicles.domain.dto.BrandDto;
import com.ravemaster.vehicles.domain.entity.BrandEntity;
import com.ravemaster.vehicles.mappers.Mapper;
import com.ravemaster.vehicles.services.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BrandController {

    private final BrandService brandService;

    private final Mapper<BrandEntity, BrandDto> mapper;

    public BrandController(BrandService brandService, Mapper<BrandEntity, BrandDto> mapper) {
        this.brandService = brandService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/brands")
    public ResponseEntity<BrandDto> createBrand(
            @RequestBody BrandDto brandDto
    ){
        BrandEntity brandEntity = mapper.mapFrom(brandDto);
        BrandEntity savedBrand = brandService.createBrand(brandEntity);
        return new ResponseEntity<>(mapper.mapTo(savedBrand), HttpStatus.CREATED);
    }

    @GetMapping(path = "/brands")
    public List<BrandDto> getBrands(){
        List<BrandEntity> brands = brandService.findAll();
        return brands.stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/brands/{id}")
    public ResponseEntity<BrandDto> getBrandById(
            @PathVariable("id") Long id
    ){
        Optional<BrandEntity> brandEntity = brandService.findbyId(id);
        return brandEntity.map(brandEntity1 -> {
            BrandDto brandDto = mapper.mapTo(brandEntity1);
            return new ResponseEntity<>(brandDto,HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping(path = "/brands/{id}")
    public ResponseEntity<BrandDto> fullUpdate(
            @PathVariable("id") Long id,
            @RequestBody BrandDto brandDto
    ){
        if (!brandService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        brandDto.setId(id);
        BrandEntity brandEntity = mapper.mapFrom(brandDto);
        BrandEntity brand = brandService.createBrand(brandEntity);
        return new ResponseEntity<>(mapper.mapTo(brand),HttpStatus.OK);
    }

    @PatchMapping(path = "/brands/{id}")
    public ResponseEntity<BrandDto> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody BrandDto brandDto
    ){
        if (!brandService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BrandEntity brandEntity = mapper.mapFrom(brandDto);
        BrandEntity brand = brandService.update(id,brandEntity);
        return new ResponseEntity<>(mapper.mapTo(brand),HttpStatus.OK);
    }

    @DeleteMapping(path = "/brands/{id}")
    public ResponseEntity deleteBrand(
            @PathVariable("id") Long id
    ){
        brandService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
