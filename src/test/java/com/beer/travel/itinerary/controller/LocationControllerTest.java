package com.beer.travel.itinerary.controller;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.beans.TravelInputForm;
import com.beer.travel.itinerary.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationControllerTest {

    private static final double LATITUDE = 51.355468;
    private static final double LONGITUDE = 11.100790;
    private static final double RANGE_WITH_FUEL = 2000;

    @Mock
    private LocationService service;

    @Mock
    private TravelInputForm travelInputForm;

    @Mock
    private Model model;

    @InjectMocks
    private LocationController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findRoute_shouldCallService_withStartingPointFromInput() {
        when(travelInputForm.getLatitude()).thenReturn(LATITUDE);
        when(travelInputForm.getLongitude()).thenReturn(LONGITUDE);

        controller.findRoute(travelInputForm, model);

        ArgumentCaptor<Point2D.Double> startingPoint = ArgumentCaptor.forClass(Point2D.Double.class);
        verify(service).findRoute(startingPoint.capture(), anyDouble());

        assertThat(startingPoint.getValue())
                .returns(LATITUDE, Point2D.Double::getX)
                .returns(LONGITUDE, Point2D.Double::getY);
    }

    @Test
    public void findRoute_shouldCallService_withRangeWithFuelFromInput() {
        ArgumentCaptor<Double> rangeWithFuel = ArgumentCaptor.forClass(Double.class);
        when(travelInputForm.getRangeWithFuel()).thenReturn(RANGE_WITH_FUEL);

        controller.findRoute(travelInputForm, model);

        verify(service).findRoute(any(), rangeWithFuel.capture());

        assertThat(rangeWithFuel.getValue()).isEqualTo(RANGE_WITH_FUEL);
    }

    @Test
    public void findRoute_shouldAddToModel_travelledDistance() {
        controller.findRoute(travelInputForm, model);

        verify(model).addAttribute(eq(LocationController.DISTANCE_TRAVELLED), anyDouble());
    }

    @Test
    public void findRoute_shouldAddToModel_visitedFactories() {
        when(service.findRoute(any(), anyDouble())).thenReturn(List.of());

        controller.findRoute(travelInputForm, model);

        verify(model).addAttribute(LocationController.VISITED_FACTORIES, List.of());
    }

    @Test
    public void findRoute_shouldAddToModel_collectedBeers() {
        var beers = List.of("green", "pipe", "pi", "go", "extra");
        when(service.findRoute(any(), anyDouble())).thenReturn(createBeerFactories(List.of(beers.subList(0, 2), beers.subList(2, beers.size()))));

        controller.findRoute(travelInputForm, model);

        ArgumentCaptor<List<String>> collectedBeers = ArgumentCaptor.forClass(List.class);
        verify(model).addAttribute(eq(LocationController.COLLECTED_BEERS), collectedBeers.capture());

        assertThat(collectedBeers.getValue()).containsExactlyInAnyOrderElementsOf(beers);
    }

    @Test
    public void findRoute_shouldAddToModel_elapsedTime() {
        when(service.findRoute(any(), anyDouble())).thenReturn(List.of());

        controller.findRoute(travelInputForm, model);

        verify(model).addAttribute(eq(LocationController.TIME_ELAPSED), any());
    }

    private List<BeerFactory> createBeerFactories(List<List<String>> beersPerFactory) {
        return beersPerFactory.stream().map(beers -> BeerFactory.builder().beerNames(beers).coordinates(new Point2D.Double()).build()).collect(Collectors.toList());
    }
}
