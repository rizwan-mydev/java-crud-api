package com.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author Connor Hunter        connh321@gmail.com
 *
 * A Movie controller responsible for recieving calls for data from the front-end of the Movie Application and
 * returning data resources about a movie.
 *
 */
@RestController
@RequestMapping("/api/v1/")
public class MovieController {

    @Autowired // Injected Singleton
    private final MovieService movieService;

    //Constructor
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /***
     * Gets all movies in json format at
     * http://localhost:8080/api/{version}/movie/
     *
     * @return Json List of all movies in the db
     */
    @GetMapping("/movie")
    public List<MovieModel> getAllMovies() {
        return movieService.getAllMovies(); // returns a list of movies
    }

    /***
     * Gets all movies in json format for a certain user
     * http://localhost:8080/api/{version}/users/movie/?username=
     *
     * @return Json List of all movies in the db
     */
    @GetMapping("/users/movie")
    public List<MovieModel> getAllUserMovies(@RequestParam("username") String username) {
        return movieService.getAllUserMovies(username); // returns a list of movies
    }


    /**
     * Creates a movie, given a request body containing the proper details on
     * http://localhost:8080/api/{version}/movie/
     *
     * @param movie a movie in json format
     * @return A response body in json format
     */
    @PostMapping("/movie")
    public MovieModel createMovie(@RequestBody MovieModel movie) {
        return movieService.createMovie(movie);
    }

    /**
     * Creates a movie for a certain user,
     * given a request body containing a movie and a username
     * http://localhost:8080/api/{version}/users/movie/?username=
     *
     * @param movie    a movie in json format
     * @param username A given username
     * @return A response body in json format
     */
    @PostMapping("/users/movie")
    public MovieModel createUserMovie(@RequestParam("username") String username, @RequestBody MovieModel movie) {
        return movieService.createUserMovie(username, movie);
    }


    /**
     * Gets a movie by mid on
     * http://localhost:8080/api/{version}/movie/{mid}/
     *
     * @param mid A given mid path variable
     * @return Http response 200 with movie response body if ok, otherwise 404 not found rnf exception
     */
    @GetMapping("/movie/{mid}")
    public ResponseEntity<MovieModel> getMovieById(@PathVariable Long mid) {
        MovieModel movie = movieService.getMovieById(mid);
        return ResponseEntity.ok(movie);
    }


    /**
     * Updates a movie by a given mid and a given request body to update to on
     * http://localhost:8080/api/{version}/movie/{mid}/
     *
     * @param mid          A given mid path variable
     * @param movieDetails Movies details to update to in form on json request body
     * @return Http response 200 with movie response body if ok, otherwise 404 not found rnf exception
     */
    @PutMapping("/movie/{mid}")
    public ResponseEntity<MovieModel> updateMovie(@PathVariable Long mid, @RequestBody MovieModel movieDetails) {
        MovieModel updatedMovie = movieService.updateMovie(mid, movieDetails);
        return ResponseEntity.ok(updatedMovie);
    }


    /**
     * Deletes a movie given an mid
     *
     * @param mid A given mid path variable
     * @return deleted : ok response body on ok, otherwise 404 not found rnf exception
     */
    @DeleteMapping("/movie/{mid}")
    public ResponseEntity<Map<String, Boolean>> deleteMovie(@PathVariable Long mid) {
        movieService.deleteMovie(mid);
        //only done on success
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true); // if the resource didn't exist it will send a 404 not found, rnf exception
        return ResponseEntity.ok(response);
    }

}
