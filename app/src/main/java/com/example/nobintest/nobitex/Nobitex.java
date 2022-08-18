package com.example.nobintest.nobitex;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Nobitex {

    public static String TAG = "NOBITEX";
    private static String token = "";

    // to remove token -> Nobitex.setToken("");
    public synchronized static void setToken(String token) {
        Nobitex.token = token;
    }

    public static String getToken() {

        if (token.equals("")) {
            Log.v(TAG, "token doesn't exist or is expired.");
            return null;
        }
        return token;
    }


    public static class Resolution {
        public static String oneHour = "60";
        public static String threeHour = "180";
        public static String sixHour = "360";
        public static String halfDay = "720";
        public static String oneDay = "D";
        public static String twoDay = "2D";
        public static String threeDay = "3D";
    }

    public static String post(String url, String json) {

        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body;
        if (json == null) {
            body = RequestBody.create(null, new byte[]{});
        } else {
            body = RequestBody.create(json, JSON);
        }

        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                Log.v(Nobitex.TAG, "err --> " + response.message());
                return "error Happened While Posting Data To Server.";

            } else {
                return response.body().string();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.v(Nobitex.TAG, e.getMessage());
        }
        return "empty message";
    }

    public static String post(String url, String json, String token) {

        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body;
        if (json == null) {
            body = RequestBody.create(null, new byte[]{});
        } else {
            body = RequestBody.create(json, JSON);
        }

        Request request = new Request.Builder()
            .url(url)
            .header("Authorization", "Token " + token)
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                Log.v(Nobitex.TAG, "err --> " + response.message());
                return "error Happened While Posting Data To Server.";

            } else {
                return response.body().string();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.v(Nobitex.TAG, e.getMessage());
        }
        return "empty message";
    }

    public static String get(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(url)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Log.v(TAG, "err --> " + response.message());
                return "error Happened While Getting Data To Server.";
            } else {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(Nobitex.TAG, e.getMessage());
        }
        return "empty message";
    }

    public static String get(String url, String token) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(url)
            .header("Authorization", "Token " + token)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Log.v(TAG, "err --> " + response.message());
                return "error Happened While Getting Data To Server.";
            }
            return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            Log.v(Nobitex.TAG, e.getMessage());
        }
        return "empty message";
    }

}
