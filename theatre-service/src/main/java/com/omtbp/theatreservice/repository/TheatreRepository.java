package com.omtbp.theatreservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.omtbp.theatreservice.entity.Theatre;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre, String> {
    List<Theatre> findByTheatreName(String theatreName);

    List<Theatre> findByCity(String city);
    @Query(value = "SELECT * FROM theatres t " +
            "WHERE t.city LIKE ?1 AND t.mid_day = ?2", nativeQuery = true)
    List<Theatre> findByCityAndMidDay(String city, Boolean midDay);
    @Query(value = "SELECT * FROM theatres t " +
            "WHERE t.city LIKE ?1 AND t.eve = ?2", nativeQuery = true)
    List<Theatre> findByCityAndEve(String city, Boolean eve);
    @Query(value = "SELECT * FROM theatres t " +
            "WHERE t.city LIKE ?1 AND t.night = ?2", nativeQuery = true)
    List<Theatre> findByCityAndNight(String city, Boolean night);
}
