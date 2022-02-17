package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.reader.CSVReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InitialDataReaderTest {

    @Mock
    private BeerFactoryService beerFactoryService;

    @Mock
    private CSVReader csvReader;

    @Mock
    private Map<Integer, BeerFactory> beerfactories;

    @InjectMocks
    private InitialDataReader initialDataReader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReadCoordinates() {
        initialDataReader.runAfterStartup();

        verify(csvReader).read(eq("geocodes.csv"), any());
    }

    @Test
    public void shouldReadBeers() {
        initialDataReader.runAfterStartup();

        verify(csvReader).read(eq("beers.csv"), any());
    }

    @Test
    public void shouldReadBeerFactories() {
        initialDataReader.runAfterStartup();

        verify(csvReader).read(eq("breweries.csv"), any());
    }

    @Test
    public void shouldSaveBeerFactories() {
        var factories = Mockito.mock(Map.class);
        var factoriesValues = Mockito.mock(List.class);

        when(factories.values()).thenReturn(factoriesValues);
        when(csvReader.read(eq("breweries.csv"), any())).thenReturn(factories);

        initialDataReader.runAfterStartup();

        verify(beerFactoryService).saveAll(factoriesValues);
    }

}
