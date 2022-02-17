package com.beer.travel.itinerary.reader;

import com.beer.travel.itinerary.beans.Beer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ParserTest {

    @Mock
    private Parser<Beer> parser;

    @Mock
    private BufferedReader reader;

    @BeforeEach
    public void setUp() {
        parser = Mockito.mock(Parser.class, Mockito.CALLS_REAL_METHODS);
        reader = Mockito.mock(BufferedReader.class);

        when(parser.parseLine(any())).then(call -> Beer.builder().beerFactoryId(Integer.valueOf(call.getArgument(0, String[].class)[0])).build());
    }

    @Test
    public void getIndex_shouldReturn0() {
        assertThat(parser.getIdIndex()).isEqualTo(0);
    }

    @Test
    public void isValid_shouldReturnTrue() {
        assertThat(parser.isValid(Beer.builder().build())).isTrue();
    }

    @Test
    public void shouldSkipFirstRow() throws IOException {
        when(reader.readLine()).thenReturn("1").thenReturn("2").thenReturn(null);
        Map<Integer, Beer> result = parser.parse(reader);

        assertThatContains(result, 2);
    }

    @Test
    public void shouldSkipValue_ifIdNotParsableToInt() throws IOException {
        when(reader.readLine()).thenReturn("1").thenReturn("2").thenReturn("a").thenReturn(null);
        Map<Integer, Beer> result = parser.parse(reader);

        assertThatContains(result, 2);
    }

    @Test
    public void shouldSkipValue_ifIsValidReturnsFalse() throws IOException {
        when(reader.readLine()).thenReturn("1").thenReturn("2").thenReturn("4").thenReturn(null);
        when(parser.isValid(any())).thenReturn(true).thenReturn(false).thenReturn(true);
        Map<Integer, Beer> result = parser.parse(reader);

        assertThatContains(result, 2);
    }

    @Test
    public void shouldSuccesfullyReturnSeveralValues() throws IOException {
        when(reader.readLine()).thenReturn("1").thenReturn("2").thenReturn("3").thenReturn("4").thenReturn("5").thenReturn(null);
        Map<Integer, Beer> result = parser.parse(reader);

        assertThatContains(result, 2, 3, 4, 5);
    }

    private void assertThatContains(Map<Integer, Beer> result, int... indexes) {
        assertThat(result).hasSize(indexes.length);
        for (Integer index : indexes) {
            assertThat(result).hasEntrySatisfying(index, beer -> assertThat(beer.getBeerFactoryId()).isEqualTo(index));
        }
    }
}
