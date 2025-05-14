package com.example.medjool.component;

import com.example.medjool.model.*;
import com.example.medjool.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductionSchedulerService {


    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderRepository orderRepository;
    private final ProductionOrderRepository productionOrderRepository;
    private final SystemSettingRepository systemSettingRepository;
    private final FactoryScheduleRepository factoryScheduleRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductionSchedulerService.class);

    @Autowired
    public ProductionSchedulerService(OrderHistoryRepository orderHistoryRepository, OrderRepository orderRepository, ProductionOrderRepository productionOrderRepository, SystemSettingRepository systemSettingRepository, FactoryScheduleRepository factoryScheduleRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.orderRepository = orderRepository;
        this.productionOrderRepository = productionOrderRepository;
        this.systemSettingRepository = systemSettingRepository;
        this.factoryScheduleRepository = factoryScheduleRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 12 * 60 * 60 * 1000) // Runs every half a day
    public void scheduleProductionOrders() {
        List<ProductionOrder> productionOrderInProgress = productionOrderRepository.findAllByProductionStatus(ProductionStatus.IN_PROGRESS);
        List<ProductionOrder> productionOrderNotStarted  = productionOrderRepository.findAllByProductionStatus(ProductionStatus.NOT_STARTED);
        double factoryWorkingHours = systemSettingRepository.findByKey("factory_working_hours").get().getValue();

        List<Order> scheduledOrders = new ArrayList<>(); // Initialize the list of orders to be scheduled...
        FactorySchedule factorySchedule = new FactorySchedule(); // Create new factory schedule
        factorySchedule.setDateTime(LocalDateTime.now());
        factorySchedule.setWorkingHours(factoryWorkingHours);


        // Scan the orders in progress first:
        if(productionOrderInProgress.isEmpty() && !productionOrderNotStarted.isEmpty()) {
            double workingHoursRate = factoryWorkingHours / productionOrderNotStarted.size();
            for(ProductionOrder productionOrder : productionOrderNotStarted) {
                Order order = productionOrder.getOrder();
                productionOrder.setRemainingHours(productionOrder.getRemainingHours() - workingHoursRate);
                factorySchedule.setRemainingHours(factorySchedule.getRemainingHours() - workingHoursRate);
                scheduledOrders.add(order);

                if(factorySchedule.getRemainingHours() <=0){
                    factorySchedule.setRemainingHours(0);
                    factorySchedule.setIsAvailable(false);
                    break;
                }
            }
        }

        // When there are no orders in progress and there are orders not started yet:
        else if(!productionOrderInProgress.isEmpty() && productionOrderNotStarted.isEmpty()){
            double workingHoursRate = factoryWorkingHours / productionOrderInProgress.size();
            for(ProductionOrder productionOrder : productionOrderInProgress) {
                Order order = productionOrder.getOrder();
                productionOrder.setRemainingHours(productionOrder.getRemainingHours() - workingHoursRate);
                factorySchedule.setRemainingHours(factorySchedule.getRemainingHours() - workingHoursRate);
                scheduledOrders.add(order);
            }
        }
    }
}