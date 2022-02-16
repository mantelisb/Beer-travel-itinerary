package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.util.PathFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LocationServiceImpl implements LocationService {

    @Autowired
    private BeerFactoryService beerFactoryService;

    public List<BeerFactory> findRoute(Point2D.Double startingPoint, double rangeWithFuel) {
        Map<Integer, BeerFactory> beerFactories = beerFactoryService.findAll().stream().collect(Collectors.toMap(BeerFactory::getId, Function.identity()));

        List<Integer> visitedPoints = new PathFinder(
                startingPoint,
                rangeWithFuel,
                beerFactories.values().stream().collect(Collectors.toMap(BeerFactory::getId, BeerFactory::getCoordinates))
        ).find();

        return visitedPoints.stream().map(beerFactories::get).collect(Collectors.toList());
    }
}
