package com.company.data;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    private static final String countiesUrl = "src/main/java/com/company/data/county.csv";
    private static final String citiesUrl = "src/main/java/com/company/data/cities.csv";

    private DatabaseManager databaseManager;

    public DataLoader(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public List<County> load() {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.typedSchemaFor(County.class)
                .withoutHeader()
                .withColumnSeparator(';');
        try {
            MappingIterator<County> it = csvMapper
                    .readerWithTypedSchemaFor(County.class)
                    .with(schema)
                    .readValues(new File(countiesUrl));

            return it.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<City> loadCities() {
        Path path = Paths.get(citiesUrl);
        List<City> cities = new ArrayList<>();
        try {
            var lines = Files.readAllLines(path);

            List<Integer> years = new ArrayList<>();

            var regions = databaseManager.getRegions();
            var counties = databaseManager.getCounties();

            for (int i = 0; i < lines.size(); i++) {
                String[] columns = lines.get(i).split(",");

                if(i == 0) {
                     years = getYears(columns);
                     continue;
                }

                String cityName = columns[0];
                String regionName = columns[1];
                String countyName = columns[2];

                Region region = null;
                County county = null;

                if(!regionName.isBlank()) {
                    var regionResult = regions.stream().filter(r -> r.getName().equalsIgnoreCase(regionName)).findFirst();
                    if(regionResult.isPresent()) {
                        region = regionResult.get();
                    }
                }

                if(!countyName.isBlank()) {
                    var countyResult = counties.stream().filter(c -> c.getName().equalsIgnoreCase(countyName)).findFirst();
                    if(countyResult.isPresent()) {
                        county = countyResult.get();
                    }
                }

                List<Population> populations = new ArrayList<>();

                fillPopulation(populations, null, columns);

                City city = new City(0, cityName, region, county, null, null);



                cities.add(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    private void fillPopulation(List<Population> populations, City city, String[] columns) {
        Integer year = 1967;
        for (int i = 3; i < columns.length; i++) {
            if(!columns[i].equals("...")) {
                var population = Integer.getInteger(columns[i]);
                var popu = new Population(year, population, city);
                populations.add(popu);
            }
            ++year;
        }
    }

    private List<Integer> getYears(String[] headers) {
        List<Integer> years = new ArrayList<>();

        for (var header :
                headers) {
            if(Character.isDigit(header.charAt(0))) {
                var yearString = header.substring(0, 4);
                years.add(Integer.getInteger(yearString));
            }
        }

        return years;
    }
}
