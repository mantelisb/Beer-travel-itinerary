package com.beer.travel.itinerary;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.reader.CSVReader;
import com.beer.travel.itinerary.reader.Parser;

import java.awt.geom.Point2D;
import java.util.Map;

public class BeerTravelItineraryApplication {

	public static void main(String[] args) {
		CSVReader csvReader = new CSVReader();
		Map<Integer, Point2D> coordinates = csvReader.read("geocodes.csv", Parser.parseCoordinates());
		Map<Integer, BeerFactory> factories = csvReader.read("breweries.csv", Parser.parseBeerFactory(coordinates));
		factories.values().forEach(System.out::println);
	}

}
