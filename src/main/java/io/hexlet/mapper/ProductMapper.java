package io.hexlet.mapper;

import io.hexlet.dto.product.ProductDTO;
import io.hexlet.dto.product.ProductShowDTO;
import io.hexlet.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = {ReferenceMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {
    public abstract Product map(ProductDTO productDTO);
    public abstract ProductDTO map(Product product);

    @Mapping(source = "category.title", target = "category")
    public abstract ProductShowDTO toShow(Product product);
}
