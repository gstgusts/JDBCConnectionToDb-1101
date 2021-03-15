package com.company.data;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Population {

    private Integer year;
    private Integer population;
    private City city;

}