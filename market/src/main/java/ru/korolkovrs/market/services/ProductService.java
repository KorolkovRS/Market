package ru.korolkovrs.market.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.korolkovrs.market.dto.ProductDTO;
import ru.korolkovrs.market.models.Product;
import ru.korolkovrs.market.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    @NonNull
    private ProductRepository productRepository;

    public Page<ProductDTO> getAllProducts(Specification<Product> spec, Integer page, Integer size) {
        return productRepository.findAll(spec, PageRequest.of(page, size));
    }


    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(ProductDTO::new);
    }

    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        System.out.println(id);
        productRepository.deleteById(id);
    }

    public Optional<Product> findProductsById(Long id) {
        return productRepository.findById(id);
    }

}
