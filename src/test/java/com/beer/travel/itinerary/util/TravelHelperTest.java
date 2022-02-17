package com.beer.travel.itinerary.util;

import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TravelHelperTest {

    @Test
    public void findDistance_shouldFindHaversinDistance() {
        assertThat(TravelHelper.findDistance(new Point2D.Double(1, 1), new Point2D.Double(2, 2))).isEqualTo(157.40120558635948);
    }


    @Test
    public void findTraveledDistance_shouldFindSumHaversinDistanceBetweenPoints() {
        assertThat(TravelHelper.findTraveledDistance(new Point2D.Double(1, 1), List.of(new Point2D.Double(2, 2), new Point2D.Double(3, 3), new Point2D.Double(4, 4), new Point2D.Double(5, 5)))).isEqualTo(1258.433112804962);
    }

}
