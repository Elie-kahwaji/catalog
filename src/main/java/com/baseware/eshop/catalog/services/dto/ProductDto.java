package com.baseware.eshop.catalog.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ProductDto {
  private Long id;
  private String name;
  private String description;
  private Double price;
  private String pictureFileName;
  private String pictureFileUri;
  private long productTypeId;
  private ProductTypeDto productType;
  private long productBrandId;
  private ProductBrandDto productBrand;
  private int availableStock;
  private int restockThreshold;
  private int maxStockThreshold;

  @JsonIgnore
  public long getProductTypeId() {
    return productTypeId;
  }

  @JsonIgnore
  public long getProductBrandId() {
    return productBrandId;
  }
}
