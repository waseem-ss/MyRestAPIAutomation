package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.APIBaseState;
import util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GetBookingTest extends APIBaseState {
    //protected Logger log;
    @Test
    public void getBookingAll() {
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking");
        response.prettyPrint();

        //log = LogManager.getLogger(ctx.getCurrentXmlTest().getName());
        log.info("Status Code : " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Get bookings is successful");
    }

    @Test
    public void getBookingWithFilter() {
        Response response ; //RestAssured.get("https://restful-booker.herokuapp.com/booking");
        //response.prettyPrint();

        //log = LogManager.getLogger(ctx.getCurrentXmlTest().getName());
        //log.info("Status Code : " + response.getStatusCode());
        //Assert.assertEquals(response.getStatusCode(), 200, "Get bookings is successful");
        log.info("The Filter out put");
        response = RestAssured.given(requestSpec)
                .get("/booking?firstname=Mary");
        Assert.assertEquals(response.getStatusCode(), 200, "Get booking with filter failed");
        response.prettyPrint();
        // Loop and print the Array of the response
        System.out.println(response.jsonPath().getString("bookingid"));
        List<Integer> listIDs = new ArrayList<>();
        listIDs = response.jsonPath().getList("bookingid");
        System.out.println("listIDs " + listIDs.getFirst() + listIDs.size() + listIDs.getLast());
        for(Integer id : listIDs){
            System.out.println("Query for booking ID: " + id);
            response = RestAssured.given(requestSpec).get("booking/" + id);
            response.prettyPrint();
            Assert.assertEquals(response.getStatusCode(),200,"Request Failed");
            Assert.assertEquals(response.xmlPath().getString("booking.firstname"), "Mary",
                    "First Name is incorrect");
        }
    }

    @Parameters({"id"})
    @Test
    public void getBookingByID(String id) {
        log.info("Retrieving the booking for Id: " + id);
        //String url = "https://restful-booker.herokuapp.com/booking/" + id;
        log.info("Invoke get booking by Id " + requestSpec.toString() + id);
        Response response = RestAssured.given(requestSpec).header("Accept", "application/xml")
                .get("booking/" + id);//RestAssured.get(url);
        response.prettyPrint();
        log.info("Status Code " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Get bookings is not successful for ID" + id);
        //converted to xml path
        String fName = response.xmlPath().getString("booking.firstname");//response.jsonPath().getString("firstname");
        /*String lName = response.jsonPath().getString("lastname");
        Integer totalPrice = response.jsonPath().getInt("totalprice");
        Boolean depositPaid = response.jsonPath().getBoolean("depositpaid");
        String checkInDate = response.jsonPath().getString("bookingdates.checkin");
        String checkOutDate = response.jsonPath().getString("bookingdates.checkout");*/

        log.info(fName);
        softAssert.assertEquals(fName, "Eric", "First name is as expected:" + fName);

        /*softAssert.assertEquals(lName,"Jones", "Last name is as expected:" +lName);
        softAssert.assertTrue(totalPrice==417, "Total price is as expected:" + totalPrice);
        softAssert.assertFalse(depositPaid, "Deposit paid value is as expected:" + depositPaid);
        softAssert.assertEquals(checkInDate,"2015-02-25", "Checkin Date is as expected:" + checkInDate);
        softAssert.assertEquals(checkOutDate,"2016-05-07", "Checkin Date is as expected:" + checkOutDate);*/

        softAssert.assertAll();
    }

    @Test(dataProvider = "Ids")
    public void getBookingByDataProviderID(String Id) {
        log.info("The Id to retrieve " + Id);
        //String url = "https://restful-booker.herokuapp.com/booking/" + Id;
        log.info("Invoke get booking by Id " + requestSpec.toString() + Id);
        Response response = RestAssured.given(requestSpec).get(Id);
        response.prettyPrint();
        log.info("Status Code " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Request failed");
    }

    @DataProvider(name = "Ids")
    public Object[][] createIds() {
        return new Object[][]{
                {"1"},
                {"2"},
                {"3"}
        };
    }

    @Test(dataProvider = "csvReader")
    public void getBookingIdCsvDataProvider(Map<String, String> testData) {
        String bookingID = testData.get("Id");
        String fName = testData.get("fName");
        String lName = testData.get("lName");
        log.info("{id: " + bookingID + "},{ fName : " + fName + "},{ " + "lNAme " + lName + "}");
        //String url = "https://restful-booker.herokuapp.com/booking/" + bookingID;
        log.info("Invoke get booking by Id " + requestSpec.toString() + bookingID);
        Response response = RestAssured.given(requestSpec).get("/" + bookingID);
        response.prettyPrint();
        String fNameResponse = response.jsonPath().getString("firstname");
        String lNameResponse = response.jsonPath().getString("lastname");
        softAssert.assertEquals(fNameResponse, fName, "First Name match failed");
        softAssert.assertEquals(lNameResponse, lName, "Last Name match failed");
        softAssert.assertAll();
    }

    @DataProvider(name = "csvReader")
    public Iterator<Object[]> expectedResponse() {
        String path = "src/test/resources/Data_provider.csv";
        return Util.csvReader(path);
    }
    /*
    @DataProvider(name = "createBookings")
    public Iterator<Object[]> createBookings(){

    }*/
    @Parameters({"Id"})
    @Test
    public void getAllBookingsV1( String Id){
        Response response = RestAssured.given().get("https://restful-booker.herokuapp.com/booking");
        System.out.println(response.prettyPrint());
        response = RestAssured.given().get("https://restful-booker.herokuapp.com/booking/" + Id);
        System.out.println("Booking for ID: "+ Id +"\n"+ response.prettyPrint());
    }


}

