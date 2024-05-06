package phuctnh.example.osrm_project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class Controller {
    private final Analyist analyist = new Analyist();
    private final MongoDB mongoDB = new MongoDB();

    @GetMapping("/search/route")
    public String getRouteInfo(@RequestParam String startingpoint, @RequestParam String destination, @RequestParam String transportation)
    {
        ArrayList<Location> start = analyist.getPlace(startingpoint);
        ArrayList<Location> end = analyist.getPlace(destination);
        String routeInfo = analyist.getRoute(start.get(0).getLat(), start.get(0).getLon(), end.get(0).getLat(), end.get(0).getLon(), transportation);

        History history_start = analyist.getHistory(startingpoint, start.get(0).getDisplay_name(),start.get(0).getLat(), start.get(0).getLon());
        History history_end = analyist.getHistory(destination, end.get(0).getDisplay_name(),end.get(0).getLat(), end.get(0).getLon());
        ArrayList<History>list = new ArrayList<>();
        list.add(history_start);
        list.add(history_end);
        for(History a: list)
        {
            System.out.println(a.getSearchword() + "-" + a.getKeyword() + "-" + a.getTime());

        }
        return routeInfo;
    }
    @GetMapping("/search")
    public String getLocation(@RequestParam String startingpoint, @RequestParam String destination, @RequestParam String transportation)
    {
        ArrayList<Location> startLocation = analyist.getPlace(startingpoint);
        ArrayList<Location> endLocation = analyist.getPlace(destination);
        String res = "Starting point: ["+ startLocation.get(0).getLat() +", " + startLocation.get(0).getLon() + "]; Destination: [" + endLocation.get(0).getLat() +", " + endLocation.get(0).getLon()+"]";
        return res;
    }

    @GetMapping("/history")
    public ArrayList<History> getHistory()
    {
        return mongoDB.showHistory();
    }
}
