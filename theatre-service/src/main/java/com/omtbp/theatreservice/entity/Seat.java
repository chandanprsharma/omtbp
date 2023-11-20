package com.omtbp.theatreservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seats")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    private String seatLabel;
    private SeatType seatType;
    private Integer price;
    @ManyToOne
    @JoinColumn(name = "screen_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Screen screen;
}
