package com.beer.travel.itinerary.reader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;

public class CSVReader {

    public <T> Map<Integer, T> read(String fileName, Function<BufferedReader, Map<Integer, T>> parser) {
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream(fileName);

        InputStreamReader streamReader =
                new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        return parser.apply(reader);
    }
}
