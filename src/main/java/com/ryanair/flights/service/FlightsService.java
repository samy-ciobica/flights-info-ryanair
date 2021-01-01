package com.ryanair.flights.service;

import com.ryanair.flights.model.provider.FlightResult;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightsService {

    List<FlightResult> getFlights(String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, int i);

}
