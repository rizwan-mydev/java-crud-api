package com.CastMember;

import com.CastMember.CastMemberController;
import com.CastMember.CastMemberModel;
import com.CastMember.CastMemberService;
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
 * Unit Tests for {@link CastMemberController} to test what information is being passed to and being recieved from the
 * {@link CastMemberService}.
 */
@ExtendWith(MockitoExtension.class)
public class CastMemberControllerUnitTest {
    @Mock
    private CastMemberService castMemberService;
    private CastMemberController underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new CastMemberController(castMemberService);
    }

    @Test
        //CastMemberController.getAllCastMembers()
    void itShouldGetAllCastMembers() {
        //given

        //when
        underTest.getAllCastMembers();

        //then
        verify(castMemberService).getAllCastMembers();
    }

    @Test
        //CastMemberController.createCastMember()
    void itShouldCreateACastMember() {
        //given
        CastMemberModel castMemberModel = new CastMemberModel(
                "TestFirstName",
                "TestLastName"
        );

        //when
        underTest.createCastMember(castMemberModel);

        //then
        ArgumentCaptor<CastMemberModel> castMemberArgumentCaptor = ArgumentCaptor.forClass(CastMemberModel.class);
        verify(castMemberService).createCastMember(castMemberArgumentCaptor.capture()); // verify castMember was passed to create castMember, and capture that castMember
        CastMemberModel capturedCastMember = castMemberArgumentCaptor.getValue();
        assertThat(capturedCastMember).isEqualTo(castMemberModel);
    }

    @Test
        //CastMemberController.getCastMemberById()
    void itShouldGetACastMemberById() {
        //given
        CastMemberModel castMemberModel = new CastMemberModel(
                "TestFirstName",
                "TestLastName"
        );

        //when
        underTest.getCastMemberById(castMemberModel.getCmid());

        //then
        ArgumentCaptor<Long> cmidArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify findById was invoked w/ cmid, and capture the cmid
        verify(castMemberService).getCastMemberById(cmidArgumentCaptor.capture());
        Long capturedCmid = cmidArgumentCaptor.getValue();
        assertThat(castMemberModel.getCmid()).isEqualTo(capturedCmid);
    }


    @Test
        //CastMemberController.updateCastMember()
    void itShouldUpdateACastMember() {
        //given
        CastMemberModel castMemberModel = new CastMemberModel(
                "TestFirstName",
                "TestLastName"
        );

        CastMemberModel newCastMemberModel = new CastMemberModel(
                "newFirstName",
                "newLastName"
        );

        //when
        underTest.updateCastMember(castMemberModel.getCmid(), newCastMemberModel);

        //then

        ArgumentCaptor<Long> cmidArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<CastMemberModel> newCastMemberArgumentCaptor = ArgumentCaptor.forClass(CastMemberModel.class);
        // verify updateCastMember was called with current CastMember cmid and new CastMember details
        verify(castMemberService).updateCastMember(cmidArgumentCaptor.capture(), newCastMemberArgumentCaptor.capture());
        CastMemberModel capturedNewCastMember = newCastMemberArgumentCaptor.getValue();
        Long capturedCurrentCmid = cmidArgumentCaptor.getValue();
        assertThat(newCastMemberModel).isEqualTo(capturedNewCastMember); //assert new castMember was passed in
        assertThat(castMemberModel.getCmid()).isEqualTo(capturedCurrentCmid);
    }


    @Test
        //CastMemberController.deleteCastMember()
    void itShouldDeleteACastMember() {
        //given
        CastMemberModel castMemberModel = new CastMemberModel(
                "TestFirstName",
                "TestLastName"
        );

        //when

        underTest.deleteCastMember(castMemberModel.getCmid());

        //then
        ArgumentCaptor<Long> cmidArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify delete was invoked w/ castMember
        verify(castMemberService).deleteCastMember(cmidArgumentCaptor.capture());
        Long capturedCmid = cmidArgumentCaptor.getValue();
        assertThat(castMemberModel.getCmid()).isEqualTo(capturedCmid);
    }
}
