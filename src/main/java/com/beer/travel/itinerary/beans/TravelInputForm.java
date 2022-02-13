package com.beer.travel.itinerary.beans;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelInputForm {

    private double latitude;
    private double longitude;
    private double rangeWithFuel;

}
