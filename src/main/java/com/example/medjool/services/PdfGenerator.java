package com.example.medjool.services;

/**
import com.example.medjool.dto.OrderResponseDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;


@Service
public class PdfGenerator {


    public void generateOrderPdf(OrderResponseDto order){
        String filePath = "/Users/salaheddine/Desktop/invoice_" + order.getId() + ".pdf";

        try{
            PdfWriter pdfWriter = new PdfWriter();
            PdfDocument pdfDocument = new PdfDocument();
            Document document = new Document(pdfDocument.getPageSize());

            document.add(new Paragraph("Order ID: " + order.getId()));
            document.add(new Paragraph("Customer: " + order.getClientName()));
            //document.add(new Paragraph("Total Amount: " + invoice.getTotal()));
            document.add(new Paragraph("Status: " + order.getStatus()));
            //document.add(new Paragraph("Expiration Date: " + invoice.getDateOfExpiration()));
            document.close();


            // Save the

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

}**/
