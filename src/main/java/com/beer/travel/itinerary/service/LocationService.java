package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.BeerFactory;

import java.awt.geom.Point2D;
import java.util.List;

public interface LocationService {

    List<BeerFactory> findRoute(Point2D.Double startingPoint, double rangeWithFuel);
}
