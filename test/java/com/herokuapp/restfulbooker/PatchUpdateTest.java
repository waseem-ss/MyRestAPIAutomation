package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.APIBaseState;
import util.Util;

import java.util.HashMap;
import java.util.Map;

public class PatchUpdateTest extends APIBaseState {

    @Test
    public void patchUpdateTest(){
        //Create a new Booking
        Map<String,Object> testData = new HashMap<>();
        testData.put("firstname","Mike");
        testData.put("lastname","Tyson");
        testData.put("totalprice",200);
        testData.put("depositpaid",false);
        testData.put("checkin","2024-04-24");
        testData.put("checkout","2024-05-08");
        testData.put("additionalneeds", "nothing");
        log.info("testData" + testData);
        JSONObject requestBody = new JSONObject(testData);
        //requestBody = Util.getRequestBody(testData);
        log.info("request body " + requestBody);
        String path ="https://restful-booker.herokuapp.com/booking";
        Response responseCreateBooking = Util.createBooking(requestBody,requestSpec);
        responseCreateBooking.prettyPrint();
        log.info("Status Code: "+ responseCreateBooking.getStatusCode());
        Assert.assertEquals(responseCreateBooking.getStatusCode(),200,"Create Booking Failed");
        log.info("Booking ID: "+ responseCreateBooking.jsonPath().getString("bookingid") + " successfully created.");
        String bookingID = responseCreateBooking.jsonPath().getString("bookingid");

        //Patch the same booking
        JSONObject requestPatchUpdate = new JSONObject();
        requestPatchUpdate.put("totalprice", 450);
        requestPatchUpdate.put("depositpaid", true);

        Response responsePatchUpdate = RestAssured.given().auth().preemptive().basic("admin","password123").
                contentType(ContentType.JSON).body(requestPatchUpdate.toString()).patch(path+"/"+ bookingID);
        responsePatchUpdate.prettyPrint();
        Assert.assertEquals(responsePatchUpdate.getStatusCode(),200,"Patch Update Booking Failed");
        softAssert.assertEquals(responsePatchUpdate.jsonPath().getInt("totalprice"),
                requestPatchUpdate.getInt("totalprice"),
                "totalprice update failed");
        softAssert.assertEquals(responsePatchUpdate.jsonPath().getBoolean("depositpaid"),
                requestPatchUpdate.getBoolean("depositpaid"),
                "Deposit paid update failed");
        softAssert.assertAll();




    }


}
