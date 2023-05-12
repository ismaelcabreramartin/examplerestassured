package tests;

import basetest.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class testProductService extends ProductService{

    static Logger logger = LoggerFactory.getLogger(Logger.class);

    JsonPath response;
    ProductsResponse containAllProducts;
    int total;
    int randomValue;

    @Test
    public void testGetProductTitlesByWorseRating(){
        double rating = 4;

        //Get all products

        getProductsApiTest();

        /*response = getProductsApi();
        containAllProducts = getAllProducts(response);

        logger.info("The products that have a rating less than or equal than " + rating + " are: ");
        getProductTitlesByWorseRating(containAllProducts, rating);*/

    }
    @Test
    public void testProducts() {

        //Get all products
        response = getProductsApi();

        containAllProducts = getAllProducts(response);

        total = containAllProducts.getLimit();

        logger.info("Validate if there is at least 1 product. If so it will show all products. " + total);
        if (total == 0) {
            logger.info("There is no product and we cannot show any product");
        } else {

            //Print all products
            printProducts(containAllProducts);

            randomValue = getRandomValueFromList(total, 1);

            logger.info("Print the specific product: " + getProductId(randomValue));
        }
    }

    @Test
    public void testProductsWithParameters() {
        //VERIFICAR IF I CREATE A NEW TEST
        getLimitSkipProducts(10, 5, "title", "price", "rating");
    }

    @Test
    public void testSearchProducts() {

        List<String> brands = getBrand();

        randomValue = getRandomValueFromList(brands.size(), 1);

        response = getSearchProducts(brands.get(randomValue));

        containAllProducts = getAllProducts(response);

        total = containAllProducts.getLimit();

        logger.info("Validate if there is at least 1 product according to searchProduct. " + total);
        if (total == 0) {
            System.out.println("There is no product and we cannot show any product");
        } else {

            logger.info("Print all products according to the searchProduct");
            printProducts(containAllProducts);
        }

    }

    @Test
    public void testCategories() throws JsonProcessingException {

        List<String> categories = getCategory();

        total = categories.size();

        logger.info("Validate is there is at least 1 category. " + total);
        if (total == 0) {
            System.out.println("There is no category coming from any product");
        } else {

            randomValue = getRandomValueFromList(total, 1);

            response = getListCategory(categories.get(randomValue));

            containAllProducts = getAllProducts(response);
            //total = containAllProducts.getLimit();

            logger.info("Print all products according to the category");
            printProducts(containAllProducts);

        }

    }
}
