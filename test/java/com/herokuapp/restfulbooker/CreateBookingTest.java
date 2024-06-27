package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.APIBaseState;
import util.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateBookingTest extends APIBaseState {

    @Test
    public void simpleXMLTest() {
        //Create a MAP
        JSONObject booking = new JSONObject();
        booking.put("firstname", "Test");
        booking.put("lastname", "User");
        booking.put("totalprice", 123);
        booking.put("depositpaid", true);

        //Creating the booking dates object
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2018-01-01");
        bookingDates.put("checkout", "2019-01-01");
        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", "nothing");

        given(requestSpec).
                body(booking.toString())
                .when().
                post("booking")
                .then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void createBookings() {
        //create a JSON body
        JSONObject booking = new JSONObject();
        booking.put("firstname", "Test");
        booking.put("lastname", "User");
        booking.put("totalprice", 123);
        booking.put("depositpaid", true);

        //Creating the booking dates object
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2018-01-01");
        bookingDates.put("checkout", "2019-01-01");
        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", "nothing");
        String path = "https://restful-booker.herokuapp.com/booking";

        /*RestAssured.given(requestSpec).contentType(ContentType.JSON).
                body(booking.toString()).
                when().post("booking").then().assertThat().statusCode(200);*/

        Response response = given(requestSpec)
                .body(booking.toString())
                .post("booking");
        log.info("Response Status code " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Create request failed");

    }

    @Test(dataProvider = "createBookingsCSV")
    public void createBookingsDataProvider(Map<String, Object> testData) {
        log.info("testData" + testData);
        JSONObject requestBody = new JSONObject();
        requestBody = Util.getRequestBody(testData);
        log.info("request body " + requestBody);
        //String path ="https://restful-booker.herokuapp.com/booking";
        Response response = Util.createBooking(requestBody, requestSpec);
        log.info("Status Code: " + response.getStatusCode());
        //RestAssured.given().contentType(ContentType.JSON).body(requestBody.toString()).when().post(path).then().assertThat().statusCode(200);
        Assert.assertEquals(response.getStatusCode(), 200, "Create Booking Failed");
        log.info("Booking ID: " + response.jsonPath().getString("bookingid") + " successfully created.");
    }

    @DataProvider(name = "createBookingsCSV")
    public Iterator<Object[]> createBooking() {
        String path = "src/test/resources/CreateBookings.csv";
        return Util.csvReader(path);
    }

    @Test
    public void createSingleBooking(){
        Map<String,Object> body = new HashMap<>();
        body.put("firstname", "Test");
        body.put("lastname", "User");
        body.put("totalprice", 123);
        body.put("depositpaid", true);
        Map<String,Object> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");
        body.put("bookingdates",dates);

        JSONObject requestBody = new JSONObject(body);
        //System.out.println(requestBody);
        Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody.toString())
                .post("https://restful-booker.herokuapp.com/booking");
        System.out.println(response.prettyPrint());
        System.out.println(response.statusCode());
        System.out.println("First Name: "+ response.jsonPath().getString("booking.firstname"));


    }
}
