package com.baseware.eshop.catalog.services.impl;

import com.baseware.eshop.catalog.entity.Product;
import com.baseware.eshop.catalog.exceptions.ResourceNotFound;
import com.baseware.eshop.catalog.repositories.ProductRepository;
import com.baseware.eshop.catalog.services.ProductService;
import com.baseware.eshop.catalog.services.dto.ProductDto;
import com.baseware.eshop.catalog.services.mappers.ProductMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final ProductMapper productMapper = ProductMapper.INSTANCE;

  @Override
  public Page<ProductDto> getItems(Pageable pageable) {
    return productRepository.findAll(pageable).map(product -> productMapper.toDto(product));
  }

  @Override
  public ProductDto getItem(Long id) {
    return productRepository.findById(id)
        .map(product -> productMapper.toDto(product))
        .orElseThrow(()-> new ResourceNotFound("Product not found for: "+id));
  }

  @Override
  public Long createProduct(ProductDto productDto) {
    Product product = productMapper.toEntity(productDto);
    product = productRepository.save(product);
    return product.getId();
  }

  @Override
  public void updateProduct(ProductDto productDto) {
    Product product = productMapper.toEntity(productDto);
    productRepository.save(product);
  }

  @Override
  public void deleteProduct(Long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    productRepository.delete(optionalProduct
        .orElseThrow(()-> new ResourceNotFound("Product not found for: "+id)));
  }
}
