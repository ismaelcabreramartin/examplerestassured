package basetest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseTest {

    static Logger logger = LoggerFactory.getLogger(Logger.class);
    protected static RequestSpecification spec;

    @BeforeAll
    public static void setUp() {

        logger.info("Starting the TESTING");

        logger.info("Set up the base URL");
        spec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .build();
    }

    public int getRandomValueFromList(int max, int min) {

        Random random = new Random();

        //List starts by 0 so we need to add '-1' in the formula
        int value = (random.nextInt(max - min) + 1) - 1;
        logger.info("Getting a random value: " + value);

        return value;
    }

}
