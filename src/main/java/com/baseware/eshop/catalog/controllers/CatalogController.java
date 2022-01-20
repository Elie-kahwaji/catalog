package com.baseware.eshop.catalog.controllers;

import com.baseware.eshop.catalog.entity.ProductBrand;
import com.baseware.eshop.catalog.services.CatalogService;
import com.baseware.eshop.catalog.services.dto.ProductBrandDto;
import com.baseware.eshop.catalog.services.dto.ProductDto;
import com.baseware.eshop.catalog.services.dto.ProductFilterDto;
import com.baseware.eshop.catalog.services.dto.ProductTypeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog")
public class CatalogController {

  private final CatalogService catalogService;

  @Operation(summary = "Return available page products",
  responses = {
      @ApiResponse(responseCode = "200",
          description = "Return list of defined products",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))}),

      @ApiResponse(responseCode = "403",
          description = "Not authorized to return items",
          content =@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = Problem.class)) ),

      @ApiResponse(responseCode = "500", description = "System failed to retrieve item list",
          content =@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = Problem.class)))
  })
  @GetMapping("/products")
  public ResponseEntity<Page<ProductDto>> getProducts(Pageable pageable, ProductFilterDto productFilter) {
      return ResponseEntity.ok(catalogService.getItems(pageable));
  }

  @Operation(summary = "Return a product by id",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "Return a product by Id",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ProductDto.class)) ),
          @ApiResponse(responseCode = "404",
              description = "Product not found",
              content =@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = Problem.class)) ),
          @ApiResponse(responseCode = "403",
              description = "Not authorized to return item",
              content =@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = Problem.class)) ),
          @ApiResponse(responseCode = "500",
              description = "System failed to retrieve product",
              content =@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = Problem.class)))
  })
  @GetMapping("/products/{id}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable("id") long id) {
    return ResponseEntity.ok(catalogService.getItem(id));
  }

  @Operation(summary = "Create product",
      responses = {
          @ApiResponse(responseCode = "201",
              description = "create a product",
              content =@Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
              schema = @Schema(implementation = Long.class))),
          @ApiResponse(responseCode = "400",
              description = "Invalid product data",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class))),
          @ApiResponse(responseCode = "403",
              description = "Not authorized to create a product",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class))),
          @ApiResponse(responseCode = "500",
              description = "System failed to create a product",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class)))
  })
  @PostMapping("/products")
  public ResponseEntity<Long> addProduct(@Valid @RequestBody ProductDto productDto) {
     return ResponseEntity.status(HttpStatus.CREATED)
         .body(catalogService.addProduct(productDto));
  }

  @Operation(summary = "update an existing product",
      responses = {
          @ApiResponse(responseCode = "204",
              description = "Update a product"),
          @ApiResponse(responseCode = "400",
              description = "Invalid product data",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class))),
          @ApiResponse(responseCode = "403",
              description = "Not authorized to update a product",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class))),
          @ApiResponse(responseCode = "500",
              description = "System failed to update a product",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class)))
  })
  @PutMapping("/products")
  public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductDto productDto) {
    catalogService.updateProduct(productDto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "Delete a product",
      responses = {
          @ApiResponse(responseCode = "204", description = "Delete a product"),
          @ApiResponse(responseCode = "404", description = "Product not found",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class))),
          @ApiResponse(responseCode = "403", description = "Not authorized to delete a product",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class))),
          @ApiResponse(responseCode = "500", description = "System failed to delete a product",
              content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class)))
      })
  @DeleteMapping("/products/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable("id")  Long id) {
    catalogService.deleteProduct(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "Return available brands",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "Return list of brands",
              content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  array = @ArraySchema(schema = @Schema(implementation = ProductBrandDto.class)))}),

          @ApiResponse(responseCode = "403",
              description = "Not authorized",
              content =@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class)) ),

          @ApiResponse(responseCode = "500", description = "System failed.",
              content =@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class)))
      })
  @GetMapping("/brands")
  public ResponseEntity<List<ProductBrandDto>> getBrands() {
    return ResponseEntity.ok(catalogService.getBrands());
  }

  @PostMapping
  public ResponseEntity<Long> addBrand(@Valid @RequestBody ProductBrandDto productBrandDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(catalogService.addBrand(productBrandDto));
  }

  @Operation(summary = "Return available product types",
      responses = {
          @ApiResponse(responseCode = "200",
              description = "Return list of types",
              content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  array = @ArraySchema(schema = @Schema(implementation = ProductBrandDto.class)))}),

          @ApiResponse(responseCode = "403",
              description = "Not authorized",
              content =@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class)) ),

          @ApiResponse(responseCode = "500", description = "System failed.",
              content =@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = Problem.class)))
      })
  @GetMapping("/types")
  public ResponseEntity<List<ProductTypeDto>> getTypes() {
    return ResponseEntity.ok(catalogService.getTypes());
  }


  @PostMapping
  public ResponseEntity<Long> addProductType(@Valid @RequestBody ProductTypeDto productTypeDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(catalogService.addProductType(productTypeDto));
  }
}
