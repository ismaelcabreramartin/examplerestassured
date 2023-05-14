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

    @Test
    public void getAllUsers() {

        //Get all users
        getTotalUsers();

        logger.info("Validate if there is at least 1 user. If so it will show all users");
        if (total == 0) {
            logger.info("There is no user and we cannot show any user");
        } else {

            logger.info("Print all users");
            printUsers(containAllUsers);

        }
    }

    @Test
    public void getUser() {

        int userId = 20;

        //Get all users
        getTotalUsers();

        logger.info("Validate if there is at least 1 user. If so it will show the user");
        if (total == 0) {
            logger.info("There is no user and we cannot show any user");
        } else {

            logger.info("Print the specific product: " + getUserId(userId));
        }
    }

    @Test
    public void searchUsers() {

        String user = "John";

        containAllUsers = getSearchUsers(user);

        total = containAllUsers.getLimit();

        logger.info("Validate if there is at least 1 user. If so it will show the user");
        if (total == 0) {
            logger.info("There is no user to display");
        } else {

            Gson gson = new Gson();
            logger.info("we have found out the following user: " + gson.toJson(containAllUsers.getUsers()));
        }
    }

    public void getTotalUsers() {
        containAllUsers = getUsersApi();

        total = containAllUsers.getLimit();
    }
}
