package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.util.PathFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LocationServiceImpl implements LocationService {

    @Autowired
    private BeerFactoryService beerFactoryService;

    public List<BeerFactory> findRoute(Point2D.Double startingPoint, double rangeWithFuel) {
        Map<Integer, BeerFactory> beerFactories = beerFactoryService.findAll().stream().collect(Collectors.toMap(BeerFactory::getId, Function.identity()));

        List<BeerFactory> efficiencyVisitedPoints = findFactoriesEfficiently(startingPoint, rangeWithFuel, beerFactories);
        List<BeerFactory> withoutEfficiencyVisitedPoints = findFactoriesWithoutEfficiency(startingPoint, rangeWithFuel, beerFactories);

        return calculateBeerTypes(efficiencyVisitedPoints) > calculateBeerTypes(withoutEfficiencyVisitedPoints) ? efficiencyVisitedPoints : withoutEfficiencyVisitedPoints;
    }

    private int calculateBeerTypes(List<BeerFactory> efficiencyVisitedPoints) {
        return efficiencyVisitedPoints.stream().map(BeerFactory::getBeerNames).mapToInt(List::size).sum();
    }

    private List<BeerFactory> findFactoriesWithoutEfficiency(Point2D.Double startingPoint, double rangeWithFuel, Map<Integer, BeerFactory> beerFactories) {
        return findFactories(startingPoint, rangeWithFuel, beerFactories, Optional.empty());
    }

    private List<BeerFactory> findFactoriesEfficiently(Point2D.Double startingPoint, double rangeWithFuel, Map<Integer, BeerFactory> beerFactories) {
        return findFactories(startingPoint, rangeWithFuel, beerFactories, Optional.of((id, distance) -> beerFactories.get(id).getBeerNames().size() / distance));
    }

    private List<BeerFactory> findFactories(Point2D.Double startingPoint, double rangeWithFuel, Map<Integer, BeerFactory> beerFactories, Optional<BiFunction<Integer, Double, Double>> efficiencyFunction) {
        return new PathFinder(
                startingPoint,
                rangeWithFuel,
                beerFactories.values().stream().collect(Collectors.toMap(BeerFactory::getId, BeerFactory::getCoordinates)),
                efficiencyFunction)
                .find().stream().map(beerFactories::get).collect(Collectors.toList());
    }
}
