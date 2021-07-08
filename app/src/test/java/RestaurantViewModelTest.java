import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantsViewModel;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RestaurantViewModelTest{

@Rule
public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
private RestaurantsViewModel restaurantsViewModel;

@Mock
public RestaurantRepository restaurantRepository;
@Mock
public CoworkerRepository coworkerRepository;

private MutableLiveData<List<Restaurant>> restaurantListMutableLiveData = new MutableLiveData<>();

private final List<Restaurant> restaurantList = new ArrayList<>();
private LatLng location;

@Before
public void setup() {
    initMocks(this);
    restaurantsViewModel = new RestaurantsViewModel(restaurantRepository, coworkerRepository);
    location = new LatLng(10.00, 5.00);
    restaurantList.add(new Restaurant("12345", "name", 10.00, 5.00, "address",true, 300, "www.urlimage.com", 3, "0666666666", "www.website"));
    restaurantList.add(new Restaurant("54321", "name1", 11.00, 6.00, "address1",false, 300, "www.urlimage.com", 3, "0666666665", "www.website1"));
        }

@Test
public void getRestaurantListWithSuccess() {

        restaurantListMutableLiveData.setValue(restaurantList);

        when(restaurantRepository.getGoogleRestaurantList(location.latitude, location.longitude)).thenReturn(restaurantListMutableLiveData);
        assertNotNull(restaurantsViewModel.getRestaurantList(location.latitude, location.longitude));
        Mockito.verify(restaurantRepository).getGoogleRestaurantList(location.latitude, location.longitude);

        restaurantRepository.getGoogleRestaurantList(location.latitude, location.longitude)
        .observeForever(pRestaurantList -> assertEquals(pRestaurantList,restaurantList));
        }
}