package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    /**
     * Parses sandwich json into a {@link Sandwich} object
     *
     * @param json representing a sandwich
     * @return  {@link Sandwich} object
     * @throws JSONException
     */
    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichJson = new JSONObject(json);

        JSONObject nameObj = sandwichJson.getJSONObject("name");
        String mainName = nameObj.getString("mainName");

        JSONArray alsoKnownAsArray = nameObj.getJSONArray("alsoKnownAs");

        // Create an {@link ArrayList} that will hold the alternative names of the sandwich
        List<String> alsoKnownAs = new ArrayList<>();

        // Parse the alternative names and add the to the {@link ArrayList} of the alternative names
        if (!(alsoKnownAsArray.length() == 0)) {
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }
        }

        String placeOfOrigin = sandwichJson.getString("placeOfOrigin");
        String description = sandwichJson.getString("description");
        String image = sandwichJson.getString("image");

        JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<>();

        if (!(ingredientsArray.length() == 0)) {
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }
        }


        // Create an {@link Sandwich} for the parsed details of the sandwich
        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description
                , image, ingredients);

        return sandwich;
    }
}
