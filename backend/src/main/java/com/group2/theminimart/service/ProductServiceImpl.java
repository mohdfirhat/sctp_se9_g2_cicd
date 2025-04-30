package com.group2.theminimart.service;

import java.util.List;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.group2.theminimart.dto.ProductDto;
import com.group2.theminimart.dto.ProductRatingDto;
import com.group2.theminimart.dto.ProductRequestDto;
import com.group2.theminimart.entity.Product;
import com.group2.theminimart.exception.ProductNotFoundException;
import com.group2.theminimart.exception.ProductSearchNotFoundException;
import com.group2.theminimart.mapper.ProductMapper;
import com.group2.theminimart.repository.ProductRepository;
import com.group2.theminimart.repository.RatingRepository;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private ProductRepository productRepository;
    private RatingRepository ratingRepository;

    public ProductServiceImpl(ProductRepository productRepository, RatingRepository ratingRepository) {
        this.productRepository = productRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public ProductDto createProduct(ProductRequestDto productRequestDto) {
        logger.info("Request to create a new product: {}", productRequestDto.getTitle());
        Product newProduct = productRepository.save(ProductMapper.productDtoToProduct(productRequestDto));
        logger.info("Created a new product|Id: {}", newProduct.getId());
        return convertToDto(newProduct);

    }

    @Override
    public ProductDto getProduct(Long id) {
        logger.info("Request to get product by id: {}", id);
        return productRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ProductNotFoundException(id));

    }

    @Override
    public List<ProductDto> getAllProducts() {
        logger.info("Request to get all products");
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    @Override
    public ProductDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        // update the product retrieved from the database.
        productToUpdate.setTitle(productRequestDto.getTitle());
        productToUpdate.setPrice(productRequestDto.getPrice());
        productToUpdate.setDescription(productRequestDto.getDescription());
        productToUpdate.setCategory(productRequestDto.getCategory());
        productToUpdate.setImage(productRequestDto.getImage());
        logger.info("Request to update product by id: {}", id);
        // save the updated product back to the database.
        Product updatedProduct = productRepository.save(productToUpdate);
        logger.info("Updated product|Id: {}", updatedProduct.getId());
        // return the updated product dto.
        return convertToDto(updatedProduct);

    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        logger.info("Request to delete product by id: {}", id);
        productRepository.deleteById(id);
        logger.info("Deleted product|Id: {}", id);

    }

    @Override
    public List<ProductDto> searchProducts(String title, String description, String category) {
        logger.info("Request to search products by title: {}, description: {}, category: {}", title, description,
                category);
        List<Product> foundProducts = productRepository.searchProducts(title, description, category);
        if (foundProducts.isEmpty()) {
            throw new ProductSearchNotFoundException(title, description, category);
        }
        logger.info("Returned products size: {}", foundProducts.size());
        return foundProducts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    // Helper method to convert Product entity to ProductDto.
    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        ProductRatingDto productRatingDto = ratingRepository.findAverageRatingByProductId(product.getId());

        logger.info("Setting product dto fields|Id: {}", product.getId());
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImage());
        productDto.setRating(productRatingDto);
        logger.info("Set and Return product dto|Id: {}", productDto.getId());
        return productDto;

    }

}
