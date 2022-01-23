package com.baseware.eshop.catalog.entity;

import com.baseware.eshop.catalog.core.data.entity.audit.Auditable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product_type")
public class ProductType extends Auditable<String> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "code")
  private String code;
  @Column(name = "name")
  private String name;
}
