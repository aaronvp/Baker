package com.example.android.baker.ui;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.example.android.baker.BakerWidgetProvider;
import com.example.android.baker.database.AppDatabase;
import com.example.android.baker.model.Ingredient;
import com.example.android.baker.model.Recipe;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class WidgetIngredientService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENT_LIST = "com.example.android.baker.action.update_ingredient_list";

    public WidgetIngredientService() {
        super("WidgetIngredientService");
    }

    /**
     * Starts this service to perform UpdatePlantWidgets action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdatePlantWidgets(Context context) {
        Intent intent = new Intent(context, WidgetIngredientService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENT_LIST);
        context.startService(intent);
    }

    /**
     * @param intent intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENT_LIST.equals(action)) {
                handleActionUpdateIngredientList();
            }
        }
    }

    private void handleActionUpdateIngredientList() {
        final StringBuilder ingredientList = new StringBuilder();
        AppDatabase recipeDatabase = AppDatabase.getInstance(getApplicationContext());
        List<Ingredient> ingredients = recipeDatabase.ingredientDao().loadIngredients();
        Recipe recipe = recipeDatabase.recipeDao().getRecipe();
        for (Ingredient ingredient : ingredients) {
            ingredientList.append(ingredient.getIngredient()).append("\n");
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakerWidgetProvider.class));
        BakerWidgetProvider.updateIngredientWidgets(getApplicationContext(), appWidgetManager,
                recipe.getName(), ingredientList.toString(), appWidgetIds);
    }

}
