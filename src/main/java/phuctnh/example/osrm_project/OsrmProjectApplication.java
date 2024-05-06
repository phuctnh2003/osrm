package phuctnh.example.osrm_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.ArrayList;

@SpringBootApplication
public class OsrmProjectApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OsrmProjectApplication.class, args);
		Analyist analyist = new Analyist();


		String place1 = "Ben Xe Mien Tay";
		String place2 = "ÆON MALL Bình Tân";
		String transportation = "walking";

		ArrayList<Location> start = analyist.getPlace(place1);
		ArrayList<Location> end = analyist.getPlace(place2);
		System.out.println(start.get(0).getDisplay_name());
		System.out.println(start.get(0).getLat());
		System.out.println(start.get(0).getLon());

		System.out.println(end.get(0).getDisplay_name());
		System.out.println( end.get(0).getLat());
		System.out.println(end.get(0).getLon());
		String routeInfo = analyist.getRoute(start.get(0).getLat(), start.get(0).getLon(), end.get(0).getLat(), end.get(0).getLon(), transportation);

		System.out.println(routeInfo);
	}
}




