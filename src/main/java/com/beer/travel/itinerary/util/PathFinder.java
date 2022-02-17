package com.beer.travel.itinerary.util;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathFinder {

    private final Stack<Integer> visitedPoints = new Stack<>();
    private final Point2D startingPoint;
    private final double rangeForFuel;
    private final Map<Integer, Point2D> coordinates;
    private final Optional<BiFunction<Integer, Double,  Double>> efficiencyFunction;

    private List<Integer> nextPossiblePoints;

    public PathFinder(Point2D startingPoint, double rangeForFuel, Map<Integer, Point2D> coordinates, Optional<BiFunction<Integer, Double, Double>> efficiencyFunction) {
        this.startingPoint = startingPoint;
        this.rangeForFuel = rangeForFuel;
        this.coordinates = coordinates;
        this.efficiencyFunction = efficiencyFunction;
    }

    public List<Integer> find() {
        nextPossiblePoints = findReachablePoints();

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
        if (nextPossiblePoints.isEmpty()) {
            return visitedPoints;
        }

        Integer nextPoint = findNextPoint(previousPoint.get());
        nextPossiblePoints.remove(nextPoint);

        if (rangeLeft > TravelHelper.findDistance(startingPoint, coordinates.get(nextPoint)) + TravelHelper.findDistance(previousPoint.get(), coordinates.get(nextPoint))) {
            visitedPoints.push(nextPoint);
            return findNext(rangeLeft - TravelHelper.findDistance(previousPoint.get(), coordinates.get(nextPoint)), () -> coordinates.get(nextPoint));
        } else {
            return findNext(rangeLeft, previousPoint);
        }

    }

    private Integer findNextPoint(Point2D previousPoint) {
        Stream<Map.Entry<Integer, Double>> distancesCost = nextPossiblePoints.stream().collect(Collectors.toMap(Function.identity(), getDistanceCost(previousPoint))).entrySet().stream();

        if (efficiencyFunction.isPresent()) {
            return distancesCost.max(Comparator.comparingDouble(entry -> Math.abs(efficiencyFunction.get().apply(entry.getKey(), entry.getValue())))).orElseThrow().getKey();
        } else {
            return distancesCost.min(Map.Entry.comparingByValue()).orElseThrow().getKey();
        }
    }

    private Function<Integer, Double> getDistanceCost(Point2D previousPoint) {
        return nextPointId -> TravelHelper.findDistance(previousPoint, coordinates.get(nextPointId)) + getDeltaDistanceFromStartingPoint(previousPoint, coordinates.get(nextPointId));
    }

    private double getDeltaDistanceFromStartingPoint(Point2D previousPoint, Point2D nextPoint) {
        return TravelHelper.findDistance(startingPoint, nextPoint) - TravelHelper.findDistance(startingPoint, previousPoint);
    }

}
