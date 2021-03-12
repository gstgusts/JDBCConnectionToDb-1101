package com.company.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String connectionUrl = "jdbc:mysql://localhost:3306/population?serverTimezone=UTC";

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

    public void addCounties(List<County> counties) {
        Connection con = null;
        try {
            con = getConnection();
            for (County county : counties) {
                var insertCounties = con.prepareStatement(
                        "insert into county (county_name, county_region_id) values (?, ?)");

                insertCounties.setString(1, county.getName());
                insertCounties.setInt(2, county.getRegionId());

                insertCounties.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, "test", "test123");
    }
}
