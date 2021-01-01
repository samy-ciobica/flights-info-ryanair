package com.ryanair.flights.api.impl;

import com.ryanair.flights.model.provider.Route;
import com.ryanair.flights.model.provider.Schedule;

import java.util.List;

public interface ApiService {

    List<List<Route>> getRoutes(String departure, String arrival, int stops);
    List<Route> getRoutes();
    Schedule getSchedules(String departure, String arrival, int year, int month);

}
