package com.omtbp.theatreservice.service;

import com.omtbp.theatreservice.entity.ShowTime;
import com.omtbp.theatreservice.entity.Theatre;

import java.util.Date;
import java.util.List;

public interface TheatreService {
    Theatre saveTheatre(Theatre theatre);
    List<Theatre> getTheatreByTheatreName(String theatreName);
    Theatre getTheatreById(String theatreId);
    List<Theatre> getTheatresByCityAndMidDayShow(String city);
    List<Theatre> getTheatresByCityAndEveShow(String city);
    List<Theatre> getTheatresByCityAndNightShow(String city);
    List<Theatre> getTheatresByCity(String city);
    List<Theatre> getAllById(List<String> theatreIds);
}