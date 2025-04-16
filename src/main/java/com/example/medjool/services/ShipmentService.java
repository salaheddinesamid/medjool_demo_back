package com.example.medjool.services;

import com.example.medjool.dto.ShipmentDetailsDto;
import com.example.medjool.model.Order;

import java.util.List;
import java.util.Optional;

public interface ShipmentService {

    void createShipment(Optional<Order> order) throws Exception;

    void cancelShipment(long shipmentId) throws Exception;

    void updateShipmentTracker(long shipmentId, String trackingNumber) throws Exception;


    void trackShipment(String shipmentId) throws Exception;

    List<ShipmentDetailsDto> getAllShipments() throws Exception;
}
