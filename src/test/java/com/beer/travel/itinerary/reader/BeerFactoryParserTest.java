package com.beer.travel.itinerary.reader;

import com.beer.travel.itinerary.beans.Beer;
import com.beer.travel.itinerary.beans.BeerFactory;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BeerFactoryParserTest {

    private static final Integer ID = 1;
    private static final String NAME = "Kalnapilis alaus darykla";
    private static final String[] SPLIT_LINE = {ID.toString(), NAME};
    private static final Point2D.Double COORDINATES = new Point2D.Double();
    private final BeerFactoryParser beerFactoryParser = new BeerFactoryParser(
            Map.of(ID, COORDINATES),
            Map.of(
                    2, Beer.builder().beerFactoryId(ID).build(),
                    3, Beer.builder().beerFactoryId(ID).build(),
                    4, Beer.builder().beerFactoryId(ID + 1).build()));


    @Test
    public void isValid_ifCoordinateExists() {
        assertThat(beerFactoryParser.isValid(BeerFactory.builder().coordinates(new Point2D.Double()).build())).isTrue();
    }

    @Test
    public void notValid_ifCoordinateDoesntExists() {
        assertThat(beerFactoryParser.isValid(BeerFactory.builder().build())).isFalse();
    }

    @Test
    public void idIndex_shouldReturn0() {
        assertThat(beerFactoryParser.getIdIndex()).isEqualTo(0);
    }

    @Test
    public void shouldCorrectlyParseIdFromLine() {
        assertThat(beerFactoryParser.parseLine(SPLIT_LINE).getId()).isEqualTo(ID);
    }

    @Test
    public void shouldCorrectlyParseNameFromLine() {
        assertThat(beerFactoryParser.parseLine(SPLIT_LINE).getName()).isEqualTo(NAME);
    }

    @Test
    public void shouldCorrectlySetBeer() {
        assertThat(beerFactoryParser.parseLine(SPLIT_LINE).getBeerNames()).hasSize(2);
    }

    @Test
    public void shouldCorrectlySetCoordinates() {
        assertThat(beerFactoryParser.parseLine(SPLIT_LINE).getCoordinates()).isEqualTo(COORDINATES);
    }
}
