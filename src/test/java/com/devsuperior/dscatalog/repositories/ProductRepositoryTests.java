package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    private static long existingId;
    private static long countTotalProducts;
    private static long nonExistingId;

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    public static void setUp() {
        existingId = 1L;
        countTotalProducts = 25L;
        nonExistingId = 0L;
    }


    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        productRepository.deleteById(existingId);
        Optional<Product> result = productRepository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void saveShouldPersistWithAutoIncrement() {
        Product product = Factory.createProduct();
        product.setId(null);
        product = productRepository.save(product);
        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() {
        Optional<Product> product = productRepository.findById(existingId);
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals(existingId, product.get().getId());
    }

    @Test
    public void findByIdShouldReturnEmptyWhenIdDoesNotExist() {
        Optional<Product> product = productRepository.findById(nonExistingId);
        Assertions.assertFalse(product.isPresent());
    }


}
