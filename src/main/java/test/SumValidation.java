package test;

import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {
    @Test
    public void sumOfCourses(){
    //  6. Verify if Sum of all Course prices matches with Purchase Amount

        JsonPath js = new JsonPath(Payload.courseDetails());
        int count = js.getInt("courses.size()");
        int totalActualAmt = 0;
        for (int i = 0; i < count; i++) {
            int price = js.get("courses[" + i + "].price");
            int copies = js.get("courses[" + i + "].copies");
            int amt = price * copies;
            totalActualAmt += amt;
        }
        System.out.println("\nTotal Amount: " + totalActualAmt);
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(totalActualAmt,purchaseAmount);

    }
}
