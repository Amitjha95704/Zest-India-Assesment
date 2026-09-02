package com.example.productapi.service;

import com.example.productapi.dto.ItemRequest;
import com.example.productapi.dto.ItemResponse;
import com.example.productapi.entity.Item;
import com.example.productapi.entity.Product;
import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.repository.ItemRepository;
import com.example.productapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    public ItemResponse create(Long productId, ItemRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + productId
                ));

        Item item = new Item();
        item.setProduct(product);
        item.setQuantity(request.getQuantity());

        return mapToResponse(itemRepository.save(item));
    }

    public List<ItemResponse> getByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException(
                    "Product not found with id: " + productId
            );
        }

        return itemRepository.findByProductId(productId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ItemResponse update(Long itemId, ItemRequest request) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found with id: " + itemId
                ));

        item.setQuantity(request.getQuantity());

        return mapToResponse(itemRepository.save(item));
    }

    public void delete(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found with id: " + itemId
                ));

        itemRepository.delete(item);
    }

    private ItemResponse mapToResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getQuantity()
        );
    }
}