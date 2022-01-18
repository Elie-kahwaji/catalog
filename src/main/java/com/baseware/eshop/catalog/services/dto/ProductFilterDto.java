package com.baseware.eshop.catalog.services.dto;

import java.util.List;
import lombok.Data;

@Data
public class ProductFilterDto {

  private List<Long> brands;

  private List<String> types;

  private String brandName;

  private String typeName;

  private String productName;
}
