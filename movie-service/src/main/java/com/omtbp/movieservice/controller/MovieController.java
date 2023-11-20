package com.omtbp.movieservice.controller;

import com.omtbp.movieservice.entity.Industry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.omtbp.movieservice.entity.Movie;
import com.omtbp.movieservice.service.MovieService;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("api/movies")
@AllArgsConstructor
@CrossOrigin(origins="*")
public class MovieController {

	private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie){
        Movie savedMovie = movieService.saveMovie(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") String movieId){
        Movie movie = movieService.getMovieById(movieId);
        return ResponseEntity.ok(movie);
    }
    @GetMapping("/m/{movieName}")
    public ResponseEntity<List<Movie>> getMovieByName(@PathVariable("movieName") String movieName){
        List<Movie> movieList = movieService.getMovieByName(movieName);
        return ResponseEntity.ok(movieList);
    }
    @GetMapping("/g")
    public ResponseEntity<List<Movie>> getByMovieGenre(@RequestParam("genre") String genre){
        List<Movie> movieList = movieService.getByGenre(genre);
        return ResponseEntity.ok(movieList);
    }
    @GetMapping("/i")
    public ResponseEntity<List<Movie>> getByMovieIndustry(@RequestParam("industry") Industry industry){
        List<Movie> movieList = movieService.getByIndustry(industry);
        return ResponseEntity.ok(movieList);
    }
}

