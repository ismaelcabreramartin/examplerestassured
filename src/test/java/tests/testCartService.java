package tests;

import basetest.CartService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class testCartService extends CartService {

    static Logger logger = LoggerFactory.getLogger(Logger.class);
    CartList containAllUsers;
    int total;
    int randomValue;

    @Test
    public void getAllCarts() {

        //Get all carts
        getTotalCarts();

        logger.info("Validate if there is at least 1 cart. If so it will show all carts. ");
        if (total == 0) {
            logger.info("There is no cart and we cannot show any cart");
        } else {

            logger.info("Print all carts");
            printCarts(containAllUsers);

        }
    }

    @Test
    public void getCart() {

        //Get all carts
        getTotalCarts();

        logger.info("Validate if there is at least 1 cart. If so it will show all carts. ");
        if (total == 0) {
            logger.info("There is no cart and we cannot show any cart");
        } else {

            randomValue = getRandomValueFromList(total, 1);

            logger.info("Print the specific cart: " + getCartId(randomValue));
        }
    }

    @Test
    public void getUserCarts() {

        //Get all carts
        getTotalCarts();

        logger.info("Validate if there is at least 1 cart. If so it will look for userIds");
        if (total == 0) {
            logger.info("There is no cart to display");
        } else {

            randomValue = getRandomValueFromList(total, 1);
            logger.info("Print the specific cart: " + getCartsUserId(randomValue, containAllUsers));
        }

    }

    public void getTotalCarts() {

        containAllUsers = getCartsApi();

        total = containAllUsers.getLimit();
    }
}
