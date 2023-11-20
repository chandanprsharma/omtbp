package com.omtbp.theatreservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "theatres_movies")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TheatreMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tmId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;
    private String movieId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "screen_id")
    private Screen screen;
    private Date fromDate;
    private Date toDate;
    private Boolean isActive;

}
