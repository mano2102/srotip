package com.srotip.productservice.service;

import com.srotip.productservice.dto.ProductRequestDTO;
import com.srotip.productservice.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);

    void deleteProduct(Long id);
}