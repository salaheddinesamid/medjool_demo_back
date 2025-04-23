package com.example.medjool.services.implementation;


import com.example.medjool.dto.OverviewDto;
import com.example.medjool.model.Order;
import com.example.medjool.model.OrderStatus;
import com.example.medjool.model.Product;
import com.example.medjool.model.SystemSetting;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ProductRepository;
import com.example.medjool.repository.SystemSettingRepository;
import com.example.medjool.services.OverviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OverviewServiceImpl implements OverviewService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final SystemSettingRepository systemSettingRepository;
    private final AlertServiceImpl alertService;

    public OverviewServiceImpl(ProductRepository productRepository, OrderRepository orderRepository, SystemSettingRepository systemSettingRepository, AlertServiceImpl alertService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.systemSettingRepository = systemSettingRepository;
        this.alertService = alertService;
    }


    @Override
    public ResponseEntity<?> getOverview() {
        final String STOCK_KEY = "min_stock_level";
        OverviewDto overviewDto = new OverviewDto();

        // Defensive check for system setting
        double minimumStockValue = systemSettingRepository.findByKey(STOCK_KEY)
                .map(SystemSetting::getValue)
                .orElseThrow(() -> new IllegalStateException("System setting 'min_stock_level' not found"));


        List<Product> products = productRepository.findAll();
        double totalStockWeight = products.stream()
                .mapToDouble(Product::getTotalWeight)
                .peek(weight->{
                    if(weight <= minimumStockValue){
                        String alert = "The product id: " + weight + " is low stock";
                        alertService.newAlert(alert);
                    }
                }).sum();




        List<Order> orders = orderRepository.findAll();

        // Calculate total revenue
        float totalRevenue = (float) orders.stream()
                .mapToDouble(Order::getTotalPrice).sum();


        long totalOrders = orders.size();
        long totalReceivedOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.RECEIVED)
                .count();

        overviewDto.setTotalStock(totalStockWeight);
        overviewDto.setTotalOrders(totalOrders);
        overviewDto.setTotalReceivedOrders(totalReceivedOrders);
        overviewDto.setTotalRevenue(totalRevenue);

        return new ResponseEntity<>(overviewDto, HttpStatus.OK);
    }

}
