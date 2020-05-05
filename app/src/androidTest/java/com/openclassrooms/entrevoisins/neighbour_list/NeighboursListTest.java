
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.ClickViewAction;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    @Rule
    public final ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule<>(ListNeighbourActivity.class);


    @Before
    public void setUp() {
        ListNeighbourActivity activity = mActivityRule.getActivity();
        assertThat(activity, notNullValue());
    }

    @NonNull
    private static ViewAction selectTabAtPosition(int position) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TabLayout.class));
            }

            @Override
            public String getDescription() {
                return "with tab at index" + position;
            }

            @Override
            public void perform(UiController uiController, View view) {
                if (view instanceof TabLayout) {
                    TabLayout tabLayout = (TabLayout) view;
                    TabLayout.Tab tab = tabLayout.getTabAt(position);

                    if (tab != null) {
                        tab.select();
                    }
                }
            }
        };
    }



    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        // This is fixed
        int ITEMS_COUNT = 12;
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT -1));
    }

    /**
     * When we click on an item, the details screen is shown
     */
    @Test
    public void myNeighboursList_clickAction_shouldStartDetails() {
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new ClickViewAction()));
        onView(ViewMatchers.withId(R.id.detailsActivity)).check(matches(isDisplayed()));

    }

    /**
     * When details screen start, the name of the text view should be the neighbour name
     */
    @Test
    public void detailsActivity_nameTextView_shouldBeNeighbourName() {
        String mName = DI.getNeighbourApiService().getNeighbours().get(0).getName();
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new ClickViewAction()));
        onView(ViewMatchers.withId(R.id.firstname)).check(matches(withText(mName)));

    }

    /**
     * Favorite list should only contains favorite neighbours
     */
    @Test
    public void myFavoriteList_contains_favoriteNeighbours() {
        List<Neighbour> mFavorites = DI.getNeighbourApiService().getFavorites();
        int mFavSize = mFavorites.size();
        int mCounter = 0;
        onView(ViewMatchers.withId(R.id.tabs)).perform(selectTabAtPosition(1));
        onView(ViewMatchers.withId(R.id.list_favorites)).check(matches(hasMinimumChildCount(mFavSize)));

        for (Neighbour in : mFavorites) {
            onView(ViewMatchers.withId(R.id.list_favorites)).perform(RecyclerViewActions.actionOnItemAtPosition(mCounter, new ClickViewAction()));
            onView(ViewMatchers.withId(R.id.firstname)).check(matches(withText(in.getName())));
            onView(ViewMatchers.withId(R.id.imageBack)).perform(click());
            mCounter +=1;
        }
    }
}
