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

    public static final double DEFAULT_LATITUDE = 51.355468;
    public static final double DEFAULT_LONGITUDE = 11.100790;
    public static final int DEFAULT_RANGE_WITH_FUEL = 2000;

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
        Point2D.Double startingPoint = new Point2D.Double(travelInputForm.getLatitude(), travelInputForm.getLongitude());
        List<BeerFactory> visitedFactories = service.findRoute(startingPoint, travelInputForm.getRangeWithFuel());
        double traveledDistance = TravelHelper.findTraveledDistance(startingPoint, visitedFactories.stream().map(BeerFactory::getCoordinates).collect(Collectors.toList()));

        model.addAttribute("distanceTravelled", traveledDistance);
        model.addAttribute("visitedFactories", visitedFactories);
        model.addAttribute("collectedBeers", visitedFactories.stream().map(BeerFactory::getBeerNames).flatMap(List::stream).collect(Collectors.toList()));

        return "location";
    }
}
