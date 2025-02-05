package test;

import files.ReusableMethods;
import io.restassured.RestAssured;
import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Basic {
    public static void main(String[] args) {
// Add place API
        //given : all input details
        //when : Submit the API with Request type and resource
        //then : Validate the response

        //To convert json file to string : Convert content of file to byte => then byte to String

//Here we Add place => Update place with new address => Get Place to validate if New Address is present in response

        RestAssured.baseURI = "https://rahulshettyacademy.com"; //this is class level so common for all http methods : post, put, get, delete

        //Add Place:
        String response = given()
                .log()
                .all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.addPlace())
                .when()
                .post("maps/api/place/add/json")
                .then()
                .log().status()
                .log().body()
                .assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract()
                .response().asString();

        System.out.println("Response: " + response);
        JsonPath js = new JsonPath(response);
        String place_id = js.getString("place_id");
        String place_id2 = js.get("place_id");
        System.out.println("\nPlace ID: " + place_id);
        System.out.println("\nPlace ID: " + place_id2);

        //Update Place:
        String updatedAddress = "C303 Dheeraj Garden, USA";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + place_id + "\",\n" +
                        "\"address\":\"" + updatedAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get Place:
        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", place_id)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println("\nLatest Updated Address: " + actualAddress);
        Assert.assertEquals(actualAddress, updatedAddress);


    }
}
