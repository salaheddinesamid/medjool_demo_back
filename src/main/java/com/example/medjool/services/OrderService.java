package com.example.medjool.services;

import com.example.medjool.dto.CommandeDto;
import com.example.medjool.dto.InvoiceDto;
import com.example.medjool.dto.OrderDto;
import com.example.medjool.dto.ProductDto;
import com.example.medjool.model.Commande;
import com.example.medjool.model.CommandeProduct;
import com.example.medjool.model.Customer;
import com.example.medjool.model.Product;
import com.example.medjool.repository.CommandeProductRepository;
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
import java.util.stream.Collectors;

@Service
public class OrderService {


    private final ProductRepository productRepository;
    private final InvoiceService invoiceService;
    private final CustomerRepository customerRepository;
    private final CommandeProductRepository commandeProductRepository;
    private final OrderRepository orderRepository;
    private final PdfGenerator pdfGenerator;
    //private final PackageRepository packageRepository;

    @Autowired
    public OrderService(ProductRepository productRepository, InvoiceService invoiceService, CustomerRepository customerRepository, CommandeProductRepository commandeProductRepository, OrderRepository orderRepository, PdfGenerator pdfGenerator) {
        this.productRepository = productRepository;
        //this.packageRepository = packageRepository;
        this.invoiceService = invoiceService;
        this.customerRepository = customerRepository;
        this.commandeProductRepository = commandeProductRepository;
        this.orderRepository = orderRepository;
        this.pdfGenerator = pdfGenerator;
    }

    @CachePut(value = "order_process")
    @Transactional
    public ResponseEntity<?> processOrder(OrderDto orderDto) {
        List<ProductDto> orders = orderDto.getProductsDto();
        List<CommandeProduct> orderItems = new ArrayList<>();

        Customer customer = customerRepository.findByName(orderDto.getClient());
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        // Create Order
        Commande order = new Commande();
        order.setCustomer(customer);
        order.setStatus("IN PROCESSING");

        order = orderRepository.save(order);  // Save to get orderId

        // Process each product in the order
        for (ProductDto orderDtoProduct : orders) {
            Optional<Product> optionalProduct = productRepository.findById(orderDtoProduct.getProductId());

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                if (product.getQuantity() >= orderDtoProduct.getQuantity()) {
                    product.setQuantity(product.getQuantity() - orderDtoProduct.getQuantity());
                    productRepository.save(product);

                    // Create Order-Product Mapping
                    CommandeProduct orderProduct = new CommandeProduct();
                    orderProduct.setCommande(order);
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(orderDtoProduct.getQuantity());

                    orderItems.add(orderProduct);
                } else {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough stock for Product ID " + orderDtoProduct.getProductId());
                }
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product ID " + orderDtoProduct.getProductId() + " not found");
            }
        }

        // Save Order Items
        commandeProductRepository.saveAll(orderItems);

        // Generate PDF
        pdfGenerator.generateOrderPdf(order);

        // Generate Invoice
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setClient(customer.getName());
        invoiceDto.setTotalAmount(orderDto.getTotal());
        invoiceDto.setStatus("NOT PAID");
        invoiceService.newInvoice(invoiceDto);

        return new ResponseEntity<>("Order has been completed successfully", HttpStatus.OK);
    }


    @Cacheable(value = "all_orders")
    public ResponseEntity<List<CommandeDto>> getAllCommandes() {
        List<Commande> commandes = orderRepository.findAll();

        // Convert Commande to CommandeDto
        List<CommandeDto> response = commandes.stream().map(order -> {
            CommandeDto dto = new CommandeDto();
            dto.setOrderId(order.getOrderId());
            dto.setCustomerName(order.getCustomer().getName());
            dto.setStatus(order.getStatus());

            // Convert CommandeProduct to ProductDto
            List<ProductDto> productDtos = order.getCommandeProducts().stream().map(orderProduct -> {
                ProductDto productDto = new ProductDto();
                productDto.setProductId(orderProduct.getProduct().getProductId());
                productDto.setType(orderProduct.getProduct().getType());
                productDto.setColor(orderProduct.getProduct().getColor());
                productDto.setPrice(orderProduct.getProduct().getPrice());
                productDto.setQuantity(orderProduct.getQuantity());
                return productDto;
            }).collect(Collectors.toList());

            dto.setProducts(productDtos);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
