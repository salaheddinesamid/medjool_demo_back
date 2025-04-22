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
import org.springframework.scheduling.annotation.Async;
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
        Optional<SystemSetting> settingOpt = Optional.ofNullable(systemSettingRepository.findByKey(STOCK_KEY));
        if (settingOpt.isEmpty()) {
            return new ResponseEntity<>("System setting 'min_stock_level' not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        double minimumStock = settingOpt.get().getValue();
        double totalStockWeight = 0;
        float totalRevenue = 0;

        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            totalStockWeight += product.getTotalWeight();

            if (product.getTotalWeight() <= minimumStock) {
                String alertContent = "The product id: " + product.getProductId() + " is low stock";
                if (!alertService.isExists(alertContent)) {
                    alertService.newAlert(alertContent);
                }
            }
        }

        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            totalRevenue += (float) order.getTotalPrice(); // assuming getTotalPrice() is non-null
        }

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
