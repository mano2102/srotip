package com.srotip.productservice;

import com.srotip.productservice.dto.ProductRequestDTO;
import com.srotip.productservice.dto.ProductResponseDTO;
import com.srotip.productservice.exception.ProductNotFoundException;
import com.srotip.productservice.model.Product;
import com.srotip.productservice.repository.ProductRepository;
import com.srotip.productservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceApplicationTests {

	@Mock
	private ProductRepository repository;

	@InjectMocks
	private ProductServiceImpl service;

	private Product product;
	private ProductRequestDTO requestDTO;

	@BeforeEach
	void setUp() {

		product = new Product();
		product.setId(1L);
		product.setName("iPhone");
		product.setDescription("Apple phone");
		product.setPrice(new BigDecimal("50000"));
		product.setCategory("Mobile");
		product.setBrand("Apple");

		requestDTO = new ProductRequestDTO();
		requestDTO.setName("iPhone");
		requestDTO.setDescription("Apple phone");
		requestDTO.setPrice(new BigDecimal("50000"));
		requestDTO.setCategory("Mobile");
		requestDTO.setBrand("Apple");
		requestDTO.setCurrency("INR");
		requestDTO.setSku("SKU-001");
	}

	// ================= CREATE =================
	@Test
	void createProduct_shouldReturnSavedProduct() {

		when(repository.save(any(Product.class))).thenReturn(product);

		ProductResponseDTO result = service.createProduct(requestDTO);

		assertNotNull(result);
		assertEquals("iPhone", result.getName());

		verify(repository, times(1)).save(any(Product.class));
	}

	// ================= GET BY ID =================
	@Test
	void getProductById_shouldReturnProduct_whenExists() {

		when(repository.findById(1L)).thenReturn(Optional.of(product));

		ProductResponseDTO result = service.getProductById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());

		verify(repository, times(1)).findById(1L);
	}

	// ================= NOT FOUND =================
	@Test
	void getProductById_shouldThrowException_whenNotFound() {

		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class,
				() -> service.getProductById(1L));
	}

	// ================= GET ALL =================
	@Test
	void getAllProducts_shouldReturnList() {

		when(repository.findAll()).thenReturn(List.of(product));

		List<ProductResponseDTO> result = service.getAllProducts();

		assertEquals(1, result.size());

		verify(repository, times(1)).findAll();
	}

	// ================= UPDATE =================
	@Test
	void updateProduct_shouldUpdateSuccessfully() {

		when(repository.findById(1L)).thenReturn(Optional.of(product));
		when(repository.save(any(Product.class))).thenReturn(product);

		ProductResponseDTO result = service.updateProduct(1L, requestDTO);

		assertNotNull(result);

		verify(repository, times(1)).findById(1L);
		verify(repository, times(1)).save(any(Product.class));
	}

	// ================= DELETE =================
	@Test
	void deleteProduct_shouldDeleteSuccessfully() {

		when(repository.findById(1L)).thenReturn(Optional.of(product));

		service.deleteProduct(1L);

		verify(repository, times(1)).delete(product);
	}

	// ================= EDGE CASE: INVALID PRICE =================
	@Test
	void createProduct_shouldHandleZeroPriceEdgeCase() {

		requestDTO.setPrice(new BigDecimal("0")); // invalid as per validation rules

		when(repository.save(any(Product.class))).thenReturn(product);

		ProductResponseDTO result = service.createProduct(requestDTO);

		assertNotNull(result);
	}

	// ================= EDGE CASE: NULL DTO FIELDS =================
	@Test
	void createProduct_shouldHandleMinimalValidData() {

		ProductRequestDTO minimal = new ProductRequestDTO();
		minimal.setName("Phone");
		minimal.setDescription("Desc");
		minimal.setPrice(new BigDecimal("100"));
		minimal.setCategory("Cat");
		minimal.setBrand("Brand");
		minimal.setCurrency("INR");
		minimal.setSku("SKU-002");

		when(repository.save(any(Product.class))).thenReturn(product);

		ProductResponseDTO result = service.createProduct(minimal);

		assertNotNull(result);
	}
}