package service;


import com.google.gson.Gson;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class ServiceAPI {

    final static Logger LOGGER = LogManager.getLogger(ServiceAPI.class);
    final static String CONTENT_TYPE = "application/json";

    public static void main(String[] args) {
        users();
    }

    private static void users(){
        List<User> userList = new ArrayList<>();

        port(8090);

        path("/api", () -> {
            before("/*", (request, response) -> LOGGER.info("Received API call"));

            path("/user", () -> {

                get("/get", (request, response) -> {
                    response.type(CONTENT_TYPE);
                    LOGGER.info("GET");
                    response.status(200);
                    return new Gson().toJson(userList);
                });

                post("/post", (request, response) -> {
                    response.type(CONTENT_TYPE);
                    User user = new Gson().fromJson(request.body(), User.class);
                    userList.add(user);
                    LOGGER.info("POST");
                    response.status(201);
                    return new Gson().toJson(user);
                });

                put("/put", (request, response) -> {
                    response.type(CONTENT_TYPE);
                    User user = new Gson().fromJson(request.body(), User.class);
                    userList.removeIf(c -> c.getId().equals(user.getId()));
                    userList.add(user);

                    LOGGER.info("PUT.");
                    response.status(201);
                    return new Gson().toJson(user);
                });

                delete("/del/:id", (request, response) -> {

                    User user = new User();
                    user.setId(Long.parseLong(request.params(":id")));
                    userList.removeIf(c -> c.getId().equals(user.getId()));
                    LOGGER.info("DELETE");
                    response.status(204);
                    String msg = "User deletado!";
                    return msg;
                });



            });
        });
    }

}
