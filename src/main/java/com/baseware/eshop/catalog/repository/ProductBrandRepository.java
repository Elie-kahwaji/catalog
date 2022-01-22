package com.baseware.eshop.catalog.repository;

import com.baseware.eshop.catalog.entity.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long> {}
