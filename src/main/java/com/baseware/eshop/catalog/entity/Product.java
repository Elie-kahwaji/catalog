package com.baseware.eshop.catalog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price")
  private Double price;

  @Column(name = "picture_file_name")
  private String pictureFileName;

  @Column(name = "picture_file_uri")
  private String pictureFileUri;

  @Column(name = "product_type_id", nullable = false)
  private long productTypeId;

  @ManyToOne
  @JoinColumn(name = "product_type_id",
      referencedColumnName = "id",
      insertable = false,
      updatable = false)
  private ProductType productType;

  @Column(name = "product_brand_id")
  private long productBrandId;

  @ManyToOne
  @JoinColumn(name = "product_brand_id",
      referencedColumnName = "id",
      insertable = false,
      updatable = false)
  private ProductBrand productBrand;

  @Column(name = "available_stock")
  private int availableStock;

  @Column(name = "restock_threshold")
  private int restockThreshold;

  @Column(name = "max_stock_threshold")
  private int maxStockThreshold;
}
