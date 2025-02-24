package com.app.ecommerce_backend.mapper;

import com.app.ecommerce_backend.dto.ProductDTO;
import com.app.ecommerce_backend.model.Product;

public class ProductMapper {

    private ProductMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .category(product.getCategory())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .internalReference(product.getInternalReference())
                .shellId(product.getShellId())
                .inventoryStatus(product.getInventoryStatus())
                .rating(product.getRating())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .code(productDTO.code())
                .name(productDTO.name())
                .description(productDTO.description())
                .image(productDTO.image())
                .category(productDTO.category())
                .price(productDTO.price())
                .quantity(productDTO.quantity())
                .internalReference(productDTO.internalReference())
                .shellId(productDTO.shellId())
                .inventoryStatus(productDTO.inventoryStatus())
                .rating(productDTO.rating())
                .build();
    }
}
