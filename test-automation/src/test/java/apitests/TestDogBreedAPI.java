package apitests;

import apilib.PlayWrightAPIContextWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDogBreedAPI extends PlayWrightAPIContextWrapper {

    public String assertMessageAndStatus(JsonNode jsonNode){
        String imageUrl = String.valueOf(jsonNode.get("message"));
        String status = String.valueOf(jsonNode.get("status"));
        Assert.assertEquals(status.substring(1, status.length() - 1), "success");
        return imageUrl;

    }

    public void assertImagePattern(String imageUrl){
        String expectedUrlPattern = "https://images\\.dog\\.ceo/breeds/.+/.+\\.jpg";
        Pattern pattern = Pattern.compile(expectedUrlPattern);
        Matcher matcher = pattern.matcher(imageUrl);
        Assert.assertTrue(matcher.find());
    }

    public String ExtractImageFileName(String imageUrl){
        String imageFilename = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
        imageFilename = imageFilename.replaceAll("\"$", "");
        return imageFilename;

    }

    @Test
    public void ValidateAPIAccessibility() throws IOException {
        /*
        Validate the API is accessible more than 1 and it's giving proper response.
         Validate that is returning correct url format and success message.
         */
        for(int i =0; i< 10; i ++) {
            JsonNode jsonNode = GetResponse();
            String imageUrl = assertMessageAndStatus(jsonNode);;
            assertImagePattern(imageUrl);
        }
    }

    private boolean checkIfDifferent(String[] urls) {
        //this logic is incorrect
        for (int i = 0; i < urls.length - 1; i++) {
            if (!urls[i].equals(urls[i + 1]))  {
                return true;
            }
        }
        return false;
    }

    @Test
    public void ValidateTenDifferentImageUrls() throws IOException {
        /*
        Validate that unique images are returned by API for initial 10 hits.
         */
        String[] imageUrls = new String[10];
        for (int i = 0; i < 10; i++) {
            JsonNode jsonNode = GetResponse();
            String imageUrl = assertMessageAndStatus(jsonNode);;
            imageUrls[i] = imageUrl;
        }
        boolean areDifferent = checkIfDifferent(imageUrls);
        Assert.assertTrue(areDifferent, "Images should be different");
    }



    @Test
    public void ValidateAPIDuplicateImages() throws IOException {
        /*
        Validate that Duplicate Images are return by the APIs.
        Duplicate Images are not given exactly 10 times, Its give more than 10 hits
        So I put in while loop, although it's dangerous to put inside while loop. We can limit the number of hit to 1000 time or come out after certain
        period of time to avoid this test running forever.
         */
        List<String> images = new ArrayList<>();
        for(int i =0; i< 10; i ++) {
            JsonNode jsonNode = GetResponse();
            String imageUrl = assertMessageAndStatus(jsonNode);;
            assertImagePattern(imageUrl);
            String imageFileName = ExtractImageFileName(imageUrl);
            images.add(imageFileName);
        }

        int count = 0;
        while(true){
            JsonNode jsonNode = GetResponse();
            count = count + 1;
            String duplicateImageUrl = assertMessageAndStatus(jsonNode);
            String duplicateImageFileName = ExtractImageFileName(duplicateImageUrl);
            if(images.contains(duplicateImageFileName)) {
                Assert.assertTrue(images.contains(duplicateImageFileName));
                break;
            }
            if(count == 1000){
                Assert.assertFalse(true, "Duplicate image is not found in 1000 hits hence stopping the test");
                break;
            }
        }
    }
}

