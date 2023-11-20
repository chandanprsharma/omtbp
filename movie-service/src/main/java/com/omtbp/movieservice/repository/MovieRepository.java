package com.omtbp.movieservice.repository;

import com.omtbp.movieservice.entity.Industry;
import com.omtbp.movieservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
	List<Movie> findByMovieName(String movieName);
	List<Movie> findByGenre(String genre);
	List<Movie> findByIndustry(Industry industry);
}
