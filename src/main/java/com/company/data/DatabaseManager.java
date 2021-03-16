package com.company.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String connectionUrl = "jdbc:mysql://localhost:3306/population?serverTimezone=UTC";

    public List<City> getCities() {
        List<City> items = new ArrayList<>();

        try {
            var con = getConnection();
            var stmt = con.createStatement();
            var rs = stmt.executeQuery("select * from v_city_full_data");

            while (rs.next()) {
                items.add(City.create(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return items;
    }

    public List<Region> getRegions() {
        List<Region> items = new ArrayList<>();

        try {
            var con = getConnection();
            var stmt = con.createStatement();
            var rs = stmt.executeQuery("select * from region");

            while (rs.next()) {

                var region = new Region(rs.getInt("region_id"),
                        rs.getString("region_name"));

                items.add(region);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return items;
    }

    public List<County> getCounties() {
        List<County> items = new ArrayList<>();

        try {
            var con = getConnection();
            var stmt = con.createStatement();
            var rs = stmt.executeQuery("select * from county");

            while (rs.next()) {

                var county = new County(rs.getInt("county_id"),
                        rs.getString("county_name"),
                        new Region(rs.getInt("county_region_id"),""));

                items.add(county);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return items;
    }

    public void addCounties(List<County> counties) {
        Connection con = null;
        try {
            con = getConnection();
            for (County county : counties) {
                var insertCounties = con.prepareStatement(
                        "insert into county (county_name, county_region_id) values (?, ?)");

                insertCounties.setString(1, county.getName());
                insertCounties.setInt(2, county.getRegion().getId());

                insertCounties.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Integer addCity(City city) {
        Connection con = null;
        try {
            con = getConnection();

            var insertCity = con.prepareStatement(
                    "insert into city (city_name, city_founded, city_region_id, city_county_id) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            insertCity.setString(1, city.getName());

            if(city.getFounded() != null) {
                insertCity.setInt(2, city.getFounded());
            } else {
                insertCity.setNull(2, java.sql.Types.INTEGER);
            }

            if(city.getRegion() != null) {
                insertCity.setInt(3, city.getRegion().getId());
            } else {
                insertCity.setNull(3, java.sql.Types.INTEGER);
            }

            if(city.getCounty() != null) {
                insertCity.setInt(4, city.getCounty().getId());
            } else {
                insertCity.setNull(4, java.sql.Types.INTEGER);
            }

            insertCity.executeUpdate();

            Integer id = 0;

            try(ResultSet keys = insertCity.getGeneratedKeys()) {
                keys.next();
                id = keys.getInt(1);
                city.setId(id);
            }

            con.close();

            for (var population :
                    city.getPopulation()) {
                population.setCity(city);
                addPopulation(population);
            }

            return id;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public Integer addPopulation(Population population) {
        Connection con = null;
        try {
            con = getConnection();

            var insertStmt = con.prepareStatement(
                    "insert into popul (pop_year, pop_number, pop_city_id) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            insertStmt.setInt(1, population.getYear());
            insertStmt.setInt(2, population.getPopulation());
            insertStmt.setInt(3, population.getCity().getId());

            insertStmt.executeUpdate();

            Integer id = 0;

            try(ResultSet keys = insertStmt.getGeneratedKeys()) {
                keys.next();
                id = keys.getInt(1);
                population.setId(id);
            }

            con.close();

            return id;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, "test", "test123");
    }
}
