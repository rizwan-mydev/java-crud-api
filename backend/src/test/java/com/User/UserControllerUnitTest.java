package com.User;

import com.User.UserController;
import com.User.UserModel;
import com.User.UserService;
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
 * Unit Tests for {@link UserController} to test what information is being passed to and being recieved from the
 * {@link UserService}.
 */
@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {

    @Mock
    private UserService userService;
    private UserController underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new UserController(userService);
    }

    @Test
        //UserController.getAllUsers()
    void itShouldGetAllUsers() {
        //given

        //when
        underTest.getAllUsers();

        //then
        verify(userService).getAllUsers();
    }

    @Test
        //UserController.createUser()
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
        verify(userService).createUser(userArgumentCaptor.capture()); // verify user was passed to create user, and capture that student
        UserModel capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(userModel);
    }

    @Test
        //UserController.getUserById()
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
        underTest.getUserById(userModel.getUsername());

        //then
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        // verify findById was invoked w/ username, and capture the username
        verify(userService).getUserById(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertThat(userModel.getUsername()).isEqualTo(capturedUsername);
    }


    @Test
        //UserController.updateUser()
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
                "newUsername",
                "newFirstName",
                "newLastName",
                "newPassword",
                "newEmail@gmail.com",
                new Date(),
                new Date()
        );

        //when
        underTest.updateUser(userModel.getUsername(), newUserModel);

        //then

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<UserModel> newUserArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);
        // verify updateUser was called with current user username and new user details
        verify(userService).updateUser(usernameArgumentCaptor.capture(), newUserArgumentCaptor.capture());
        UserModel capturedNewUser = newUserArgumentCaptor.getValue();
        String capturedCurrentUsername = usernameArgumentCaptor.getValue();
        assertThat(newUserModel).isEqualTo(capturedNewUser); //assert new user was passed in
        assertThat(userModel.getUsername()).isEqualTo(capturedCurrentUsername);
    }


    @Test
        //UserController.deleteUser()
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

        underTest.deleteUser(userModel.getUsername());

        //then
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        // verify delete was invoked w/ user
        verify(userService).deleteUser(usernameArgumentCaptor.capture());
        String capturedUsername = usernameArgumentCaptor.getValue();
        assertThat(userModel.getUsername()).isEqualTo(capturedUsername);
    }

}