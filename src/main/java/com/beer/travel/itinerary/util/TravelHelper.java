package com.beer.travel.itinerary.util;

import com.beer.travel.itinerary.beans.BeerFactory;
import org.apache.lucene.util.SloppyMath;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TravelHelper {

    public static Map<Integer, Map<Integer, Double>> findTravelMatrix(List<BeerFactory> closest) {
        return closest
                .stream()
                .collect(Collectors.toMap(BeerFactory::getId, sourceBeerFactory -> closest
                        .stream()
                        .filter(targetBeerFactory -> !sourceBeerFactory.getId().equals(targetBeerFactory.getId()))
                        .collect(Collectors.toMap(BeerFactory::getId, targetBeerFactory -> findDistance(sourceBeerFactory.getCoordinates(), targetBeerFactory.getCoordinates())))));
    }

    public static double findDistance(Point2D source, Point2D target) {
        return SloppyMath.haversin(source.getX(), source.getY(), target.getX(), target.getY());
    }


}
