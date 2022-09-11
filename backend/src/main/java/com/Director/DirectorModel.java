package com.Director;

import com.Movie.MovieModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Hunter        connh321@gmail.com
 * <p>
 * A model for a director in the director table
 */
@Entity
@Table(name = "director")
public class DirectorModel {

    //non-parameterized constructor
    public DirectorModel() {
    }

    //parameterized constructor
    public DirectorModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //N:M with Movie
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "directorModels")
    private final List<MovieModel> movieModels = new ArrayList<>();

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "did")
    private Long did;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    ///
    ///GETTERS AND SETTERS
    ///

    public Long getDid() {
        return did;
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

    public List<MovieModel> getMovieModels() {
        return movieModels;
    }

}
