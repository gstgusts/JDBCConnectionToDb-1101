import com.company.data.DataLoader;
import com.company.data.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        var dm = new DatabaseManager();

        var cities = dm.getCities();

        for (var city :
                cities) {
            System.out.println(city.getName() + " " +city.getRegion().getName());
        }

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
