package com.example.medjool.documents;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documents")
@Getter
@Setter
public class Order {

    @Id
    private String id;

    private String orderId;

    private byte[] content;
}
