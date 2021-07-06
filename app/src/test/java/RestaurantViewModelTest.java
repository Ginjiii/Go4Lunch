//import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.go4lunch.goforlunch.models.Coworker;
//import com.example.go4lunch.goforlunch.models.Restaurant;
//import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;
//import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
//import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
//import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantsViewModel;
//import com.go4lunch.R;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.libraries.places.api.model.OpeningHours;
//import com.google.firebase.auth.FirebaseAuth;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class RestaurantViewModelTest {
//
//    @Rule
//    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
//
//    @Mock
//    CoworkerRepository coworkerRepository;
//    @Mock
//    RestaurantRepository restaurantRepository;
//    @Mock
//    FirebaseAuth mAuth;
//
//    private RestaurantsViewModel restaurantsViewModel;
//    private List<Restaurant> restaurantList;
//    private String mCurrentUid = "66";
//    private String mRestoId = "Resto";
//
//    @Before
//    public void setUp() {
//
//        when(restaurantRepository.getLatLngLiveData()).thenReturn(new MutableLiveData<>(new LatLng(12, 12)));
//        when(mAuth.getUid()).thenReturn(mCurrentUid);
//    }
//
//    @Test
//    public void listOfRestaurantsCorrectlyMappedGivenApiResponse() throws InterruptedException {
//        //GIVEN
//        List<NearBySearchResult> nearByRestaurants = new ArrayList<>();
//        NearBySearchResult apiResult1 = new NearBySearchResult(null, "name1", null, "1", 5f, "Rue resto");
//        NearBySearchResult apiResult2 = new NearBySearchResult(null, "name2", null, "2", 3f, "Avenue resto");
//        NearBySearchResult apiResult3 = new NearBySearchResult(null, "name3", null, "3", 1f, "Boulevard resto");
//
//        nearByRestaurants.add(apiResult1);
//        nearByRestaurants.add(apiResult2);
//        nearByRestaurants.add(apiResult3);
//
//        NearBySearchResponse apiResponse = new NearBySearchResponse(nearByRestaurants);
//
//        when(mPlacesRepo.getNearByResponseLiveData()).thenReturn(new MutableLiveData<>(apiResponse));
//        when(mPlacesRepo.getHoursDetailLiveData()).thenReturn(new MutableLiveData<>());
//        when(coworkerRepository.getAllCoworker()).thenReturn(new MutableLiveData<>());
//
//        //WHEN
//        restaurantsViewModel = new RestaurantsViewModel(coworkerRepository, mPlacesRepo, mAuth, restaurantRepository);
//        restaurantsViewModel.init(RestaurantsViewModel.NEARBY_SEARCH);
//
//        restaurantList = LiveDataTestUtil.getOrAwaitValue(restaurantsViewModel.restaurantsLiveData);
//
//        //THEN
//        assertEquals(3, restaurantList.size());
//
//        //RESTO 1
//        assertEquals("name1", restaurantList.get(0).getName());
//        assertEquals("1", restaurantList.get(0).getRestaurantID());
//        assertEquals("Rue resto", restaurantList.get(0).getAddress());
//        assertEquals(R.drawable.ic_baseline_star_rate_24, restaurantList.get(0).getRating());
//
//        //RESTO 2
//        assertEquals("name2", restaurantList.get(1).getName());
//        assertEquals("2", restaurantList.get(1).getRestaurantID());
//        assertEquals("Avenue resto", restaurantList.get(1).getAddress());
//        assertEquals(R.drawable.ic_baseline_star_rate_24, restaurantList.get(1).getRating());
//
//        //RESTO 3
//        assertEquals("name3", restaurantList.get(2).getName());
//        assertEquals("3", restaurantList.get(2).getRestaurantID());
//        assertEquals("Boulevard resto", restaurantList.get(2).getAddress());
//        assertEquals(R.drawable.ic_baseline_star_rate_24, restaurantList.get(2).getRating());
//    }
//
//
//    @Test
//    public void specificRestaurantCorrectlyMappedGivenApiResponse() throws InterruptedException {
//        //GIVEN
//        RestaurantDetailsResult apiResult = new RestaurantDetailsResult(
//                "www.website.com", "name", "1", 5f, "Rue resto", "0606060606");
//        RestaurantDetailsResponse apiResponse = new RestaurantDetailsResponse(apiResult);
//
//        when(mPlacesRepo.getDetailsResponseLiveData()).thenReturn(new MutableLiveData<>(apiResponse));
//
//        //WHEN
//        restaurantsViewModel = new RestaurantsViewModel(coworkerRepository, mPlacesRepo, mAuth, restaurantRepository);
//        restaurantsViewModel.init("1");
//
//        restaurantList = LiveDataTestUtil.getOrAwaitValue(restaurantsViewModel.restaurantsLiveData);
//
//        //THEN
//        assertEquals("name", restaurantList.get(0).getName());
//        assertEquals("Rue resto", restaurantList.get(0).getAddress());
//        assertEquals(R.drawable.ic_baseline_star_rate_24, restaurantList.get(0).getRating());
//        assertEquals("1", restaurantList.get(0).getRestaurantID());
//    }
//
//    @Test
//    public void numberOfWorkmatesUpdatedGivenFireStoreResponse() throws InterruptedException {
//        //GIVEN
//        List<Coworker> coworkers = new ArrayList<>();
//        coworkers.add(new Coworker("workmate", "Anne", "", mRestoId, null, null));
//
//        List<NearBySearchResult> nearByRestaurants = new ArrayList<>();
//        NearBySearchResult apiResult1 = new NearBySearchResult(null, "name1", null, "1", 5f, null);
//        NearBySearchResult apiResult2 = new NearBySearchResult(null, "name2", null, mRestoId, 3f, null);
//
//        nearByRestaurants.add(apiResult1);
//        nearByRestaurants.add(apiResult2);
//
//        NearBySearchResponse apiResponse = new NearBySearchResponse(nearByRestaurants);
//
//        when(mPlacesRepo.getNearByResponseLiveData()).thenReturn(new MutableLiveData<>(apiResponse));
//        when(mPlacesRepo.getHoursDetailLiveData()).thenReturn(new MutableLiveData<>());
//        when(coworkerRepository.getAllCoworker()).thenReturn(new MutableLiveData<>(coworkers));
//
//        //WHEN
//        restaurantsViewModel = new RestaurantsViewModel(coworkerRepository, mPlacesRepo, mAuth, mLocationRepo);
//        restaurantsViewModel.init(RestaurantsViewModel.NEARBY_SEARCH);
//
//        restaurantList = LiveDataTestUtil.awaitValue(restaurantsViewModel.restaurantsLiveData);
//
//        //THEN
//        assertEquals(0, restaurantList.get(0).getCoworkersEatingHere());
//        assertEquals(1, restaurantList.get(1).getCoworkersEatingHere());
//
//    }
//
//    @Test
//    public void hourDetailsUpdatedGivenApiResponse() throws InterruptedException {
//        //GIVEN
//        boolean day = true; //Monday
//
//        OpeningHours openingHours = new OpeningHours(
//                {new OpeningPeriod(new RestaurantDetail.Open(day, "0000"), new Close(day, "0000"))});
//
//        List<NearBySearchResult> nearByRestaurants = new ArrayList<>();
//        NearBySearchResult apiResult1 = new NearBySearchResult(null, "name1", null, "1", 5f, "Rue resto");
//
//        nearByRestaurants.add(apiResult1);
//
//        NearBySearchResponse apiResponse = new NearBySearchResponse(nearByRestaurants);
//
//        when(mPlacesRepo.getHoursDetailLiveData()).thenReturn(new MutableLiveData<>(Collections.singletonList(hourDetails)));
//        when(mPlacesRepo.getNearByResponseLiveData()).thenReturn(new MutableLiveData<>(apiResponse));
//        when(mUsersRepo.getAllUserLiveData()).thenReturn(new MutableLiveData<>());
//
//        //WHEN
//        restaurantsViewModel = new RestaurantsViewModel(mApplication, coworkerRepository, mPlacesRepo, mAuth, mLocationRepo);
//        restaurantsViewModel.init(RestaurantsViewModel.NEARBY_SEARCH);
//
//        restaurantList = LiveDataTestUtil.awaitValue(restaurantsViewModel.mRestaurantsLiveData);
//
//        //THEN
//        assertEquals("Closed today", restaurantList.get(0).getHours());
//
//    }
//
//
//}