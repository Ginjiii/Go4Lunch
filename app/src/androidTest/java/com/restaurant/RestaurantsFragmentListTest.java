package com.restaurant;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantsViewModel;

import org.junit.Rule;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsFragmentListTest {

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();
    private RestaurantsViewModel restaurantsViewModel;

    @Mock
    public RestaurantRepository restaurantRepository;

    private MutableLiveData<List<Restaurant>> mGetGoogleRestaurantList = new MutableLiveData<>();

    private List<Restaurant> mRestaurantList = new ArrayList<>();

 //  @Before
 //  public void setup() {
 //      initMocks(this);
 //      restaurantsViewModel = new RestaurantsViewModel(restaurantRepository);
 //  }

}