package com.example.medjool.services.implementation;


import com.example.medjool.dto.OverviewDto;
import com.example.medjool.model.Order;
import com.example.medjool.model.OrderStatus;
import com.example.medjool.model.Product;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ProductRepository;
import com.example.medjool.repository.SystemSettingRepository;
import com.example.medjool.services.OverviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<OverviewDto> getOverview() {
        OverviewDto overviewDto = new OverviewDto();
        Double totalStockWeight = 0d;


        double MINIMUM_STOCK = systemSettingRepository.findByKey("min_stock_level").getValue();


        float total_revenue = 0f;
        for (Product product : productRepository.findAll()) {
            totalStockWeight += product.getTotalWeight();
            if (product.getTotalWeight() <= MINIMUM_STOCK) {
                String content = "The product id: " + product.getProductId() + "is low stock";
                if(!alertService.isExists(content)){
                    alertService.newAlert(content);
                }
            }

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
