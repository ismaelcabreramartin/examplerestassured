package basetest;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserService extends BaseTest{

    JsonPath response;
    ProductService.ProductsResponse containAllProducts;
    int total;
    int randomValue;

    protected JsonPath getUsersApi() {

        logger.info("Calling API products");

        JsonPath res =
                given(spec)
                        .contentType(ContentType.JSON)
                        .when()
                        .get("/users")
                        .then()
                        .statusCode(200)
                        .extract().jsonPath();

        return res;
    }

    protected String getUserId(int userId) {

        System.out.println("*********************************************************************************");

        logger.info("Calling API for looking a specific user");
        JsonPath res = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .get("/users/" + userId)
                .then()
                .statusCode(200)
                .extract().jsonPath();

        String user = res.get().toString();

        return user;
    }
    protected UsersResponse getAllUsers(JsonPath res) {

        logger.info("getAllUsers method...");

        Map map = res.get();

        List usersReturn = (List) map.get("users");

        UsersResponse containAllUsers = new UsersResponse();

        containAllUsers.setTotal((int) map.get("total"));
        containAllUsers.setSkip((int) map.get("skip"));
        containAllUsers.setLimit((int) map.get("limit"));

        containAllUsers.setUsers((List<User>) usersReturn);

        return containAllUsers;
    }

    protected void printUsers(UsersResponse containAllUsers) {

        logger.info("Next 4 lines are going to print the info coming from Users API");
        logger.info("Display current Total: " + containAllUsers.getTotal());
        logger.info("Display current Skip: " + containAllUsers.getSkip());
        logger.info("Display current Limit: " + containAllUsers.getLimit());
        logger.info("Display all current users: " + containAllUsers.getUsers());

    }

    public class UsersResponse {
        @Getter
        @Setter
        private List<User> users;
        @Getter
        @Setter
        private int total;
        @Getter
        @Setter
        private int skip;
        @Getter
        @Setter
        private int limit;
    }

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

    public class Hair {
        private String color;
        private String type;
    }

    public class Address {
        private String address;
        private String city;
        private Coordinates coordinates;
        private String postalCode;
        private String state;
    }

    public class Coordinates {
        private double lat;
        private double lng;
    }

    public class Bank {
        private String cardExpire;
        private String cardNumber;
        private String cardType;
        private String currency;
        private String iban;
    }

    public class Company {
        private Address address;
        private String department;
        private String name;
        private String title;
    }

}
