package com.example.medjool.services;

import com.example.medjool.dto.OverviewDto;
import com.example.medjool.model.Order;
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

    public OverviewService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }


    public ResponseEntity<OverviewDto> getOverview() {
        OverviewDto overviewDto = new OverviewDto();

        Float totalStockWeight = 0f;

        float total_revenue = 0f;
        for (Product product : productRepository.findAll()) {
            totalStockWeight += product.getTotalWeight();
        }

        for (Order order : orderRepository.findAll()) {
            total_revenue += order.getTotalPrice();
        }
        Long totalOrders = orderRepository.count();
        Long totalReceivedOrders = orderRepository.findAllByStatus("RECEIVED").stream().count();



        overviewDto.setTotalStock(totalStockWeight);
        overviewDto.setTotalOrders(totalOrders);
        overviewDto.setTotalReceivedOrders(totalReceivedOrders);
        overviewDto.setTotalRevenue(total_revenue);

        return new ResponseEntity<>(overviewDto, HttpStatus.OK);
    }
}
