package com.baseware.eshop.catalog.core.data.entity.audit;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@ToString
@EqualsAndHashCode
@MappedSuperclass
public class Auditable<T> {
  @Column(name = "created_date", updatable = false)
  @Temporal(TIMESTAMP)
  @CreatedDate
  protected Date creationDate;

  @Column(name = "updated_date")
  @LastModifiedDate
  @Temporal(TIMESTAMP)
  protected Date updatedDate;

  @CreatedBy
  @Column(name="created_by")
  protected T createdBy;

  @LastModifiedBy
  @Column(name="modified_by")
  protected T modifiedBy;
}
