package tests;

import org.junit.jupiter.api.Test;

import basetest.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class testMartAppFeatures {

    private static ProductService productService = new ProductService();
    static Logger logger = LoggerFactory.getLogger(Logger.class);

    String response;
    ProductService.ProductsResponse containAllProducts;
    int total;
    int randomValue;

    @Test
    public void testGetProductTitlesByWorseRating(){
        double rating = 4.15;

        //Get all products

        containAllProducts = productService.getProductsApi();

        logger.info("The products that have a rating less than or equal than... " + rating + " are: ");
        productService.getProductTitlesByWorseRating(containAllProducts, rating);

    }
}
