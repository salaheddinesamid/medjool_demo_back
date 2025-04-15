package com.example.medjool.controller;

import com.example.medjool.dto.ShipmentDetailsDto;
import com.example.medjool.services.implementation.ShipmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipment")

public class ShipmentController {

    private final ShipmentServiceImpl shipmentService;

    @Autowired
    public ShipmentController(ShipmentServiceImpl shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/get_all")
    public List<ShipmentDetailsDto> getAllShipments() throws Exception {
        return shipmentService.getAllShipments();
    }


    @PutMapping("/update/tracking/{shipmentId}")
    public void updateTrackingNumber(@PathVariable long shipmentId, @RequestParam String trackingNumber) throws Exception {
        shipmentService.updateShipmentTracker(shipmentId, trackingNumber);
    }

}
