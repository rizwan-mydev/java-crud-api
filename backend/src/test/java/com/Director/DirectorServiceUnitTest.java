package com.Director;

import com.Exception.ResourceNotFoundException;
import com.Director.DirectorModel;
import com.Director.DirectorRepository;
import com.Director.DirectorService;
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
 * Unit Tests for {@link DirectorService} to test business logic.
 */
@ExtendWith(MockitoExtension.class)
class DirectorServiceUnitTest {

    @Mock
    private DirectorRepository directorRepository;
    private DirectorService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new DirectorService(directorRepository);
    }

    @Test
        //DirectorService.getAllDirectors()
    void itShouldGetAllDirectors() {
        //given
        underTest.getAllDirectors();
        //when

        //then
        verify(directorRepository).findAll();
    }

    @Test
        //DirectorService.createDirector()
    void itShouldCreateADirector() {
        //given
        DirectorModel directorModel = new DirectorModel(
                "TestFirstName",
                "TestLastName"
        );

        //when
        underTest.createDirector(directorModel);

        //then
        ArgumentCaptor<DirectorModel> directorArgumentCaptor = ArgumentCaptor.forClass(DirectorModel.class);
        verify(directorRepository).save(directorArgumentCaptor.capture()); // verify director was saved, and capture that director
        DirectorModel capturedDirector = directorArgumentCaptor.getValue();
        assertThat(capturedDirector).isEqualTo(directorModel);
    }

    @Test
        //DirectorService.getDirectorById()
    void itShouldGetADirectorById() {
        //given
        DirectorModel directorModel = new DirectorModel(
                "TestFirstName",
                "TestLastName"
        );

        //when
        when(directorRepository.findById(any())).thenReturn(Optional.of(directorModel));
        underTest.getDirectorById(directorModel.getDid());

        //then
        ArgumentCaptor<Long> directorArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify findById was invoked w/ did, and capture the did
        verify(directorRepository).findById(directorArgumentCaptor.capture());
        Long capturedDid = directorArgumentCaptor.getValue();
        assertThat(directorModel.getDid()).isEqualTo(capturedDid);
    }

    @Test
        //DirectorService.getDirectorById()
    void itShouldNotGetADirectorById() {
        /** throws {@link ResourceNotFoundException}*/
        //given
        Long did = 1L;

        //when

        // I get a director that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.getDirectorById(did))
                .hasMessageContaining("Director With the did: " + did + " does not exist!"
                );
    }

    @Test
        //DirectorService.updateDirector()
    void itShouldUpdateADirector() {
        //given
        DirectorModel directorModel = new DirectorModel(
                "TestFirstName",
                "TestLastName"
        );
        DirectorModel newDirectorModel = new DirectorModel(
                "newFirstName",
                "newLastName"
        );
        //when
        when(directorRepository.findById(any())).thenReturn(Optional.of(directorModel));
        underTest.updateDirector(newDirectorModel.getDid(), newDirectorModel);

        //then
        assertThat(directorModel.getFirstName()).isEqualTo(newDirectorModel.getFirstName());
        assertThat(directorModel.getLastName()).isEqualTo(newDirectorModel.getLastName());


        ArgumentCaptor<DirectorModel> directorArgumentCaptor = ArgumentCaptor.forClass(DirectorModel.class);
        // verify save was invoked w/ correct director, and capture the NEW director
        verify(directorRepository).save(directorArgumentCaptor.capture());
        DirectorModel capturedDirector = directorArgumentCaptor.getValue();
        assertThat(newDirectorModel.getDid()).isEqualTo(capturedDirector.getDid()); //assert new director is saved
    }

    @Test
        //DirectorService.updateDirector()
    void itShouldNotUpdateADirector() {
        /** throws {@link ResourceNotFoundException}*/
        //exception thrown iff director does not exist

        //given
        Long did = 1L;

        //when

        // I get a director that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.updateDirector(did, new DirectorModel()))
                .hasMessageContaining("Director With the did: " + did + " does not exist!"
                );
    }

    @Test
        //DirectorService.deleteDirector()
    void itShouldDeleteADirector() {
        //given
        DirectorModel directorModel = new DirectorModel(
                "TestFirstName",
                "TestLastName"
        );

        //when
        when(directorRepository.findById(any())).thenReturn(Optional.of(directorModel));
        underTest.deleteDirector(directorModel.getDid());

        //then
        ArgumentCaptor<DirectorModel> directorArgumentCaptor = ArgumentCaptor.forClass(DirectorModel.class);
        // verify delete was invoked w/ director
        verify(directorRepository).delete(directorArgumentCaptor.capture());
        DirectorModel capturedDirector = directorArgumentCaptor.getValue();
        assertThat(directorModel).isEqualTo(capturedDirector);
    }

    @Test
        //DirectorService.deleteDirector()
    void itShouldNotDeleteADirector() {
        /** throws {@link ResourceNotFoundException}*/
        //exception thrown iff director does not exist

        //given
        Long did = 1L;

        //when

        // I get a director that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.deleteDirector(did))
                .hasMessageContaining("Director With the did: " + did + " does not exist!"
                );
    }
}