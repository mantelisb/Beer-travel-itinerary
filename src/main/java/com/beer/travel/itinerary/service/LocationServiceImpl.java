package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.reader.CSVReader;
import com.beer.travel.itinerary.reader.Parser;
import com.beer.travel.itinerary.util.PathFinder;
import org.springframework.stereotype.Component;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LocationServiceImpl implements LocationService {

    public List<BeerFactory> findRoute(Point2D.Double startingPoint, double rangeWithFuel) {
        CSVReader csvReader = new CSVReader();
        Map<Integer, Point2D> coordinates = csvReader.read("geocodes.csv", Parser.parseCoordinates());
        Map<Integer, BeerFactory> factories = csvReader.read("breweries.csv", Parser.parseBeerFactory(coordinates));

        List<Integer> visitedPoints = new PathFinder(
                startingPoint,
                rangeWithFuel,
                factories.values().stream().collect(Collectors.toMap(BeerFactory::getId, BeerFactory::getCoordinates))
        ).find();

        return visitedPoints.stream().map(factories::get).collect(Collectors.toList());
    }
}
