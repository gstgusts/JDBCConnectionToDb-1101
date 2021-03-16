package com.company.data;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class City {
    private Integer id;
    private String name;
    private Region region;
    private County county;
    private Integer founded;
    private List<Population> population;

    @SneakyThrows
    public static City create(ResultSet rs) {
        var city = new City(rs.getInt("city_id"),
                rs.getString("city_name"),
                Region.create(rs),
                null,
                null,
                new ArrayList<>());

        return city;
    }
}