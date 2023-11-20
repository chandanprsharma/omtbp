package com.omtbp.theatreservice.repository;

import com.omtbp.theatreservice.entity.Screen;
import com.omtbp.theatreservice.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
    List<Screen> findByScreenName(String screenName);
    List<Screen> findAllByTheatre(String theatreId);
}
