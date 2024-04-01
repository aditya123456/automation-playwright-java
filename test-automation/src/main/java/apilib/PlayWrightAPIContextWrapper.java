package apilib;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;

import java.io.IOException;

import static org.testng.AssertJUnit.assertTrue;

public class PlayWrightAPIContextWrapper {

    private Playwright playwright = Playwright.create();

    APIRequest request = playwright.request();

    private APIRequestContext requestContext = request.newContext();

    String URL = "https://dog.ceo/api/breeds/image/random";


    public JsonNode GetResponse() throws IOException {
        APIResponse response = requestContext.get(URL);
        assertTrue(response.ok());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.body());;
        return jsonResponse ;
    }
}
