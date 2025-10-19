package com.accenture.challenge.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BookStoreApi {

    private static final String BASE = "https://demoqa.com";
    private final ObjectMapper om = new ObjectMapper();
    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    public static class UserContext {

        public String userId;
        public String username;
        public String password;
        public String token;
    }

    public UserContext createUser(String username, String password) throws Exception {
        ObjectNode payload = om.createObjectNode();
        payload.put("userName", username);
        payload.put("password", password);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/Account/v1/User"))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(om.writeValueAsString(payload), StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 201) {
            throw new RuntimeException("Create user failed: " + resp.body());
        }
        JsonNode json = om.readTree(resp.body());
        UserContext uc = new UserContext();
        uc.userId = json.get("userID").asText();
        uc.username = username;
        uc.password = password;
        return uc;
    }

    public String generateToken(String username, String password) throws Exception {
        ObjectNode payload = om.createObjectNode();
        payload.put("userName", username);
        payload.put("password", password);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/Account/v1/GenerateToken"))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(om.writeValueAsString(payload), StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Generate token failed: " + resp.body());
        }
        JsonNode json = om.readTree(resp.body());
        return json.get("token").asText();
    }

    public boolean isAuthorized(String username, String password) throws Exception {
        ObjectNode payload = om.createObjectNode();
        payload.put("userName", username);
        payload.put("password", password);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/Account/v1/Authorized"))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(om.writeValueAsString(payload), StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Authorized failed: " + resp.body());
        }
        return Boolean.parseBoolean(resp.body());
    }

    public JsonNode listBooks() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/BookStore/v1/Books"))
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("List books failed: " + resp.body());
        }
        return om.readTree(resp.body()).get("books");
    }

    public void addBooks(String userId, String token, List<String> isbns) throws Exception {
        ObjectNode payload = om.createObjectNode();
        payload.put("userId", userId);
        ArrayNode arr = payload.putArray("collectionOfIsbns");
        for (String isbn : isbns) {
            ObjectNode item = om.createObjectNode();
            item.put("isbn", isbn);
            arr.add(item);
        }
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/BookStore/v1/Books"))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(om.writeValueAsString(payload), StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 201) {
            throw new RuntimeException("Add books failed: " + resp.statusCode() + " " + resp.body());
        }
    }

    public JsonNode getUser(String userId, String token) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/Account/v1/User/" + userId))
                .timeout(Duration.ofSeconds(30))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Get user failed: " + resp.body());
        }
        return om.readTree(resp.body());
    }
}
