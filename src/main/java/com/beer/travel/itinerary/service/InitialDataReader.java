package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.Beer;
import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.reader.CSVReader;
import com.beer.travel.itinerary.reader.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
public class InitialDataReader {

    @Autowired
    private BeerFactoryService beerFactoryService;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        CSVReader csvReader = new CSVReader();

        Map<Integer, Point2D.Double> coordinates = csvReader.read("geocodes.csv", Parser.parseCoordinates());
        Map<Integer, Beer> beerNames = csvReader.read("beers.csv", Parser.parseBeerNames());
        Map<Integer, BeerFactory> factories = csvReader.read(
                "breweries.csv",
                Parser.parseBeerFactory(
                        coordinates,
                        beerNames.values().stream().collect(groupingBy(Beer::getBeerFactoryId, Collectors.mapping(Beer::getName, Collectors.toList())))));

        beerFactoryService.saveAll(factories.values());
    }
}
