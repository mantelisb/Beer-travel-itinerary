package com.beer.travel.itinerary.controller;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.beans.TravelInputForm;
import com.beer.travel.itinerary.service.LocationService;
import com.beer.travel.itinerary.util.TravelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/location")
public class LocationController {

    private static final double DEFAULT_LATITUDE = 51.355468;
    private static final double DEFAULT_LONGITUDE = 11.100790;
    private static final int DEFAULT_RANGE_WITH_FUEL = 2000;
    public static final String DISTANCE_TRAVELLED = "distanceTravelled";
    public static final String VISITED_FACTORIES = "visitedFactories";
    public static final String COLLECTED_BEERS = "collectedBeers";
    public static final String TIME_ELAPSED = "timeElapsed";

    @Autowired
    private LocationService service;

    @GetMapping
    public String getInitialData(Model model) {
        model.addAttribute("travelInputForm", TravelInputForm.builder()
                .latitude(DEFAULT_LATITUDE)
                .longitude(DEFAULT_LONGITUDE)
                .rangeWithFuel(DEFAULT_RANGE_WITH_FUEL)
                .build());
        return "location";
    }

    @PostMapping
    public String findRoute(@ModelAttribute TravelInputForm travelInputForm, Model model) {
        long start = System.currentTimeMillis();

        Point2D.Double startingPoint = new Point2D.Double(travelInputForm.getLatitude(), travelInputForm.getLongitude());
        List<BeerFactory> visitedFactories = service.findRoute(startingPoint, travelInputForm.getRangeWithFuel());
        double traveledDistance = TravelHelper.findTraveledDistance(startingPoint, visitedFactories.stream().map(BeerFactory::getCoordinates).collect(Collectors.toList()));

        model.addAttribute(DISTANCE_TRAVELLED, traveledDistance);
        model.addAttribute(VISITED_FACTORIES, visitedFactories);
        model.addAttribute(COLLECTED_BEERS, visitedFactories.stream().map(BeerFactory::getBeerNames).flatMap(List::stream).collect(Collectors.toList()));

        long finish = System.currentTimeMillis();
        model.addAttribute(TIME_ELAPSED, finish - start);

        return "location";
    }
}
