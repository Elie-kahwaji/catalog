package com.baseware.eshop.catalog.services;

import com.baseware.eshop.catalog.services.dto.ProductBrandDto;
import com.baseware.eshop.catalog.services.dto.ProductDto;
import com.baseware.eshop.catalog.services.dto.ProductTypeDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatalogService {

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
  Long addProduct(ProductDto productDto);

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

  /**
   * Return a list of brands.
   *
   * @return list of brands.
   */
  List<ProductBrandDto> getBrands();

  /**
   * Add a brand.
   * @param productBrandDto the new brand.
   * @return the unique id of the brand.
   */
  Long addBrand(ProductBrandDto productBrandDto);

  /**
   * Return list of product types.
   *
   * @return list of product types.
   */
  List<ProductTypeDto> getTypes();

  /**
   * Add a product type.
   * @param productTypeDto the new product type.
   * @return the unique id of the product type.
   */
  Long addProductType(ProductTypeDto productTypeDto);
}
