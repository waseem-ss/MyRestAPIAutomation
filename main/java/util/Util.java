package util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Util {


    /*
     * Parameter: String path
     * Returns: Iterator for Array of Objects
     * */
    public static Iterator<Object[]> csvReader(String path) {
        List<Object[]> list = new ArrayList<Object[]>();
        //Reading from the CSV file
        File file = new File(path);
        try {
            CSVReader csvReader = new CSVReader(new FileReader(file));
            String[] keys = csvReader.readNext();
            if (keys != null) {
                String[] data;
                while ((data = csvReader.readNext()) != null) {
                    Map<String, String> testData = new HashMap<>();
                    //System.out.println("data :" + data);
                    for (int i = 0; i < keys.length; i++) {
                        System.out.println("Iteration = " + i);
                        System.out.println("key[" + i + "]" + keys[i] + ", data[" + i + "]" + data[i]);
                        testData.put(keys[i], data[i]);
                    }
                    System.out.println("Test Data added to the List " + testData);
                    list.add(new Object[]{testData});
                }
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Number of entries added to the List " + list.stream().count());
        return list.iterator();
    }

    public static JSONObject getRequestBody(Map<String,Object>testData) {
        //log.info("testData" + testData);
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", testData.get("firstname"));
        requestBody.put("lastname", testData.get("lastname"));
        requestBody.put("totalprice", testData.get("totalprice"));
        requestBody.put("depositpaid", testData.get("depositpaid"));

        //Creating the booking dates object
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", testData.get("checkin"));
        bookingDates.put("checkout", testData.get("checkout"));
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", testData.get("additionalneeds"));
        return requestBody;
    }

    public static Response createBooking(JSONObject requestBody, RequestSpecification spec) {

       return RestAssured.given(spec).contentType(ContentType.JSON).
                body(requestBody.toString()).post("booking");
    }
}
