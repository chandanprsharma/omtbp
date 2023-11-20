package com.omtbp.theatreservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.omtbp.theatreservice.entity.Theatre;
import com.omtbp.theatreservice.repository.TheatreRepository;
import com.omtbp.theatreservice.service.TheatreService;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TheatreServiceImpl implements TheatreService{
	 @Autowired
	 private TheatreRepository theatreRepository;
	@Override
	public Theatre saveTheatre(Theatre theatre) {

		//Generate unique theatreId
		String uniqueTheatreId = UUID.randomUUID().toString();
		theatre.setTheatreId(uniqueTheatreId);
		return theatreRepository.save(theatre);
	}

	@Override
	public Theatre getTheatreById(String theatreId) {
		 return theatreRepository.findById(theatreId).get();
	}

	@Override
	public List<Theatre> getTheatresByCityAndMidDayShow(String city) {
		return theatreRepository.findByCityAndMidDay(city, Boolean.TRUE);
	}

	@Override
	public List<Theatre> getTheatresByCityAndEveShow(String city) {
		return theatreRepository.findByCityAndEve(city, Boolean.TRUE);
	}

	@Override
	public List<Theatre> getTheatresByCityAndNightShow(String city) {
		return theatreRepository.findByCityAndNight(city, Boolean.TRUE);
	}

	@Override
	public List<Theatre> getTheatreByTheatreName(String theatreName) {
		 return theatreRepository.findByTheatreName(theatreName);
	}

	@Override
	public List<Theatre> getTheatresByCity(String city) {
		return theatreRepository.findByCity(city);
	}

	@Override
	public List<Theatre> getAllById(List<String> theatreIds) {
		return theatreRepository.findAllById(theatreIds);
	}
}
