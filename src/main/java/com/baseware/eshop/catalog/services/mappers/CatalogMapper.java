package com.baseware.eshop.catalog.services.mappers;

import com.baseware.eshop.catalog.entity.Product;
import com.baseware.eshop.catalog.entity.ProductBrand;
import com.baseware.eshop.catalog.entity.ProductType;
import com.baseware.eshop.catalog.services.dto.ProductBrandDto;
import com.baseware.eshop.catalog.services.dto.ProductDto;
import com.baseware.eshop.catalog.services.dto.ProductTypeDto;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CatalogMapper {

  CatalogMapper INSTANCE = Mappers.getMapper(CatalogMapper.class);

  @Mapping(source = "productType.name", target = "typeName")
  @Mapping(source = "productBrand.name", target = "brandName")
  ProductDto toProductDto(Product product);

  Product toProductEntity(ProductDto productDto);

  ProductBrandDto toBrandDto(ProductBrand productBrand);

  ProductBrand toBrandEntity(ProductBrandDto productBrandDto);

  ProductTypeDto toProductTypeDto(ProductType productType);

  ProductType toProductTypeEntity(ProductTypeDto productTypeDto);
}
