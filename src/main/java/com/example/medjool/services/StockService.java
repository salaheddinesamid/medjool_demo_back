package com.example.medjool.services;

import com.example.medjool.dto.StockAvailabilityDto;
import com.example.medjool.model.Product;
import com.example.medjool.repository.ProductRepository;
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

@Service
public class StockService {

    private final ProductRepository productRepository;

    @Autowired
    public StockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Integer productId){
        return productRepository.findById(productId).get();
    }


    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }


    // Upload a CSV file to update the stock
    public ResponseEntity<Object> updateStock(MultipartFile file) throws IOException {

        // Create a buffered reader:
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        // Parse the csv:
        CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT);

        for (CSVRecord record : csvParser) {
            Product product = productRepository.findById(Integer.valueOf(record.get("product_id"))).get();
            product.setTotalWeight(Float.valueOf(record.get("New weight")));
            productRepository.save(product);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<Object> stockAvailability(StockAvailabilityDto stockAvailabilityDto) {

        Product product = productRepository.findByCallibreAndQuality(
                stockAvailabilityDto.getCallibre(), stockAvailabilityDto.getQuality()
        );

        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (product.getTotalWeight() < stockAvailabilityDto.getTotalQuantity()) {
            return new ResponseEntity<>(product.getTotalWeight(), HttpStatus.BAD_REQUEST);
        } else if (product.getTotalWeight() > stockAvailabilityDto.getTotalQuantity()) {
            return new ResponseEntity<>("The product is available in the stock", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
