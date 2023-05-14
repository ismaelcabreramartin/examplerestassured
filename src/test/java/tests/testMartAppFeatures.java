package tests;

import basetest.CartService;
import basetest.ProductService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class testMartAppFeatures {

    private static ProductService productService = new ProductService();
    private static CartService cartService = new CartService();
    static Logger logger = LoggerFactory.getLogger(Logger.class);

    private Gson gson = new Gson();

    String response;
    ProductService.ProductsResponse containAllProducts;
    CartService.CartList containAllCarts;

    @Test
    public void testGetProductTitlesByWorseRating(){

        double rating = 4.15;

        //Get all products
        containAllProducts = productService.getProductsApi();

        logger.info("The products that have a rating less than or equal than... " + rating + " are: ");
        productService.getProductTitlesByWorseRating(containAllProducts, rating);

    }

    @Test
    public void testGetCartWithHighestTotal() {

        //Get all carts
        containAllCarts = cartService.getCartsApi();

        gson = new Gson();

        logger.info("This is the cart with the highest total value:  " + gson.toJson(cartService.getCartWithHighestTotal(containAllCarts)));
    }

    @Test
    public void testGetCartWithLowestTotal() {

        //Get all carts
        containAllCarts = cartService.getCartsApi();

        gson = new Gson();

        logger.info("This is the cart with the lowest total value:  " + gson.toJson(cartService.getCartWithLowestTotal(containAllCarts)));
    }

    @Test
    public void testAddProductImagesToUserCart() {

        int userId = 30;

        String[] addImage = {"https://i.dummyjson.com/data/products/11/1.jpg"
                , "https://i.dummyjson.com/data/products/12/1.jpg"
                , "https://i.dummyjson.com/data/products/13/1.jpg"
                , "https://i.dummyjson.com/data/products/14/1.jpg"
                , "https://i.dummyjson.com/data/products/15/1.jpg"};

        List<CartService.ProductWithImages> productWithImagesList = new ArrayList<>();

        //Get all carts
        containAllCarts = cartService.getCartsApi();

        int total = containAllCarts.getLimit();

        logger.info("Validate if there is at least 1 cart. If so it will look for userIds");
        if (total == 0) {
            logger.error("There is no cart to display");

            //It is going to throw an error to fail the testing
            throw new IllegalMonitorStateException ("API returned no data");
        } else {

            //This method will return if the userId is in the database
            CartService.CartList containAllCarts = gson.fromJson(cartService.getCartsUserId(userId), CartService.CartList.class);

            //This method will return the products with images or without nothing if the userId is not in the database
            productWithImagesList = cartService.getAddProductImagesToUserCart(containAllCarts, addImage);

            logger.info("Print user's product: " + gson.toJson(productWithImagesList));

        }

    }
}
