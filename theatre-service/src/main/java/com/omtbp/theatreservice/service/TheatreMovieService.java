package com.omtbp.theatreservice.service;

import com.omtbp.theatreservice.dto.TheatreMovieDto;
import com.omtbp.theatreservice.entity.ShowTime;
import com.omtbp.theatreservice.entity.Theatre;
import com.omtbp.theatreservice.entity.TheatreMovie;

import java.util.Date;
import java.util.List;

public interface TheatreMovieService {
    Boolean saveTheatreMovie(TheatreMovieDto theatreMovieDto);
    Boolean checkTheatreScreenAvailability(String theatreId, Long screenId, Date fromDate, Date toDate);
    List<String> findTheatreRunningMovie(List<String> theatreIds, String movieId, Date date);
    List<Theatre> getTheatreByMovieCityShowDateAndTime(String movieId, String city, Date date, ShowTime showTime);
}