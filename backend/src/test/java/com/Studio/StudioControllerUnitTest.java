package com.Studio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

/**
 * @author Connor Hunter        connh321@gmail.com
 * <p>
 * Unit Tests for {@link StudioController} to test what information is being passed to and being recieved from the
 * {@link StudioService}.
 */
@ExtendWith(MockitoExtension.class)
public class StudioControllerUnitTest {
    @Mock
    private StudioService studioService;
    private StudioController underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new StudioController(studioService);
    }

    @Test
        //StudioController.getAllStudios()
    void itShouldGetAllStudios() {
        //given

        //when
        underTest.getAllStudios();

        //then
        verify(studioService).getAllStudios();
    }

    @Test
        //StudioController.createStudio()
    void itShouldCreateAStudio() {
        //given
        StudioModel studioModel = new StudioModel(
                "Test"
        );

        //when
        underTest.createStudio(studioModel);

        //then
        ArgumentCaptor<StudioModel> studioArgumentCaptor = ArgumentCaptor.forClass(StudioModel.class);
        verify(studioService).createStudio(studioArgumentCaptor.capture()); // verify studio was passed to create studio, and capture that studio
        StudioModel capturedStudio = studioArgumentCaptor.getValue();
        assertThat(capturedStudio).isEqualTo(studioModel);
    }

    @Test
        //StudioController.getStudioById()
    void itShouldGetAStudioById() {
        //given
        StudioModel studioModel = new StudioModel(
                "Test"
        );

        //when
        underTest.getStudioById(studioModel.getSid());

        //then
        ArgumentCaptor<Long> sidArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify findById was invoked w/ sid, and capture the sid
        verify(studioService).getStudioById(sidArgumentCaptor.capture());
        Long capturedSid = sidArgumentCaptor.getValue();
        assertThat(studioModel.getSid()).isEqualTo(capturedSid);
    }


    @Test
        //StudioController.updateStudio()
    void itShouldUpdateAStudio() {
        //given
        StudioModel studioModel = new StudioModel(
                "Test"
        );

        StudioModel newStudioModel = new StudioModel(
                "newName"
        );

        //when
        underTest.updateStudio(studioModel.getSid(), newStudioModel);

        //then

        ArgumentCaptor<Long> sidArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<StudioModel> newStudioArgumentCaptor = ArgumentCaptor.forClass(StudioModel.class);
        // verify updateStudio was called with current Studio sid and new Studio details
        verify(studioService).updateStudio(sidArgumentCaptor.capture(), newStudioArgumentCaptor.capture());
        StudioModel capturedNewStudio = newStudioArgumentCaptor.getValue();
        Long capturedCurrentSid = sidArgumentCaptor.getValue();
        assertThat(newStudioModel).isEqualTo(capturedNewStudio); //assert new studio was passed in
        assertThat(studioModel.getSid()).isEqualTo(capturedCurrentSid);
    }


    @Test
        //StudioController.deleteStudio()
    void itShouldDeleteAStudio() {
        //given
        StudioModel studioModel = new StudioModel(
                "Test"
        );

        //when

        underTest.deleteStudio(studioModel.getSid());

        //then
        ArgumentCaptor<Long> sidArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify delete was invoked w/ studio
        verify(studioService).deleteStudio(sidArgumentCaptor.capture());
        Long capturedSid = sidArgumentCaptor.getValue();
        assertThat(studioModel.getSid()).isEqualTo(capturedSid);
    }
}
