package com.example.medjool.services.implementation;

import com.example.medjool.dto.ShipmentDetailsDto;
import com.example.medjool.model.Order;
import com.example.medjool.model.Shipment;
import com.example.medjool.repository.OrderRepository;
import com.example.medjool.repository.ShipmentRepository;
import com.example.medjool.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;
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
    public void cancelShipment(long shipmentId) throws Exception {

    }


    @Override
    public void updateShipmentTracker(long shipmentId, String trackingNumber) throws Exception {

        Shipment shipment = shipmentRepository.findById(shipmentId).orElseThrow(() -> new Exception("Shipment not found"));
        shipment.setTrackingNumber(trackingNumber);
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
            shipmentDetailsDto.setShipmentId(shipment.getShipmentId());
            shipmentDetailsDto.setTrackingNumber(shipment.getTrackingNumber());
            shipmentDetailsDto.setOrderId(shipment.getOrder().getId());
            return shipmentDetailsDto;
        }).toList();
    }
}
