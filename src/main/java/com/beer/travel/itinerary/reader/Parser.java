package com.beer.travel.itinerary.reader;

import com.beer.travel.itinerary.beans.BeerFactory;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Parser {

    public static Function<BufferedReader, Map<Integer, Point2D>> parseCoordinates() {
        return parser(
                splitLine -> new Point2D.Double(Double.parseDouble(splitLine[2]), Double.parseDouble(splitLine[3])),
                parsedValue -> true,
                1);
    }

    public static Function<BufferedReader, Map<Integer, BeerFactory>> parseBeerFactory(Map<Integer, Point2D> coordinates) {
        return parser(
                splitLine -> beerFactoryParser(splitLine, coordinates),
                parsedValue -> parsedValue.getCoordinates() != null,
                0);
    }

    private static BeerFactory beerFactoryParser(String[] splitLine, Map<Integer, Point2D> coordinates) {
        Integer id = Integer.valueOf(splitLine[0]);
        return BeerFactory.builder().id(id).name(splitLine[1]).coordinates(coordinates.get(id)).build();
    }


    private static <T> Function<BufferedReader, Map<Integer, T>> parser(Function<String[], T> lineParser, Predicate<T> valid, int idIndex) {
        return reader -> {
            Map<Integer, T> parsedValues = new HashMap<>();
            try {
                String line = reader.readLine();
                while ((line = reader.readLine()) != null) {
                    String[] splitLine = line.split(",");
                    Integer id;
                    try {
                        id = Integer.valueOf(splitLine[idIndex]);
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    T value = lineParser.apply(splitLine);
                    if (valid.test(value)) {
                        parsedValues.put(id, value);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return parsedValues;
        };
    }
}
