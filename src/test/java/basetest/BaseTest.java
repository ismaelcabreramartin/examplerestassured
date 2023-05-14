package basetest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseTest {

    static Logger logger = LoggerFactory.getLogger(Logger.class);

    protected static RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("https://dummyjson.com")
            .build();;

    @BeforeAll
    public static void setUp() {

        logger.info("Starting the test");
        System.out.println("STARTING THE TESTING");

    }


}
