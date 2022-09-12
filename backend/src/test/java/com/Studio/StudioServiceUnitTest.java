package com.Studio;

import com.Exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * @author Connor Hunter        connh321@gmail.com
 * <p>
 * Unit Tests for {@link StudioService} to test business logic.
 */
@ExtendWith(MockitoExtension.class)
class StudioServiceUnitTest {

    @Mock
    private StudioRepository studioRepository;
    private StudioService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new StudioService(studioRepository);
    }

    @Test
        //StudioService.getAllStudios()
    void itShouldGetAllStudios() {
        //given
        underTest.getAllStudios();
        //when

        //then
        verify(studioRepository).findAll();
    }

    @Test
        //StudioService.createStudio()
    void itShouldCreateAStudio() {
        //given
        StudioModel studioModel = new StudioModel(
                "TestName"
        );

        //when
        underTest.createStudio(studioModel);

        //then
        ArgumentCaptor<StudioModel> studioArgumentCaptor = ArgumentCaptor.forClass(StudioModel.class);
        verify(studioRepository).save(studioArgumentCaptor.capture()); // verify studio was saved, and capture that studio
        StudioModel capturedStudio = studioArgumentCaptor.getValue();
        assertThat(capturedStudio).isEqualTo(studioModel);
    }

    @Test
        //StudioService.getStudioById()
    void itShouldGetAStudioById() {
        //given
        StudioModel studioModel = new StudioModel(
                "TestName"
        );

        //when
        when(studioRepository.findById(any())).thenReturn(Optional.of(studioModel));
        underTest.getStudioById(studioModel.getSid());

        //then
        ArgumentCaptor<Long> studioArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify findById was invoked w/ sid, and capture the sid
        verify(studioRepository).findById(studioArgumentCaptor.capture());
        Long capturedSid = studioArgumentCaptor.getValue();
        assertThat(studioModel.getSid()).isEqualTo(capturedSid);
    }

    @Test
        //StudioService.getStudioById()
    void itShouldNotGetAStudioById() {
        /** throws {@link ResourceNotFoundException}*/
        //given
        Long sid = 1L;

        //when

        // I get a studio that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.getStudioById(sid))
                .hasMessageContaining("Studio With the sid: " + sid + " does not exist!"
                );
    }

    @Test
        //StudioService.updateStudio()
    void itShouldUpdateAStudio() {
        //given
        StudioModel studioModel = new StudioModel(
                "TestName"
        );
        StudioModel newStudioModel = new StudioModel(
                "TestName"
        );

        //when
        when(studioRepository.findById(any())).thenReturn(Optional.of(studioModel));
        underTest.updateStudio(newStudioModel.getSid(), newStudioModel);

        //then
        assertThat(studioModel.getName()).isEqualTo(newStudioModel.getName());

        ArgumentCaptor<StudioModel> studioArgumentCaptor = ArgumentCaptor.forClass(StudioModel.class);
        // verify save was invoked w/ correct studio, and capture the NEW studio
        verify(studioRepository).save(studioArgumentCaptor.capture());
        StudioModel capturedStudio = studioArgumentCaptor.getValue();
        assertThat(newStudioModel.getSid()).isEqualTo(capturedStudio.getSid()); //assert new studio is saved
    }

    @Test
        //StudioService.updateStudio()
    void itShouldNotUpdateAStudio() {
        /** throws {@link com.Exception.ResourceNotFoundException}*/
        //exception thrown iff studio does not exist

        //given
        Long sid = 1L;

        //when

        // I get a studio that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.updateStudio(sid, new StudioModel()))
                .hasMessageContaining("Studio With the sid: " + sid + " does not exist!"
                );
    }

    @Test
        //StudioService.deleteStudio()
    void itShouldDeleteAStudio() {
        //given
        StudioModel studioModel = new StudioModel(
                "TestName"
        );

        //when
        when(studioRepository.findById(any())).thenReturn(Optional.of(studioModel));
        underTest.deleteStudio(studioModel.getSid());

        //then
        ArgumentCaptor<StudioModel> studioArgumentCaptor = ArgumentCaptor.forClass(StudioModel.class);
        // verify delete was invoked w/ studio
        verify(studioRepository).delete(studioArgumentCaptor.capture());
        StudioModel capturedStudio = studioArgumentCaptor.getValue();
        assertThat(studioModel).isEqualTo(capturedStudio);
    }

    @Test
        //StudioService.deleteStudio()
    void itShouldNotDeleteAStudio() {
        /** throws {@link com.Exception.ResourceNotFoundException}*/
        //exception thrown iff studio does not exist

        //given
        Long sid = 1L;

        //when

        // I get a studio that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.deleteStudio(sid))
                .hasMessageContaining("Studio With the sid: " + sid + " does not exist!"
                );
    }
}