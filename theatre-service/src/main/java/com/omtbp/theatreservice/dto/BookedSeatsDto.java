package com.omtbp.theatreservice.dto;

import com.omtbp.theatreservice.entity.ShowTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookedSeatsDto implements Serializable {

    private static final long serialVersionUID=1L;
    private String bookingId;
    private Long totalPrice;
    private Long discount;
    private Long netPrice;
    private BookRequestedSeatsDto bookingDetails;
}
