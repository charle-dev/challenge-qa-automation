package com.accenture.challenge.steps;

import com.accenture.challenge.api.BookStoreApi;
import com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApiSteps {

    private final BookStoreApi api = new BookStoreApi();
    private BookStoreApi.UserContext ctx;
    private List<String> chosenIsbns = new ArrayList<>();

    @Given("que eu crio um usuario via API")
    public void createUser() throws Exception {
        String username = "user_" + UUID.randomUUID().toString().substring(0,8);
        String password = "Aa!" + UUID.randomUUID().toString().substring(0,8) + "9";
        ctx = api.createUser(username, password);
    }

    @And("eu gero um token de acesso")
    public void generateToken() throws Exception {
        ctx.token = api.generateToken(ctx.username, ctx.password);
    }

    @And("eu confirmo que o usuario esta autorizado")
    public void isAuthorized() throws Exception {
        Assertions.assertTrue(api.isAuthorized(ctx.username, ctx.password));
    }

    @And("eu listo os livros e escolho dois")
    public void listAndPickBooks() throws Exception {
        JsonNode books = api.listBooks();
        Assertions.assertTrue(books.isArray() && books.size() >= 2, "Menos de 2 livros retornados");
        chosenIsbns.add(books.get(0).get("isbn").asText());
        chosenIsbns.add(books.get(1).get("isbn").asText());
    }

    @When("eu reservo os dois livros para o usuario")
    public void reserveTwoBooks() throws Exception {
        api.addBooks(ctx.userId, ctx.token, chosenIsbns);
    }

    @Then("os detalhes do usuario exibem os dois livros")
    public void userDetailsShowBooks() throws Exception {
        JsonNode user = api.getUser(ctx.userId, ctx.token);
        JsonNode books = user.get("books");
        Assertions.assertEquals(2, books.size(), "Quantidade de livros não é 2");
        List<String> got = new ArrayList<>();
        for (JsonNode b : books) got.add(b.get("isbn").asText());
        Assertions.assertTrue(got.containsAll(chosenIsbns));
    }
}
