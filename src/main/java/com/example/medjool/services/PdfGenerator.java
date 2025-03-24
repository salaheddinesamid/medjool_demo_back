package com.example.medjool.services;

import org.springframework.stereotype.Service;


@Service
public class PdfGenerator {
    /*

    private void generateInvoicePdf(Invoice invoice){

    }

    public void generateOrderPdf(Commande order){
        String filePath = "/Users/salaheddine/Desktop/invoice_" + order.getOrderId() + ".pdf";

        try{
            PdfWriter pdfWriter = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Invoice ID: " + order.getOrderId()));
            document.add(new Paragraph("Customer: " + order.getCustomer().getName()));
            //document.add(new Paragraph("Total Amount: " + invoice.getTotal()));
            document.add(new Paragraph("Status: " + order.getStatus()));
            //document.add(new Paragraph("Expiration Date: " + invoice.getDateOfExpiration()));
            document.close();


            // Save the

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
     */
}
