package com.app.ecommerce_backend.controller;

import com.app.ecommerce_backend.dto.ProductDTO;
import com.app.ecommerce_backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product API", description = "Gestion des produits")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Créer un nouveau produit", description = "Permet de créer un nouveau produit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produit créé avec succès",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDto) {
        log.info("Received create product request");
        ProductDTO createdProduct = productService.createProduct(productDto);
        log.info("Returning response for create product");
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @Operation(summary = "Récupérer tous les produits", description = "Retourne la liste de tous les produits")
    @ApiResponse(responseCode = "200", description = "Liste des produits récupérée avec succès",
            content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.info("Received request to get all products");
        List<ProductDTO> products = productService.getAllProducts();
        log.info("Returning {} products", products.size());
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Récupérer un produit par son ID", description = "Retourne les détails d'un produit existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit récupéré",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        log.info("Received request to retrieve product with id: {}", id);
        ProductDTO product = productService.getProductById(id);
        log.info("Returning product details for id: {}", id);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Mettre à jour un produit", description = "Met à jour les informations d'un produit existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit mis à jour avec succès",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long id,
                                                    @Valid @RequestBody ProductDTO productDto) {
        log.info("Received update request for product id: {}", id);
        ProductDTO updatedProduct = productService.updateProduct(id, productDto);
        log.info("Returning updated product for id: {}", id);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Supprimer un produit", description = "Supprime un produit par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produit supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        log.info("Received delete request for product id: {}", id);
        productService.deleteProduct(id);
        log.info("Product id {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
