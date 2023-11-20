package com.omtbp.theatreservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "theatres")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Theatre {

    @Id
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Screen> screens; //fetch = FetchType.LAZY,

}
