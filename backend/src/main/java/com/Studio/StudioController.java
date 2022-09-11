package com.Studio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author Connor Hunter        connh321@gmail.com
 *
 * A Studio controller responsible for recieving calls for data from the front-end of the Studio Application and
 * returning data resources about a studio.
 *
 */
@RestController
@RequestMapping("/api/v1/")
public class StudioController {

    @Autowired // Injected Singleton
    private final StudioService studioService;

    //Constructor
    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    /***
     * Gets all studios in json format at
     * http://localhost:8080/api/{version}/studio/
     *
     * @return Json List of all studios in the db
     */
    @GetMapping("/studio")
    public List<StudioModel> getAllStudios() {
        return studioService.getAllStudios(); // returns a list of studios
    }


    /***
     * Gets all studios in json format for a certain movie
     * http://localhost:8080/api/{version}/studio/movie/?mid=
     *
     * @return Json List of all studios in the db
     */
    @GetMapping("/studio/movie")
    public List<StudioModel> getAllMovieStudios(@RequestParam("mid") Long mid) {
        return studioService.getAllMovieStudios(mid); // returns a list of studios
    }


    /**
     * Creates a studio, given a request body containing the proper details on
     * http://localhost:8080/api/{version}/studio/
     *
     * @param studio a studio in json format
     * @return A response body in json format
     */
    @PostMapping("/studio")
    public StudioModel createStudio(@RequestBody StudioModel studio) {
        return studioService.createStudio(studio);
    }


    /**
     * Creates a studio for a certain Movie,
     * given a request body containing a studio and a Movie id
     * http://localhost:8080/api/{version}/studio/movie/?mid=
     *
     * @param studio    a studio in json format
     * @param mid A given movie
     * @return A response body in json format
     */
    @PostMapping("/studio/movie")
    public StudioModel createMovieStudio(@RequestParam("mid") Long mid, @RequestBody StudioModel studio) {
        return studioService.createMovieStudio(mid, studio);
    }


    /**
     * Gets a studio by sid on
     * http://localhost:8080/api/{version}/studio/{sid}/
     *
     * @param sid A given sid path variable
     * @return Http response 200 with studio response body if ok, otherwise 404 not found rnf exception
     */
    @GetMapping("/studio/{sid}")
    public ResponseEntity<StudioModel> getStudioById(@PathVariable Long sid) {
        StudioModel studio = studioService.getStudioById(sid);
        return ResponseEntity.ok(studio);
    }


    /**
     * Updates a studio by a given sid and a given request body to update to on
     * http://localhost:8080/api/{version}/studio/{sid}/
     *
     * @param sid           A given sid path variable
     * @param studioDetails Studios details to update to in form on json request body
     * @return Http response 200 with studio response body if ok, otherwise 404 not found rnf exception
     */
    @PutMapping("/studio/{sid}")
    public ResponseEntity<StudioModel> updateStudio(@PathVariable Long sid, @RequestBody StudioModel studioDetails) {
        StudioModel updatedStudio = studioService.updateStudio(sid, studioDetails);
        return ResponseEntity.ok(updatedStudio);
    }


    /**
     * Deletes a studio given an sid
     *
     * @param sid A given sid path variable
     * @return deleted : ok response body on ok, otherwise 404 not found rnf exception
     */
    @DeleteMapping("/studio/{sid}")
    public ResponseEntity<Map<String, Boolean>> deleteStudio(@PathVariable Long sid) {
        studioService.deleteStudio(sid);
        //only done on success
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true); // if the resource didn't exist it will send a 404 not found, rnf exception
        return ResponseEntity.ok(response);
    }
}
