package com.company.data;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public List<County> load() {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.typedSchemaFor(County.class).withoutHeader().withColumnSeparator(';');
        try {
            MappingIterator<County> it = csvMapper
                    .readerWithTypedSchemaFor(County.class)
                    .with(schema)
                    .readValues(new File("src/main/java/com/company/data/county.csv"));
            return it.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
