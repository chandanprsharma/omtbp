package com.omtbp.theatreservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.omtbp.theatreservice.entity.ShowTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BrowseTheatreDto implements Serializable {

    private static final long serialVersionUID=1L;

    private String movieId;
    private String city;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date date;
    private ShowTime showTime;
}
