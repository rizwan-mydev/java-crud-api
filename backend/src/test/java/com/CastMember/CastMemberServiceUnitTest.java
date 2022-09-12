package com.CastMember;

import com.CastMember.CastMemberModel;
import com.CastMember.CastMemberRepository;
import com.CastMember.CastMemberService;
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
 * Unit Tests for {@link CastMemberService} to test business logic.
 */
@ExtendWith(MockitoExtension.class)
class CastMemberServiceUnitTest {

    @Mock
    private CastMemberRepository castMemberRepository;
    private CastMemberService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new CastMemberService(castMemberRepository);
    }

    @Test
        //CastMemberService.getAllCastMembers()
    void itShouldGetAllCastMembers() {
        //given
        underTest.getAllCastMembers();
        //when

        //then
        verify(castMemberRepository).findAll();
    }

    @Test
        //CastMemberService.createCastMember()
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
        verify(castMemberRepository).save(castMemberArgumentCaptor.capture()); // verify castMember was saved, and capture that castMember
        CastMemberModel capturedCastMember = castMemberArgumentCaptor.getValue();
        assertThat(capturedCastMember).isEqualTo(castMemberModel);
    }

    @Test
        //CastMemberService.getCastMemberById()
    void itShouldGetACastMemberById() {
        //given
        CastMemberModel castMemberModel = new CastMemberModel(
                "TestFirstName",
                "TestLastName"
        );

        //when
        when(castMemberRepository.findById(any())).thenReturn(Optional.of(castMemberModel));
        underTest.getCastMemberById(castMemberModel.getCmid());

        //then
        ArgumentCaptor<Long> castMemberArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        // verify findById was invoked w/ cmid, and capture the cmid
        verify(castMemberRepository).findById(castMemberArgumentCaptor.capture());
        Long capturedCmid = castMemberArgumentCaptor.getValue();
        assertThat(castMemberModel.getCmid()).isEqualTo(capturedCmid);
    }

    @Test
        //CastMemberService.getCastMemberById()
    void itShouldNotGetACastMemberById() {
        /** throws {@link ResourceNotFoundException}*/
        //given
        Long cmid = 1L;

        //when

        // I get a castMember that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.getCastMemberById(cmid))
                .hasMessageContaining("CastMember With the cmid: " + cmid + " does not exist!"
                );
    }

    @Test
        //CastMemberService.updateCastMember()
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
        when(castMemberRepository.findById(any())).thenReturn(Optional.of(castMemberModel));
        underTest.updateCastMember(newCastMemberModel.getCmid(), newCastMemberModel);

        //then
        assertThat(castMemberModel.getFirstName()).isEqualTo(newCastMemberModel.getFirstName());
        assertThat(castMemberModel.getLastName()).isEqualTo(newCastMemberModel.getLastName());


        ArgumentCaptor<CastMemberModel> castMemberArgumentCaptor = ArgumentCaptor.forClass(CastMemberModel.class);
        // verify save was invoked w/ correct castMember, and capture the NEW castMember
        verify(castMemberRepository).save(castMemberArgumentCaptor.capture());
        CastMemberModel capturedCastMember = castMemberArgumentCaptor.getValue();
        assertThat(newCastMemberModel.getCmid()).isEqualTo(capturedCastMember.getCmid()); //assert new castMember is saved
    }

    @Test
        //CastMemberService.updateCastMember()
    void itShouldNotUpdateACastMember() {
        /** throws {@link ResourceNotFoundException}*/
        //exception thrown iff castMember does not exist

        //given
        Long cmid = 1L;

        //when

        // I get a castMember that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.updateCastMember(cmid, new CastMemberModel()))
                .hasMessageContaining("CastMember With the cmid: " + cmid + " does not exist!"
                );
    }

    @Test
        //CastMemberService.deleteCastMember()
    void itShouldDeleteACastMember() {
        //given
        CastMemberModel castMemberModel = new CastMemberModel(
                "TestFirstName",
                "TestLastName"
        );

        //when
        when(castMemberRepository.findById(any())).thenReturn(Optional.of(castMemberModel));
        underTest.deleteCastMember(castMemberModel.getCmid());

        //then
        ArgumentCaptor<CastMemberModel> castMemberArgumentCaptor = ArgumentCaptor.forClass(CastMemberModel.class);
        // verify delete was invoked w/ castMember
        verify(castMemberRepository).delete(castMemberArgumentCaptor.capture());
        CastMemberModel capturedCastMember = castMemberArgumentCaptor.getValue();
        assertThat(castMemberModel).isEqualTo(capturedCastMember);
    }

    @Test
        //CastMemberService.deleteCastMember()
    void itShouldNotDeleteACastMember() {
        /** throws {@link ResourceNotFoundException}*/
        //exception thrown iff castMember does not exist

        //given
        Long cmid = 1L;

        //when

        // I get a castMember that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.deleteCastMember(cmid))
                .hasMessageContaining("CastMember With the cmid: " + cmid + " does not exist!"
                );
    }
}