package com.example.medjool.controller;
import com.example.medjool.services.FileHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file/")
public class FileHandlingController {
    private final FileHandling fileHandling;

    public FileHandlingController(FileHandling fileHandling) {
        this.fileHandling = fileHandling;

    }


    // Upload order details PDF:
    public ResponseEntity<Object> uploadOrderPDF(MultipartFile file) {
        return new ResponseEntity<>("Uploading file", HttpStatus.OK);
    }

    /*
    @PostMapping(value = "stock/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateStock(@RequestParam("file")MultipartFile multipartFile){

        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a CSV file.");
        }

        return parseCSV.parseCSV(multipartFile);
    }


     */

}