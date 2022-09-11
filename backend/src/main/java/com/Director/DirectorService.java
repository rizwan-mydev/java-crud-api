package com.Director;

import com.Exception.ResourceNotFoundException;
import com.Movie.MovieModel;
import com.Movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author Connor Hunter        connh321@gmail.com
 *
 * A Director service responsible for business logic and making calls so the data accessing repository and returning the
 * proper data.
 *
 */
@Service
public class DirectorService {

    @Autowired // Injected Singleton
    private final DirectorRepository directorRepository;

    @Autowired // Injected Singleton
    private MovieService movieService;

    //Constructor
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    /**
     * Gets all directors from the database
     *
     * @return A list of all directors
     */
    public List<DirectorModel> getAllDirectors() {
        return directorRepository.findAll(); // returns a list of directors
    }


    /**
     * Gets all directors from the database for a certain movie
     *
     * @return A list of all directors
     */
    public List<DirectorModel> getAllMovieDirectors(Long mid) {
        MovieModel currentMovie = movieService.getMovieById(mid);
        return currentMovie.getDirectorModels(); // returns a list of directors
    }


    /**
     * Saves a new director into the repository
     *
     * @param director A given director
     * @return a newly saved Director
     */
    public DirectorModel createDirector(DirectorModel director) {
        return directorRepository.save(director);
    }

    /**
     * Creates a director for a certain movie,
     *
     * @param director    a director in json format
     * @param mid A given mid
     * @return A response body in json format
     */
    public DirectorModel createMovieDirector(Long mid, DirectorModel director) {
        //add director to movies directors
        MovieModel currentMovie = movieService.getMovieById(mid);
        director.getMovieModels().add(currentMovie);
        currentMovie.getDirectorModels().add(director);
        //Cascade.All saves the details
        return directorRepository.save(director);
    }


    /**
     * Gets a director by did and returns it
     *
     * @param did A given did
     * @return A director, or an RNF Exception
     */
    public DirectorModel getDirectorById(Long did) {
        return directorRepository.findById(did)
                .orElseThrow(() -> new ResourceNotFoundException("Director With the did: " + did + " does not exist!"));
    }


    /**
     * Updates a given director by did given some new details
     *
     * @param did    A long did
     * @param directorDetails A director details
     * @return A director, or an RNF exception
     */
    public DirectorModel updateDirector(Long did, DirectorModel directorDetails) {
        DirectorModel director = directorRepository.findById(did)
                .orElseThrow(() -> new ResourceNotFoundException("Director With the did: " + did + " does not exist!"));

        director.setFirstName(directorDetails.getFirstName());
        director.setLastName(directorDetails.getLastName());


        return directorRepository.save(director);
    }


    /**
     * Deletes a director given a did
     *
     * @param did A long did
     */
    public void deleteDirector(Long did) {
        DirectorModel director = directorRepository.findById(did)
                .orElseThrow(() -> new ResourceNotFoundException("Director With the did: " + did + " does not exist!"));
        directorRepository.delete(director);
    }

}
