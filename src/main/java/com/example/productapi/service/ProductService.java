package com.example.productapi.service;

import com.example.productapi.dto.ProductRequest;
import com.example.productapi.dto.ProductResponse;
import com.example.productapi.entity.Product;
import com.example.productapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse create(ProductRequest request) {
        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setCreatedBy("system");
        product.setCreatedOn(LocalDateTime.now());

        return mapToResponse(productRepository.save(product));
    }

    public PageResponse<ProductResponse> getAll(int page, int size) {

        Page<Product> productPage = productRepository.findAll(
                PageRequest.of(page, size)
        );

        List<ProductResponse> products = productPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();

        return new PageResponse<>(
                products,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );
    }

    
    public ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id
                ));

        return mapToResponse(product);
    }
    

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id
                ));

        product.setProductName(request.getProductName());
        product.setModifiedBy("system");
        product.setModifiedOn(LocalDateTime.now());

        return mapToResponse(productRepository.save(product));
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id
                ));

        productRepository.delete(product);
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setProductName(product.getProductName());
        response.setCreatedBy(product.getCreatedBy());
        response.setCreatedOn(product.getCreatedOn());
        response.setModifiedBy(product.getModifiedBy());
        response.setModifiedOn(product.getModifiedOn());

        return response;
    }
}