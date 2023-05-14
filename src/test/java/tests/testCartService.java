package tests;

import basetest.CartService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class testCartService extends CartService {

    static Logger logger = LoggerFactory.getLogger(Logger.class);
    CartList containAllUsers;
    int total;

    @Test
    public void getAllCarts() {

        //Get all carts
        getTotalCarts();

        logger.info("Validate if there is at least 1 cart. If so it will show all carts. ");
        if (total == 0) {
            logger.error("There is no cart and we cannot show any cart");

            //It is going to throw an error to fail the testing
            throw new IllegalMonitorStateException ("API returned no data");
        } else {

            logger.info("Print all carts");
            printCarts(containAllUsers);

        }
    }

    @Test
    public void getCart() {

        int cartId = 15;

        //Get all carts
        getTotalCarts();

        logger.info("Validate if there is at least 1 cart. If so it will show all carts. ");
        if (total == 0) {
            logger.error("There is no cart and we cannot show any cart");

            //It is going to throw an error to fail the testing
            throw new IllegalMonitorStateException ("API returned no data");
        } else {

            logger.info("Print the specific cart: " + getCartId(cartId));
        }
    }

    @Test
    public void getUserCarts() {

        int userId = 15;

        //Get all carts
        getTotalCarts();

        logger.info("Validate if there is at least 1 cart. If so it will look for userIds");
        if (total == 0) {
            logger.error("There is no cart to display");

            //It is going to throw an error to fail the testing
            throw new IllegalMonitorStateException ("API returned no data");
        } else {

            logger.info("Print the specific cart: " + getCartsUserId(userId));
        }

    }

    public void getTotalCarts() {

        containAllUsers = getCartsApi();

        total = containAllUsers.getLimit();
    }
}
