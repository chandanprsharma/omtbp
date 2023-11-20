package com.omtbp.movieservice.service;

import com.omtbp.movieservice.entity.Industry;
import com.omtbp.movieservice.entity.Movie;

import java.util.List;

public interface MovieService {
    Movie saveMovie(Movie movie);
    Movie getMovieById(String movieId);
    List<Movie> getMovieByName(String movieName);
    List<Movie> getByGenre(String genre);
    List<Movie> getByIndustry(Industry industry);
}