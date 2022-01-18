package com.baseware.eshop.catalog.services;

import com.baseware.eshop.catalog.services.dto.ProductDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  /**
   * Return a paged catalog items.
   *
   * @param pageable pagination criteria.
   * @return page catalog items.
   */
  Page<ProductDto> getItems(Pageable pageable);

  /**
   * Return a product item.
   *
   * @param id the identification of the product.
   * @return the product
   */
  ProductDto getItem(Long id);

  /**
   * Create a product.
   * @param productDto the new product.
   * @return the unique id of the product.
   */
  Long createProduct(ProductDto productDto);

  /**
   * Update a product
   * @param productDto update information of the product.
   */
  void updateProduct(ProductDto productDto);

  /**
   * Delete a product.
   *
   * @param id the id of the product.
   */
  void deleteProduct(Long id);
}
