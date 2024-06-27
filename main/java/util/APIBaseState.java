package util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

public class APIBaseState  {
    protected Logger log ;
    protected RequestSpecification requestSpec;
    protected RequestSpecBuilder requestSpecBuilder;
   // protected Util util;
    protected SoftAssert softAssert;
    @BeforeMethod(alwaysRun = true)
    public void setUp (ITestContext ctx){
        this.log = LogManager.getLogger(ctx.getCurrentXmlTest().getName());
        log.info("Begin Test Method " + ctx.getCurrentXmlTest().getClass());
        this.softAssert = new SoftAssert();
        /*requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com/")
                .build();*/
        requestSpec = new RequestSpecBuilder()
                .addHeader("Content-Type","application/json")
                .addHeader("Accept","application/xml")
                //.setContentType(ContentType.JSON)
                .setBaseUri("https://restful-booker.herokuapp.com/")
                .build();
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        log.info("Tear Down");
    }

}
