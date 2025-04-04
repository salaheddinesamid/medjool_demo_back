package com.example.medjool.services;

import com.example.medjool.constants.ServerConstants;
import com.example.medjool.dto.OverviewDto;
import com.example.medjool.model.Order;
import com.example.medjool.model.OrderStatus;
import com.example.medjool.model.Product;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OverviewService {

    private final ProductRepository productRepository;;
    private final OrderRepository orderRepository;

    private final AlertService alertService;
    private final ServerConstants serverConstants;

    public OverviewService(ProductRepository productRepository, OrderRepository orderRepository, AlertService alertService, ServerConstants serverConstants) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.alertService = alertService;
        this.serverConstants = serverConstants;
    }


    public ResponseEntity<OverviewDto> getOverview() {
        OverviewDto overviewDto = new OverviewDto();

        Double totalStockWeight = 0d;

        float total_revenue = 0f;
        for (Product product : productRepository.findAll()) {

            if (product.getTotalWeight() <= 200){
                String content = "The product id: " + product.getProductId() + "is low stock";
                alertService.newAlert(content);
            }
            totalStockWeight += product.getTotalWeight();
        }

        for (Order order : orderRepository.findAll()) {
            total_revenue += (float) order.getTotalPrice();
        }
        Long totalOrders = orderRepository.count();
        Long totalReceivedOrders = (long) orderRepository.findAllByStatus(OrderStatus.valueOf("RECEIVED")).size();

        overviewDto.setTotalStock(totalStockWeight);
        overviewDto.setTotalOrders(totalOrders);
        overviewDto.setTotalReceivedOrders(totalReceivedOrders);
        overviewDto.setTotalRevenue(total_revenue);

        return new ResponseEntity<>(overviewDto, HttpStatus.OK);
    }
}
