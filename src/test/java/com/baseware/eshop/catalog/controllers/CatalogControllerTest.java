package com.baseware.eshop.catalog.controllers;

import com.baseware.eshop.catalog.controllers.errors.GlobalExceptionHandler;
import com.baseware.eshop.catalog.core.data.exceptions.ResourceNotFound;
import com.baseware.eshop.catalog.services.CatalogService;
import com.baseware.eshop.catalog.services.dto.ProductDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CatalogController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CatalogControllerTest {

  private final static String MOCK_DATA = ResourceUtils.CLASSPATH_URL_PREFIX.concat("data/mock.json");

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private CatalogController catalogController;

  @MockBean
  private CatalogService catalogService;

  private List<ProductDto> mockProductList;

  @BeforeAll
  public void loadData() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    File file = ResourceUtils.getFile(MOCK_DATA);
    mockProductList = objectMapper.readValue(file, new TypeReference<>() {});
  }

  @BeforeEach
  void beforeEach() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(catalogController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        .build();
  }

  @Test
  public void givenProducts_whenGetProducts_thenStatus200() throws Exception {

    Mockito.when(catalogService.getItems(PageRequest.of(0, 20)))
        .thenReturn(new PageImpl(mockProductList.subList(0,20)));

    mockMvc.perform(get("/api/v1/catalog/products")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.content", hasSize(20)))
        .andExpect(jsonPath("$.content[0].name").value(mockProductList.get(0).getName()));
  }

  @Test
  public void givenProducts_whenGetProductsById_thenStatus200() throws Exception {
    final Long actualProductId = 3L;
    final ProductDto productDto = mockProductList.get(2);

    Mockito.when(catalogService.getItem(actualProductId))
        .thenReturn(productDto);

    mockMvc.perform(get("/api/v1/catalog/products/{id}", actualProductId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(productDto.getId()))
        .andExpect(jsonPath("$.name").value(productDto.getName()));
  }

  @Test
  public void givenProducts_whenGetProductsById_thenStatus404() throws Exception {
    Mockito.when(catalogService.getItem(anyLong()))
        .thenThrow(new ResourceNotFound("product not found"));

    mockMvc.perform(get("/api/v1/catalog/products/{id}", anyLong())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
  }

}
