package com.baseware.eshop.catalog.controllers;

import com.baseware.eshop.catalog.services.ProductService;
import com.baseware.eshop.catalog.services.dto.ProductDto;
import com.baseware.eshop.catalog.services.dto.ProductFilterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog")
public class CatalogController {

  private final ProductService productService;

  @Operation(summary = "Return available page products",
  responses = {
      @ApiResponse(responseCode = "200", description = "Return list of defined products"),
      @ApiResponse(responseCode = "403", description = "Not authorized to return items"),
      @ApiResponse(responseCode = "500", description = "System failed to retrieve item list")
  })
  @GetMapping("/products")
  public ResponseEntity<Page<ProductDto>> getProducts(Pageable pageable, ProductFilterDto productFilter) {
      return ResponseEntity.ok(productService.getItems(pageable));
  }

  @Operation(summary = "Return a product by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Return a product by Id"),
          @ApiResponse(responseCode = "404", description = "Product not found"),
          @ApiResponse(responseCode = "403", description = "Not authorized to return item"),
          @ApiResponse(responseCode = "500", description = "System failed to retrieve product")
  })
  @GetMapping("/products/{id}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable("id") long id) {
    return ResponseEntity.ok(productService.getItem(id));
  }

  @Operation(summary = "Create product",
      responses = {
          @ApiResponse(responseCode = "201", description = "create a product"),
          @ApiResponse(responseCode = "400", description = "Invalid product data"),
          @ApiResponse(responseCode = "403", description = "Not authorized to create a product"),
          @ApiResponse(responseCode = "500", description = "System failed to create a product")
  })
  @PostMapping("/products")
  public ResponseEntity<Long> createProduct(@Valid @RequestBody ProductDto productDto) {
     return ResponseEntity.status(HttpStatus.CREATED)
         .body(productService.createProduct(productDto));
  }

  @Operation(summary = "update an existing product",
      responses = {
          @ApiResponse(responseCode = "204", description = "Update a product"),
          @ApiResponse(responseCode = "400", description = "Invalid product data"),
          @ApiResponse(responseCode = "403", description = "Not authorized to update a product"),
          @ApiResponse(responseCode = "500", description = "System failed to update a product")
  })
  @PutMapping("/products")
  public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductDto productDto) {
    productService.updateProduct(productDto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "Delete a product",
      responses = {
          @ApiResponse(responseCode = "204", description = "Delete a product"),
          @ApiResponse(responseCode = "404", description = "Product not found"),
          @ApiResponse(responseCode = "403", description = "Not authorized to delete a product"),
          @ApiResponse(responseCode = "500", description = "System failed to delete a product")
      })
  @DeleteMapping("/products/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable("id")  Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
