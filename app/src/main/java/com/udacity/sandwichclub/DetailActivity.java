package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    /**
     * Views used for presenting details about a sandwich
     */
    private TextView mAlternateNameTextView;
    private TextView mOriginTextView;
    private TextView mIngridientsTextView;
    private TextView mDescriptionTextView;

    /** Varable that holds the reference of Sandwich the details of witch
     * we should show
     */
    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Find the views in the Activity layout
        mAlternateNameTextView = findViewById(R.id.also_known_tv);
        mIngridientsTextView = findViewById(R.id.ingredients_tv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];


        mSandwich = null;
        try {
            // Parse the json that represents a sandwich to get a sandwich back
            mSandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        // Get the list of alternate names for the sandwich
        List<String> alsoKnownAsList = mSandwich.getAlsoKnownAs();

        // Append each names plus a comma
        for(String name : alsoKnownAsList)
            mAlternateNameTextView.append(name + ", ");

        List<String> ingredients = mSandwich.getIngredients();
        for (String ingredient : ingredients){
            mIngridientsTextView.append(ingredient + ", ");
        }

        // Bind the remaining details
        mOriginTextView.setText(mSandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(mSandwich.getDescription());
    }
}
