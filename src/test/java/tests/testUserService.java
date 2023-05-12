package tests;

import basetest.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class testUserService extends UserService {

    static Logger logger = LoggerFactory.getLogger(Logger.class);
    UsersResponse containAllUsers;
    int total;
    int randomValue;

    @Test
    public void getAllUsers() {

        //Get all users
        getTotalUsers();

        logger.info("Validate if there is at least 1 user. If so it will show all users. ");
        if (total == 0) {
            logger.info("There is no user and we cannot show any user");
        } else {

            logger.info("Print all users");
            printUsers(containAllUsers);

        }
    }

    @Test
    public void getUser() {

        //Get all users
        getAllUsers();

        logger.info("Validate if there is at least 1 user. If so it will show all users. ");
        if (total == 0) {
            logger.info("There is no user and we cannot show any user");
        } else {

            randomValue = getRandomValueFromList(total, 1);

            logger.info("Print the specific product: " + getUserId(randomValue));
        }
    }

    @Test
    public void searchUsers() {

        String user = "John";

        logger.info("It is going to search user: " + user);

        containAllUsers = getSearchUsers(user);

        total = containAllUsers.getLimit();

        if (total == 0) {
            logger.info("There is no user to display");
        } else {

            Gson gson = new Gson();
            logger.info("we have found out the following user/s: " + gson.toJson(containAllUsers.getUsers()));
        }
    }

    public void getTotalUsers() {
        containAllUsers = getUsersApi();

        total = containAllUsers.getLimit();
    }
}
