import com.company.data.DataLoader;
import com.company.data.DatabaseManager;

public class Main {
    public static void main(String[] args) {
      var dm = new DatabaseManager();
//
//        for (var region :
//                dm.getRegions()) {
//            System.out.println(region.getName());
//        }

        var dl = new DataLoader();
        var counties = dl.load();

        dm.addCounties(counties);

    }
}
