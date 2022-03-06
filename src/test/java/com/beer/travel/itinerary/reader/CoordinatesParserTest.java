package com.beer.travel.itinerary.reader;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinatesParserTest {

    private static final Double LATITUDE = 20d;
    private static final Double LONGITUDE = 10d;
    private static final String[] SPLIT_LINE = {"1", "2", LATITUDE.toString(), LONGITUDE.toString()};
    private final CoordinatesParser coordinatesParser = new CoordinatesParser();


    @Test
    public void shouldCorrectlyParseLatitudeFromLine() {
        assertThat(coordinatesParser.parseLine(SPLIT_LINE).getX()).isEqualTo(LATITUDE);
    }

    @Test
    public void shouldCorrectlyParseLongitudeFromLine() {
        assertThat(coordinatesParser.parseLine(SPLIT_LINE).getY()).isEqualTo(LONGITUDE);
    }

    @Test
    public void idIndex_shouldReturn1() {
        assertThat(coordinatesParser.getIdIndex()).isEqualTo(1);
    }
}
