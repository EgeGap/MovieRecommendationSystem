package com.ege.app.rest.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.logging.Logger;

@Service
public class MovieRecommendationService {

   // private final Logger logger = Logger.getLogger(getClass().getName());

    public String recommendMovie(String[] filmNames) {
        if (filmNames == null || filmNames.length == 0) {
            throw new IllegalArgumentException("Film names cannot be null or empty.");
        }

        StringBuilder promptBuilder = new StringBuilder();
        for (String filmName : filmNames) {
            promptBuilder.append(filmName).append(", ");
        }
        promptBuilder.delete(promptBuilder.length(), promptBuilder.length());
        String prompt = promptBuilder.toString();

        String recommendedMovie = chatGPT(prompt);

        return recommendedMovie;
    }

    private String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "API KEY";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt
                    + "\"}]}";
            connection.setDoOutput(true);
            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                writer.write(body);
                writer.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("HTTP error code: " + responseCode);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
               // logger.info("API Response: " + response.toString());
                return extractMovieNameFromResponse(response.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error communicating with OpenAI API", e);
        }
    }

    private String extractMovieNameFromResponse(String jsonResponse) {
        JSONObject responseObject = new JSONObject(jsonResponse);

        JSONArray choicesArray = responseObject.getJSONArray("choices");
        JSONObject firstChoice = choicesArray.getJSONObject(0);

        JSONObject message = firstChoice.getJSONObject("message");
        String movieContent = message.getString("content");

        String[] lines = movieContent.split("\n");
        String movieName = lines[0].trim(); 

        return movieName;
    }

}
