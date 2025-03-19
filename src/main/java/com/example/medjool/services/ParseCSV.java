package com.example.medjool.services;

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

/**
@Service
public class ParseCSV {

    private final ProductRepository productRepository;

    @Autowired
    public ParseCSV(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> parseCSV(MultipartFile multipartFile){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

            for (CSVRecord line : csvParser){
                Product product = productRepository.findById(Integer.valueOf(line.get("product_id"))).get();
                product.setQuantity(Integer.valueOf(line.get("quantity")));
                productRepository.save(product);
            }

            return new ResponseEntity<>("The stock has been updated successfully", HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
**/