package com.baseware.eshop.catalog.service.dto;

import java.util.List;
import lombok.Data;

@Data
public class ProductFilterDto {

  private List<Long> brands;

  private List<Long> types;

  private String brandName;

  private String typeName;

  private String productName;
}
