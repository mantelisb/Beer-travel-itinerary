package com.beer.travel.itinerary;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.reader.CSVReader;
import com.beer.travel.itinerary.reader.Parser;
import com.beer.travel.itinerary.util.PathFinder;
import com.beer.travel.itinerary.util.TravelHelper;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BeerTravelItineraryApplication {

    private static final Point2D STARTING_POINT = new Point2D.Double(51.355468, 11.100790);
    private static final double RANGE_FOR_FUEL = 2000;

    private static Map<Integer, Point2D> coordinates;
    private static Map<Integer, BeerFactory> factories;

    public static void main(String[] args) {
        CSVReader csvReader = new CSVReader();
        coordinates = csvReader.read("geocodes.csv", Parser.parseCoordinates());
        factories = csvReader.read("breweries.csv", Parser.parseBeerFactory(coordinates));

        List<Integer> visitedPoints = new PathFinder(STARTING_POINT, RANGE_FOR_FUEL, coordinates).find();

        double traveled = 0;

        System.out.printf("HOME: %s, distance: %s", STARTING_POINT, traveled);
        System.out.println();

        for (int i = 0; i < visitedPoints.size(); i++) {
            BeerFactory factory = factories.get(visitedPoints.get(i));
            if (i == 0 ) {

                traveled += TravelHelper.findDistance(factory.getCoordinates(), STARTING_POINT);
            } else {

                traveled += TravelHelper.findDistance(factory.getCoordinates(), coordinates.get(visitedPoints.get(i-1)));
            }
//            traveled += point.getValue();

            System.out.printf("%s: %s, distance: %s", factory.getName(), factory.getCoordinates(), traveled);
            System.out.println();
        }
//
//        for (Integer point : visitedPoints) {
//            BeerFactory factory = factories.get(point);
//            traveled += TravelHelper.findDistance(coordinates.get(point), coordinates.get(visitedPoints.get(visitedPoints.size() - 1)));
////            traveled += point.getValue();
//
//            System.out.printf("%s: %s, distance: %s", factory.getName(), factory.getCoordinates(), traveled);
//            System.out.println();
//        }

        traveled += TravelHelper.findDistance(STARTING_POINT, coordinates.get(visitedPoints.get(visitedPoints.size() - 1)));
        System.out.printf("HOME: %s, distance: %s", STARTING_POINT, traveled);
        System.out.println();
    }

}
