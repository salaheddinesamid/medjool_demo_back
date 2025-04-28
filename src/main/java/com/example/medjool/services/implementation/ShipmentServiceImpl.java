package com.example.medjool.services.implementation;

import com.example.medjool.dto.OrderResponseDto;
import com.example.medjool.dto.ShipmentDetailsDto;
import com.example.medjool.model.Order;
import com.example.medjool.model.Shipment;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ShipmentRepository;
import com.example.medjool.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;

    private static final String SHIPMENT_URL = "https://www.tracking.com/tracking/";
    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, ShipmentRepository shipmentRepository1, OrderRepository orderRepository) {

        this.shipmentRepository = shipmentRepository1;
        this.orderRepository = orderRepository;
    }
    @Override
    public void createShipment(Optional<Order> order) throws Exception {

        order.ifPresent(o->{
            try{
                Shipment shipment = new Shipment();
                shipment.setOrder(order.get());
                shipmentRepository.save(shipment);
            }
            catch (Exception e){
                throw new RuntimeException("Error creating shipment: " + e.getMessage());
            }
        });
    }

    @Override
    public ResponseEntity<String> cancelShipment(long shipmentId) throws Exception {
        Shipment shipment = shipmentRepository.findById(shipmentId).orElseThrow(() -> new Exception("Shipment not found"));
        shipmentRepository.delete(shipment);
        return new ResponseEntity<>("The shipment has been canceled...", HttpStatus.OK);
    }


    @Override
    public void updateShipmentTracker(long shipmentId, String trackingNumber) throws Exception {

        Shipment shipment = shipmentRepository.findById(shipmentId).orElseThrow(() -> new Exception("Shipment not found"));
        String trackingUrl = SHIPMENT_URL + trackingNumber;
        shipment.setTrackingNumber(trackingNumber);
        shipment.setTrackingUrl(trackingUrl);
        shipmentRepository.save(shipment);
    }

    @Override
    public void trackShipment(String shipmentId) throws Exception {

    }

    @Override
    public List<ShipmentDetailsDto> getAllShipments() throws Exception {

        List<Shipment> shipments = shipmentRepository.findAll();
        return shipments.stream().map(shipment -> {
            ShipmentDetailsDto shipmentDetailsDto = new ShipmentDetailsDto();
            Order order = shipment.getOrder();
            OrderResponseDto orderResponseDto = new OrderResponseDto(order);
            shipmentDetailsDto.setShipmentId(shipment.getShipmentId());
            shipmentDetailsDto.setTrackingNumber(shipment.getTrackingNumber());
            shipmentDetailsDto.setTrackingUrl(shipment.getTrackingUrl());
            shipmentDetailsDto.setOrderDetails(orderResponseDto);
            return shipmentDetailsDto;
        }).toList();
    }
}
