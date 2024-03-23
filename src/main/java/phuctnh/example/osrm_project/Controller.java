package phuctnh.example.osrm_project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class Controller {
    private final Analyist analyist = new Analyist();
    @GetMapping("/search")
    public String getRouteInfo(@RequestParam String place1, @RequestParam String place2, @RequestParam String transportation)
    {
        ArrayList<Double> start = analyist.getPlace(place1);
        ArrayList<Double> end = analyist.getPlace(place2);
        String routeInfo = analyist.getRoute(start.get(0), start.get(1), end.get(0), end.get(1), transportation);
        return routeInfo;
    }
}
