package com.example.medjool.dto;


import lombok.Data;

import java.util.Date;

@Data
public class InvoiceDto {

    String client;
    Float totalAmount;
    Date date;
    String status;

}
