package phuctnh.example.osrm_project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class Controller {
    private final Analyist analyist = new Analyist();
    @GetMapping("/search/route")
    public String getRouteInfo(@RequestParam String startingpoint, @RequestParam String destination, @RequestParam String transportation)
    {
        ArrayList<Double> start = analyist.getPlace(startingpoint);
        ArrayList<Double> end = analyist.getPlace(destination);
        String routeInfo = analyist.getRoute(start.get(0), start.get(1), end.get(0), end.get(1), transportation);
        return routeInfo;
    }
    @GetMapping("/search")
    public String getLocation(@RequestParam String startingpoint, @RequestParam String destination, @RequestParam String transportation)
    {
        ArrayList<Double> startLocation = analyist.getPlace(startingpoint);
        ArrayList<Double> endLocation = analyist.getPlace(destination);
        String res = "Starting point: ["+ startLocation.get(0) +", " + startLocation.get(1) + "]; Destination: [" + endLocation.get(0) +", " + endLocation.get(1)+"]";
        return res;
    }
}
