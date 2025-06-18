package com.ravemaster.vehicles.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravemaster.vehicles.TestDataUtil;
import com.ravemaster.vehicles.domain.dto.BrandDto;
import com.ravemaster.vehicles.domain.entity.BrandEntity;
import com.ravemaster.vehicles.mappers.Mapper;
import com.ravemaster.vehicles.services.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BrandControllerIntegrationTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final BrandService brandService;

    private final Mapper<BrandEntity, BrandDto> brandMapper;

    @Autowired
    public BrandControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper, BrandService brandService, Mapper<BrandEntity, BrandDto> brandMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.brandService = brandService;
        this.brandMapper = brandMapper;
    }

    @Test
    public void testThatCreateBrandReturns201Created() throws Exception{
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        String brandJson = objectMapper.writeValueAsString(brandMapper.mapTo(brandEntity));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBrandReturnsSavedBrand() throws Exception{
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandDto brandDto = brandMapper.mapTo(brandEntity);
        String brandJson = objectMapper.writeValueAsString(brandDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(brandDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.country").value(brandDto.getCountry())
        );
    }

    @Test
    public void testThatListBrandsReturns200Ok() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBrandsReturnsListOfBrands() throws Exception{

        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity brand = brandService.createBrand(brandEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(brand.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].country").value(brand.getCountry())
        );
    }

    @Test
    public void testThatFindBrandReturns200Ok() throws Exception{
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity brand = brandService.createBrand(brandEntity);
        String brandJson = objectMapper.writeValueAsString(brandMapper.mapTo(brand));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindBrandReturnsRequestedBrand() throws Exception{
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity brand = brandService.createBrand(brandEntity);
        BrandDto brandDto = brandMapper.mapTo(brand);
        String brandJson = objectMapper.writeValueAsString(brandDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/brands/"+brand.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(brandDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.country").value(brandDto.getCountry())
        );
    }

    @Test
    public void testThatFullUpdateBrantReturns200Ok() throws Exception {
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity brand = brandService.createBrand(brandEntity);
        BrandDto brandDto = brandMapper.mapTo(brand);
        String brandJson = objectMapper.writeValueAsString(brandDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/brands/"+brandDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturns404NotFound() throws Exception {
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandDto brandDto = brandMapper.mapTo(brandEntity);
        String brandJson = objectMapper.writeValueAsString(brandDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/brands/234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsUpdatedAuthor() throws Exception {
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity brand = brandService.createBrand(brandEntity);
        BrandDto brandDto = brandMapper.mapTo(brand);

        BrandEntity brandEntity2 = TestDataUtil.createBrandEntityB();
        BrandDto brandDto2 = brandMapper.mapTo(brandEntity2);
        String brandJson = objectMapper.writeValueAsString(brandDto2);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/brands/"+brandDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(brandDto.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(brandDto2.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.country").value(brandDto2.getCountry())
        );
    }

    @Test
    public void testThatPartialUpdateBrandReturnsHttpOk() throws Exception {
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity brand = brandService.createBrand(brandEntity);
        brandEntity.setName("Mercedes Benz");
        BrandDto brandDto = brandMapper.mapTo(brandEntity);
        String brandJson = objectMapper.writeValueAsString(brandDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/brands/"+brand.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBrandReturnsPartiallyUpdated() throws Exception {
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity brand = brandService.createBrand(brandEntity);
        BrandDto brandDto = brandMapper.mapTo(brand);

        BrandEntity brandEntity2 = TestDataUtil.createBrandEntityB();
        BrandDto brandDto2 = brandMapper.mapTo(brandEntity2);
        String authorJson = objectMapper.writeValueAsString(brandDto2);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/brands/"+brandDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(brandDto.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(brandDto2.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.country").value(brandDto2.getCountry())
        );
    }

    @Test
    public void testThatDeleteBrandReturnsHttp204forNonExistingBrand() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/brands/837")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteBrandReturnsHttp204forExistingBrand() throws Exception{
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity brand = brandService.createBrand(brandEntity);
        BrandDto brandDto = brandMapper.mapTo(brand);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/brands/"+brandDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

}
