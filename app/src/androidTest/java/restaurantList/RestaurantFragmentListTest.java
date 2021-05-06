package restaurantList;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ActivityTestRule;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.ui.MainActivity;
import com.go4lunch.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class RestaurantFragmentListTest {

    private Context context;

    private Coworker coworker;

    private CoworkerRepository coworkerRepository;;

    @Rule
    public final ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setup(){
        coworker = new Coworker("uid", "name", "email", "urlPhoto");
        coworker.setRestaurantUid("restaurantUid");
        coworker.setRestaurantName("le petit café");
        coworker.setRestaurantAddress("123 rue blablabla");
        coworkerRepository = CoworkerRepository.getInstance();
        coworkerRepository.createCoworker(coworker.getUid(), coworker.getCoworkerName(), coworker.getCoworkerEmail());
        coworkerRepository.updateRestaurantPicked(coworker.getRestaurantUid(), coworker.getRestaurantName(), coworker.getRestaurantAddress(), coworker);
        this.context = ApplicationProvider.getApplicationContext();
        mainActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.bottom_navigation_menu_list_button)).perform(click());
    }

    @Test
    public void recyclerView_isDisplayed(){
        onView(withId(R.id.restaurantRecyclerView)).check(matches(isDisplayed()));
    }
}
