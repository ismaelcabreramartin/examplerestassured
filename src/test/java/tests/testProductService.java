package tests;

import basetest.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class testProductService extends ProductService{

    static Logger logger = LoggerFactory.getLogger(Logger.class);
    ProductsResponse containAllProducts;
    int total;
    List<String> categories;

    @Test
    public void getAllProducts() {

        //Get all products
        getTotalProducts();

        logger.info("Validate if there is at least 1 product. If so it will show all products. " + total);
        if (total == 0) {
            logger.info("There is no product to display");
        } else {

            //Print all products
            printProducts(containAllProducts);
        }
    }

    @Test
    public void getAllProductsWithParameters() {
        logger.info("Entered specific parameters");
        getLimitSkipProducts(10, 5, "title", "price", "rating");
    }

    @Test
    public void getProduct() {

        int productId = 20;

        //Get all products
        getTotalProducts();

        logger.info("Validate if there is at least 1 product. If so it will show all products. " + total);
        if (total == 0) {
            logger.info("There is no product to display");
        } else {

            Gson gson = new Gson();

            logger.info("Print the specific product: " + gson.toJson(getProductId(productId)));
        }
    }

    @Test
    public void searchProducts() {

        containAllProducts = getSearchProducts("mentira");

        total = containAllProducts.getLimit();

        logger.info("Validate if there is at least 1 product according to searchProduct");
        if (total == 0) {
            logger.info("There is no product to display");
        } else {

            logger.info("Print all products according to the searchProduct");
            printProducts(containAllProducts);
        }

    }

    @Test
    public void getCategories() throws JsonProcessingException {

        //Get all category names
        categories = getCategory();

        total = categories.size();

        logger.info("Validate if there is at least 1 category. ");
        if (total == 0) {
            logger.info("There is no category coming from any product");
        } else {

            logger.info("Print all categories from the products");
            logger.info("Categories: " + categories);

        }
    }

    @Test
    public void getProductsByCategory() throws JsonProcessingException {

        String category = "smartphones";

        containAllProducts = getListCategory(category);

        total = containAllProducts.getLimit();

        logger.info("Validate if there is at least 1 category. ");
        if (total == 0) {
            logger.info("There is no category coming from any product");
        } else {

            logger.info("Print all products according to the category");
            printProducts(containAllProducts);
        }

    }

    public void getTotalProducts() {

        containAllProducts = getProductsApi();

        total = containAllProducts.getLimit();
    }
}
