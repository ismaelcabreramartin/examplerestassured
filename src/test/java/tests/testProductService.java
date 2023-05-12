package tests;

import basetest.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class testProductService extends ProductService{

    static Logger logger = LoggerFactory.getLogger(Logger.class);
    ProductsResponse containAllProducts;
    int total;
    int randomValue;

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
        //VERIFICAR IF I CREATE A NEW TEST
        getLimitSkipProducts(10, 5, "title", "price", "rating");
    }

    @Test
    public void getProduct() {

        //Get all products
        getTotalProducts();

        logger.info("Validate if there is at least 1 product. If so it will show all products. " + total);
        if (total == 0) {
            logger.info("There is no product to display");
        } else {

            randomValue = getRandomValueFromList(total, 1);

            logger.info("Print the specific product: " + getProductId(randomValue));
        }
    }

    @Test
    public void searchProducts() {

        List<String> brands = getBrand();

        randomValue = getRandomValueFromList(brands.size(), 1);

        containAllProducts = getSearchProducts(brands.get(randomValue));

        total = containAllProducts.getLimit();

        logger.info("Validate if there is at least 1 product according to searchProduct. " + total);
        if (total == 0) {
            System.out.println("There is no product to display");
        } else {

            logger.info("Print all products according to the searchProduct");
            printProducts(containAllProducts);
        }

    }

    @Test
    public void getCategories() throws JsonProcessingException {

        //Get all category names
        getTotalCategoryNames();

        logger.info("Validate is there is at least 1 category. " + total);
        if (total == 0) {
            System.out.println("There is no category coming from any product");
        } else {

            logger.info("Print all categories from the products");
            logger.info("Categories: " + categories);

        }
    }

    @Test
    public void getProductsByCategory() throws JsonProcessingException {

        //Get all category names
        getTotalCategoryNames();

        logger.info("Validate is there is at least 1 category. " + total);
        if (total == 0) {
            System.out.println("There is no category coming from any product");
        } else {

            randomValue = getRandomValueFromList(total, 1);

            containAllProducts = getListCategory(categories.get(randomValue));

            logger.info("Print all products according to the category");
            printProducts(containAllProducts);

        }
    }

    public void getTotalProducts() {

        containAllProducts = getProductsApi();

        total = containAllProducts.getLimit();
    }

    public void getTotalCategoryNames() throws JsonProcessingException {

        categories = getCategory();

        total = categories.size();
    }
}
