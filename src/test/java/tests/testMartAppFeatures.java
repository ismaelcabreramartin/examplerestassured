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

        List<CartService.ProductWithImages> productWithImagesList = new ArrayList<>();

        int userId = 30;

        String[] addImage = {"https://i.dummyjson.com/data/products/11/1.jpg"
                , "https://i.dummyjson.com/data/products/12/1.jpg"
                , "https://i.dummyjson.com/data/products/13/1.jpg"
                , "https://i.dummyjson.com/data/products/14/1.jpg"
                , "https://i.dummyjson.com/data/products/15/1.jpg"};

        //Get all carts
        containAllCarts = cartService.getCartsApi();

        int total = containAllCarts.getLimit();

        logger.info("Validate if there is at least 1 cart. If so it will look for userIds");
        if (total == 0) {
            logger.info("There is no cart to display");
        } else {

            /**********************************************************************************************/
            /******  The code is failing into cartService.getAddProductImagesToUserCart(...)         ******/
            /******  The code is failing here because cannot cast to lass basetest.CartService$Cart  ******/
            /**********************************************************************************************/
            /*productWithImagesList =*/ cartService.getAddProductImagesToUserCart(cartService.getCartsUserId(userId), addImage);

        }


    }
}
