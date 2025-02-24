package com.app.ecommerce_backend.service;

import com.app.ecommerce_backend.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO product);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, ProductDTO product);

    void deleteProduct(Long id);
}
