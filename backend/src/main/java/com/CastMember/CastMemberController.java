package com.CastMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author Connor Hunter        connh321@gmail.com
 *
 * A CastMember controller responsible for recieving calls for data from the front-end of the CastMember Application and
 * returning data resources about a castMember.
 *
 */
@RestController
@RequestMapping("/api/v1/")
public class CastMemberController {

    @Autowired // Injected Singleton
    private final CastMemberService castMemberService;

    //Constructor
    public CastMemberController(CastMemberService castMemberService) {
        this.castMemberService = castMemberService;
    }

    /***
     * Gets all castMembers in json format at
     * http://localhost:8080/api/{version}/castMember/
     *
     * @return Json List of all castMembers in the db
     */
    @GetMapping("/castMember")
    public List<CastMemberModel> getAllCastMembers() {
        return castMemberService.getAllCastMembers(); // returns a list of castMembers
    }


    /***
     * Gets all castMembers in json format for a certain movie
     * http://localhost:8080/api/{version}/movie/castMember/?mid=
     *
     * @return Json List of all castMembers in the db
     */
    @GetMapping("/movie/castMember")
    public List<CastMemberModel> getAllMovieCastMembers(@RequestParam("mid") Long mid) {
        return castMemberService.getAllMovieCastMembers(mid); // returns a list of castMembers
    }


    /**
     * Creates a castMember, given a request body containing the proper details on
     * http://localhost:8080/api/{version}/castMember/
     *
     * @param castMember a castMember in json format
     * @return A response body in json format
     */
    @PostMapping("/castMember")
    public CastMemberModel createCastMember(@RequestBody CastMemberModel castMember) {
        return castMemberService.createCastMember(castMember);
    }

    /**
     * Creates a castMember for a certain Movie,
     * given a request body containing a castMember and a Movie id
     * http://localhost:8080/api/{version}/movie/castMember/?mid=
     *
     * @param castMember    a castMember in json format
     * @param mid A given movie
     * @return A response body in json format
     */
    @PostMapping("/movie/castMember")
    public CastMemberModel createMovieCastMember(@RequestParam("mid") Long mid, @RequestBody CastMemberModel castMember) {
        return castMemberService.createMovieCastMember(mid, castMember);
    }


    /**
     * Gets a castMember by cmid on
     * http://localhost:8080/api/{version}/castMember/{cmid}/
     *
     * @param cmid A given cmid path variable
     * @return Http response 200 with castMember response body if ok, otherwise 404 not found rnf exception
     */
    @GetMapping("/castMember/{cmid}")
    public ResponseEntity<CastMemberModel> getCastMemberById(@PathVariable Long cmid) {
        CastMemberModel castMember = castMemberService.getCastMemberById(cmid);
        return ResponseEntity.ok(castMember);
    }


    /**
     * Updates a castMember by a given cmid and a given request body to update to on
     * http://localhost:8080/api/{version}/castMember/{cmid}/
     *
     * @param cmid          A given cmid path variable
     * @param castMemberDetails CastMembers details to update to in form on json request body
     * @return Http response 200 with castMember response body if ok, otherwise 404 not found rnf exception
     */
    @PutMapping("/castMember/{cmid}")
    public ResponseEntity<CastMemberModel> updateCastMember(@PathVariable Long cmid, @RequestBody CastMemberModel castMemberDetails) {
        CastMemberModel updatedCastMember = castMemberService.updateCastMember(cmid, castMemberDetails);
        return ResponseEntity.ok(updatedCastMember);
    }


    /**
     * Deletes a castMember given an cmid
     *
     * @param cmid A given cmid path variable
     * @return deleted : ok response body on ok, otherwise 404 not found rnf exception
     */
    @DeleteMapping("/castMember/{cmid}")
    public ResponseEntity<Map<String, Boolean>> deleteCastMember(@PathVariable Long cmid) {
        castMemberService.deleteCastMember(cmid);
        //only done on success
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true); // if the resource cmidn't exist it will send a 404 not found, rnf exception
        return ResponseEntity.ok(response);
    }
}
