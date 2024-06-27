package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.APIBaseState;

public class FilterBookingTest extends APIBaseState {
    @Parameters({"fName"})
    @Test
    public void filterByFirstName (String fName){
        Response response = RestAssured.given(requestSpec).cookie("Test", "Test")
                .get("booking?firstname=" + fName);
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200,"Query Failed");
        log.info("Headers: " + response.getHeaders());
        log.info("Header server: " + response.getHeader("Server"));
        log.info("Cookies: " + response.getCookies());
        log.info("Test values" + response.getCookie("Test"));
    }
}
