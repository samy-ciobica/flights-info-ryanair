package com.ryanair.flights.controller;


import com.ryanair.flights.enums.ErrorEnum;
import com.ryanair.flights.exception.IntegrationException;
import com.ryanair.flights.model.provider.FlightResult;
import com.ryanair.flights.model.provider.Schedule;
import com.ryanair.flights.service.FlightsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class FlightsInfoController {

    @Autowired
    private FlightsService flightsService;


    @GetMapping(value = "/interconnections",
            produces = {"application/json"})
    public ResponseEntity<List<FlightResult>> getInterconnections(
            @Valid @RequestParam(value = "departure") String departure,
            @Valid @RequestParam(value = "arrival") String arrival,
            @Valid @RequestParam(value = "departureDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime,
            @Valid @RequestParam(value = "arrivalDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDateTime
    ) {
        this.validateInterconnectionData(departure, arrival, departureDateTime, arrivalDateTime);
        List<FlightResult> result = this.flightsService.getFlights(departure, arrival, departureDateTime, arrivalDateTime, 1);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    private void validateInterconnectionData(String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        if (StringUtils.isEmpty(departure) || departure.length() < 3 || departure.length() > 3) {
            throw new IntegrationException(HttpStatus.BAD_REQUEST, ErrorEnum.ERR_400);
        }
        if (StringUtils.isEmpty(arrival) || arrival.length() < 3 || arrival.length() > 3) {
            throw new IntegrationException(HttpStatus.BAD_REQUEST, ErrorEnum.ERR_400);
        }
        if (departureDateTime == null || arrivalDateTime == null || departureDateTime.isAfter(arrivalDateTime)) {
            throw new IntegrationException(HttpStatus.BAD_REQUEST, ErrorEnum.ERR_400);
        }
    }

}
