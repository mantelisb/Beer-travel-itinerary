package com.beer.travel.itinerary.service;

import com.beer.travel.itinerary.beans.BeerFactory;
import com.beer.travel.itinerary.repository.BeerFactoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BeerFactoryServiceImplTest {

    @Mock
    private BeerFactoryRepository beerFactoryRepository;

    @InjectMocks
    private BeerFactoryServiceImpl beerFactoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveAll_shouldCallRepositorySavelAll() {
        Collection<BeerFactory> factories = new LinkedList<>();
        beerFactoryService.saveAll(factories);

        verify(beerFactoryRepository).saveAll(factories);
    }

    @Test
    public void findAll_shouldReturnAllFromRepository() {
        List<BeerFactory> factories = new LinkedList<>();
        when(beerFactoryRepository.findAll()).thenReturn(factories);

        Collection<BeerFactory> result = beerFactoryService.findAll();

        assertThat(result).isEqualTo(factories);
    }
}
