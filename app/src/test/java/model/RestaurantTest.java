package model;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RestaurantTest
{
    private String placeIdTest = "placeIdTest";
    private List<Coworker> listTest = new ArrayList<>();
    private String nameTest = "NameTest";
    private String addressTest = "123 Street";

    private Restaurant restaurantForTest = new Restaurant(placeIdTest, nameTest);

    @Test
    public void RestaurantEquals_False()
    {
        // GIVEN : Create a new restaurant without the same placeId
        Restaurant restaurantToCompare = new Restaurant("placeId2", listTest, nameTest);

        // THEN : The 2 restaurants are not equals
        assertNotEquals(restaurantForTest, restaurantToCompare);
    }

    @Test
    public void RestaurantEquals_True()
    {
        // GIVEN : Create a new restaurant without the same placeId
        Restaurant restaurantToCompare = new Restaurant(placeIdTest, listTest, "Name2", "Address2");

        // THEN : The 2 restaurants are not equals
        assertEquals(restaurantForTest, restaurantToCompare);
    }
}