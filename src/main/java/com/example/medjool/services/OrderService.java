package com.example.medjool.services;

import com.example.medjool.dto.InvoiceDto;
import com.example.medjool.dto.OrderDto;
import com.example.medjool.dto.ProductDto;
import com.example.medjool.model.Commande;
import com.example.medjool.model.Customer;
import com.example.medjool.model.Product;
import com.example.medjool.repository.CustomerRepository;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {


    private final ProductRepository productRepository;
    private final InvoiceService invoiceService;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    //private final PackageRepository packageRepository;

    @Autowired
    public OrderService(ProductRepository productRepository, InvoiceService invoiceService, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        //this.packageRepository = packageRepository;
        this.invoiceService = invoiceService;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @CachePut(value = "order_process")
    @Transactional
    public ResponseEntity<?> processOrder(OrderDto orderDto) {
        List<ProductDto> orders = orderDto.getProductsDto();
        List<Product> products = new ArrayList<>();

        Customer customer = customerRepository.findByName(orderDto.getClient());
        orders.forEach(order -> {
            Optional<Product> optionalProduct = productRepository.findById(order.getProductId());

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setQuantity(product.getQuantity() - order.getQuantity());
                products.add(product);

                productRepository.save(product); // Save the updated product
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product ID " + order.getProductId() + " not found");
            }
        });

        // Create order:
        Commande order = new Commande();
        order.setProducts(products);
        order.setCustomer(customer);
        order.setStatus("IN PROCESSING");
        orderRepository.save(order);

        // Generate invoice:

        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setClient(customer.getName());
        invoiceDto.setTotalAmount(orderDto.getTotal());
        invoiceDto.setStatus("NOT PAID");
        invoiceService.newInvoice(invoiceDto);

        return new ResponseEntity<>("Order has been completed successfully", HttpStatus.OK);
    }

    @Cacheable(value = "orders")
    public ResponseEntity<List<Commande>> getAllOrders(){
        List<Commande> commandes = orderRepository.findAll();
        return new ResponseEntity<>(commandes,HttpStatus.OK);
    }
}
