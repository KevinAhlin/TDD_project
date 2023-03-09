package com.example.produktapi.service;

import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)     // Sätter upp miljön för Mockning
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService underTest;

    @Captor
    ArgumentCaptor<Product> productCaptor;

    Product testProduct;

    @BeforeEach
    void setup() {
        testProduct = new Product("Testprodukt", 26.0,"Testkategori","Testbeskrivning","");
        testProduct.setId(1);
    }

    @Test
    @DisplayName("test getAllProducts")
    void whenGetAllProducts_thenExactlyOneInteractionWithRepositoryMethodFindAll() {
        //when
        underTest.getAllProducts();

        //then
        verify(productRepository).findAll();
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("test getAllCategories")
    void whenGetAllCategories_thenExactlyOneInteractionWithRepositoryMethodGetByCategory() {
        // when
        underTest.getAllCategories();

        // then
        verify(productRepository).findAllCategories();
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("test getProductByCategory")
    void whenGetProductsByCategory_thenExactlyOneInteractionWithRepositoryMethodGetByCategory() {
        // when
        underTest.getProductsByCategory("Electronics");

        // then
        verify(productRepository).findByCategory("Electronics");
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("test getProductById - normalflöde")
    void givenProductWithExistingId_whenGetProductById_thenReturnEqual() {
        //given
        // given bestämmer hur en metod ska bete sig och sen sätter vi willReturn som returnerar i detta fall en Optional av testProduct
        // Alltså om findById hittar en produkt kommer den att returnera den produkt med samma id i form av en Optional
        given(productRepository.findById(testProduct.getId())).willReturn(Optional.of(testProduct));

        // when
        Product testProduct2 = underTest.getProductById(1);     // hämtar en produkt med samma id

        System.out.println("Product 1: " + testProduct);
        System.out.println("Product 2: " + testProduct2);

        // then
        assertEquals(testProduct, testProduct2);
    }

    @Test
    @DisplayName("test getProductById - felflöde")
    void givenProductWithNonExistingId_whenGetProductById_thenReturnThrow() {
        //given
        // given bestämmer hur en metod ska bete sig och sen sätter vi willReturn som returnerar i detta fall en Optional av testProduct som är tom
        given(productRepository.findById(testProduct.getId())).willReturn(Optional.empty());

        // when
        // then - kolla att exception kastas
        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> underTest.getProductById(testProduct.getId()));
    }

    @Test
    @DisplayName("test AddProduct - normalföde")
    void whenAddProduct_thenSaveMethodShouldBeCalled() {
        // when
        underTest.addProduct(testProduct);

        // Capture fångar värdet (produkten) som sparas i save metoden i addProduct
        verify(productRepository).save(productCaptor.capture());

        // then
        // kollar att testProduct är densamma som värdet av productCaptor
        assertEquals(testProduct, productCaptor.getValue());
    }

    @Test
    @DisplayName("test addProduct - felflöde")
    void whenAddProductWithExistingId_thenShouldThrowException() {
        // given
        // given bestämmer hur en metod ska bete sig och sen sätter vi willReturn som returnerar i detta fall en Optional av testProduct
        given(productRepository.findByTitle(testProduct.getTitle())).willReturn(Optional.of(testProduct));

        // when
        // then - kolla att exception kastas
        BadRequestException exception =
                assertThrows(BadRequestException.class, () -> underTest.addProduct(testProduct));

        // För att kolla så att vi aldrig sparas något när vi får en BadRequestException
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("test updateProduct - normalföde")
    void whenTryingToUpdateExistingProduct_thenReturnTrue() {
        //given
        System.out.println(testProduct);
        // Lägg till två produkter där ena uppdateras för den andra

        Product testProduct2 = new Product("Ny produkt",46.0,"Annan kategori","Annan beskrivning","");

        // when
        // given bestämmer hur en metod ska bete sig och sen sätter vi willReturn som returnerar i detta fall en Optional av testProduct
        given(productRepository.findById(testProduct.getId())).willReturn(Optional.of(testProduct));
        underTest.updateProduct(testProduct2, testProduct.getId());

        System.out.println("Product 1: " + testProduct);
        System.out.println("Product 2: " + testProduct2);

        // then
        verify(productRepository,times(1)).findById(testProduct.getId());
        verify(productRepository,times(1)).save(testProduct2);
        //verifyNoMoreInteractions(productRepository);

    }

    @Test
    @DisplayName("test updateProduct - felflöde")
    void whenTryingToUpdateNonExistingProduct_thenWillThrowException() {
        // given
        Integer productId = 6;
        // Skapa en produkt som inte har något id
        Product NoIdProduct = new Product("Produkt utan id",40.0,"","","");

        // when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                        () -> underTest.updateProduct(NoIdProduct,productId));

        //then
        // Kolla att felmeddelanden stämmer
        assertEquals("Produkt med id " + productId + " hittades inte", exception.getMessage());
    }

    @Test
    @DisplayName("test deleteProduct - normalföde")
    void whenTryingToDeleteExistingProduct_thenReturnTrue() {
        // given
        System.out.println(testProduct);

        // when
        // 'given' bestämmer hur en metod ska bete sig och sen sätter vi willReturn som returnerar i detta fall en Optional av testProduct
        given(productRepository.findById(testProduct.getId())).willReturn(Optional.of(testProduct));
        underTest.deleteProduct(testProduct.getId());

        // then
        verify(productRepository, times(1)).deleteById(testProduct.getId());
        verify(productRepository, times(1)).findById(testProduct.getId());
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("test deleteProduct - felflöde")
    void whenTryingToDeleteNonExistingProduct_thenWillThrowException() {
        // given
        Integer productId = 6;

        // when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> underTest.deleteProduct(productId));

        //then
        // Kolla att felmeddelanden stämmer
        assertEquals("Produkt med id " + productId + " hittades inte", exception.getMessage());
    }
}