package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.Beer;
import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.reader.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.awt.geom.Point2D;
import java.util.Map;

@Component
public class InitialDataReader {

    @Autowired
    private BeerFactoryService beerFactoryService;

    @Autowired
    private CSVReader csvReader;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        Map<Integer, Point2D.Double> coordinates = csvReader.read("geocodes.csv", new CoordinatesParser());
        Map<Integer, Beer> beerNames = csvReader.read("beers.csv", new BeerParser());
        Map<Integer, BeerFactory> factories = csvReader.read(
                "breweries.csv",
                new BeerFactoryParser(
                        coordinates,
                        beerNames));

        beerFactoryService.saveAll(factories.values());
    }
}
