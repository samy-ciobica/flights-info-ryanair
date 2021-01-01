package com.ryanair.flights.service.impl;

import com.ryanair.flights.api.impl.ApiService;
import com.ryanair.flights.model.provider.*;
import com.ryanair.flights.service.FlightsService;
import com.ryanair.flights.utils.FlightAux;
import com.ryanair.flights.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightsServiceImpl implements FlightsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightsServiceImpl.class);
    private final ApiService apiService;

    @Autowired
    public FlightsServiceImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getFlightInfo() {
        this.apiService.getRoutes();
    }

    @Override
    public List<FlightResult> getFlights(String departure, String arrival, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, int stops) {

        List<FlightResult> flightsList = new ArrayList<>();

        List<List<Route>> validRoutes = this.apiService.getRoutes(departure, arrival, stops);

        List<YearMonth> datesBetween = Utils.getDatesBetween(departureDateTime.toLocalDate(), arrivalDateTime.toLocalDate());
        List<List<FlightAux>> flightAuxList = new ArrayList<>();
        validRoutes.stream().forEach(routeList -> {
            List<FlightAux> tempList = new ArrayList<>();
            routeList.stream().forEach(route -> {

                List<Schedule> scheduleList = new ArrayList<>();
                datesBetween.stream().forEach(e -> {
                    scheduleList.add(this.apiService.getSchedules(route.getAirportFrom(), route.getAirportTo(), e.getYear(), e.getMonthValue()));
                });
                tempList.add(new FlightAux(route, scheduleList));
            });
            flightAuxList.add(tempList);
        });

        return this.toValidFlights(flightAuxList, departureDateTime, arrivalDateTime);
    }

    private List<FlightResult> toValidFlights(List<List<FlightAux>> flightAuxList, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<FlightResult> results = new ArrayList<>();
        for (LocalDateTime i = startDateTime; i.isBefore(endDateTime); i = i.plusDays(1)) {

            LocalDateTime j = startDateTime.getDayOfMonth() == endDateTime.getDayOfMonth() && startDateTime.getMonthValue() == endDateTime.getMonthValue() ?
                    LocalDateTime.of(startDateTime.toLocalDate(), endDateTime.toLocalTime()) : LocalDateTime.of(startDateTime.toLocalDate(), LocalTime.MAX);
            i = i.getDayOfMonth() == startDateTime.getDayOfMonth() ? startDateTime : i.withHour(0).withMinute(0).withSecond(0);

            LocalDateTime finalI = i;
            LocalDateTime finalJ = j;

            results.addAll(flightAuxList.stream().map(e -> {
                return toFlightResultDto(e, finalI, finalJ);
            }).filter(Objects::nonNull).collect(Collectors.toList()));
        }
        return results;
    }


    private FlightResult toFlightResultDto(List<FlightAux> flightsList, final LocalDateTime flightDateTime, LocalDateTime arrivalDateTime) {
        FlightResult flightResult = new FlightResult();
        flightResult.setStops(flightsList.size() > 1 ? 1 : 0);
        flightResult.setLegs(new ArrayList<>());

        if (flightsList.size() == 1) {
            flightsList.get(0).getScheduleList().forEach(a -> {
                a.getDays().forEach(b -> {
                    if (b.getDay() == flightDateTime.getDayOfMonth()) {
                        b.getFlights().stream().filter(f -> flightDateTime.toLocalTime().isBefore(f.getDepartureTime()) && arrivalDateTime.toLocalTime().isAfter(f.getArrivalTime()))
                                .forEach(c -> {
                                    flightResult.getLegs().add(new Leg(flightsList.get(0).getRoute().getAirportFrom(), flightsList.get(0).getRoute().getAirportTo(),
                                            LocalDateTime.of(flightDateTime.toLocalDate(), c.getDepartureTime()), LocalDateTime.of(flightDateTime.toLocalDate(), c.getArrivalTime())));
                                });
                    }
                });
            });
        } else {
            for (int j = 0; j < flightsList.size(); j++) {
                int finalJ = j;
                flightsList.get(j).getScheduleList().stream().forEach(x -> {
                    Optional<Day> opt = x.getDays().stream().filter(z -> flightDateTime.getMonthValue() == x.getMonth() && z.getDay() == flightDateTime.getDayOfMonth()).findFirst();
                    if (opt.isPresent()) {
                        Day day = opt.get();

                        day.getFlights().stream().filter(f -> flightDateTime.toLocalTime().isBefore(f.getDepartureTime()) && arrivalDateTime.toLocalTime().isAfter(f.getArrivalTime()))
                                .forEach(e -> {
                                    if (flightResult.getLegs().size() > 0 && e.getDepartureTime().plusHours(2).isAfter(flightResult.getLegs().get(flightResult.getLegs().size() - 1).getArrivalDateTime().toLocalTime())
                                            && flightResult.getLegs().get(flightResult.getLegs().size() - 1).getArrivalAirport().equals(flightsList.get(finalJ).getRoute().getAirportFrom())) {
                                        flightResult.getLegs().add(new Leg(flightsList.get(finalJ).getRoute().getAirportFrom(), flightsList.get(finalJ).getRoute().getAirportTo(),
                                                LocalDateTime.of(flightDateTime.toLocalDate(), e.getDepartureTime()), LocalDateTime.of(flightDateTime.toLocalDate(), e.getArrivalTime())));
                                    } else if (flightResult.getLegs().size() == 0) {
                                        flightResult.getLegs().add(new Leg(flightsList.get(finalJ).getRoute().getAirportFrom(), flightsList.get(finalJ).getRoute().getAirportTo(),
                                                LocalDateTime.of(flightDateTime.toLocalDate(), e.getDepartureTime()), LocalDateTime.of(flightDateTime.toLocalDate(), e.getArrivalTime())));
                                    }
                                });

                    }
                });
            }
        }
        if (flightResult.getLegs().isEmpty() || flightResult.getStops() + 1 != flightResult.getLegs().size()) {
            return null;
        }
        return flightResult;
    }


}
