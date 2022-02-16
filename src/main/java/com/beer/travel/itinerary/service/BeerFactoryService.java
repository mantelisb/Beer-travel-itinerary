package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.BeerFactory;

import java.util.Collection;

public interface BeerFactoryService {

    void saveAll(Collection<BeerFactory> factories);

    Collection<BeerFactory> findAll();
}
