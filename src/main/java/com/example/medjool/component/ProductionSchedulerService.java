package com.example.medjool.component;

import com.example.medjool.model.ProductionOrder;
import com.example.medjool.model.ProductionStatus;
import com.example.medjool.model.SystemSetting;
import com.example.medjool.repository.OrderHistoryRepository;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ProductionOrderRepository;
import com.example.medjool.repository.SystemSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductionSchedulerService {


    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderRepository orderRepository;
    private final ProductionOrderRepository productionOrderRepository;
    private final SystemSettingRepository systemSettingRepository;

    @Autowired
    public ProductionSchedulerService(OrderHistoryRepository orderHistoryRepository, OrderRepository orderRepository, ProductionOrderRepository productionOrderRepository, SystemSettingRepository systemSettingRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.orderRepository = orderRepository;
        this.productionOrderRepository = productionOrderRepository;
        this.systemSettingRepository = systemSettingRepository;
    }

    @Transactional
    @Scheduled()
    public void scheduleProductionOrders() {
        List<ProductionOrder> productionOrderInProgress = productionOrderRepository.findAllByProductionStatus(ProductionStatus.IN_PROGRESS);
        List<ProductionOrder> productionOrderNotStarted  = productionOrderRepository.findAllByProductionStatus(ProductionStatus.NOT_STARTED);
        SystemSetting systemSetting = systemSettingRepository.findByKey("factory_working_hours").get();


    }
}
