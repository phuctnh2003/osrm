package phuctnh.example.osrm_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class OsrmProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsrmProjectApplication.class, args);
		Analyist analyist = new Analyist();

		int n = 1;
		String place1 = "55/15 Trần Hưng Đạo, Thành phố Hồ Chí Minh";
		String place2 = "ÆON MALL Bình Tân, 1, Đường số 17A, Phường Bình Trị Đông B, Quận Bình Tân, Thành phố Hồ Chí Minh, 73118, Việt Nam";
		String transportation = "walking";

		ArrayList<Double> start = analyist.getPlace(place1);
		ArrayList<Double> end = analyist.getPlace(place2);

		String routeInfo = analyist.getRoute(start.get(0), start.get(1), end.get(0), end.get(1), transportation);

		System.out.println(routeInfo);
	}
}




