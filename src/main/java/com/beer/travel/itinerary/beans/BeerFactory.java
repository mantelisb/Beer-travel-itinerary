package com.beer.travel.itinerary.beans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.awt.geom.Point2D;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class BeerFactory {

    private Integer id;
    private String name;
    private Point2D coordinates;

}
