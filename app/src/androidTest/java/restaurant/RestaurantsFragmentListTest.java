package restaurant;

import com.example.go4lunch.goforlunch.models.common.Location;
import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantsViewModel;

import junit.framework.TestCase;

public class RestaurantsFragmentListTest extends TestCase {

    private Location location;
    private RestaurantsViewModel restaurantsViewModel;
    private android.location.Location locationUser;

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testGetLocationUser() {
        assertEquals(location, locationUser);
    }

}