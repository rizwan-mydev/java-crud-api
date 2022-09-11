package com.User;

import com.Movie.MovieModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.*;

/**
 * @author Connor Hunter        connh321@gmail.com
 * <p>
 * A model for a user in the users table
 */
@Entity
@Table(name = "users")
public class UserModel {

    //non-parameterized constructor
    public UserModel() {
    }

    //parameterized constructor
    public UserModel(String username, String firstName, String lastName, String password, String email, Date creationDate, Date lastAccessDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.creationDate = creationDate;
        this.lastAccessDate = lastAccessDate;
    }

    //1:M with Movie
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<MovieModel> movieModels = new ArrayList<>();

    @Id //pk
    @Column(name = "username")
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Temporal(TemporalType.DATE) //json format date
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Temporal(TemporalType.DATE) //json format date
    @Column(name = "last_access_date", nullable = false)
    private Date lastAccessDate;

    ///
    ///GETTERS AND SETTERS
    ///

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public List<MovieModel> getMovieModels() {
        return movieModels;
    }

    public void setMovieModels(List<MovieModel> movieModels) {
        this.movieModels = movieModels;
    }
}
