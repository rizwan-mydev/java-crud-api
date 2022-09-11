package com.Studio;

import com.Exception.ResourceNotFoundException;
import com.Movie.MovieModel;
import com.Movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author Connor Hunter        connh321@gmail.com
 *
 * A Studio service responsible for business logic and making calls so the data accessing repository and returning the
 * proper data.
 *
 */
@Service
public class StudioService {

    @Autowired // Injected Singleton
    private final StudioRepository studioRepository;

    @Autowired // Injected Singleton
    private MovieService movieService;

    //Constructor
    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    /**
     * Gets all studios from the database
     *
     * @return A list of all studios
     */
    public List<StudioModel> getAllStudios() {
        return studioRepository.findAll(); // returns a list of studios
    }


    /**
     * Gets all studios from the database for a certain movie
     *
     * @return A list of all studios
     */
    public List<StudioModel> getAllMovieStudios(Long mid) {
        MovieModel currentMovie = movieService.getMovieById(mid);
        return currentMovie.getStudioModels(); // returns a list of studios
    }


    /**
     * Saves a new studio into the repository
     *
     * @param studio A given studio
     * @return a newly saved Studio
     */
    public StudioModel createStudio(StudioModel studio) {
        return studioRepository.save(studio);
    }

    /**
     * Creates a studio for a certain movie,
     *
     * @param studio    a studio in json format
     * @param mid A given mid
     * @return A response body in json format
     */
    public StudioModel createMovieStudio(Long mid, StudioModel studio) {
        //add studio to movies studios
        MovieModel currentMovie = movieService.getMovieById(mid);
        studio.getMovieModels().add(currentMovie);
        currentMovie.getStudioModels().add(studio);
        //Cascade.All saves the details
        return studioRepository.save(studio);
    }


    /**
     * Gets a studio by sid and returns it
     *
     * @param sid A given sid
     * @return A studio, or an RNF Exception
     */
    public StudioModel getStudioById(Long sid) {
        return studioRepository.findById(sid)
                .orElseThrow(() -> new ResourceNotFoundException("Studio With the sid: " + sid + " does not exist!"));
    }


    /**
     * Updates a given studio by sid given some new details
     *
     * @param sid    A long sid
     * @param studioDetails A studio details
     * @return A studio, or an RNF exception
     */
    public StudioModel updateStudio(Long sid, StudioModel studioDetails) {
        StudioModel studio = studioRepository.findById(sid)
                .orElseThrow(() -> new ResourceNotFoundException("Studio With the sid: " + sid + " does not exist!"));

        studio.setName(studioDetails.getName());

        return studioRepository.save(studio);
    }


    /**
     * Deletes a studio given a sid
     *
     * @param sid A long sid
     */
    public void deleteStudio(Long sid) {
        StudioModel studio = studioRepository.findById(sid)
                .orElseThrow(() -> new ResourceNotFoundException("Studio With the sid: " + sid + " does not exist!"));
        studioRepository.delete(studio);
    }

}
