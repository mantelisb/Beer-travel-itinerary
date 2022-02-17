package com.beer.travel.itinerary.reader;

import com.beer.travel.itinerary.beans.Beer;

public class BeerParser extends Parser<Beer> {

    @Override
    Beer parseLine(String[] splitLine) {
        return Beer.builder().beerFactoryId(Integer.valueOf(splitLine[1])).name(splitLine[2]).build();
    }
}
