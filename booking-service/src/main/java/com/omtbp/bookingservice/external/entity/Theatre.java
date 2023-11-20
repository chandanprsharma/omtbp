package com.omtbp.bookingservice.external.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Theatre {

    private String theatreId;
    private String theatreName;
    private String city;
    private String state;
    private String theatreAddress;
    private Integer noOfShow;
    private Boolean midDay;
    private Boolean eve;
    private Boolean night;
    private Boolean isActive;
    private Set<Screen> screens;

}
