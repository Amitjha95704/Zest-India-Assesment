package com.example.productapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemResponse {

    private Long id;
    private Long productId;
    private Integer quantity;
}