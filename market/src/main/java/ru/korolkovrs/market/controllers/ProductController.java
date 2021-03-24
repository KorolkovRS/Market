package ru.korolkovrs.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.korolkovrs.market.dto.ProductDto;
import ru.korolkovrs.market.exception_handlers.ResourceNotFoundException;
import ru.korolkovrs.market.models.Product;
import ru.korolkovrs.market.repositories.specifications.ProductSpecifications;
import ru.korolkovrs.market.services.ProductService;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> getAllProducts(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "productsAtTitle", defaultValue = "5") Integer size
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.getAllProducts(ProductSpecifications.build(params), page - 1, size);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return new ProductDto(productService.getProductById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product with id " + id + " doesn't exist")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody ProductDto productDTO) {
        productDTO.setId(null);
        Product product = new Product(productDTO);
        return new ProductDto(productService.saveOrUpdate(product));
    }

    @PutMapping
    public ProductDto update(@RequestBody ProductDto productDTO) {
        Product product = new Product(productDTO);
        return new ProductDto(productService.saveOrUpdate(product));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
