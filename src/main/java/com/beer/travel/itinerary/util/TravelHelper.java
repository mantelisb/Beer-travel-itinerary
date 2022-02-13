package com.beer.travel.itinerary.util;

import com.beer.travel.itinerary.beans.BeerFactory;
import org.apache.lucene.util.SloppyMath;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TravelHelper {

    public static Map<Integer, Map<Integer, Double>> findTravelMatrix(List<Map.Entry<Integer, Point2D>> closest) {
        return closest
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, sourcePoint -> closest
                        .stream()
                        .filter(targetSource -> !sourcePoint.getKey().equals(targetSource.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, targetSource -> findDistance(sourcePoint.getValue(), targetSource.getValue())))));
    }

    public static double findDistance(Point2D source, Point2D target) {
        return SloppyMath.haversin(source.getX(), source.getY(), target.getX(), target.getY());
    }


}
