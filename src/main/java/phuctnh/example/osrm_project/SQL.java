package phuctnh.example.osrm_project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class SQL {
    private static final String jdbcURL = "jdbc:mysql://admin.neosoft.vn:3306/kawhfhmf_NeoSQL?useUnicode=true&characterEncoding=UTF-8";
    private static final String username = "kawhfhmf_neo_user";
    private static final String password = "NeoSQL2009!";

    public void Create_History()  {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String table = "CREATE TABLE place_search_history ( id VARCHAR(255) PRIMARY KEY ,searchword text, keyword text, latitude VARCHAR(255), longitude VARCHAR(255), time TIMESTAMP)";
            statement.execute(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Insert_History(History history) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            // Sử dụng prepared statement để tránh vấn đề với các ký tự đặc biệt
            String insert = "INSERT INTO place_search_history (id, searchword, keyword, latitude, longitude, time) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, history.getId());
            preparedStatement.setString(2, history.getSearchword());
            preparedStatement.setString(3, history.getKeyword());
            preparedStatement.setString(4, history.getLatitude());
            preparedStatement.setString(5, history.getLongitude());
            preparedStatement.setTimestamp(6, history.getTime());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<History> Show_History() {
        ArrayList<History>list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM place_search_history";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                History history = new History();
                history.setId(resultSet.getString(1));
                history.setSearchword(resultSet.getString(2));
                history.setKeyword(resultSet.getString(3));
                history.setLatitude(resultSet.getString(4));
                history.setLongitude(resultSet.getString(5));
                history.setTime(resultSet.getTimestamp(6));
                list.add(history);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void Drop_History() {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();
            String delete_table = "DROP TABLE place_search_history";
            statement.execute(delete_table);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public ArrayList<History> Check_Searchword(String searchword) {
        ArrayList<History> historyList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String query = "SELECT * FROM place_search_history WHERE searchword = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, searchword);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                History history = new History();
                history.setId(resultSet.getString("id"));
                history.setSearchword(resultSet.getString("searchword"));
                history.setKeyword(resultSet.getString("keyword"));
                history.setLatitude(resultSet.getString("latitude"));
                history.setLongitude(resultSet.getString("longitude"));
                history.setTime(resultSet.getTimestamp("time"));
                historyList.add(history);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }



}
