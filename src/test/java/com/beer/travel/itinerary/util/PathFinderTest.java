package com.beer.travel.itinerary.util;

import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PathFinderTest {

    public static final Optional<BiFunction<Integer, Double, Double>> EFFICIENCY_FUNCTION = Optional.of((id, distance) -> id * distance);
    private final Point2D startingPoint = new Point2D.Double(1, 1);
    private final double rangeForFuel = 2000;
    private final Map<Integer, Point2D> coordinates = Map.of(
            1, new Point2D.Double(2, 2),
            2, new Point2D.Double(-10, -10),
            4, new Point2D.Double(90, -45),
            5, new Point2D.Double(-45, 90),
            6, new Point2D.Double(75, 180),
            7, new Point2D.Double(25, 56),
            15, new Point2D.Double(-2.1, 1.1111111),
            16, new Point2D.Double(2.2, -2.2222),
            17, new Point2D.Double(-1.1111, -2.222),
            18, new Point2D.Double(1.1, 2.2));

    private List<Integer> notVisitedPoints;

    @Test
    public void shouldReturnEmptyPath_ifCoordinatesNotProvided_withoutEfficiency() {
        assertThat(new PathFinder(startingPoint, rangeForFuel, Map.of(), Optional.empty()).find()).isEmpty();
    }

    @Test
    public void pathShouldHaveUniquePoints_withoutEfficiency() {
        List<Integer> path = new PathFinder(startingPoint, rangeForFuel, coordinates, Optional.empty()).find();
        assertThat(path).hasSize((int) path.stream().distinct().count());
    }

    @Test
    public void totalTravelDistance_shouldNotExceedRangeForFuel_withoutEfficiency() {
        List<Integer> path = new PathFinder(startingPoint, rangeForFuel, coordinates, Optional.empty()).find();
        assertThat(TravelHelper.findTraveledDistance(startingPoint, path.stream().map(coordinates::get).collect(Collectors.toList()))).isLessThanOrEqualTo(rangeForFuel);
    }

    @Test
    public void shouldReturnEmptyPath_ifCoordinatesNotProvided_withEfficiency() {
        assertThat(new PathFinder(startingPoint, rangeForFuel, Map.of(), EFFICIENCY_FUNCTION).find()).isEmpty();
    }

    @Test
    public void pathShouldHaveUniquePoints_withEfficiency() {
        List<Integer> path = new PathFinder(startingPoint, rangeForFuel, coordinates, EFFICIENCY_FUNCTION).find();
        assertThat(path).hasSize((int) path.stream().distinct().count());
    }

    @Test
    public void totalTravelDistance_shouldNotExceedRangeForFuel_withEfficiency() {
        List<Integer> path = new PathFinder(startingPoint, rangeForFuel, coordinates, EFFICIENCY_FUNCTION).find();
        assertThat(TravelHelper.findTraveledDistance(startingPoint, path.stream().map(coordinates::get).collect(Collectors.toList()))).isLessThanOrEqualTo(rangeForFuel);
    }
}
