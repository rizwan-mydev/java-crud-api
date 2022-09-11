package com.CastMember;

import com.Movie.MovieModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Hunter        connh321@gmail.com
 * <p>
 * A model for a castMember in the castMember table
 */
@Entity
@Table(name = "cast_member")
public class CastMemberModel {

    //non-parameterized constructor
    public CastMemberModel() {
    }

    //parameterized constructor
    public CastMemberModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //N:M with Movie
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "castMemberModels")
    private List<MovieModel> movieModels = new ArrayList<>();

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmid")
    private Long cmid;


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    ///
    ///GETTERS AND SETTERS
    ///

    public Long getCmid() {
        return cmid;
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
