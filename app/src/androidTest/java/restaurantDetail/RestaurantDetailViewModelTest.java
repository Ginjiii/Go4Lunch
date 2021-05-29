package restaurantDetail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailViewModel;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailViewModelTest {

    private RestaurantDetailViewModel restaurantDetailViewModel;
    private Coworker coworker;
    private Restaurant restaurant;
    private List<Restaurant> restaurants;
    private String restaurantId;
    private RestaurantRepository restaurantRepository;
    private CoworkerRepository coworkerRepository;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        coworker = new Coworker("uid","name","email","photoUrl");
        restaurantId = "restaurantId";
        restaurant = new Restaurant("restaurantID","name",48.633331,2.33333,"address",10,100,"photoReference",5,"phoneNumber","webSite");
        restaurants = new ArrayList<>();
        restaurants.add(restaurant);
        coworkerRepository.updateRestaurantPicked("restaurantId","name","address","coworkerId");
        restaurantDetailViewModel = new RestaurantDetailViewModel(restaurantRepository, coworkerRepository);
        restaurantDetailViewModel.fetchInfoRestaurant(restaurant);
    }

    @Test
    public void testLikedAndPicked() {
        Assert.assertFalse(restaurantDetailViewModel.isRestaurantLiked.getValue());
        Assert.assertTrue(restaurantDetailViewModel.isRestaurantPicked.getValue());
    }
}