package com.company.data;
import lombok.AllArgsConstructor;
import lombok.Data;
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
}