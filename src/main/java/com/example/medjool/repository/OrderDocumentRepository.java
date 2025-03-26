package com.example.medjool.repository;

import com.example.medjool.documents.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderDocumentRepository extends MongoRepository<Order,String> {
}
