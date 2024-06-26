package phuctnh.example.osrm_project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class Analyist {
    private final double HCM_LATITUDE = 10.776390;
    private final double HCM_LONGITUDE = 106.701139;


    public ArrayList<Location> getPlace(String locationName) {
        ArrayList<Location> list = new ArrayList<>();
        try {
            String encodedLocationName = URLEncoder.encode(tokenLocation(locationName.toLowerCase()), "UTF-8");
            String urlString = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedLocationName + "&bounded=1";
            URL url = new URL(urlString);
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
            Type type = new TypeToken<ArrayList<Location>>() {
            }.getType();
            ArrayList<Location> res = gson.fromJson(response.toString(), type);
            if (res != null && !res.isEmpty()) {
                boolean flag = false; // kiem tra dia diem gan HCM neu khong ton tai thi` chuyen qua
                for (Location loc : res) {
                    if (checkHCM(loc.getLat(), loc.getLon())) {
                        list.add(loc);
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    for (Location loc : res) {
                        list.add(loc);
                    }
                }
            } else {
                System.out.println("Địa điểm không tồn tại");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public String getRoute(String startLatitude, String startLongitude, String endLatitude, String endLongitude, String profile) {
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

    public String tokenLocation(String location) {
        if (location.contains("/")) {
            String locationName = "Hẻm " + location;
            try {
                String encodedLocationName = URLEncoder.encode(locationName, "UTF-8");
                String urlString = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedLocationName + "&bounded=1";
                System.out.println(urlString);
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();
                if (response.toString().contains(locationName)) {
                    return locationName;
                } else// Neu cu phap hem dau` sai thi chuyen qua dang moi
                {
                    String analyzestr = locationName.replace("Hẻm", "").trim();

                    int vt = analyzestr.indexOf("/");
                    if (vt != -1) {
                        String str1 = analyzestr.substring(0, vt);
                        String str2 = analyzestr.substring(analyzestr.indexOf(" "));
                        return "Hẻm " + str1 + str2;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return location;
    }

    public History getHistory(String searchword, String keyword, String latitude, String longitude) {
        History history = new History();
        history.setId(UUID.randomUUID().toString());
        history.setSearchword(searchword);
        history.setKeyword(keyword);
        history.setLatitude(latitude);
        history.setLongitude(longitude);
        history.setTime(new Timestamp(System.currentTimeMillis()));
        return history;
    }


    // Giả su GPS
    private boolean checkHCM(String latitude, String longitude) {
        double epsilon = 0.1;
        return Math.abs(Double.parseDouble(latitude) - HCM_LATITUDE) < epsilon && Math.abs((Double.parseDouble(longitude)) - HCM_LONGITUDE) < epsilon;
    }
}
