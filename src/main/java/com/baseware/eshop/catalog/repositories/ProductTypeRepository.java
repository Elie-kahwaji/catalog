package com.baseware.eshop.catalog.repositories;

import com.baseware.eshop.catalog.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {}
