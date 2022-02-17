package com.beer.travel.itinerary.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Parser<T> {

    public Map<Integer, T> parse(BufferedReader reader) {
            Map<Integer, T> parsedValues = new HashMap<>();
            try {
                String line = reader.readLine();
                while ((line = reader.readLine()) != null) {
                    String[] splitLine = line.split(",");
                    Integer id;
                    try {
                        id = Integer.valueOf(splitLine[getIdIndex()]);

                        T value = parseLine(splitLine);
                        if (isValid(value)) {
                            parsedValues.put(id, value);
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return parsedValues;
    }

    int getIdIndex() {
        return 0;
    }

    boolean isValid(T value) {
        return true;
    };

    abstract T parseLine(String[] splitLine);
}
