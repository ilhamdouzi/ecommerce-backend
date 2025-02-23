package com.app.ecommerce_backend.mapper;

import com.app.ecommerce_backend.dto.ProductDTO;
import com.app.ecommerce_backend.model.Product;

public class ProductMapper {

    private ProductMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getInternalReference(),
                product.getShellId(),
                product.getInventoryStatus(),
                product.getRating(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
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
