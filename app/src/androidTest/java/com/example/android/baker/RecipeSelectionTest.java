package com.example.android.baker;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.android.baker.ui.activity.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeSelectionTest {

    public static final String RECIPE_INGREDIENT_HEADER = "Ingredients";

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void scrollToItemBelowFold_checkItsText() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.rv_recipeList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        // Match the text in an item below the fold and check that it's displayed.
        String itemElementText = "Nutella Pie";
        onView(withId(R.id.tv_ingredients_label)).check(matches(withText(RECIPE_INGREDIENT_HEADER)));

    }

}
