package com.example.medjool.services;


import com.example.medjool.dto.InvoiceDto;
import com.example.medjool.model.Customer;
import com.example.medjool.model.Invoice;
import com.example.medjool.repository.CustomerRepository;
import com.example.medjool.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository,
                          CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }

    public void newInvoice(InvoiceDto invoiceDto){
        try{
            Customer customer = customerRepository.findByName(invoiceDto.getClient());
            Invoice invoice = new Invoice();
            invoice.setCustomer(customer);
            LocalDate localDate = LocalDate.now();

            // Expiration (30 days)

            LocalDate expirationDate = localDate.plusDays(30);
            invoice.setDate(localDate);
            invoice.setTotal(invoiceDto.getTotalAmount());
            invoice.setDateOfExpiration(expirationDate);

            invoiceRepository.save(invoice);
        }catch(Exception exception){
            throw exception;
        }
    }

    public ResponseEntity<List<Invoice>> getAllInvoices(){
        return new ResponseEntity<>(invoiceRepository.findAll(),HttpStatus.OK);
    }
}
