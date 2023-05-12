package basetest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProductService extends BaseTest{

    protected JsonPath getProductsApi() {

        logger.info("Calling API products");

        JsonPath res =
                given(spec)
                        .contentType(ContentType.JSON)
                        .when()
                        .get("/products")
                        .then()
                        .statusCode(200)
                        .extract().jsonPath();

        return res;
    }

    protected void getLimitSkipProducts(int limit, int skip, String title, String price, String rating) {

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

    protected String getProductId(int productId) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API for looking a specific productId");
        JsonPath res = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .get("/products/" + productId)
                .then()
                .statusCode(200)
                .extract().jsonPath();

       String product = res.get().toString();

        return product;
    }

    protected JsonPath getSearchProducts(String product) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API to seach for a specific word: " + product);

        JsonPath res = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .get("/products/search?q=" + product)
                .then()
                .statusCode(200)
                .extract().jsonPath();

        return res;
    }
    protected List<String> getCategory() throws JsonProcessingException {

        System.out.println("*********************************************************************************");

        logger.info("Calling API to show the categories");
        JsonPath res = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .get("/products/categories")
                .then()
                .statusCode(200)
                .extract().jsonPath();

        return res.get();

    }

    protected JsonPath getListCategory(String category) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API to show the products for specific category: " + category);
        JsonPath res = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .get("/products/category/" + category)
                .then()
                .statusCode(200)
                .extract().jsonPath();

        return res;

    }

    //This method takes the response and add the information into the classes
    // ProductsResponse and Products. Check at the end each clase
    protected ProductsResponse getAllProducts(JsonPath res) {

        logger.info("getAllProducts method...");

        Map map = res.get();

        List productsReturn = (List) map.get("products");

        ProductsResponse containAllProducts = new ProductsResponse();

        containAllProducts.setTotal((int) map.get("total"));
        containAllProducts.setSkip((int) map.get("skip"));
        containAllProducts.setLimit((int) map.get("limit"));

        containAllProducts.setProducts((List<Product>) productsReturn);

        return containAllProducts;
    }

    protected void printProducts(ProductsResponse containAllProducts) {

        logger.info("Next 4 lines are going to print the info coming from Products API");
        logger.info("Display current Total: " + containAllProducts.getTotal());
        logger.info("Display current Skip: " + containAllProducts.getSkip());
        logger.info("Display current Limit: " + containAllProducts.getLimit());
        logger.info("Display all current products: " + containAllProducts.getProducts());

    }


    protected void getProductsApiTest() {

        logger.info("Calling API products");


        Map <String, Object > responseBody = null;

        /*responseBody = given(spec)
                        .contentType(ContentType.JSON)
                        .when()
                        .get("/products")
                        .then()
                        .statusCode(200)
                        .extract().body().as(new TypeRef<Map<String, Object>>() {
                });*/

        JsonNode response = given(spec)
                        .contentType(ContentType.JSON)
                        .when()
                        .get("/products")
                        .then()
                        .statusCode(200)
                        .extract().body().as(JsonNode.class);

        ObjectMapper mapper = new ObjectMapper();
        ProductsResponse products = mapper.convertValue(
                response,
                new TypeReference<ProductsResponse>(){}
        );

        System.out.println("rest " + products);
        //List products = (List) responseBody.get("products");


        //System.out.println("rest " + products.get(0));

        /*for(Product p: products) {
            System.out.println("only products: " + products.get(0).getTitle());
        }*/

        //System.out.println("products: " + responseBody.get("products"));

        //Product p = (Product) products.get(0);

        //System.out.println("only products: " + ((Product) products.get(0)).getTitle());
        System.out.println("wait");




    }
    protected void getProductTitlesByWorseRating(ProductsResponse containAllProducts, double rating){

        int total = containAllProducts.getLimit();

        if (total == 0) {
            logger.error("There is no product in the products API");
        } else {


            //logger.info("product " + containAllProducts.getProducts().get(1).title);




            for (int i = 1; i < 30 ; i++){

                if (containAllProducts.products.get(i).getRating() <= rating) {
                    logger.info("product " + containAllProducts.products.get(i).getTitle()
                    + "has a rating " + containAllProducts.products.get(i).getRating());
                }
            }
        }

    }


    protected List<String> getBrand() {

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
