package com.ryanair.flights.api.impl;

import com.ryanair.flights.config.ApplicationConfiguration;
import com.ryanair.flights.config.Constants;
import com.ryanair.flights.model.provider.Flight;
import com.ryanair.flights.model.provider.Route;
import com.ryanair.flights.model.provider.Schedule;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiServiceImpl implements ApiService {

    private final Logger LOGGER = LoggerFactory.getLogger(ApiServiceImpl.class);
    private final ApplicationConfiguration config;
    private final RestTemplate restTemplate;
    private static final String OPERATOR = "RYANAIR";

    @Autowired
    public ApiServiceImpl(ApplicationConfiguration config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Route> getRoutes() {
        try {
            final String url = this.config.getRoutesApiUrl();
            ResponseEntity<List<Route>> result = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Route>>() {
            });
            if (!result.getStatusCode().equals(HttpStatus.OK)) {
                return new ArrayList<>();
            }
            return result.getBody().stream().filter(e -> e.getOperator().equals(OPERATOR) && e.getConnectingAirport() == null).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Could not retrieve the ROUTES from the API" + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Cacheable(Constants.SCHEDULE_CACHE)
    @Override
    public Schedule getSchedules(String departure, String arrival, int year, int month) {
        try {
            String url = this.config.getSchedulesApiUrl();
            Map<String, String> pathVariables = new HashMap<>();
            pathVariables.put("departure", departure);
            pathVariables.put("arrival", arrival);
            pathVariables.put("year", String.valueOf(year));
            pathVariables.put("month", String.valueOf(month));

            ResponseEntity<Schedule> result = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Schedule>() {
            }, pathVariables);
            return result.getStatusCode().equals(HttpStatus.OK) ? result.getBody() : null;

        } catch (Exception e) {
            LOGGER.error("Could not retrieve the SCHEDULES for the departure:{} and arrival, for the year and date {}/{}. Error:{} ", departure, arrival, year, month, e.getMessage());
            return null;
        }
    }

    @Override
    public List<List<Route>> getRoutes(String departure, String arrival, int stops) {

        List<Route> routes = this.getRoutes();
        LOGGER.debug("We have found {} routes", routes.size());
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        routes.stream().filter(e -> e.getAirportFrom().equalsIgnoreCase(departure) || e.getAirportTo().equalsIgnoreCase(arrival)).forEach(e -> {
            graph.addVertex(e.getAirportFrom());
            graph.addVertex(e.getAirportTo());
            graph.addEdge(e.getAirportFrom(), e.getAirportTo());
        });
        int maxPathLength = stops + 1;
        AllDirectedPaths adp = new AllDirectedPaths(graph);
        List<GraphPath<String, DefaultEdge>> graphPathList = adp.getAllPaths(departure, arrival, true, maxPathLength);
        List<List<Route>> resultList = new ArrayList<>();
        graphPathList.stream().forEach(e-> {
            List<String> verticesList = e.getVertexList();
            List<Route> legList = new LinkedList<>();
            for (int i = 0; i < verticesList.size() - 1; i++) {
                legList.add(new Route(verticesList.get(i), verticesList.get(i + 1)));
            }
            resultList.add(legList);
        });
        return resultList;
    }

//    @Cacheable(Constants.ROUTES_CACHE)
//    @Override
//    public List<GraphPath<String, DefaultEdge>> getAllPaths(String departure, String arrival, int stops) {
//        List<Route> routes = this.getRoutes();
//        LOGGER.debug("We have found {} routes", routes.size());
//        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
//        routes.stream().filter(e -> e.getAirportFrom().equalsIgnoreCase(departure) || e.getAirportTo().equalsIgnoreCase(arrival)).forEach(e -> {
//            graph.addVertex(e.getAirportFrom());
//            graph.addVertex(e.getAirportTo());
//            graph.addEdge(e.getAirportFrom(), e.getAirportTo());
//        });
//        int maxPathLength = stops + 1;
//        AllDirectedPaths adp = new AllDirectedPaths(graph);
//        return adp.getAllPaths(departure, arrival, true, maxPathLength);
//    }

}
