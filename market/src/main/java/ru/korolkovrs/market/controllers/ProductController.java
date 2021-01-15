package ru.korolkovrs.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.korolkovrs.market.dto.ProductDTO;
import ru.korolkovrs.market.models.Product;
import ru.korolkovrs.market.services.ProductService;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<ProductDTO> getAllProducts(
            @RequestParam(name = "min_price", defaultValue = "0") Integer min_price,
            @RequestParam(name = "max_price", required = false) Integer max_price,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "productsAtTitle", defaultValue = "5") Integer size
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.getAllProducts(page - 1, size);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        productDTO.setId(null);
        Product product = new Product(productDTO);
        return new ProductDTO(productService.saveOrUpdate(product));
    }

    @PutMapping
    public ProductDTO update(@RequestBody ProductDTO productDTO) {
        Product product = new Product(productDTO);
        return new ProductDTO(productService.saveOrUpdate(product));
    }

    @DeleteMapping("delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
