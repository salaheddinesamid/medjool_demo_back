package com.example.medjool.services.implementation;

import com.example.medjool.dto.NewProductDto;
import com.example.medjool.dto.ProductResponseDto;
import com.example.medjool.model.Product;
import com.example.medjool.repository.ProductRepository;
import com.example.medjool.services.StockService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Product getProduct(String productId){
        Optional<Product> product = productRepository.findById(productId);
        return product.orElse(null);
    }

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
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        ) {
            for (CSVRecord record : csvParser) {
                String productId = record.get("product_id");
                Optional<Product> optionalProduct = productRepository.findById(productId);

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setTotalWeight(Double.valueOf(record.get("quantity")));
                    productRepository.save(product);
                } else {
                    // Optionally log or collect missing product IDs
                    System.out.println("Product not found: " + productId);
                }
            }

            return new ResponseEntity<>("Stock updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update stock: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<Object> createNewProduct(NewProductDto newProductDto) {
        Product product = productRepository.findByCallibreAndColorAndQualityAndFarm(
                newProductDto.getCallibre(),
                newProductDto.getColor(),
                newProductDto.getQuality(),
                newProductDto.getFarm()
        );

        if (product != null) {
            return new ResponseEntity<>("Product already exists", HttpStatus.BAD_REQUEST);
        } else {
            Product newProduct = new Product();
            newProduct.setCallibre(newProductDto.getCallibre());
            newProduct.setColor(newProductDto.getColor());
            newProduct.setQuality(newProductDto.getQuality());
            newProduct.setFarm(newProductDto.getFarm());
            newProduct.setTotalWeight(newProductDto.getTotalWeight());

            // Now generate product ID using the correctly set values
            newProduct.setProductId(
                    newProductDto.getCallibre(),
                    newProductDto.getQuality(),
                    newProductDto.getColor(),
                    newProductDto.getFarm()
            );

            productRepository.save(newProduct);
            return new ResponseEntity<>("New product created successfully", HttpStatus.OK);
        }
    }

}
