package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIT {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    private Long exisitingId;
    private Long nonExistingId;
    private Long countTotalProducts;


    @BeforeEach
    public void setUp() {
        exisitingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;

    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists() {
        productService.delete(exisitingId);
        Assertions.assertEquals(countTotalProducts - 1, productRepository.count());
    }

    @Test
    public void deleteShouldThrowNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.delete(nonExistingId));
    }

    @Test
    public void findAllPagedShouldReturnPageWhenPageIsValid() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductDTO> page = productService.findAllPaged(pageRequest);
        Assertions.assertFalse(page.isEmpty());
        Assertions.assertEquals(0, page.getNumber());
        Assertions.assertEquals(10, page.getSize());
        Assertions.assertEquals(countTotalProducts, page.getTotalElements());
    }

    @Test
    public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
        PageRequest pageRequest = PageRequest.of(50, 10);
        Page<ProductDTO> page = productService.findAllPaged(pageRequest);
        Assertions.assertTrue(page.isEmpty());
    }
}
