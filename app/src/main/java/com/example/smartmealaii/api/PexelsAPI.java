package com.example.smartmealaii.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class PexelsAPI {

    private static final String API_KEY =
            "v8I9m6fKxSTqINvHrGics1mID1e7TNQJauHyC7g3VQiTHbKYHHNcx6b6";

    public interface ImageCallback {
        void onSuccess(String url);
        void onError();
    }

    public static void searchImage(Context context, String query, ImageCallback callback) {

        try { query = query.trim().replace(" ", "%20"); }
        catch (Exception ignored) {}

        String url = "https://api.pexels.com/v1/search?query=" + query + "&per_page=1";

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                res -> {
                    try {
                        JSONArray photos = res.getJSONArray("photos");
                        if (photos.length() == 0) {
                            callback.onError();
                            return;
                        }

                        String img = photos.getJSONObject(0)
                                .getJSONObject("src")
                                .getString("medium");

                        callback.onSuccess(img);
                    } catch (Exception e) {
                        callback.onError();
                    }
                },
                error -> callback.onError()
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> h = new HashMap<>();
                h.put("Authorization", API_KEY);
                return h;
            }
        };

        queue.add(req);
    }
}
