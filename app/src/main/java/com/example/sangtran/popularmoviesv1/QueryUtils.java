package com.example.sangtran.popularmoviesv1;

/**
 * Created by Sang Tran on 2017-01-11.
 */

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static String mMovieId;
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return an {@link Movie} object to represent a single movie.
     */
    public static List<Movie> fetchMovieData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Movie> movies = extractMovies(jsonResponse);

        // Return the {@link Event}
        return movies;
    }

    /**
     * Query the USGS dataset and return an {@link Movie} object to represent a single movie.
     */
    public static List<Trailer> fetchTrailerData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Trailer> trailers = extractTrailers(jsonResponse);

        // Return the {@link Event}
        return trailers;
    }

    /**
     * Query the USGS dataset and return an {@link Movie} object to represent a single movie.
     */
    public static List<Review> fetchReviewData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Review> reviews = extractReview(jsonResponse);

        // Return the {@link Event}
        return reviews;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Movie} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Movie> extractMovies(String movieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        //String varaibles used to build image path
        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
        final String IMAGE_SIZE = "w185";

        //http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Movie> movies = new ArrayList<Movie>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject response = new JSONObject(movieJSON);
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject movieObj = results.getJSONObject(i);
                String movieId = movieObj.getString("id");
                String movieTitle = movieObj.getString("title");
                String moviePosterPath = movieObj.getString("poster_path");
                String movieDate = movieObj.getString("release_date");
                String movieOverview = movieObj.getString("overview");
                String movieVoteAvg = movieObj.getString("vote_average");

                //set MovieId to get trailer and reviews
                mMovieId = movieId;

                //build Image Uri
                Uri baseUri = Uri.parse(BASE_IMAGE_URL + IMAGE_SIZE + "/"+ moviePosterPath);
                Uri.Builder uriBuilder = baseUri.buildUpon();
                String imagePathUri = uriBuilder.build().toString();

                Movie movie = new Movie(movieId, movieTitle,movieDate,movieOverview,imagePathUri, movieVoteAvg);
                movies.add(movie);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }

        // Return the list of earthquakes
        return movies;
    }

    /**
     * Return a list of {@link Trailer} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Trailer> extractTrailers(String trailerJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(trailerJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Trailer> trailers = new ArrayList<Trailer>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject response = new JSONObject(trailerJSON);
            JSONArray youtube = response.getJSONArray("youtube");
            for (int i = 0; i < youtube.length(); i++) {
                JSONObject trailerObject = youtube.getJSONObject(i);
                String trailerName = trailerObject.getString("name");
                String source = trailerObject.getString("source");

                //TRAILER
                Trailer trailer = new Trailer(trailerName, source);
                trailers.add(trailer);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the trailer JSON results", e);
        }

        // Return the list of earthquakes
        return trailers;
    }

    /**
     * Return a list of {@link Review} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Review> extractReview(String reviewJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(reviewJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Review> reviews = new ArrayList<Review>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject response = new JSONObject(reviewJSON);
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject reviewObject = results.getJSONObject(i);
                String reviewAuthor = reviewObject.getString("author");
                String reviewContent = reviewObject.getString("content");

                //TRAILER
                Review review = new Review(reviewAuthor, reviewContent);
                reviews.add(review);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Review JSON results", e);
        }

        // Return the list of earthquakes
        return reviews;
    }
}