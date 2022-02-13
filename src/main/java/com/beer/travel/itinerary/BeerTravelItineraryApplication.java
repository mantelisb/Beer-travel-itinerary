package com.beer.travel.itinerary;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.reader.CSVReader;
import com.beer.travel.itinerary.reader.Parser;
import com.beer.travel.itinerary.util.TravelHelper;
import org.apache.lucene.util.SloppyMath;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeerTravelItineraryApplication {

	private static final Point2D STARTING_POINT = new Point2D.Double(51.355468, 11.100790);

	public static void main(String[] args) {
		CSVReader csvReader = new CSVReader();
		Map<Integer, Point2D> coordinates = csvReader.read("geocodes.csv", Parser.parseCoordinates());
		Map<Integer, BeerFactory> factories = csvReader.read("breweries.csv", Parser.parseBeerFactory(coordinates));


		List<BeerFactory> closest = factories.values().stream().filter(beerFactory -> TravelHelper.findDistance(STARTING_POINT, beerFactory.getCoordinates()) < 1000).collect(Collectors.toList());
		closest.forEach(System.out::println);
		System.out.println(closest.size());

		Map<Integer, Map<Integer, Double>> travelMatrix = TravelHelper.findTravelMatrix(closest);
		travelMatrix.values().forEach(System.out::println);


	}

}
