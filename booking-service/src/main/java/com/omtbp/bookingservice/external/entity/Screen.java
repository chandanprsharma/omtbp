package com.omtbp.bookingservice.external.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Screen {

    private Long screenId;
    private String screenName;
    private Boolean isActive;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Theatre theatre;
    private Set<Seat> seats;
}
