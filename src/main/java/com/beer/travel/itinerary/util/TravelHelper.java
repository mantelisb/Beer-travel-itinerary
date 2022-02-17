package com.beer.travel.itinerary.util;

import org.apache.lucene.util.SloppyMath;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TravelHelper {

    public static double findDistance(Point2D source, Point2D target) {
        return SloppyMath.haversin(source.getX(), source.getY(), target.getX(), target.getY());
    }

    public static double findTraveledDistance(Point2D startingPoint, List<Point2D> travelPath) {
        if (travelPath.isEmpty()) {
            return 0;
        }

        double traveled = 0;
        traveled += findDistance(startingPoint, travelPath.get(0));

        for (int i = 1; i < travelPath.size(); i++) {
            traveled += findDistance(travelPath.get(i - 1), travelPath.get(i));
        }

        traveled += findDistance(startingPoint, travelPath.get(travelPath.size() - 1));
        return traveled;
    }


}
