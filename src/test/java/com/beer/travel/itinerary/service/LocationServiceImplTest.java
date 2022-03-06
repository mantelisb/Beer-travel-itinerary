package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.BeerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class LocationServiceImplTest {

    private static final double DEFAULT_RANGE = 2000;
    private final Point2D.Double initialCoordinates = new Point2D.Double(0, 0);

    private final List<BeerFactory> VISITED_BEER_FACTORIES_WITHOUT_EFFICIENCY = createBeerFactories(List.of(List.of("extra", "go"), List.of("utenos"), List.of("kalnapilis")));
    private final List<BeerFactory> VISITED_BEER_FACTORIES_WITH_EFFICIENCY = createBeerFactories(List.of(List.of("extra", "go"), List.of("bravor", "classic", "whiteness")));


    @Mock
    private BeerFactoryService beerFactoryService;

    @Captor
    private ArgumentCaptor<Optional<BiFunction<Integer, Double, Double>>> efficiencyFunctionCaptor;

    @InjectMocks
    @Spy
    private LocationServiceImpl locationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(beerFactoryService.findAll()).thenReturn(Collections.EMPTY_LIST);
        doReturn(VISITED_BEER_FACTORIES_WITH_EFFICIENCY).when(locationService).findFactories(any(), anyDouble(), any(), any());
        doReturn(VISITED_BEER_FACTORIES_WITHOUT_EFFICIENCY).when(locationService).findFactories(any(), anyDouble(), any(), eq(Optional.empty()));
    }

    @Test
    public void findRoute_shouldFetchAllBeerFactories() {
        locationService.findRoute(initialCoordinates, DEFAULT_RANGE);

        verify(beerFactoryService).findAll();
    }

    @Test
    public void findRoute_shouldReturnEfficientlyFoundBeerFactory_ifEfficientlyFoundMoreBeerTypes() {
        var beerFactories = locationService.findRoute(initialCoordinates, DEFAULT_RANGE);

        assertThat(beerFactories).isEqualTo(VISITED_BEER_FACTORIES_WITH_EFFICIENCY);
    }

    @Test
    public void findRoute_shouldReturnWithoutEfficientlyFoundBeerFactory_ifWithoutEfficientlyFoundMoreBeerTypes() {
        var beerFactoriesWithoutEfficiency = createBeerFactories(List.of(List.of("extra", "go", "utenos", "kalnapilis", "green", "blue")));
        doReturn(beerFactoriesWithoutEfficiency).when(locationService).findFactories(any(), anyDouble(), any(), eq(Optional.empty()));

        var beerFactories = locationService.findRoute(initialCoordinates, DEFAULT_RANGE);

        assertThat(beerFactories).isEqualTo(beerFactoriesWithoutEfficiency);
    }

    @Test
    public void findRoute_shouldCallFindFactories_withOutEfficiency() {
        locationService.findRoute(initialCoordinates, DEFAULT_RANGE);

        verify(locationService, times(2)).findFactories(any(), anyDouble(), any(), efficiencyFunctionCaptor.capture());

        assertThat(efficiencyFunctionCaptor.getAllValues()).anyMatch(Optional::isEmpty);
    }

    @Test
    public void findRoute_shouldCallFindFactories_withEfficiency() {
        locationService.findRoute(initialCoordinates, DEFAULT_RANGE);

        verify(locationService, times(2)).findFactories(any(), anyDouble(), any(), efficiencyFunctionCaptor.capture());

        assertThat(efficiencyFunctionCaptor.getAllValues()).anyMatch(Optional::isPresent);
    }

    @Test
    public void efficiencyFunction_shouldDivideBeersByDistance() {
        int beerId = 1;
        List<String> beers = List.of("green", "blue");
        when(beerFactoryService.findAll()).thenReturn(List.of(BeerFactory.builder().id(beerId).beerNames(beers).build()));

        locationService.findRoute(initialCoordinates, DEFAULT_RANGE);

        verify(locationService, times(2)).findFactories(any(), anyDouble(), any(), efficiencyFunctionCaptor.capture());

        var efficiencyFunction = efficiencyFunctionCaptor.getAllValues().stream().filter(Optional::isPresent).findAny().orElseThrow().orElseThrow();

        var distance = 4d;
        assertThat(efficiencyFunction.apply(beerId, distance)).isEqualTo(beers.size() / distance);
    }

    private List<BeerFactory> createBeerFactories(List<List<String>> beersPerFactory) {
        return beersPerFactory.stream().map(beers -> BeerFactory.builder().beerNames(beers).build()).collect(Collectors.toList());
    }
}
