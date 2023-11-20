package com.omtbp.movieservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.omtbp.movieservice.entity.Industry;
import com.omtbp.movieservice.entity.Movie;
import com.omtbp.movieservice.exception.InvalidParameterException;
import com.omtbp.movieservice.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.omtbp.movieservice.repository.MovieRepository;
import com.omtbp.movieservice.service.MovieService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    @Override
    public Movie saveMovie(Movie movie) {
        try {
            //Generate unique movieId
            String uniqueMovieId = UUID.randomUUID().toString();
            movie.setMovieId(uniqueMovieId);
            return movieRepository.save(movie);
        } catch (Exception e) {
            throw new InvalidParameterException("Failed to add movie. " + e.getMessage());
        }
    }


    @Override
    public Movie getMovieById(String movieId) {
        try {
            Optional<Movie> optionalMovie = movieRepository.findById(movieId);
            return optionalMovie.get();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Unable to find movie with given ID. " + e.getMessage());
        }
    }

    @Override
    public List<Movie> getMovieByName(String movieName) {
        try {
            return movieRepository.findByMovieName(movieName);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Unable to find movie with given name. " + e.getMessage());
        }
    }

    @Override
    public List<Movie> getByGenre(String genre) {
        try {
            return movieRepository.findByGenre(genre);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Unable to find movie with given genre. " + e.getMessage());
        }
    }

    @Override
    public List<Movie> getByIndustry(Industry industry) {
        try {
            return movieRepository.findByIndustry(industry);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Unable to find movie with given industry. " + e.getMessage());
        }
    }
}
