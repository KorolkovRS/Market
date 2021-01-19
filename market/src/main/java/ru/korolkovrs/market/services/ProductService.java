package ru.korolkovrs.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.korolkovrs.market.dto.ProductDTO;
import ru.korolkovrs.market.models.Product;
import ru.korolkovrs.market.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<ProductDTO> getAllProducts(Integer page, Integer size) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
        Page<ProductDTO> productDTOPage = new PageImpl<>(
                productPage.getContent().stream().map(ProductDTO::new).collect(Collectors.toList()),
                productPage.getPageable(),
                productPage.getTotalElements()
        );
        return productDTOPage;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
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

    public List<Product> findProductsById(Integer minPrice, Integer maxPrice) {
        return productRepository.findAllByPriceBetween(minPrice, maxPrice);
    }

}
