package com.omtbp.theatreservice.service.impl;

import com.omtbp.theatreservice.dto.SeatAllocationDto;
import com.omtbp.theatreservice.dto.TheatreMovieDto;
import com.omtbp.theatreservice.entity.ShowTime;
import com.omtbp.theatreservice.entity.Theatre;
import com.omtbp.theatreservice.entity.TheatreMovie;
import com.omtbp.theatreservice.repository.*;
import com.omtbp.theatreservice.service.ScreenService;
import com.omtbp.theatreservice.service.SeatAllocationService;
import com.omtbp.theatreservice.service.TheatreMovieService;
import com.omtbp.theatreservice.service.TheatreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TheatreMovieServiceImpl implements TheatreMovieService {
    @Autowired
    private TheatreMovieRepository theatreMovieRepository;
    @Autowired
    @Lazy
    private SeatAllocationService seatAllocationService;
    @Autowired
    private ScreenService screenService;
	@Autowired
    @Lazy
	private TheatreService theatreService;

    @Override
    @Transactional
    public Boolean saveTheatreMovie(TheatreMovieDto theatreMovieDto) {
        if (checkTheatreScreenAvailability(theatreMovieDto.getTheatreId(), theatreMovieDto.getScreenId(), theatreMovieDto.getFromDate(), theatreMovieDto.getToDate())) {
            SeatAllocationDto seatAllocationDto = new SeatAllocationDto();
            seatAllocationDto.setMovieId(theatreMovieDto.getMovieId());
            seatAllocationDto.setTheatreId(theatreMovieDto.getTheatreId());
            seatAllocationDto.setScreenId(theatreMovieDto.getScreenId());
            seatAllocationDto.setFromDate(theatreMovieDto.getFromDate());
            seatAllocationDto.setToDate(theatreMovieDto.getToDate());
            seatAllocationDto.setSeats(screenService.getScreenById(theatreMovieDto.getScreenId()).getSeats());

            if(seatAllocationService.allotMovieSeat(seatAllocationDto)) {
				TheatreMovie theatreMovie = new TheatreMovie();
				theatreMovie.setMovieId(theatreMovieDto.getMovieId());
				theatreMovie.setTheatre(theatreService.getTheatreById(theatreMovieDto.getTheatreId()));
				theatreMovie.setScreen(screenService.getScreenById(theatreMovieDto.getScreenId()));
				theatreMovie.setFromDate(theatreMovieDto.getFromDate());
				theatreMovie.setToDate(theatreMovieDto.getToDate());
				theatreMovie.setIsActive(Boolean.TRUE);
				theatreMovieRepository.save(theatreMovie);
				return Boolean.TRUE;
			}
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean checkTheatreScreenAvailability(String theatreId, Long screenId, Date fromDate, Date toDate) {
        Long count = theatreMovieRepository.findByTheatreIdAndScreenIdAndFromDateAndToDate(theatreId, screenId, fromDate, toDate);
        return count > 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public List<String> findTheatreRunningMovie(List<String> theatreIds, String movieId, Date date) {
        return theatreMovieRepository.findByTheatreIdAndMovieIdAndDate(theatreIds, movieId, date, date);
    }

    @Override
    public List<Theatre> getTheatreByMovieCityShowDateAndTime(String movieId, String city, Date date, ShowTime showTime) {
        List<Theatre> theatres;
        switch (showTime) {
            case MID_DAY:
                theatres = theatreService.getTheatresByCityAndMidDayShow(city);
                break;
            case EVENING:
                theatres = theatreService.getTheatresByCityAndEveShow(city);
                break;
            case NIGHT:
                theatres = theatreService.getTheatresByCityAndNightShow(city);
                break;
            default:
                theatres = theatreService.getTheatresByCity(city);
        }
        List<String> theatreIds = theatres.stream().map(t -> t.getTheatreId()).collect(Collectors.toList());
        theatreIds = findTheatreRunningMovie(theatreIds, movieId, date);
        return theatreService.getAllById(theatreIds);
    }
}
