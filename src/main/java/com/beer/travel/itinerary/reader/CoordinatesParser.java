package com.beer.travel.itinerary.reader;

import java.awt.geom.Point2D;

public class CoordinatesParser extends Parser<Point2D.Double> {

    @Override
    Point2D.Double parseLine(String[] splitLine) {
        return new Point2D.Double(Double.parseDouble(splitLine[2]), Double.parseDouble(splitLine[3]));
    }

    @Override
    int getIdIndex() {
        return 1;
    }
}
