package com.baseware.eshop.catalog.services.mappers;

import com.baseware.eshop.catalog.entity.Product;
import com.baseware.eshop.catalog.services.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  ProductDto toDto(Product product);

  Product toEntity(ProductDto productDto);
}
