# Beer-travel-itinerary

run with `docker-compose -f src/main/docker/docker-compose.yml up`

Build app can be found on `http://localhost:8887/location`

In the travel input form should be set desired starting location, and range which you can travel. 
After pressing submit, it will calculate which beer factories you can visit, and came back to starting location.

There is 2 simple algorithms implemented:
---
* Find all reachable points from starting point, keeping in mind that it should be enough fuel to come back
* Find next point
    * Calculate distance change to starting point if traveling from current to all not visited points
    * Find minimum distance change, for points from where it would be still possible to get back
---
* Find all reachable points from starting point, keeping in mind that it should be enough fuel to come back
* Find next point
    * Calculate distance change to starting point if traveling from current to all not visited points
    * Find which distance change, will give you the most beer types per traveled unit, for points from where it would be still possible to get back
    
After program tries to find travel path with both above algorithms, it checks which will result in more collected beer types, and displays travel information