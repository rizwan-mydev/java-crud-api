package com.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author Connor Hunter        connh321@gmail.com
 *
 * A User controller responsible for recieving calls for data from the front-end of the Movie Application and
 * returning data resources about users.
 *
 */
@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired // Injected Singleton
    private final UserService userService;

    //Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /***
     * Gets all users in json format at
     * http://localhost:8080/api/{version}/users/
     *
     * @return Json List of all users in the db
     */
    @GetMapping("/users")
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers(); // returns a list of users
    }


    /**
     * Creates a user, given a request body containing the proper details on
     * http://localhost:8080/api/{version}/users/
     *
     * @param user a user in json format
     * @return A response body in json format
     */
    @PostMapping("/users")
    public UserModel createUser(@RequestBody UserModel user) {
        return userService.createUser(user);
    }


    /**
     * Gets a user by username on
     * http://localhost:8080/api/{version}/users/{username}/
     *
     * @param username A given username path variable
     * @return Http response 200 with user response body if ok, otherwise 404 not found rnf exception
     */
    @GetMapping("/users/{username}")
    public ResponseEntity<UserModel> getUserById(@PathVariable String username) {
        UserModel user = userService.getUserById(username);
        return ResponseEntity.ok(user);
    }


    /**
     * Updates a user by a given username and a given request body to update to on
     * http://localhost:8080/api/{version}/users/{username}/
     *
     * @param username    A given username path variable
     * @param userDetails Users details to update to in form on json request body
     * @return Http response 200 with user response body if ok, otherwise 404 not found rnf exception
     */
    @PutMapping("/users/{username}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String username, @RequestBody UserModel userDetails) {
        UserModel updatedUser = userService.updateUser(username, userDetails);
        return ResponseEntity.ok(updatedUser);
    }


    /**
     * Deletes a user given a username
     *
     * @param username A given username path variable
     * @return deleted : ok response body on ok, otherwise 404 not found rnf exception
     */
    @DeleteMapping("/users/{username}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        //only done on success
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true); // if the resource didn't exist it will send a 404 not found, rnf exception
        return ResponseEntity.ok(response);
    }
}
