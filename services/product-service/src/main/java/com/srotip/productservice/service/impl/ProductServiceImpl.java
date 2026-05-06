package com.srotip.productservice.service.impl;

import com.srotip.productservice.constants.AppConstants;
import com.srotip.productservice.dto.ProductRequestDTO;
import com.srotip.productservice.dto.ProductResponseDTO;
import com.srotip.productservice.event.ProductCreatedEvent;
import com.srotip.productservice.exception.ProductNotFoundException;
import com.srotip.productservice.mapper.ProductMapper;
import com.srotip.productservice.model.Product;
import com.srotip.productservice.producer.ProductEventProducer;
import com.srotip.productservice.repository.ProductRepository;
import com.srotip.productservice.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductEventProducer eventProducer;

    public ProductServiceImpl(ProductRepository repository,
            ProductEventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    @Override
    @CachePut(value = "products", key = "#result.id")
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        Product product = ProductMapper.toEntity(dto);
        Product saved = repository.save(product);

        // 🚀 BUILD EVENT
        ProductCreatedEvent event = new ProductCreatedEvent(
                saved.getId(),
                saved.getSku(),
                100, // default stock OR from dto later
                saved.getCategory(),
                saved.getBrand());

        // 📡 SEND EVENT TO KAFKA
        eventProducer.publishProductCreatedEvent(event);

        return ProductMapper.toDTO(saved);
    }

    // ================= GET BY ID (CACHE HERE 🔥) =================
    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductResponseDTO getProductById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        AppConstants.PRODUCT_NOT_FOUND + " : " + id));

        return ProductMapper.toDTO(product);
    }

    // ================= GET ALL =================
    @Override
    @Cacheable(value = "products_all")
    public List<ProductResponseDTO> getAllProducts() {

        return repository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    // ================= UPDATE =================
    @Override
    @CachePut(value = "products", key = "#id")
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {

        Product existing = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        AppConstants.PRODUCT_NOT_FOUND + " : " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setDiscountPrice(dto.getDiscountPrice());
        existing.setCategory(dto.getCategory());
        existing.setSubCategory(dto.getSubCategory());
        existing.setBrand(dto.getBrand());
        existing.setCurrency(dto.getCurrency());
        existing.setImageUrl(dto.getImageUrl());
        existing.setThumbnailUrl(dto.getThumbnailUrl());
        existing.setWeight(dto.getWeight());
        existing.setDimensions(dto.getDimensions());
        existing.setManufacturer(dto.getManufacturer());
        existing.setCountryOfOrigin(dto.getCountryOfOrigin());
        existing.setIsReturnable(dto.getReturnable());
        existing.setStatus(dto.getStatus());

        Product updated = repository.save(existing);

        return ProductMapper.toDTO(updated);
    }

    // ================= DELETE =================
    @Override
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        AppConstants.PRODUCT_NOT_FOUND + " : " + id));
        if (product == null) {
            repository.delete(product);
        }

    }
}