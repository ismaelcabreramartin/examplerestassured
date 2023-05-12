package basetest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductService extends BaseTest{

    private Gson gson = new Gson();
    public ProductsResponse getProductsApi() {

        logger.info("Calling API products");

        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/products")
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        ProductsResponse productsResponse = gson.fromJson(res, ProductsResponse.class);

        return productsResponse;
    }

    public void getLimitSkipProducts(int limit, int skip, String title, String price, String rating) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API products with parameters and showing the results: ");
        // Get the products and display some names coming from the products according to the parameters
        given(spec)
                .contentType(ContentType.JSON)
            .when()
                .get("/products?limit=" + limit +"&skip=" + skip + "&select=" + title + "," + price + "," + rating)
            .then()
                .statusCode(200)
                .log().body();
    }

    public Product getProductId(int productId) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API for looking a specific productId");
        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/products/" + productId)
                .then()
                    .statusCode(200)
                    .extract().body().asString();

       Product product = gson.fromJson(res, Product.class);

       return product;
    }

    public ProductsResponse getSearchProducts(String product) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API to seach for a specific word: " + product);

        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/products/search?q=" + product)
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        ProductsResponse productsResponse = gson.fromJson(res, ProductsResponse.class);

        return productsResponse;
    }
    public List<String> getCategory() throws JsonProcessingException {

        System.out.println("*********************************************************************************");

        logger.info("Calling API to show the categories");

        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/products/categories")
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        List<String> response = gson.fromJson(res, List.class);

        return response;

    }

    public ProductsResponse getListCategory(String category) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API to show the products for specific category: " + category);
        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/products/category/" + category)
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        ProductsResponse productsResponse = gson.fromJson(res, ProductsResponse.class);

        return productsResponse;

    }

    public void printProducts(ProductsResponse containAllProducts) {

        logger.info("Next 4 lines are going to print the info coming from Products API");
        logger.info("Display current Total: " + containAllProducts.getTotal());
        logger.info("Display current Skip: " + containAllProducts.getSkip());
        logger.info("Display current Limit: " + containAllProducts.getLimit());
        logger.info("Display all current products: " + gson.toJson(containAllProducts.getProducts()));

    }

    public void getProductsApiTest() {

        logger.info("Calling API products");

        String responseBody =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/products")
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        ProductsResponse productsResponse = gson.fromJson(responseBody, ProductsResponse.class);

        List<Product> product = productsResponse.products;
    }
    public void getProductTitlesByWorseRating(ProductsResponse containAllProducts, double rating){

        int total = containAllProducts.getLimit();

        if (total == 0) {
            logger.error("There is no product in the products API");
        } else {

            List<Product> products = containAllProducts.products;

            for (int i = 0; i < (total-1) ; i++){

                if (products.get(i).getRating() <= rating) {
                    logger.info("product " + containAllProducts.products.get(i).getTitle()
                    + " has a rating " + containAllProducts.products.get(i).getRating());
                }
            }
        }

    }


    public List<String> getBrand() {

        List<String> brand = new ArrayList<>();
        //Add values
        brand.add("phone");
        brand.add("iphone");
        brand.add("Samsung");
        brand.add("Huawei");
        brand.add("Apple");
        brand.add("laptops");

        return brand;
    }

    @Getter
    @Setter
    public class ProductsResponse {
        private List<Product> products;
        private int total;
        private int skip;
        private int limit;
    }

    @Getter
    @Setter
    public class Product {

        private int id;
        private String title;
        private String description;
        private double price;
        private double discountPercentage;
        private double rating;
        private int stock;
        private String brand;
        private String category;
        private String thumbnail;
        private List<String> images;
    }
}
