package com.ravemaster.vehicles.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelDto {
    private Long id;
    private String modelName;
    private BrandDto brandDto;
}
