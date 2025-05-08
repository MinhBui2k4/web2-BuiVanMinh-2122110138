package com.example.BuiVanMinh.service.impl;

import com.example.BuiVanMinh.domain.Brand;
import com.example.BuiVanMinh.domain.Category;
import com.example.BuiVanMinh.domain.Product;
import com.example.BuiVanMinh.repository.BrandRepository;
import com.example.BuiVanMinh.repository.CategoryRepository;
import com.example.BuiVanMinh.repository.ProductRepository;
import com.example.BuiVanMinh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    private String generateImageName(String originalFilename) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return "p-" + timestamp + "_" + originalFilename;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByBrand(Long brandId) {
        return productRepository.findByBrandId(brandId);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(Long categoryId, Long brandId) {
        return productRepository.findByCategoryIdAndBrandId(categoryId, brandId);
    }

    @Override
    public Product createProduct(Product product, Category category, Brand brand, MultipartFile image) {
        product.setCategory(category);
        product.setBrand(brand);

        if (image != null && !image.isEmpty()) {
            String imageName = generateImageName(image.getOriginalFilename());
            Path imagePath = Paths.get("src/main/resources/static/images/products", imageName);
            try {
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, image.getBytes());
                product.setImage("/images/products/" + imageName);
            } catch (IOException e) {
                throw new RuntimeException("Không thể lưu ảnh: " + e.getMessage());
            }
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product productDetails, Category category, Brand brand, MultipartFile image) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setCategory(category);
        product.setBrand(brand);

        if (image != null && !image.isEmpty()) {
            String imageName = generateImageName(image.getOriginalFilename());
            Path imagePath = Paths.get("src/main/resources/static/images/products", imageName);
            try {
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, image.getBytes());
                product.setImage("/images/products/" + imageName);
            } catch (IOException e) {
                throw new RuntimeException("Không thể lưu ảnh: " + e.getMessage());
            }
        }

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
