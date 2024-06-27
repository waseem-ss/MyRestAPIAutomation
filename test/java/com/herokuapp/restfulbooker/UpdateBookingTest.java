package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.APIBaseState;
import util.Util;

import java.util.HashMap;
import java.util.Map;

public class UpdateBookingTest extends APIBaseState {

    @Parameters({"id"})
    @Test
    public void updateBookings(String id) {
        //Booking ID: 299 successfully created
        //Booking ID: 321 successfully created
        //Booking ID: 337 successfully created.
        // int bookingId = Integer.parseInt(id);
        //Create Booking
        Map<String, Object> testData = new HashMap<>();
        testData.put("firstname", "John");
        testData.put("lastname", "Wick");
        testData.put("totalprice", 200);
        testData.put("depositpaid", false);
        testData.put("checkin", "2024-04-24");
        testData.put("checkout", "2024-05-08");
        testData.put("additionalneeds", "nothing");
        log.info("testData" + testData);
        JSONObject requestBody = new JSONObject();
        requestBody = Util.getRequestBody(testData);
        log.info("Creating booking for test data request body " + requestBody);
        String url = "https://restful-booker.herokuapp.com/booking";
        //Create a new bookint to be updated later
        Response responseCreate = Util.createBooking(requestBody, requestSpec);
        log.info("Status Code: " + responseCreate.getStatusCode());
        responseCreate.prettyPrint();
        Assert.assertEquals(responseCreate.getStatusCode(), 200, "Create request failed");

        int bookingIdToUpdate = responseCreate.jsonPath().getInt("bookingid");
        log.info("Booking created with ID " + bookingIdToUpdate);
        //log.info("Update the booking ID: " + bookingId);
        //String path ="https://restful-booker.herokuapp.com/booking";
        //Map<String, Object> bookingUpdate = new Map<>();
        JSONObject bookingUpdate = new JSONObject();
        bookingUpdate.put("firstname", responseCreate.jsonPath().getString("booking.firstname"));
        bookingUpdate.put("lastname", responseCreate.jsonPath().getString("booking.lastname"));
        bookingUpdate.put("totalprice", 400);
        bookingUpdate.put("depositpaid", true);
        JSONObject bookingDate = new JSONObject();
        bookingDate.put("checkin", responseCreate.jsonPath().getString("booking.bookingdates.checkin"));
        bookingDate.put("checkout", responseCreate.jsonPath().getString("booking.bookingdates.checkout"));
        bookingUpdate.put("bookingdates", bookingDate);
        bookingUpdate.put("additionalneeds", "nothing");


        //Put Rest API Call
        Response responseUpdated = RestAssured.given().auth().preemptive().basic("admin", "password123")
                .contentType(ContentType.JSON).body(bookingUpdate.toString())
                .put(url + "/" + bookingIdToUpdate);
        responseUpdated.prettyPrint();
        Assert.assertEquals(responseUpdated.getStatusCode(), 200, "Update request failed for booking ID " + bookingIdToUpdate);
        softAssert.assertEquals(responseUpdated.jsonPath().getInt("totalprice"), bookingUpdate.getInt("totalprice"), "totalprice update failed");
        softAssert.assertEquals(responseUpdated.jsonPath().getBoolean("depositpaid"), bookingUpdate.getBoolean("depositpaid"), "Deposit paid update failed");
        softAssert.assertAll();
    }
}
