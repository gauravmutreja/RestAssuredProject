package test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {
// Dynamically build payload with external data inputs
    @Test(dataProvider = "BookData")
    public void addBook(String isbn,String aisle){
        RestAssured.baseURI = "http://216.10.245.166";
        String resp =
        given()
                .headers("Content-Type","application/Json")
                .body(Payload.addBook(isbn,aisle))
        .when().post("/Library/Addbook.php")
        .then().log().status()
                .assertThat().statusCode(200)
                .extract().response().asString();

        System.out.println("Response: "+resp);

        JsonPath js = ReusableMethods.rawToJson(resp);
        String ID = js.get("ID");
        System.out.println("ID : "+ID);

        System.out.println("*************************************");
        //Delete Book
        given()
                .headers("Content-Type","application/Json")
                .body("{\n" +
                        " \n" +
                        "\"ID\" : \""+ID+"\"\n" +
                        " \n" +
                        "}")
                .when().post("/Library/DeleteBook.php")
                .then().log().status()
                .log().body()
                .assertThat().statusCode(200);

        System.out.println("*************************************" +
                "*************************************");

    }

    @DataProvider(name = "BookData")
    public Object[][] getData(){
        //array=collection of elements
        //multidimensional array= collection of arrays

        return new Object[][] {{"Book4", "123"},{"Book5", "456"},{"Book6", "789"}};
    }
}
