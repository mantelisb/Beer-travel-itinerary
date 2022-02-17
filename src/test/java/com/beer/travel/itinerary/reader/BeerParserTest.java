package com.beer.travel.itinerary.reader;

import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class BeerParserTest {

    public static final Integer BEER_FACTORY_ID = 2;
    public static final String BEER_NAME = "EXTRA";
    public static final String[] SPLIT_LINE = {"1", BEER_FACTORY_ID.toString(), BEER_NAME};
    private final BeerParser beerParser = new BeerParser();

    @Test
    public void shouldCorrectlyParseIdFromLine() {
        assertThat(beerParser.parseLine(SPLIT_LINE).getBeerFactoryId()).isEqualTo(BEER_FACTORY_ID);
    }

    @Test
    public void shouldCorrectlyParseBeerNameFromLine() {
        assertThat(beerParser.parseLine(SPLIT_LINE).getName()).isEqualTo(BEER_NAME);
    }

    @Test
    public void idIndex_shouldReturn0() {
        assertThat(beerParser.getIdIndex()).isEqualTo(0);
    }
}
