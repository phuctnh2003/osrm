package phuctnh.example.osrm_project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Analyist {
    public ArrayList<Double> getPlace(String locationName) {
        ArrayList<Double> list = new ArrayList<>();

        try {
            String urlString = "https://nominatim.openstreetmap.org/search?format=json&q=" + URLEncoder.encode(tokenLocation(locationName), "UTF-8");
            URL url = new URL(urlString);
            System.out.println(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Location>>(){}.getType();
            ArrayList<Location> res = gson.fromJson(response.toString(), type);

            if (res != null) {
                list.add(res.get(0).lat);//vi do
                list.add(res.get(0).lon);// kinh do
            }
            else
            {
                System.out.println("Không tìm thấy dia diem");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getRoute(double startLatitude , double startLongitude, double endLatitude, double endLongitude, String profile) {
        StringBuilder response = new StringBuilder();
        try {
            String urlString = "https://router.project-osrm.org/route/v1/" + profile + "/" + startLongitude + "," + startLatitude + ";" + endLongitude + "," + endLatitude + "?overview=false";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
    public String tokenLocation(String locationName)
    {
        // 20/40/8 Đ. Cô Bắc, Phường 1, Phú Nhuận, Thành phố Hồ Chí Minh, Việt Nam
        StringBuilder respone = new StringBuilder();
        String res, res1, res2, res3;

        if(locationName.contains("/") && !locationName.contains("Hẻm") && !locationName.contains("hẻm") )
        {
            res1 = "Hẻm " + locationName;
            respone = new StringBuilder(res1);
            res2 = res1.substring(0, res1.indexOf('/', 0));// cat Hem .... trc dau /
            res3 = String.valueOf(respone.delete(0 , res1.indexOf(" ", res1.indexOf('/',0))));// xoa trc dau/
            res = res2 + res3;
            return res;
        }
        else
            return locationName;

    }
}
