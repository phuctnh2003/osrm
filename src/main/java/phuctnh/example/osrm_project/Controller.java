package phuctnh.example.osrm_project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
public class Controller {
    private final Analyist analyist = new Analyist();
    private final MongoDB mongoDB = new MongoDB();
    private final SQL sql = new SQL();

    @GetMapping("/search")
    public ArrayList<Location> getRouteInfo(@RequestParam String startingpoint, @RequestParam String destination, @RequestParam String transportation)
    {
        ArrayList<Location> list = new ArrayList<>();

            ArrayList<Location> start = analyist.getPlace(startingpoint);
            ArrayList<Location> end = analyist.getPlace(destination);
            list.addAll(start);
            list.addAll(end);
            //String routeInfo = analyist.getRoute(start.get(0).getLat(), start.get(0).getLon(), end.get(0).getLat(), end.get(0).getLon(), transportation);
            // String res = "Starting point: ["+start.get(0).getDisplay_name()+", "+ start.get(0).getLat() +", " + start.get(0).getLon() + "]; Destination: [" +end.get(0).getDisplay_name()+", "+ end.get(0).getLat() +", " + end.get(0).getLon()+"]";
            History history_start = analyist.getHistory(startingpoint, start.get(0).getDisplay_name(), start.get(0).getLat(), start.get(0).getLon());
            History history_end = analyist.getHistory(destination, end.get(0).getDisplay_name(), end.get(0).getLat(), end.get(0).getLon());
            sql.Insert_History(history_start);
            sql.Insert_History(history_end);

        return list;
    }
    @GetMapping("/search/coordinates")
    public String getLocation(@RequestParam String startingpoint, @RequestParam String destination, @RequestParam String transportation)
    {
        ArrayList<Location> start = analyist.getPlace(startingpoint);
        ArrayList<Location> end = analyist.getPlace(destination);
        String res = "Starting point: ["+ start.get(0).getLat() +", " + start.get(0).getLon() + "]; Destination: [" + end.get(0).getLat() +", " + end.get(0).getLon()+"]";
        History history_start = analyist.getHistory(startingpoint, start.get(0).getDisplay_name(),start.get(0).getLat(), start.get(0).getLon());
        History history_end = analyist.getHistory(destination, end.get(0).getDisplay_name(),end.get(0).getLat(), end.get(0).getLon());
        sql.Insert_History(history_start);
        sql.Insert_History(history_end);
        return res;
    }

    @GetMapping("/history")
    public ArrayList<History> getHistory()
    {
        return sql.Show_History();
    }
}
