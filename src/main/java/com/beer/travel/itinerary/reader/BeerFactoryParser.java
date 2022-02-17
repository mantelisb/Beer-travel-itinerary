package com.beer.travel.itinerary.reader;

import com.beer.travel.itinerary.beans.Beer;
import com.beer.travel.itinerary.beans.BeerFactory;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class BeerFactoryParser extends Parser<BeerFactory> {

    private Map<Integer, Point2D.Double> coordinates;
    private Map<Integer, List<String>> beersByFactory;

    public BeerFactoryParser(Map<Integer, Point2D.Double> coordinates, Map<Integer, Beer> beers) {
        this.coordinates = coordinates;
        this.beersByFactory = beers.values().stream().collect(groupingBy(Beer::getBeerFactoryId, Collectors.mapping(Beer::getName, Collectors.toList())));
    }

    @Override
    BeerFactory parseLine(String[] splitLine) {
        Integer id = Integer.valueOf(splitLine[0]);
        return BeerFactory.builder().id(id).name(splitLine[1]).coordinates(coordinates.get(id)).beerNames(beersByFactory.get(id)).build();
    }

    @Override
    boolean isValid(BeerFactory beerFactory) {
        return beerFactory.getCoordinates() != null;
    };

}
