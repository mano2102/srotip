package com.srotip.productservice.mapper;

import com.srotip.productservice.dto.ProductRequestDTO;
import com.srotip.productservice.dto.ProductResponseDTO;
import com.srotip.productservice.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setSubCategory(dto.getSubCategory());
        product.setPrice(dto.getPrice());
        product.setDiscountPrice(dto.getDiscountPrice());
        product.setCurrency(dto.getCurrency());
        product.setSku(dto.getSku());
        product.setBarcode(dto.getBarcode());
        product.setBrand(dto.getBrand());
        product.setStatus(dto.getStatus());
        product.setImageUrl(dto.getImageUrl());
        product.setThumbnailUrl(dto.getThumbnailUrl());
        product.setWeight(dto.getWeight());
        product.setDimensions(dto.getDimensions());
        product.setManufacturer(dto.getManufacturer());
        product.setCountryOfOrigin(dto.getCountryOfOrigin());
        product.setIsReturnable(dto.getReturnable());

        return product;
    }

    public static ProductResponseDTO toDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setSubCategory(product.getSubCategory());
        dto.setPrice(product.getPrice());
        dto.setDiscountPrice(product.getDiscountPrice());
        dto.setCurrency(product.getCurrency());
        dto.setSku(product.getSku());
        dto.setBarcode(product.getBarcode());
        dto.setBrand(product.getBrand());
        dto.setStatus(product.getStatus());
        dto.setImageUrl(product.getImageUrl());
        dto.setThumbnailUrl(product.getThumbnailUrl());
        dto.setRating(product.getRating());
        dto.setReviewCount(product.getReviewCount());
        dto.setWeight(product.getWeight());
        dto.setDimensions(product.getDimensions());
        dto.setManufacturer(product.getManufacturer());
        dto.setCountryOfOrigin(product.getCountryOfOrigin());
        dto.setReturnable(product.getIsReturnable());
        dto.setActive(product.getIsActive());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        return dto;
    }
}