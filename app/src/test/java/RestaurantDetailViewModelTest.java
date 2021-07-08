import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class RestaurantDetailViewModelTest {

//    @Rule
//    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
//
//    @Mock
//    CoworkerRepository coworkerRepository;
//    @Mock
//    RestaurantRepository restaurantRepository;
//    @Mock
//    FirebaseAuth firebaseAuth;
//    @Mock
//    private RestaurantDetailViewModel restaurantDetailViewModel;
//
//    private Coworker coworker;
//    private String currentUid = "321";
//    private String restaurantId = "restaurantId";
//    private MutableLiveData<Restaurant> restaurantLiveData = new MutableLiveData<>();
//    private MutableLiveData<Restaurant> googleRestaurantDetail = new MutableLiveData<>();
//    private Restaurant restaurants;
//
//    @Before
//    public void setUp()  {
//        when(coworkerRepository.getActualUser()).thenReturn(coworker);
//        when(restaurantRepository.getGoogleRestaurantDetail(restaurantId)).thenReturn(googleRestaurantDetail);
//    }
//
//    @Test
//    public void fetchRestaurantViewModel() throws InterruptedException {
//        //Given
//        Restaurant restaurantExpected = new Restaurant("1234","name",48.633331,2.33333,"address",true,100,"photoReference",5,"phoneNumber","webSite");
//        //When
//        restaurantDetailViewModel.getRestaurantDetail(restaurantId);
//        restaurants = LiveDataTestUtil.getOrAwaitValue(restaurantDetailViewModel.getRestaurantDetail(restaurantId));
//        Restaurant restaurantDetailResult = LiveDataTestUtil.getOrAwaitValue(restaurantDetailViewModel.getRestaurantDetail(restaurantId));
//        restaurantDetailViewModel = new RestaurantDetailViewModel(restaurantRepository, coworkerRepository);
//        //Then
//
//        Assert.assertNotNull(restaurantDetailResult);
//        Assert.assertEquals(restaurantExpected, restaurantDetailResult );
//    }
//}

    private RestaurantDetailViewModel restaurantDetailViewModel;

    private Coworker coworker;


    private String restaurantId;

    private List<Restaurant> restaurants;
    private Restaurant restaurant1;

    @Mock
    private CoworkerRepository coworkerRepository;
    @Mock
    private RestaurantRepository restaurantRepository;

    private LatLng location;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        location = new LatLng(10.00, 5.00);
        coworker = new Coworker("uid", "name", "email", "urlPhoto");
        restaurantId = "12345";
        restaurant1 = new Restaurant("12345", "name", 10.00, 5.00, "address",true, 300, "www.urlimage.com", 3, "0666666666", "www.website");
        restaurants = new ArrayList<>();
        restaurants.add(restaurant1);

    }

    @Test
    public void start_correctInfoRestaurantSetup(){
        when(coworkerRepository.getActualUser()).thenReturn(coworker);
        when(coworkerRepository.getAllCoworker()).thenReturn(mockQuerySnap());

        restaurantDetailViewModel = new RestaurantDetailViewModel(restaurantRepository, coworkerRepository);
        restaurantDetailViewModel.fetchInfoRestaurant(restaurant1);
        assertEquals(restaurant1.getName(), restaurantDetailViewModel.restaurant.getName());
       assertEquals(restaurant1.getAddress(), restaurantDetailViewModel.restaurant.getAddress());
        assertEquals(restaurant1.getPhotoReference(), restaurantDetailViewModel.restaurant.getPhotoReference());
        assertEquals((int) restaurant1.getRating(), (int) restaurantDetailViewModel.restaurant.getRating());

        restaurantDetailViewModel.coworkersIdMutableLiveData.observeForever(result -> assertEquals(restaurant1.getCoworkersEatingHere(), result));

        assertNotNull(restaurantDetailViewModel.restaurant.getPhoneNumber());
        assertFalse(restaurantDetailViewModel.isRestaurantLiked.getValue());
    }




    private Task<QuerySnapshot> mockQuerySnap(){
        return new Task<QuerySnapshot>() {
            @Override
            public boolean isComplete() {
                return false;
            }

            @Override
            public boolean isSuccessful() {
                return false;
            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public QuerySnapshot getResult() {
                return null;
            }

            @Override
            public <X extends Throwable> QuerySnapshot getResult(@NonNull Class<X> aClass) throws X {
                return null;
            }

            @Nullable
            @Override
            public Exception getException() {
                return null;
            }

            @NonNull
            @Override
            public Task<QuerySnapshot> addOnSuccessListener(@NonNull OnSuccessListener<? super QuerySnapshot> onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<QuerySnapshot> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super QuerySnapshot> onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<QuerySnapshot> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super QuerySnapshot> onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<QuerySnapshot> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<QuerySnapshot> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<QuerySnapshot> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
                return null;
            }
        };
    }
}