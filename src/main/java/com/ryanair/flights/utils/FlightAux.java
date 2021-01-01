package com.ryanair.flights.utils;

import com.ryanair.flights.model.provider.Route;
import com.ryanair.flights.model.provider.Schedule;

import java.util.List;

public class FlightAux {
    private Route route;
    private List<Schedule> scheduleList;

    public FlightAux() {
    }

    public FlightAux(Route route, List<Schedule> scheduleList) {
        this.route = route;
        this.scheduleList = scheduleList;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

}
