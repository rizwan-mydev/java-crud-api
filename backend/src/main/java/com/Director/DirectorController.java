package com.Director;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author Connor Hunter        connh321@gmail.com
 *
 * A Director controller responsible for recieving calls for data from the front-end of the Director Application and
 * returning data resources about a director.
 *
 */
@RestController
@RequestMapping("/api/v1/")
public class DirectorController {

    @Autowired // Injected Singleton
    private final DirectorService directorService;

    //Constructor
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    /***
     * Gets all directors in json format at
     * http://localhost:8080/api/{version}/director/
     *
     * @return Json List of all directors in the db
     */
    @GetMapping("/director")
    public List<DirectorModel> getAllDirectors() {
        return directorService.getAllDirectors(); // returns a list of directors
    }

    
    /***
     * Gets all directors in json format for a certain movie
     * http://localhost:8080/api/{version}/movie/director/?mid=
     *
     * @return Json List of all directors in the db
     */
    @GetMapping("/movie/director")
    public List<DirectorModel> getAllMovieDirectors(@RequestParam("mid") Long mid) {
        return directorService.getAllMovieDirectors(mid); // returns a list of directors
    }


    /**
     * Creates a director, given a request body containing the proper details on
     * http://localhost:8080/api/{version}/director/
     *
     * @param director a director in json format
     * @return A response body in json format
     */
    @PostMapping("/director")
    public DirectorModel createDirector(@RequestBody DirectorModel director) {
        return directorService.createDirector(director);
    }


    /**
     * Creates a director for a certain Movie,
     * given a request body containing a director and a Movie id
     * http://localhost:8080/api/{version}/movie/director/?mid=
     *
     * @param director    a director in json format
     * @param mid A given movie
     * @return A response body in json format
     */
    @PostMapping("/movie/director")
    public DirectorModel createMovieDirector(@RequestParam("mid") Long mid, @RequestBody DirectorModel director) {
        return directorService.createMovieDirector(mid, director);
    }


    /**
     * Gets a director by did on
     * http://localhost:8080/api/{version}/director/{did}/
     *
     * @param did A given did path variable
     * @return Http response 200 with director response body if ok, otherwise 404 not found rnf exception
     */
    @GetMapping("/director/{did}")
    public ResponseEntity<DirectorModel> getDirectorById(@PathVariable Long did) {
        DirectorModel director = directorService.getDirectorById(did);
        return ResponseEntity.ok(director);
    }


    /**
     * Updates a director by a given did and a given request body to update to on
     * http://localhost:8080/api/{version}/director/{did}/
     *
     * @param did          A given did path variable
     * @param directorDetails Directors details to update to in form on json request body
     * @return Http response 200 with director response body if ok, otherwise 404 not found rnf exception
     */
    @PutMapping("/director/{did}")
    public ResponseEntity<DirectorModel> updateDirector(@PathVariable Long did, @RequestBody DirectorModel directorDetails) {
        DirectorModel updatedDirector = directorService.updateDirector(did, directorDetails);
        return ResponseEntity.ok(updatedDirector);
    }


    /**
     * Deletes a director given an did
     *
     * @param did A given did path variable
     * @return deleted : ok response body on ok, otherwise 404 not found rnf exception
     */
    @DeleteMapping("/director/{did}")
    public ResponseEntity<Map<String, Boolean>> deleteDirector(@PathVariable Long did) {
        directorService.deleteDirector(did);
        //only done on success
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true); // if the resource didn't exist it will send a 404 not found, rnf exception
        return ResponseEntity.ok(response);
    }
}
