package com.omtbp.movieservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Movie {

    @Id
    private String movieId;
    private String movieName;
    private String genre;
    private String cbfcRating;
	private String language;
	private String director;
    @Enumerated(EnumType.STRING)
    private Industry industry;
    private Integer yearOfRelease;

}
