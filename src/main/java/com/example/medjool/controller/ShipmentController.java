package com.example.medjool.controller;

import com.example.medjool.dto.ShipmentDetailsDto;
import com.example.medjool.services.implementation.ShipmentServiceImpl;
import lombok.Getter;
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


    @PutMapping("/update/tracking/{trackingNumber}")
    public void updateTrackingNumber(@PathVariable String trackingNumber, @RequestBody ShipmentDetailsDto shipmentDetailsDto) throws Exception {
        shipmentService.updateShipmentTracker(trackingNumber, shipmentDetailsDto);
    }

}
