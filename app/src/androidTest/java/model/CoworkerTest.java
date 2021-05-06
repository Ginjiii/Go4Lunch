package model;

import com.example.go4lunch.goforlunch.models.Coworker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)
public class CoworkerTest {

    private Coworker coworker;
    private String name;
    private String uid;
    private String email;
    private String photoUrl;
    private String restaurantUid;
    private String restaurantName;
    private String restaurantAddress;

    private String likedRestaurant1;
    private String likedRestaurant2;
    private String likedRestaurant3;

    private List<String> likedRestaurants;

    @Before
    public void setup(){
        name = "Jean Michel";
        uid = "12345";
        photoUrl = "http://photo";
        email = "name@email.com";
        coworker = new Coworker(uid, name, email, photoUrl);
        restaurantUid = "54321";
        restaurantName = "restaurantName";
        restaurantAddress = "123 rue blablabla";
        likedRestaurant1 = "uid1";
        likedRestaurant2 = "uid2";
        likedRestaurant3 = "uid3";
        likedRestaurants = new ArrayList<>();
        likedRestaurants.add(likedRestaurant1);
        likedRestaurants.add(likedRestaurant2);
        likedRestaurants.add(likedRestaurant3);
    }

    @Test
    public void getCorrectInfoFromUser() throws Exception{
        assertEquals(uid, coworker.getUid());
        assertEquals(name, coworker.getCoworkerName());
        assertEquals(email, coworker.getCoworkerEmail());
        assertEquals(photoUrl, coworker.getCoworkerPhotoUrl());
    }

    @Test
    public void changeInfoUser_getCorrectInfo() throws Exception{
        String newName = "Michel Jean";
        String newUid = "4321";
        String newPhotoUrl = "http://newphoto";
        String newEmail = "new@email.com";
        coworker.setCoworkerName(newName);
        coworker.setUid(newUid);
        coworker.setCoworkerPhotoUrl(newPhotoUrl);
        coworker.setCoworkerEmail(newEmail);
        coworker.setRestaurantUid(restaurantUid);
        coworker.setRestaurantName(restaurantName);
        coworker.setRestaurantAddress(restaurantAddress);
        coworker.addLikedRestaurant(likedRestaurant1);
        coworker.addLikedRestaurant(likedRestaurant2);
        coworker.addLikedRestaurant(likedRestaurant3);


        assertEquals(newUid, coworker.getUid());
        assertEquals(newName, coworker.getCoworkerName());
        assertEquals(newEmail, coworker.getCoworkerEmail());
        assertEquals(newPhotoUrl, coworker.getCoworkerPhotoUrl());
        assertEquals(restaurantUid, coworker.getRestaurantUid());
        assertEquals(restaurantName, coworker.getRestaurantName());
        assertEquals(restaurantAddress, coworker.getRestaurantAddress());
        assertEquals(likedRestaurants, coworker.getLikedRestaurants());
    }
}