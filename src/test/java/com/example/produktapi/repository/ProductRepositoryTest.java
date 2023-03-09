package com.example.produktapi.repository;

import com.example.produktapi.model.Product;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest            // Used when we want to test our repository
class ProductRepositoryTest {

    @Autowired          // Helps us create an instantiation of ProductRepository (like a constructor)
    private ProductRepository underTest;

    /*
    @Test
    void whenTestingOurRepositoryIsEmpty_returnFalse() {
        List<Product> products = underTest.findAll();
        assertFalse(products.isEmpty());
    }
    */

    @Test
    void whenSearchingForAnExistingTitle_thenReturnThatProduct() {
        // given
        String title = "En dator";
        Product product = new Product(title,
                20000.0,
                "Elektronik",
                "Bra att ha",
                "urlTillBild");
        underTest.save(product);

        // when
        Optional<Product> optionalProduct = underTest.findByTitle(title);

        // then
        // One way to write three tests
        assertTrue(optionalProduct.isPresent());         // checks if optionalProduct contains a product
        assertFalse(optionalProduct.isEmpty());
        assertEquals(title, optionalProduct.get().getTitle());

        // Another way to write the same tests
        assertAll(
                () -> assertTrue(optionalProduct.isPresent()),
                () -> assertFalse(optionalProduct.isEmpty()),
                () -> assertEquals(title, optionalProduct.get().getTitle())
        );
    }

    @Test
    void whenSearchingForNonExistingTitle_thenReturnEmptyOptional() {
        // when
        Optional<Product> optionalProduct = underTest.findByTitle("En titel som inte finns");

        // then
        assertAll(
                () -> assertFalse(optionalProduct.isPresent()),
                () -> assertTrue(optionalProduct.isEmpty())
        );
    }

    @Test
    void givenListWithExistingCategory_whenSearchingForAnExistingCategory_thenCheckOptionalIfEmptyAndReturnFalse() {
        // given
        String title = "En dator";
        Product product = new Product(title,
                15000.0,
                "Electronics",
                "description",
                "enURLSträngHär");
        underTest.save(product);

        // when
        List<Product> listOfCategoryProducts = underTest.findByCategory("Electronics");

        // then
        assertFalse(listOfCategoryProducts.isEmpty());      // check if the category list is empty
    }

    @Test
    void givenListOfString_whenCheckingListIfEmpty_thenReturnFalse() {
        //when
        List<String> Categories = underTest.findAllCategories();

        //then
        assertFalse(Categories.isEmpty());
    }
}