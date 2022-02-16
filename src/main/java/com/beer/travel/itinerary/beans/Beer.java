package com.beer.travel.itinerary.beans;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
public class Beer {

    @Id
    private Integer beerFactoryId;
    private String name;
}
