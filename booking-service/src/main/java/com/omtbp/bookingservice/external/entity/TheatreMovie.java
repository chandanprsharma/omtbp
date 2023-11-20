package com.omtbp.bookingservice.external.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TheatreMovie {

    private Long tmId;
    private Theatre theatre;
    private String movieId;
    private Screen screen;
    private Date fromDate;
    private Date toDate;
    private Boolean isActive;

}
