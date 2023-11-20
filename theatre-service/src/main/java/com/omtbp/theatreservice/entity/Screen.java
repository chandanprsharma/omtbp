package com.omtbp.theatreservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "screens")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screenId;
    private String screenName;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "theatre_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Theatre theatre;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seat> seats;
}
