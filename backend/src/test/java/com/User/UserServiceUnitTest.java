package com.User;

import com.User.UserModel;
import com.User.UserRepository;
import com.User.UserService;
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
 * Unit Tests for {@link UserService} to test business logic.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new UserService(userRepository);
    }

    @Test
        //UserService.getAllUsers()
    void itShouldGetAllUsers() {
        //given
        underTest.getAllUsers();
        //when

        //then
        verify(userRepository).findAll();
    }

    @Test
        //UserService.createUser()
    void itShouldCreateAUser() {
        //given
        UserModel userModel = new UserModel(
                "TestUserName",
                "TestFirstName",
                "TestLastName",
                "TestPassword",
                "Test@gmail.com",
                new Date(),
                new Date()
        );

        //when
        underTest.createUser(userModel);

        //then
        ArgumentCaptor<UserModel> userArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userRepository).save(userArgumentCaptor.capture()); // verify user was saved, and capture that student
        UserModel capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(userModel);
    }

    @Test
        //UserService.getUserById()
    void itShouldGetAUserById() {
        //given
        UserModel userModel = new UserModel(
                "TestUserName",
                "TestFirstName",
                "TestLastName",
                "TestPassword",
                "Test@gmail.com",
                new Date(),
                new Date()
        );

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(userModel));
        underTest.getUserById(userModel.getUsername());

        //then
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        // verify findById was invoked w/ username, and capture the username
        verify(userRepository).findById(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertThat(userModel.getUsername()).isEqualTo(capturedUsername);
    }

    @Test
        //UserService.getUserById()
    void itShouldNotGetAUserById() {
        /** throws {@link com.Exception.ResourceNotFoundException}*/
        //given
        String username = "iDontExist";

        //when

        // I get a user that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.getUserById("iDontExist"))
                .hasMessageContaining("User With the username: " + username + " does not exist!"
                );
    }

    @Test
        //UserService.updateUser()
    void itShouldUpdateAUser() {
        //given
        UserModel userModel = new UserModel(
                "TestUserName",
                "TestFirstName",
                "TestLastName",
                "TestPassword",
                "Test@gmail.com",
                new Date(),
                new Date()
        );
        UserModel newUserModel = new UserModel(
                "TestUserName", //does not change
                "newFirstName",            //does not change
                "newLastName",              //does not change
                "newPassword",
                "newEmail@gmail.com",
                new Date(),                 //does not change
                new Date()                  //does not change
        );

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(userModel));
        underTest.updateUser(newUserModel.getUsername(), newUserModel);

        //then
        assertThat(userModel.getPassword()).isEqualTo(newUserModel.getPassword());
        assertThat(userModel.getEmail()).isEqualTo(newUserModel.getEmail());

        ArgumentCaptor<UserModel> userArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);
        // verify save was invoked w/ correct user, and capture the NEW user
        verify(userRepository).save(userArgumentCaptor.capture());
        UserModel capturedUser = userArgumentCaptor.getValue();
        assertThat(newUserModel.getUsername()).isEqualTo(capturedUser.getUsername()); //assert new user is saved
    }

    @Test
        //UserService.updateUser()
    void itShouldNotUpdateAUser() {
        /** throws {@link com.Exception.ResourceNotFoundException}*/
        //exception thrown iff user does not exist

        //given
        String username = "iDontExist";

        //when

        // I get a user that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.updateUser("iDontExist", new UserModel()))
                .hasMessageContaining("User With the username: " + username + " does not exist!"
                );
    }

    @Test
        //UserService.deleteUser()
    void itShouldDeleteAUser() {
        //given
        UserModel userModel = new UserModel(
                "TestUserName",
                "TestFirstName",
                "TestLastName",
                "TestPassword",
                "Test@gmail.com",
                new Date(),
                new Date()
        );

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(userModel));
        underTest.deleteUser(userModel.getUsername());

        //then
        ArgumentCaptor<UserModel> userArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);
        // verify delete was invoked w/ user
        verify(userRepository).delete(userArgumentCaptor.capture());
        UserModel capturedUser = userArgumentCaptor.getValue();
        assertThat(userModel).isEqualTo(capturedUser);
    }

    @Test
        //UserService.deleteUser()
    void itShouldNotDeleteAUser() {
        /** throws {@link com.Exception.ResourceNotFoundException}*/
        //exception thrown iff user does not exist

        //given
        String username = "iDontExist";

        //when

        // I get a user that doesn't exist

        //then
        assertThatThrownBy(
                () ->
                        underTest.deleteUser("iDontExist"))
                .hasMessageContaining("User With the username: " + username + " does not exist!"
                );
    }
}