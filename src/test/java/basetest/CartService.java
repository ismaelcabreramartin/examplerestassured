package basetest;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CartService extends BaseTest{

    private Gson gson = new Gson();

    private int total;

    public CartList getCartsApi() {

        logger.info("Calling carts's API");

        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/carts")
                .then()
                    .assertThat().statusCode(200)
                    .extract().body().asString();

        CartList cartsResponse = gson.fromJson(res, CartList.class);

        return cartsResponse;
    }

    public String getCartId(int cartId) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API to look a specific cart");

        String res;

        try {
            res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/carts/" + cartId)
                .then()
                    .assertThat().statusCode(200)
                    .extract().body().asString();

            return res;

        } catch (AssertionError assertionError) {
            //assertionError.printStackTrace();
            logger.error("API .../carts/{cartId} has failed " + assertionError.getMessage());

            return "This " + cartId + " cartId does NOT exist in the database!";
        }
    }

    public String getCartsUserId(Integer userId) {

        System.out.println("*********************************************************************************");

        String res;

        logger.info("Calling API to look userId");

        try {
            res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/carts/user/" + userId)
                .then()
                    .assertThat().statusCode(200)
                    .extract().body().asString();

            return res;

        } catch (Exception assertionError) {
            //assertionError.printStackTrace();
            logger.error("API .../carts/user/{userIds} has failed" + assertionError.getMessage());

            return "This " + userId + " id does NOT exist in the database!";
        }

    }

    public void printCarts(CartList containAllCarts) {

        logger.info("Next 4 lines are going to print the info coming from Users API");
        logger.info("Display current Total: " + containAllCarts.getTotal());
        logger.info("Display current Skip: " + containAllCarts.getSkip());
        logger.info("Display current Limit: " + containAllCarts.getLimit());
        logger.info("Display all current users: " + gson.toJson(containAllCarts.getCarts()));

    }

    public Cart getCartWithHighestTotal(CartList containAllCarts) {

        logger.info("Checking the highest Total value from the carts list");

        List<Cart> cart = containAllCarts.carts;

        total = cart.size();

        Cart highestCart = (cart.get(0));

        for(int i = 1; i < total; i++) {

            if (cart.get(i).total > highestCart.total) {
                highestCart = cart.get(i);
            }

        }

        return highestCart;
    }

    public Cart getCartWithLowestTotal(CartList containAllCarts) {

        logger.info("Checking the lowest Total value from the carts list");

        List<Cart> cart = containAllCarts.carts;

        total = cart.size();

        Cart lowesttCart = (cart.get(0));

        for(int i = 1; i < total; i++) {

            if (cart.get(i).total < lowesttCart.total) {
                lowesttCart = cart.get(i);
            }

        }

        return lowesttCart;
    }

    public List<ProductWithImages> getAddProductImagesToUserCart(CartList containAllCarts, String[] addImages) {

        total = containAllCarts.getLimit();

        logger.info("Validate if the user has a cart. If so it will continue the testing");
        if (total == 0) {
            logger.info("There is no userId with cart in the database!");
            List<ProductWithImages> empty = new ArrayList<>();
            return empty;
        } else {

            //CartList containAllCarts = gson.fromJson(total, CartList.class);
            String stringTempCart = gson.toJson(containAllCarts.getCarts());

            /***I have facing issues to convert the previous String to Cart class, so I have figure out ***/
            /***another solution removing the first [ and last ] working with String, then I can convert it to an object***/
            //At this moment I can convert the object into Product list*/
            List<Product> product  = removeSquareBrackets(stringTempCart);

            List<ProductWithImages> productWithImagesList = new ArrayList<>();

            for(int i = 0; i < product.size(); i++) {
                String getProduct = gson.toJson(product.get(i));

                //Add product's image at the end of the elements of the list (currently is a string)
                getProduct = getProduct.substring(0, getProduct.length()-1) + ",\"image\":\"" + addImages[i] + "\"}";

                //Add the new String into the productWithImagesList
                productWithImagesList.add(gson.fromJson(getProduct, ProductWithImages.class));
            }
            return productWithImagesList;
        }
    }

    public List<Product> removeSquareBrackets(String squareBrackets) {

        squareBrackets = squareBrackets.substring(1, squareBrackets.length());
        squareBrackets = squareBrackets.substring(0, squareBrackets.length()-1);

        // Now I can extract Products list and put it into a Product Object
        Map<Cart, Object> cart = gson.fromJson(squareBrackets, Map.class);

        //At this moment I can convert the object into Product list
        List<Product> product  = (List<Product>) cart.get("products");

        return product;
    }

    @Getter
    @Setter
    public class CartList {
        private List<Cart> carts;
        private int total;
        private int skip;
        private int limit;

        // getters and setters
    }

    @Getter
    @Setter
    public class Cart {
        private int id;
        private List<Product> products;
        private int total;
        private int discountedTotal;
        private int userId;
        private int totalProducts;
        private int totalQuantity;
    }

    @Getter
    @Setter
    public class Product {
        private int id;
        private String title;
        private int price;
        private int quantity;
        private int total;
        private double discountPercentage;
        private int discountedPrice;
    }

    @Getter
    @Setter
    public class ProductWithImages {
        private int id;
        private String title;
        private int price;
        private int quantity;
        private int total;
        private double discountPercentage;
        private int discountedPrice;
        private String image;
    }
}
