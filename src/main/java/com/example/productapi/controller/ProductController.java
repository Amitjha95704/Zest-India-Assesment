package com.example.productapi.controller;

import com.example.productapi.dto.PageResponse;
import com.example.productapi.dto.ProductRequest;
import com.example.productapi.dto.ProductResponse;
import com.example.productapi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.create(request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(productService.getAll(page, size));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(productService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.ok(
                productService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}