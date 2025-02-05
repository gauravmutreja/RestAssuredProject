package test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    /*We will perform the following:
    1. Print No of courses returned by API
    2.Print Purchase Amount
    3. Print Title of the first course
    4. Print All course titles and their respective Prices
    5. Print no of copies sold by RPA Course
    6. Verify if Sum of all Course prices matches with Purchase Amount
    */

    public static void main(String[] args) {

        JsonPath js = new JsonPath(Payload.courseDetails());

        //    1. Print No. of courses returned by API
        int count = js.getInt("courses.size()");
        System.out.println("1. No. of courses: " + count);

        //    2.Print Purchase Amount
        int totalpurchaseAmt = js.getInt("dashboard.purchaseAmount");
        System.out.println("2. Purchase Amount = " + totalpurchaseAmt);

        //    3. Print Title of the first course

        String titleOfFirstCourse = js.get("courses[0].title");
        System.out.println("3. Title of the first course: " + titleOfFirstCourse);

        //    4. Print All course titles and their respective Prices
        System.out.println("\n4. All course titles and their respective Prices: ");
        for (int i = 0; i < count; i++) {
            String courseTitle = js.get("courses[" + i + "].title");
            System.out.print("Tile: " + courseTitle);
            System.out.println(" ,Price=" + js.getInt("courses[" + i + "].price"));
            System.out.println(totalpurchaseAmt);
        }

        //    5. Print no of copies sold by RPA Course
        for (int i = 0; i < count; i++) {
            String courseTitle = js.get("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                System.out.print("\n5. Print no of copies sold by RPA Course: ");
                System.out.print(js.get("courses[" + i + "].copies").toString());
                break;
            }
        }

        //    6. Verify if Sum of all Course prices matches with Purchase Amount : In class Sum Validation
    }
}
