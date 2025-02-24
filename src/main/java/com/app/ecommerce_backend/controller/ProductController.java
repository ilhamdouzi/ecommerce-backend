package com.app.ecommerce_backend.controller;

import com.app.ecommerce_backend.dto.ProductDTO;
import com.app.ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDto) {
        log.info("Received create product request");
        ProductDTO createdProduct = productService.createProduct(productDto);
        log.info("Returning response for create product");
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.info("Received request to get all products");
        List<ProductDTO> products = productService.getAllProducts();
        log.info("Returning {} products", products.size());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        log.info("Received request to retrieve product with id: {}", id);
        ProductDTO product = productService.getProductById(id);
        log.info("Returning product details for id: {}", id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long id,
                                                    @Valid @RequestBody ProductDTO productDto) {
        log.info("Received update request for product id: {}", id);
        ProductDTO updatedProduct = productService.updateProduct(id, productDto);
        log.info("Returning updated product for id: {}", id);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        log.info("Received delete request for product id: {}", id);
        productService.deleteProduct(id);
        log.info("Product id {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
