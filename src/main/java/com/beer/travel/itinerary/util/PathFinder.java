package com.beer.travel.itinerary.util;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PathFinder {

    private final Stack<Integer> visitedPoints = new Stack<>();
    private final Point2D startingPoint;
    private final double rangeForFuel;
    private final Map<Integer, Point2D> coordinates;

    private List<Integer> notVisitedPoints;

    public PathFinder(Point2D startingPoint, double rangeForFuel, Map<Integer, Point2D> coordinates) {
        this.startingPoint = startingPoint;
        this.rangeForFuel = rangeForFuel;
        this.coordinates = coordinates;
    }

    public List<Integer> find() {
        notVisitedPoints = findReachablePoints();

        return findNext(rangeForFuel, () -> startingPoint);
    }

    private List<Integer> findReachablePoints() {
        return coordinates.entrySet()
                .stream()
                .filter(coordinateEntry -> TravelHelper.findDistance(startingPoint, coordinateEntry.getValue()) < rangeForFuel / 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Integer> findNext(double rangeLeft, Supplier<Point2D> previousPoint) {
        Map<Integer, Double> distancesFromPreviousPoint = notVisitedPoints.stream().collect(Collectors.toMap(Function.identity(), id -> TravelHelper.findDistance(previousPoint.get(), coordinates.get(id))));
        Map.Entry<Integer, Double> nextPoint = distancesFromPreviousPoint.entrySet().stream().min(Map.Entry.comparingByValue()).orElseThrow(RuntimeException::new);
        notVisitedPoints.remove(nextPoint.getKey());

        if (rangeLeft > TravelHelper.findDistance(startingPoint, coordinates.get(nextPoint.getKey()))) {
            visitedPoints.push(nextPoint.getKey());
            return findNext(rangeLeft - nextPoint.getValue(), () -> coordinates.get(nextPoint.getKey()));
        } else {
            visitedPoints.pop();
            return visitedPoints;
        }

    }

}
