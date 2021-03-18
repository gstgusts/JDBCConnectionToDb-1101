import com.company.data.DataLoader;
import com.company.data.DatabaseManager;
import com.company.data.Population;

public class Main {
    public static void main(String[] args) {
        var dm = new DatabaseManager();

        //        var cities = dm.getCitiesWithPopulationData();
//
//        for (var city :
//                cities) {
//            System.out.println("--------------------");
//            System.out.println(city.getName());
//            System.out.println("--------------------");
//
//            for (var pop :
//                    city.getPopulation()) {
//                System.out.println(pop.getYear() + " " +pop.getPopulation());
//            }
//        }

        var city = dm.getCityById(6);

        dm.getPopulationDataForCity(city);

        System.out.println(city.getName());

        for (var pop :
                city.getPopulation()) {
            System.out.println(pop.getYear() + " "+ pop.getPopulation());
        }

//        Population pop = new Population(0, 1600, 23, city);
//
//        dm.addPopulation(pop);
//
//        dm.getPopulationDataForCity(city);
//
//        for (var pop2 :
//                city.getPopulation()) {
//            System.out.println(pop2.getYear() + " "+ pop2.getPopulation());
//        }


//        var cities = dm.getCities();

//        for (var city :
//                cities) {
//            System.out.println(city.getName() + " " +city.getRegion().getName() + " " + city.getFounded());
//
//            if(city.getCounty() != null) {
//                System.out.println(city.getCounty().getId());
//            }
//        }

        // var dl = new DataLoader(dm);

       // var allCities = dl.loadCities();
//
//        for (var region :
//                dm.getRegions()) {
//            System.out.println(region.getName());
//        }

//        var dl = new DataLoader();
//        var counties = dl.load();
//
//        dm.addCounties(counties);

    }
}
