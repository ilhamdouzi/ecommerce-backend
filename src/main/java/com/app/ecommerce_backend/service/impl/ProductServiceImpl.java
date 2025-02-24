package com.app.ecommerce_backend.service.impl;

import com.app.ecommerce_backend.dto.ProductDTO;
import com.app.ecommerce_backend.exception.ResourceNotFoundException;
import com.app.ecommerce_backend.mapper.ProductMapper;
import com.app.ecommerce_backend.model.Product;
import com.app.ecommerce_backend.repository.ProductRepository;
import com.app.ecommerce_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductDTO product) {
        log.debug("Mapping ProductDTO to entity");
        var productToSave = ProductMapper.toEntity(product);
        log.debug("Saving new product entity to the repository");
        var savedProduct = productRepository.save(productToSave);
        log.debug("Product saved: {}", savedProduct);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        log.debug("Fetching all products from the repository");
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        log.debug("Fetching product with id: {}", id);
        ProductDTO product = productRepository.findById(id)
                .map(ProductMapper::toDTO)
                .orElseThrow(() -> {
                    log.warn("Product not found with id: {}", id);
                    return new ResourceNotFoundException("Product not found with ID: " + id);
                });
        log.debug("Retrieved product: {}", product);
        return product;
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        log.debug("Updating product with id: {}", id);
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        log.debug("Mapping updated ProductDTO to entity");
        var updatedProduct = ProductMapper.toEntity(productDTO);
        updatedProduct.setId(existingProduct.getId());
        updatedProduct.setCreatedAt(existingProduct.getCreatedAt());
        ProductDTO result = ProductMapper.toDTO(productRepository.save(updatedProduct));
        log.debug("Product updated: {}", result);
        return result;
    }

    @Override
    public void deleteProduct(Long id) {
        log.debug("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            log.warn("Attempted to delete non-existent product with id: {}", id);
            throw new ResourceNotFoundException("Product not found with provided id");
        }
        productRepository.deleteById(id);
        log.debug("Product with id {} deleted", id);

    }
}
