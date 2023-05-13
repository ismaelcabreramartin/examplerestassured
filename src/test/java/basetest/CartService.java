package basetest;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CartService extends BaseTest{

    private Gson gson = new Gson();

    public CartList getCartsApi() {

        logger.info("Calling API carts");

        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/carts")
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        CartList cartsResponse = gson.fromJson(res, CartList.class);

        return cartsResponse;
    }

    public String getCartId(int cartId) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API for looking a specific cart");
        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/carts/" + cartId)
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        return res;
    }

    public String getCartsUserId(Integer id, CartList containAllUsers) {

        List<Cart> carts = containAllUsers.carts;

        System.out.println("*********************************************************************************");

        logger.info("Calling API for looking userId " + carts.get(id).userId);
        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/carts/user/" + carts.get(id).userId)
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        return res;
    }

    public CartList getSearchCarts(String cart) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API for looking a specific user");
        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/carts/search?q=" + cart)
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        CartList totalCarts = gson.fromJson(res, CartList.class);

        return totalCarts;
    }

    public void printCarts(CartList containAllCarts) {

        logger.info("Next 4 lines are going to print the info coming from Users API");
        logger.info("Display current Total: " + containAllCarts.getTotal());
        logger.info("Display current Skip: " + containAllCarts.getSkip());
        logger.info("Display current Limit: " + containAllCarts.getLimit());
        logger.info("Display all current users: " + gson.toJson(containAllCarts.getCarts()));

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
}
