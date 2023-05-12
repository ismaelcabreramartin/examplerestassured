package basetest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;


public class BaseTest {

    static Logger logger = LoggerFactory.getLogger(Logger.class);
    protected static RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .build();;

    @BeforeAll
    public static void setUp() {

        logger.info("Starting the TESTING");

    }

    public int getRandomValueFromList(int max, int min) {

        Random random = new Random();

        //List starts by 0 so we need to add '-1' in the formula
        int value = (random.nextInt(max - min) + 1) - 1;
        logger.info("Getting a random value: " + value);

        return value;
    }

}
