package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.BeerFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.geom.Point2D;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationServiceImplTest {

    private static final int DEFAULT_RANGE = 2000;
    private final Point2D.Double initialCoordinates = new Point2D.Double(0, 0);

    private final BeerFactory beerFactoryInRange = BeerFactory.builder().id(1).coordinates(new Point2D.Double(1, 1)).beerNames(List.of()).build();

    @Mock
    private BeerFactoryService beerFactoryService;

    @InjectMocks
    private LocationServiceImpl locationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(beerFactoryService.findAll()).thenReturn(List.of(beerFactoryInRange, BeerFactory.builder().id(2).coordinates(new Point2D.Double(90,180)).build()));
    }

    @Test
    public void findRoute_shouldFetchAllBeerFactories() {
        var lol = locationService.findRoute(initialCoordinates, 2000);

        verify(beerFactoryService).findAll();
    }

    @Test
    public void findRoute_shouldFindOneBeerFactory_ifOnlyOneInRange() {
        var lol = locationService.findRoute(initialCoordinates, DEFAULT_RANGE);

        Assertions.assertThat(lol).hasSize(1).element(0).isEqualTo(beerFactoryInRange);
    }
}
