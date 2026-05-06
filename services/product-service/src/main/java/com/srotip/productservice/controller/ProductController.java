package com.srotip.productservice.controller;

import com.srotip.productservice.constants.ApiPaths;
import com.srotip.productservice.constants.AppConstants;
import com.srotip.productservice.dto.ApiResponse;
import com.srotip.productservice.dto.ProductRequestDTO;
import com.srotip.productservice.dto.ProductResponseDTO;
import com.srotip.productservice.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.BASE_PATH)
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ================= CREATE PRODUCT =================
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(
            @Valid @RequestBody ProductRequestDTO dto) {

        ProductResponseDTO response = service.createProduct(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(AppConstants.PRODUCT_CREATED, response));
    }

    // ================= GET PRODUCT BY ID =================
    @GetMapping(ApiPaths.BY_ID)
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(
            @PathVariable Long id) {

        ProductResponseDTO response = service.getProductById(id);

        return ResponseEntity
                .ok(ApiResponse.success(AppConstants.PRODUCT_FETCHED, response));
    }

    // ================= GET ALL PRODUCTS =================
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts() {

        List<ProductResponseDTO> response = service.getAllProducts();

        return ResponseEntity
                .ok(ApiResponse.success(AppConstants.PRODUCTS_FETCHED, response));
    }

    // ================= UPDATE PRODUCT =================
    @PutMapping(ApiPaths.BY_ID)
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto) {

        ProductResponseDTO response = service.updateProduct(id, dto);

        return ResponseEntity
                .ok(ApiResponse.success(AppConstants.PRODUCT_UPDATED, response));
    }

    // ================= DELETE PRODUCT =================
    @DeleteMapping(ApiPaths.BY_ID)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        service.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}