package com.srotip.productservice.service.impl;

import com.srotip.events.ProductCreatedEvent;
import com.srotip.productservice.constants.AppConstants;
import com.srotip.productservice.dto.ProductRequestDTO;
import com.srotip.productservice.dto.ProductResponseDTO;
import com.srotip.productservice.exception.ProductNotFoundException;
import com.srotip.productservice.mapper.ProductMapper;
import com.srotip.productservice.model.Product;
import com.srotip.productservice.producer.ProductEventProducer;
import com.srotip.productservice.repository.ProductRepository;
import com.srotip.productservice.service.ProductService;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product productToProcess;

        try {
            // 1. Try to find it first
            Optional<Product> existing = repository.findBySku(dto.getSku());

            if (existing.isPresent()) {
                productToProcess = existing.get();
            } else {
                // 2. If not found, try to save
                Product newProduct = ProductMapper.toEntity(dto);
                // We use saveAndFlush to force the DB to check the constraint NOW
                productToProcess = repository.saveAndFlush(newProduct);
            }
        } catch (DataIntegrityViolationException e) {
            // 3. RECOVERY: If a race condition happened and another thread saved
            // the SKU between our "check" and our "save", the DB throws this error.
            // We catch it and just fetch what the other thread created.
            productToProcess = repository.findBySku(dto.getSku())
                    .orElseThrow(() -> new RuntimeException("Race condition failure"));
        }

        // 4. ALWAYS SEND EVENT: This will now run even if the save "failed"
        // because we recovered the product in the catch block.
        ProductCreatedEvent event = new ProductCreatedEvent(
                productToProcess.getId(),
                productToProcess.getSku(),
                100,
                productToProcess.getCategory(),
                productToProcess.getBrand());

        eventProducer.publishProductCreatedEvent(event);

        return ProductMapper.toDTO(productToProcess);
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