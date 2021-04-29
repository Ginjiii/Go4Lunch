package model;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CoworkerTest {

    private String emailTest = "email@test.fr";
    private String nameTest = "Name Test";
    private String illustrationTest = "Illustration Test";
    private Restaurant restaurantForTest = new Restaurant();

    @Test
    public void setRestaurantChoose_Success() {
        // GIVEN : Create a new User
        Coworker userForTest = new Coworker(emailTest, nameTest, illustrationTest);
        // Test this User's boolean is false
        Assert.assertFalse(userForTest.isRestaurantChoice());
        // Test User's restaurantChoose is null
        assertNull(userForTest.getCoworkerRestaurantChosen());

        // WHEN : Add a restaurant with the method setCoworkerRestaurantChosen()
        userForTest.setCoworkerRestaurantChosen(restaurantForTest);

        // THEN : Verify boolean and restaurantChoice changed
        Assert.assertTrue(userForTest.isRestaurantChoice());
        assertNotNull(userForTest.getCoworkerRestaurantChosen());
    }

    @Test
    public void UserEquals_False() {
        // GIVEN : Create 2 Users without the same email
        Coworker userForTest = new Coworker(emailTest, nameTest, illustrationTest);
        Coworker userToCompare = new Coworker("email2@test.fr", nameTest, illustrationTest);

        // THEN : The Users are not equals
        assertNotEquals(userForTest, userToCompare);
    }

    @Test
    public void UserEquals_True() {
        // GIVEN : Create 2 Users with the same email
        Coworker userForTest = new Coworker(emailTest, nameTest, illustrationTest);
        Coworker userToCompare = new Coworker(emailTest, "Name2", "Illustration2");

        // THEN : The Users are equals
        assertEquals(userForTest, userToCompare);
    }
}