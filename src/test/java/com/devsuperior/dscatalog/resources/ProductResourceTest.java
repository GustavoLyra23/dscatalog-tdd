package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.factory.Factory;
import com.devsuperior.dscatalog.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductResource.class)
public class ProductResourceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @BeforeEach
    void setUp() {
        PageImpl<ProductDTO> page = new PageImpl<>(List.of(Factory.createProductDTO()));
        Mockito.when(productService.findAllPaged(any(Pageable.class))).thenReturn(page);
    }

    @Test
    public void findAllShouldReturnPageOfProductsDto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products")).andExpect(status().isOk());
    }
}
