package tests;

import basetest.ProductService;
import basetest.UserService;
import io.restassured.path.json.JsonPath;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class testUserService extends UserService {

    static Logger logger = LoggerFactory.getLogger(Logger.class);
    JsonPath response;
    UsersResponse containAllUsers;
    int total;
    int randomValue;

    @Test
    public void testUsers() {

        //Get all users
        response = getUsersApi();

        containAllUsers = getAllUsers(response);

        total = containAllUsers.getLimit();

        logger.info("Validate if there is at least 1 user. If so it will show all users. " + total);
        if (total == 0) {
            logger.info("There is no user and we cannot show any user");
        } else {
            logger.info("Print all users");
            printUsers(containAllUsers);

            randomValue = getRandomValueFromList(total, 1);

            logger.info("Print the specific product: " + getUserId(randomValue));
        }
    }
}
