package com.ravemaster.vehicles.repositories;

import com.ravemaster.vehicles.TestDataUtil;
import com.ravemaster.vehicles.domain.entity.BrandEntity;
import com.ravemaster.vehicles.repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BrandEntityRepositoryIntegrationTests {

    private final BrandRepository underTest;

    @Autowired
    public BrandEntityRepositoryIntegrationTests(BrandRepository underTest) {
        this.underTest = underTest;
    }


    @Test
    public void testThatBrandCanBCreatedAndFetched(){
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity createdBrandEntity = underTest.save(brandEntity);
        Optional<BrandEntity> savedBrandEntity = underTest.findById(brandEntity.getId());
        assertThat(savedBrandEntity).isPresent();
        assertThat(savedBrandEntity.get()).isEqualTo(createdBrandEntity);
    }

    @Test
    public void testThatMultipleBrandsCanBeCreatedAndFetched(){
        BrandEntity bmw = TestDataUtil.createBrandEntityA();
        BrandEntity mitsubishi = TestDataUtil.createBrandEntityB();
        BrandEntity dodge = TestDataUtil.createBrandEntityC();

        BrandEntity savedBmw = underTest.save(bmw);
        BrandEntity savedMitsubishi = underTest.save(mitsubishi);
        BrandEntity savedDodge = underTest.save(dodge);

        Iterable<BrandEntity> savedBrands = underTest.findAll();

        assertThat(savedBrands).hasSize(3).containsExactly(savedBmw,savedMitsubishi,savedDodge);
    }

    @Test
    public void testThatBrandCanBeUpdated(){
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity savedEntity = underTest.save(brandEntity);
        savedEntity.setName("Bentley");
        savedEntity.setCountry("United Kingdom");
        underTest.save(savedEntity);
        Optional<BrandEntity> result = underTest.findById(savedEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedEntity);

    }

    @Test
    public void testThatBrandCanBeDeleted(){
        BrandEntity brandEntity = TestDataUtil.createBrandEntityA();
        BrandEntity savedEntity = underTest.save(brandEntity);
        underTest.deleteById(savedEntity.getId());
        Optional<BrandEntity> result = underTest.findById(savedEntity.getId());
        assertThat(result).isEmpty();
    }


}
