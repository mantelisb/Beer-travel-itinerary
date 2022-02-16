package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.repository.BeerFactoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BeerFactoryServiceImpl implements BeerFactoryService {

    @Autowired
    private BeerFactoryRepository beerFactoryRepository;

    @Override
    public void saveAll(Collection<BeerFactory> factories) {
        beerFactoryRepository.saveAll(factories);
    }

    @Override
    public Collection<BeerFactory> findAll() {
        return beerFactoryRepository.findAll();
    }
}
