package com.ravemaster.vehicles.repositories;

import com.ravemaster.vehicles.TestDataUtil;
import com.ravemaster.vehicles.domain.entity.BrandEntity;
import com.ravemaster.vehicles.domain.entity.ModelEntity;
import com.ravemaster.vehicles.repository.ModelRepository;
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
public class ModelEntityRepositoryIntegrationTests {

    private final ModelRepository underTest;

    @Autowired
    public ModelEntityRepositoryIntegrationTests(ModelRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatModelCanBeCreatedAndFetched(){
        BrandEntity bmw = TestDataUtil.createBrandEntityA();
        ModelEntity fiveSeries = TestDataUtil.createModelEntityA(bmw);
        ModelEntity savedModel = underTest.save(fiveSeries);
        Optional<ModelEntity> result = underTest.findById(savedModel.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedModel);
    }

    @Test
    public void testThatMultipleModelsCanBeCreatedAndFetched(){
        BrandEntity bmw = TestDataUtil.createBrandEntityA();
        BrandEntity mitsubishi = TestDataUtil.createBrandEntityB();
        BrandEntity dodge = TestDataUtil.createBrandEntityC();

        ModelEntity fiveSeries = TestDataUtil.createModelEntityA(bmw);
        ModelEntity outlander = TestDataUtil.createModelEntityB(mitsubishi);
        ModelEntity challenger = TestDataUtil.createModelEntityC(dodge);

        ModelEntity savedBmw = underTest.save(fiveSeries);
        ModelEntity savedMitsubishi = underTest.save(outlander);
        ModelEntity savedDodge = underTest.save(challenger);

        Iterable<ModelEntity> results = underTest.findAll();
        assertThat(results).hasSize(3).containsExactly(savedBmw,savedMitsubishi,savedDodge);
    }

    @Test
    public void testThatModelCanBeUpdated(){
        BrandEntity bmw = TestDataUtil.createBrandEntityA();
        ModelEntity fiveSeries = TestDataUtil.createModelEntityA(bmw);
        ModelEntity savedBmw = underTest.save(fiveSeries);
        savedBmw.getBrandEntity().setName("Bentley");
        savedBmw.getBrandEntity().setCountry("United Kingdom");
        savedBmw.setModelName("flying spur");
        ModelEntity newModel = underTest.save(savedBmw);
        Optional<ModelEntity> result = underTest.findById(savedBmw.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(newModel);
    }

    @Test
    public void testThatModelCanBeDeleted(){
        BrandEntity bmw = TestDataUtil.createBrandEntityA();
        ModelEntity fiveSeries = TestDataUtil.createModelEntityA(bmw);
        ModelEntity savedBmw = underTest.save(fiveSeries);
        underTest.deleteById(savedBmw.getId());
        Optional<ModelEntity> result = underTest.findById(savedBmw.getId());
        assertThat(result).isEmpty();
    }
}
