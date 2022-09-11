package com.Studio;

import com.Movie.MovieModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Hunter        connh321@gmail.com
 * <p>
 * A model for a studio in the studio table
 */
@Entity
@Table(name = "studio")
public class StudioModel {

    //non-parameterized constructor
    public StudioModel() {
    }

    //parameterized constructor
    public StudioModel(String name) {
        this.name = name;
    }

    //N:M with Studio
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "studioModels")
    private final List<MovieModel> movieModels = new ArrayList<>();

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private Long sid;

    @Column(name = "name", nullable = false)
    private String name;

    ///
    ///GETTERS AND SETTERS
    ///

    public Long getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieModel> getMovieModels() {
        return movieModels;
    }

}
