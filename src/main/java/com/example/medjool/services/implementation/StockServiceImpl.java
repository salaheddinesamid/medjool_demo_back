package com.example.medjool.services.implementation;

import com.example.medjool.dto.ProductResponseDto;
import com.example.medjool.model.Product;
import com.example.medjool.repository.ProductRepository;
import com.example.medjool.services.StockService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    private final ProductRepository productRepository;

    @Autowired
    public StockServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long productId){
        Optional<Product> product = productRepository.findById(productId);
        return product.orElse(null);
    }


    @Cacheable(value = "products")
    @Override
    public List<ProductResponseDto> getAllProducts(){

        System.out.println("👉 Fetching products from DB...");
        List<Product> products = productRepository.findAll();

        List<ProductResponseDto> productResponseDtos =
                products.stream().map(product -> new ProductResponseDto(product)).collect(Collectors.toList());
        return productResponseDtos;
    }


    // Upload a CSV file to update the stock
    @Override
    public ResponseEntity<Object> updateStock(MultipartFile file) throws IOException {

        // Create a buffered reader:
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        // Parse the csv:
        CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT);

        for (CSVRecord record : csvParser) {
            Product product = productRepository.findById(Long.valueOf(record.get("product_id"))).get();
            product.setTotalWeight(Double.valueOf(record.get("New weight")));
            productRepository.save(product);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
