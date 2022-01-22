package com.baseware.eshop.catalog.service.dto;

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
  private String typeName;
  private long productBrandId;
  private String brandName;
  private int availableStock;
  private int restockThreshold;
  private int maxStockThreshold;
}
