package com.example.medjool.component;

import com.example.medjool.model.*;
import com.example.medjool.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Runs every half a day
    public void scheduleProductionOrders() {
        List<ProductionOrder> productionOrderInProgress = productionOrderRepository.findAllByProductionStatus(ProductionStatus.IN_PROGRESS);
        List<ProductionOrder> productionOrderNotStarted = productionOrderRepository.findAllByProductionStatus(ProductionStatus.NOT_STARTED);
        double factoryWorkingHours = systemSettingRepository.findByKey("factory_working_hours").get().getValue();

        FactorySchedule factorySchedule = factoryScheduleRepository.findByDate(LocalDate.now());
        if (factorySchedule == null) {
            factorySchedule = new FactorySchedule();
            factorySchedule.setDate(LocalDate.now());
            factorySchedule.setWorkingHours(factoryWorkingHours);
            factorySchedule.setRemainingHours(factoryWorkingHours);
            factorySchedule.setIsAvailable(true);
            factoryScheduleRepository.save(factorySchedule);
        }

        scheduleInProgressOrders(factorySchedule, productionOrderInProgress);

        if (factorySchedule.getRemainingHours() > 0) {
            scheduleNotStartedOrders(factorySchedule, productionOrderNotStarted);
        }

    }
    @Transactional
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Runs every half a day
    public void checkCompletedOrders(){

    }

    private void scheduleInProgressOrders(FactorySchedule schedule, List<ProductionOrder> orders){

        List<Order> scheduledOrders = new ArrayList<>(); // Initialize the list of orders to be scheduled...
        double factoryWorkingHours = systemSettingRepository.findByKey("factory_working_hours").get().getValue();
        // Scan the orders in progress first:
        if(!orders.isEmpty()) {
            double workingHoursRate = factoryWorkingHours / orders.size();
            for(ProductionOrder productionOrder : orders) {
                Order order = productionOrder.getOrder();
                productionOrder.setRemainingHours(Math.max(0,productionOrder.getRemainingHours() - workingHoursRate));
                productionOrderRepository.save(productionOrder);
                schedule.setRemainingHours(Math.max(0,productionOrder.getRemainingHours() - workingHoursRate));
                scheduledOrders.add(order);

            }
            if(schedule.getRemainingHours() <=0){
                schedule.setRemainingHours(0);
                schedule.setIsAvailable(false);
            }
        }

        schedule.setOrders(scheduledOrders);
        factoryScheduleRepository.save(schedule);
    }

    private void scheduleNotStartedOrders(FactorySchedule schedule, List<ProductionOrder> orders){

        List<Order> scheduledOrders = new ArrayList<>();
        if(!orders.isEmpty()) {
            double workingHoursRate = schedule.getWorkingHours() / orders.size();
            for(ProductionOrder productionOrder : orders) {
                Order order = productionOrder.getOrder();
                productionOrder.setProductionStatus(ProductionStatus.IN_PROGRESS);
                if(productionOrder.getRemainingHours() > 0) {
                    if(productionOrder.getRemainingHours() > workingHoursRate) {
                        productionOrder.setRemainingHours(Math.min(productionOrder.getRemainingHours(), workingHoursRate));
                    }
                    else if(productionOrder.getRemainingHours() == workingHoursRate) {
                        productionOrder.setRemainingHours(workingHoursRate);
                    }
                    else{
                        productionOrder.setRemainingHours(productionOrder.getWorkingHours() - workingHoursRate);
                    }
                }
                else{
                    productionOrder.setRemainingHours(0);
                    productionOrder.setProductionStatus(ProductionStatus.COMPLETED);
                }
                schedule.setRemainingHours(schedule.getRemainingHours() - workingHoursRate);
                scheduledOrders.add(order);
                if(schedule.getRemainingHours() <=0){
                    schedule.setRemainingHours(0);
                    schedule.setIsAvailable(false);
                    break;
                }
            }
        }
        schedule.setOrders(scheduledOrders);
        factoryScheduleRepository.save(schedule);
    }
}