package com.Movie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

/**
 * @author Connor Hunter        connh321@gmail.com
 * <p>
 * Unit Tests for {@link MovieController} to test what information is being passed to and being recieved from the
 * {@link MovieService}.
 */
@ExtendWith(MockitoExtension.class)
public class MovieControllerUnitTest {
    @Mock
    private MovieService movieService;
    private MovieController underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new MovieController(movieService);
    }

    @Test
        //MovieController.getAllMovies()
    void itShouldGetAllMovies() {
        //given

        //when
        underTest.getAllMovies();

        //then
        verify(movieService).getAllMovies();
    }

    @Test
        //MovieController.createMovie()
    void itShouldCreateAMovie() {
        //given
        MovieModel movieModel = new MovieModel(
                "TestTitle",
                "TestLength",
                "TestGenre",
                new Date()
        );

        //when
        underTest.createMovie(movieModel);

        //then
        ArgumentCaptor<MovieModel> movieArgumentCaptor = ArgumentCaptor.forClass(MovieModel.class);
        verify(movieService).createMovie(movieArgumentCaptor.capture()); // verify movie was passed to create movie, and capture that movie
        MovieModel capturedMovie = movieArgumentCaptor.getValue();
        assertThat(capturedMovie).isEqualTo(movieModel);
    }

    @Test
        //MovieController.getMovieById()
    void itShouldGetAMovieById() {
        //given
        MovieModel movieModel = new MovieModel(
                "TestTitle",
                "TestLength",
                "TestGenre",
                new Date()
        );

        //when
        underTest.getMovieById(movieModel.getMid());

        //then
        ArgumentCaptor<Long> midArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify findById was invoked w/ mid, and capture the mid
        verify(movieService).getMovieById(midArgumentCaptor.capture());
        Long capturedMid = midArgumentCaptor.getValue();
        assertThat(movieModel.getMid()).isEqualTo(capturedMid);
    }


    @Test
        //MovieController.updateMovie()
    void itShouldUpdateAMovie() {
        //given
        MovieModel movieModel = new MovieModel(
                "TestTitle",
                "TestLength",
                "TestGenre",
                new Date()
        );

        MovieModel newMovieModel = new MovieModel(
                "newTitle",
                "newLength",
                "newGenre",
                new Date()
        );

        //when
        underTest.updateMovie(movieModel.getMid(), newMovieModel);

        //then

        ArgumentCaptor<Long> midArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<MovieModel> newMovieArgumentCaptor = ArgumentCaptor.forClass(MovieModel.class);
        // verify updateMovie was called with current Movie mid and new Movie details
        verify(movieService).updateMovie(midArgumentCaptor.capture(), newMovieArgumentCaptor.capture());
        MovieModel capturedNewMovie = newMovieArgumentCaptor.getValue();
        Long capturedCurrentMid = midArgumentCaptor.getValue();
        assertThat(newMovieModel).isEqualTo(capturedNewMovie); //assert new movie was passed in
        assertThat(movieModel.getMid()).isEqualTo(capturedCurrentMid);
    }


    @Test
        //MovieController.deleteMovie()
    void itShouldDeleteAMovie() {
        //given
        MovieModel movieModel = new MovieModel(
                "TestTitle",
                "TestLength",
                "TestGenre",
                new Date()
        );

        //when

        underTest.deleteMovie(movieModel.getMid());

        //then
        ArgumentCaptor<Long> midArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify delete was invoked w/ movie
        verify(movieService).deleteMovie(midArgumentCaptor.capture());
        Long capturedMid = midArgumentCaptor.getValue();
        assertThat(movieModel.getMid()).isEqualTo(capturedMid);
    }
}
