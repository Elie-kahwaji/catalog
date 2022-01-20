package com.baseware.eshop.catalog.services.impl;

import com.baseware.eshop.catalog.entity.Product;
import com.baseware.eshop.catalog.exceptions.ResourceNotFound;
import com.baseware.eshop.catalog.repositories.ProductBrandRepository;
import com.baseware.eshop.catalog.repositories.ProductRepository;
import com.baseware.eshop.catalog.repositories.ProductTypeRepository;
import com.baseware.eshop.catalog.services.CatalogService;
import com.baseware.eshop.catalog.services.dto.ProductBrandDto;
import com.baseware.eshop.catalog.services.dto.ProductDto;
import com.baseware.eshop.catalog.services.dto.ProductTypeDto;
import com.baseware.eshop.catalog.services.mappers.CatalogMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

  private final ProductRepository productRepository;
  private final ProductBrandRepository brandRepository;
  private final ProductTypeRepository typeRepository;

  private final CatalogMapper catalogMapper = CatalogMapper.INSTANCE;

  @Override
  public Page<ProductDto> getItems(Pageable pageable) {
    return productRepository.findAll(pageable)
        .map(catalogMapper::toProductDto);
  }

  @Override
  public ProductDto getItem(Long id) {
    return productRepository.findById(id)
        .map(catalogMapper::toProductDto)
        .orElseThrow(()-> new ResourceNotFound("Product not found for: "+id));
  }

  @Override
  public Long addProduct(ProductDto productDto) {
    Product product = catalogMapper.toProductEntity(productDto);
    product = productRepository.save(product);
    return product.getId();
  }

  @Override
  public void updateProduct(ProductDto productDto) {
    Product product = catalogMapper.toProductEntity(productDto);
    productRepository.save(product);
  }

  @Override
  public void deleteProduct(Long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    productRepository.delete(optionalProduct
        .orElseThrow(()-> new ResourceNotFound("Product not found for: "+id)));
  }

  @Override
  public List<ProductBrandDto> getBrands() {
    return brandRepository
        .findAll()
        .stream().map(catalogMapper::toBrandDto)
        .collect(Collectors.toList());
  }

  @Override
  public Long addBrand(ProductBrandDto productBrandDto) {
    return brandRepository.save(catalogMapper.toBrandEntity(productBrandDto)).getId();
  }


  @Override
  public List<ProductTypeDto> getTypes() {
    return typeRepository.findAll()
        .stream()
        .map(catalogMapper::toProductTypeDto)
        .collect(Collectors.toList());
  }

  @Override
  public Long addProductType(ProductTypeDto productTypeDto) {
    return typeRepository.save(catalogMapper.toProductTypeEntity(productTypeDto)).getId();
  }
}
