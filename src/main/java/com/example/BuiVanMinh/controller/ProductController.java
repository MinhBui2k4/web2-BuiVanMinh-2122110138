package com.example.BuiVanMinh.controller;

import com.example.BuiVanMinh.domain.Brand;
import com.example.BuiVanMinh.domain.Category;
import com.example.BuiVanMinh.domain.Product;
import com.example.BuiVanMinh.repository.BrandRepository;
import com.example.BuiVanMinh.repository.CategoryRepository;
import com.example.BuiVanMinh.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productService.getAllProducts();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> getProductsByFilter(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "brandId", required = false) Long brandId) {
        logger.info("Fetching products with filters - categoryId: {}, brandId: {}", categoryId, brandId);
        try {
            List<Product> products;
            if (categoryId != null && brandId != null) {
                products = productService.getProductsByCategoryAndBrand(categoryId, brandId);
            } else if (categoryId != null) {
                products = productService.getProductsByCategory(categoryId);
            } else if (brandId != null) {
                products = productService.getProductsByBrand(brandId);
            } else {
                products = productService.getAllProducts();
            }
            logger.info("Found {} products with applied filters", products.size());
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error fetching filtered products", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        logger.info("Fetching product with id: {}", id);
        try {
            Optional<Product> product = productService.getProductById(id);
            return product.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Error fetching product with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).body("Error fetching product: " + e.getMessage());
        }
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<?> createProduct(
            @RequestPart("name") String name,
            @RequestPart("description") String description,
            @RequestPart("price") String price,
            @RequestPart("quantity") String quantity,
            @RequestPart("category_id") String categoryId,
            @RequestPart("brand_id") String brandId,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        logger.info("Creating new product: {}", name);
        try {
            Category category = categoryRepository.findById(Long.parseLong(categoryId))
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
            Brand brand = brandRepository.findById(Long.parseLong(brandId))
                    .orElseThrow(() -> new RuntimeException("Brand not found with ID: " + brandId));

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(Double.parseDouble(price));
            product.setQuantity(Integer.parseInt(quantity));

            Product created = productService.createProduct(product, category, brand, image);
            logger.info("Product created with ID: {}", created.getId());
            return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            logger.error("Error creating product: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error creating product: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestPart("name") String name,
            @RequestPart("description") String description,
            @RequestPart("price") String price,
            @RequestPart("quantity") String quantity,
            @RequestPart("category_id") String categoryId,
            @RequestPart("brand_id") String brandId,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        logger.info("Updating product with ID: {}", id);
        try {
            Category category = categoryRepository.findById(Long.parseLong(categoryId))
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
            Brand brand = brandRepository.findById(Long.parseLong(brandId))
                    .orElseThrow(() -> new RuntimeException("Brand not found with ID: " + brandId));

            Product productDetails = new Product();
            productDetails.setName(name);
            productDetails.setDescription(description);
            productDetails.setPrice(Double.parseDouble(price));
            productDetails.setQuantity(Integer.parseInt(quantity));

            Product updated = productService.updateProduct(id, productDetails, category, brand, image);
            logger.info("Product updated with ID: {}", updated.getId());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.error("Error updating product with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error updating product: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        try {
            productService.deleteProduct(id);
            logger.info("Product deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting product with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error deleting product: " + e.getMessage());
        }
    }
}
