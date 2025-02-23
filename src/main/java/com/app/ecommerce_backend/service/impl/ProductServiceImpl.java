package com.app.ecommerce_backend.service.impl;

import com.app.ecommerce_backend.dto.ProductDTO;
import com.app.ecommerce_backend.exception.InvalidRequestException;
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
        var productToSave = ProductMapper.toEntity(product);
        var savedProduct = productRepository.save(productToSave);
        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        var updatedProduct = ProductMapper.toEntity(productDTO);
        updatedProduct.setId(existingProduct.getId());
        updatedProduct.setCreatedAt(existingProduct.getCreatedAt());
        return ProductMapper.toDTO(productRepository.save(updatedProduct));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with provided id");
        }
        productRepository.deleteById(id);

    }
}
