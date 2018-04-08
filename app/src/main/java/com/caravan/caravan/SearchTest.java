package com.caravan.caravan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rmvanwyk on 4/2/18.
 */

public class SearchTest {
    public int size;
    public List<Location> locations = new ArrayList<>();

    public SearchTest() {
        this.size = 6;
        this.locations.add(new Location("Nashville", "Tennessee", "Yazoo", "Brewery"));
        this.locations.add(new Location("Nashville", "Tennessee", "Bearded Iris", "Brewery"));
        this.locations.add(new Location("Nashville", "Tennessee", "Grimey's", "Retail Store"));
        this.locations.add(new Location("Asheville", "North Carolina", "Wicked Weed", "Brewery"));
        this.locations.add(new Location("Chicago", "Illinois", "Revolution", "Brewery"));
        this.locations.add(new Location("Los Angeles", "California", "Umami Burger", "Resturant"));
    }

    public static class Location {
        public String state;
        public String city;
        public String name;
        public String type;

        public Location(String city, String state, String name, String type) {
            this.city = city;
            this.state = state;
            this.name = name;
            this.type = type;
        }
    }
}
