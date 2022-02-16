package com.beer.travel.itinerary.beans;

import lombok.*;

import javax.persistence.*;
import java.awt.geom.Point2D;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
public class BeerFactory {

    @Id
    private Integer id;
    private String name;
    private Point2D.Double coordinates;
    @ElementCollection
    @CollectionTable(name="BeerNames", joinColumns=@JoinColumn(name="beer_factory_id"))
    private List<String> beerNames;

}
