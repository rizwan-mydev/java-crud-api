package com.Director;

import com.Director.DirectorController;
import com.Director.DirectorModel;
import com.Director.DirectorService;
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
 * Unit Tests for {@link DirectorController} to test what information is being passed to and being recieved from the
 * {@link DirectorService}.
 */
@ExtendWith(MockitoExtension.class)
public class DirectorControllerUnitTest {
    @Mock
    private DirectorService directorService;
    private DirectorController underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new DirectorController(directorService);
    }

    @Test
        //DirectorController.getAllDirectors()
    void itShouldGetAllDirectors() {
        //given

        //when
        underTest.getAllDirectors();

        //then
        verify(directorService).getAllDirectors();
    }

    @Test
        //DirectorController.createDirector()
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
        verify(directorService).createDirector(directorArgumentCaptor.capture()); // verify director was passed to create director, and capture that director
        DirectorModel capturedDirector = directorArgumentCaptor.getValue();
        assertThat(capturedDirector).isEqualTo(directorModel);
    }

    @Test
        //DirectorController.getDirectorById()
    void itShouldGetADirectorById() {
        //given
        DirectorModel directorModel = new DirectorModel(
                "TestFirstName",
                "TestLastName"
        );

        //when
        underTest.getDirectorById(directorModel.getDid());

        //then
        ArgumentCaptor<Long> didArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify findById was invoked w/ did, and capture the did
        verify(directorService).getDirectorById(didArgumentCaptor.capture());
        Long capturedDid = didArgumentCaptor.getValue();
        assertThat(directorModel.getDid()).isEqualTo(capturedDid);
    }


    @Test
        //DirectorController.updateDirector()
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
        underTest.updateDirector(directorModel.getDid(), newDirectorModel);

        //then

        ArgumentCaptor<Long> didArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<DirectorModel> newDirectorArgumentCaptor = ArgumentCaptor.forClass(DirectorModel.class);
        // verify updateDirector was called with current Director did and new Director details
        verify(directorService).updateDirector(didArgumentCaptor.capture(), newDirectorArgumentCaptor.capture());
        DirectorModel capturedNewDirector = newDirectorArgumentCaptor.getValue();
        Long capturedCurrentDid = didArgumentCaptor.getValue();
        assertThat(newDirectorModel).isEqualTo(capturedNewDirector); //assert new director was passed in
        assertThat(directorModel.getDid()).isEqualTo(capturedCurrentDid);
    }


    @Test
        //DirectorController.deleteDirector()
    void itShouldDeleteADirector() {
        //given
        DirectorModel directorModel = new DirectorModel(
                "TestFirstName",
                "TestLastName"
        );

        //when

        underTest.deleteDirector(directorModel.getDid());

        //then
        ArgumentCaptor<Long> didArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify delete was invoked w/ director
        verify(directorService).deleteDirector(didArgumentCaptor.capture());
        Long capturedDid = didArgumentCaptor.getValue();
        assertThat(directorModel.getDid()).isEqualTo(capturedDid);
    }
}
