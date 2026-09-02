package com.example.productapi.controller;

import com.example.productapi.dto.ItemRequest;
import com.example.productapi.dto.ItemResponse;
import com.example.productapi.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/{productId}/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> create(
            @PathVariable Long productId,
            @Valid @RequestBody ItemRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemService.create(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getByProductId(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                itemService.getByProductId(productId)
        );
    }
}