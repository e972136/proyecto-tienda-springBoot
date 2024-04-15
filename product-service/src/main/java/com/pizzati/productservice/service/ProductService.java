package com.pizzati.productservice.service;

import com.pizzati.productservice.entity.Product;
import com.pizzati.productservice.entity.dto.ProductRequest;
import com.pizzati.productservice.entity.dto.ProductResponse;
import com.pizzati.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Salvado{}",product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> all = productRepository.findAll();
        return all.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice()
        );
    }
}
