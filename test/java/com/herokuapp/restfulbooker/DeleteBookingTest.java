package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.APIBaseState;
import util.Util;

import java.util.HashMap;
import java.util.Map;

public class DeleteBookingTest extends APIBaseState {
    @Test
    public void deleteBookingTest(){
        //Create the booking to be deleted
        Map<String,Object> testData = new HashMap<>();
        testData.put("firstname","Henry");
        testData.put("lastname","Walker");
        testData.put("totalprice",100);
        testData.put("depositpaid",false);
        testData.put("checkin","2024-05-24");
        testData.put("checkout","2024-06-08");
        testData.put("additionalneeds", "Child care");
        log.info("testData" + testData);
        JSONObject requestBody = new JSONObject();
        requestBody = Util.getRequestBody(testData);
        log.info("request body " + requestBody);
        String path ="https://restful-booker.herokuapp.com/booking";
        Response responseCreateBooking = Util.createBooking(requestBody,requestSpec);
        responseCreateBooking.prettyPrint();
        log.info("Status Code: "+ responseCreateBooking.getStatusCode());
        Assert.assertEquals(responseCreateBooking.getStatusCode(),200,"Create Booking Failed");
        log.info("Booking ID: "+ responseCreateBooking.jsonPath().getString("bookingid") + " successfully created.");
        String bookingID = responseCreateBooking.jsonPath().getString("bookingid");

        //Delete
        Response responseDeleteBooking = RestAssured.given().auth().preemptive().basic("admin","password123")
                .delete(path + "/" + bookingID);
        log.info("Delete response "+ responseDeleteBooking.prettyPrint());
        Assert.assertEquals(responseDeleteBooking.getStatusCode(),201,"Delete Booking Failed");
    }
}
