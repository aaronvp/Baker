package com.example.android.baker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.example.android.baker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends Fragment {


    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        //RecipeListAdapter recipeListAdapter = new RecipeListAdapter(getContext(), Recipe.getRecipeList());
        CardView cardView = (CardView) rootView.findViewById(R.id.card_view);
        TextView textView = rootView.findViewById(R.id.textViewName);
        textView.setText(Recipe.getRecipeList().get(1).getRecipeName());
        return rootView;
    }

}
