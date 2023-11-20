package com.omtbp.theatreservice.repository;

import com.omtbp.theatreservice.entity.TheatreMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TheatreMovieRepository extends JpaRepository<TheatreMovie, Long> {
    @Query(value = "SELECT count(tm.tm_id) FROM theatres_movies tm " +
            "WHERE tm.theatre_id = ?1 AND tm.screen_id = ?2 " +
            "AND tm.from_date <= ?3 AND tm.to_date >= ?4", nativeQuery = true)
    Long findByTheatreIdAndScreenIdAndFromDateAndToDate(
            String theatreId,
            Long screenId,
            Date fromDate,
            Date toDate);
    @Query(value = "SELECT tm.theatre_id FROM theatres_movies tm " +
            "WHERE tm.theatre_id IN (:theatre_ids) AND tm.movie_id = :movie_id " +
            "AND tm.from_date <= :from_date AND tm.to_date >= :to_date", nativeQuery = true)
    List<String> findByTheatreIdAndMovieIdAndDate(
            @Param("theatre_ids") List<String> theatreIds,
            @Param("movie_id") String movieId,
            @Param("from_date") Date fromDate,
            @Param("to_date") Date toDate);

}
