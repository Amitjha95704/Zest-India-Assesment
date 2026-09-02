package com.example.productapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String productName;
    private String createdBy;
    private LocalDateTime createdOn;
    private String modifiedBy;
    private LocalDateTime modifiedOn;
}