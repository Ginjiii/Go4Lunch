package com.restaurantDetail;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailViewModel;
import com.example.go4lunch.goforlunch.utils.LiveDataTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class RestaurantDetailViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    CoworkerRepository coworkerRepository;
    @Mock
    RestaurantRepository restaurantRepository;

    private RestaurantDetailViewModel restaurantDetailViewModel;
    private Coworker coworker;
    private String restaurantId;
    private MutableLiveData<Restaurant> restaurantLiveData = new MutableLiveData<>();
    private MutableLiveData<Restaurant> googleRestaurantDetail = new MutableLiveData<>();
    private Restaurant restaurants;

    @Before
    public void setUp()  {

        when(coworkerRepository.getActualUser()).thenReturn(coworker);
        when(restaurantRepository.getGoogleRestaurantDetail(restaurantId)).thenReturn(googleRestaurantDetail);
    }

    @Test
    public void fetchRestaurantViewModel() throws InterruptedException {
        //Given
        Restaurant restaurantExpected = new Restaurant("restaurantID","name",48.633331,2.33333,"address",10,100,"photoReference",5,"phoneNumber","webSite");
        //When
        restaurantDetailViewModel.getRestaurantDetail(restaurantId);
        restaurants = LiveDataTestUtil.getOrAwaitValue(restaurantDetailViewModel.getRestaurantDetail(restaurantId));
        Restaurant restaurantDetailResult = LiveDataTestUtil.getOrAwaitValue(restaurantDetailViewModel.getRestaurantDetail(restaurantId));
        //Then
        Assert.assertEquals(restaurantExpected, restaurantDetailResult );
    }
}