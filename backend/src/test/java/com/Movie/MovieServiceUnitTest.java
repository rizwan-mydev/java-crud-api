package com.Movie;

import com.Exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * @author Connor Hunter        connh321@gmail.com
 * <p>
 * Unit Tests for {@link MovieService} to test business logic.
 */
@ExtendWith(MockitoExtension.class)
class MovieServiceUnitTest {

    @Mock
    private MovieRepository movieRepository;
    private MovieService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new MovieService(movieRepository);
    }

    @Test
        //MovieService.getAllMovies()
    void itShouldGetAllMovies() {
        //given
        underTest.getAllMovies();
        //when

        //then
        verify(movieRepository).findAll();
    }

    @Test
        //MovieService.createMovie()
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
        verify(movieRepository).save(movieArgumentCaptor.capture()); // verify movie was saved, and capture that movie
        MovieModel capturedMovie = movieArgumentCaptor.getValue();
        assertThat(capturedMovie).isEqualTo(movieModel);
    }

    @Test
        //MovieService.getMovieById()
    void itShouldGetAMovieById() {
        //given
        MovieModel movieModel = new MovieModel(
                "TestTitle",
                "TestLength",
                "TestGenre",
                new Date()
        );

        //when
        when(movieRepository.findById(any())).thenReturn(Optional.of(movieModel));
        underTest.getMovieById(movieModel.getMid());

        //then
        ArgumentCaptor<Long> movieArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify findById was invoked w/ mid, and capture the mid
        verify(movieRepository).findById(movieArgumentCaptor.capture());
        Long capturedMid = movieArgumentCaptor.getValue();
        assertThat(movieModel.getMid()).isEqualTo(capturedMid);
    }

    @Test
        //MovieService.getMovieById()
    void itShouldNotGetAMovieById() {
        /** throws {@link ResourceNotFoundException}*/
        //given
        Long mid = 1L;

        //when

        // I get a movie that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.getMovieById(mid))
                .hasMessageContaining("Movie With the mid: " + mid + " does not exist!"
                );
    }

    @Test
        //MovieService.updateMovie()
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
        when(movieRepository.findById(any())).thenReturn(Optional.of(movieModel));
        underTest.updateMovie(newMovieModel.getMid(), newMovieModel);

        //then
        assertThat(movieModel.getGenre()).isEqualTo(newMovieModel.getGenre());
        assertThat(movieModel.getTitle()).isEqualTo(newMovieModel.getTitle());
        assertThat(movieModel.getReleaseDate()).isEqualTo(newMovieModel.getReleaseDate());
        assertThat(movieModel.getLength()).isEqualTo(newMovieModel.getLength());


        ArgumentCaptor<MovieModel> movieArgumentCaptor = ArgumentCaptor.forClass(MovieModel.class);
        // verify save was invoked w/ correct movie, and capture the NEW movie
        verify(movieRepository).save(movieArgumentCaptor.capture());
        MovieModel capturedMovie = movieArgumentCaptor.getValue();
        assertThat(newMovieModel.getMid()).isEqualTo(capturedMovie.getMid()); //assert new movie is saved
    }

    @Test
        //MovieService.updateMovie()
    void itShouldNotUpdateAMovie() {
        /** throws {@link ResourceNotFoundException}*/
        //exception thrown iff movie does not exist

        //given
        Long mid = 1L;

        //when

        // I get a movie that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.updateMovie(mid, new MovieModel()))
                .hasMessageContaining("Movie With the mid: " + mid + " does not exist!"
                );
    }

    @Test
        //MovieService.deleteMovie()
    void itShouldDeleteAMovie() {
        //given
        MovieModel movieModel = new MovieModel(
                "TestTitle",
                "TestLength",
                "TestGenre",
                new Date()
        );

        //when
        when(movieRepository.findById(any())).thenReturn(Optional.of(movieModel));
        underTest.deleteMovie(movieModel.getMid());

        //then
        ArgumentCaptor<MovieModel> movieArgumentCaptor = ArgumentCaptor.forClass(MovieModel.class);
        // verify delete was invoked w/ movie
        verify(movieRepository).delete(movieArgumentCaptor.capture());
        MovieModel capturedMovie = movieArgumentCaptor.getValue();
        assertThat(movieModel).isEqualTo(capturedMovie);
    }

    @Test
        //MovieService.deleteMovie()
    void itShouldNotDeleteAMovie() {
        /** throws {@link ResourceNotFoundException}*/
        //exception thrown iff movie does not exist

        //given
        Long mid = 1L;

        //when

        // I get a movie that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.deleteMovie(mid))
                .hasMessageContaining("Movie With the mid: " + mid + " does not exist!"
                );
    }
}