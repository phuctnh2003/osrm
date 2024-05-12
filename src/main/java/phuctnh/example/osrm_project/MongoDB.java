package phuctnh.example.osrm_project;

import com.mongodb.client.*;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MongoDB {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private String username = "nosneosoft";
    private String password = "NeoNDB2009!";
    private String host = "database.neosoft.vn";
    private int port = 27017;
    private String databaseName = "neodb";
    private String collectionName = "placesearch_history";
    private String connectionString = "mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + databaseName;

    public void insertHistory(History history)
    {
        try
        {
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document>collection = database.getCollection(collectionName);
            Document document = new Document();
            document.append("id", history.getId());
            document.append("searchword", history.getSearchword());
            document.append("keyword", history.getKeyword());
            document.append("latitude", history.getLatitude());
            document.append("longitude", history.getLongitude());
            document.append("time", history.getTime());
            collection.insertOne(document);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public ArrayList<History> showHistory() {
        ArrayList<History> historyList = new ArrayList<>();
        try {
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document filter = new Document();
            MongoCursor<Document> cursor = collection.find(filter).iterator();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                History history = new History();
                history.setId(document.getString("id"));
                history.setSearchword(document.getString("searchword"));
                history.setKeyword(document.getString("keyword"));
                history.setLatitude(document.getString("latitude"));
                history.setLongitude(document.getString("longitude"));
                history.setTime((Timestamp) document.get("time"));
                historyList.add(history);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historyList;
    }
    public void insert(Document data)
    {
        try {
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document>collection = database.getCollection(collectionName);
            collection.insertOne(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void find(Document data)
    {
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        MongoCursor<Document> cursor = collection.find(data).iterator();
        while(cursor.hasNext())
        {
            System.out.println(cursor.next());
        }
    }
    public void update(Document data, Document dataUpdate) {
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.updateOne(data, new Document("$set", dataUpdate));
    }
    public void delete(Document data) {
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.deleteOne(data);
    }
    public void closeConnection() {
        mongoClient.close();
    }

}
