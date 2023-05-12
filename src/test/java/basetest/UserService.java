package basetest;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserService extends BaseTest{

    ProductService.ProductsResponse containAllProducts;
    int total;
    int randomValue;
    private Gson gson = new Gson();

    public UsersResponse getUsersApi() {

        logger.info("Calling API products");

        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/users")
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        UsersResponse usersResponse = gson.fromJson(res, UsersResponse.class);

        return usersResponse;
    }

    public String getUserId(int userId) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API for looking a specific user");
        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/users/" + userId)
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        return res;
    }

    public UsersResponse getSearchUsers(String user) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API for looking a specific user");
        String res =
                given(spec)
                    .contentType(ContentType.JSON)
                .when()
                    .get("/users/search?q=" + user)
                .then()
                    .statusCode(200)
                    .extract().body().asString();

        UsersResponse totalUsers = gson.fromJson(res, UsersResponse.class);

        return totalUsers;
    }

    public void printUsers(UsersResponse containAllUsers) {

        logger.info("Next 4 lines are going to print the info coming from Users API");
        logger.info("Display current Total: " + containAllUsers.getTotal());
        logger.info("Display current Skip: " + containAllUsers.getSkip());
        logger.info("Display current Limit: " + containAllUsers.getLimit());
        logger.info("Display all current users: " + gson.toJson(containAllUsers.getUsers()));

    }

    @Getter
    @Setter
    public class UsersResponse {

        private List<User> users;
        private int total;
        private int skip;
        private int limit;
    }

    @Getter
    @Setter
    public class User {
        private int id;
        private String firstName;
        private String lastName;
        private String maidenName;
        private int age;
        private String gender;
        private String email;
        private String phone;
        private String username;
        private String password;
        private String birthDate;
        private String image;
        private String bloodGroup;
        private int height;
        private double weight;
        private String eyeColor;
        private Hair hair;
        private String domain;
        private String ip;
        private Address address;
        private String macAddress;
        private String university;
        private Bank bank;
        private Company company;
        private String ein;
        private String ssn;
        private String userAgent;
    }

    @Getter
    @Setter
    public class Hair {
        private String color;
        private String type;
    }

    @Getter
    @Setter
    public class Address {
        private String address;
        private String city;
        private Coordinates coordinates;
        private String postalCode;
        private String state;
    }

    @Getter
    @Setter
    public class Coordinates {
        private double lat;
        private double lng;
    }

    @Getter
    @Setter
    public class Bank {
        private String cardExpire;
        private String cardNumber;
        private String cardType;
        private String currency;
        private String iban;
    }

    @Getter
    @Setter
    public class Company {
        private Address address;
        private String department;
        private String name;
        private String title;
    }

}
