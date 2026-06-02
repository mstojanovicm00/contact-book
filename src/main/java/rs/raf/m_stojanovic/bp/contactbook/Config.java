package rs.raf.m_stojanovic.bp.contactbook;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class Config {

    private static final LocalDateTime NOW = LocalDateTime.now();

    private static Properties properties = null;
    private static Connection relationalDatabaseConnection;
    private static MongoClient mongoDatabaseClient;
    private static MongoDatabase mongoDatabaseConnection;

    public static void logToMongoDatabase(String message) {
        MongoCollection<Document> logs = mongoDatabaseConnection.getCollection("logs");
        logs.updateOne(
                new Document("datetime", NOW),
                new Document("$push", new Document("messages",
                        new Document("message", message).append("datetime", LocalDateTime.now())))
        );
    }

    public static void connectToMongoDatabase(String host, String port, String db) {
        String url = "mongodb://" + host + ":" + port;
        mongoDatabaseClient = MongoClients.create(url);
        mongoDatabaseConnection = mongoDatabaseClient.getDatabase(db);

        MongoCollection<Document> logs = mongoDatabaseConnection.getCollection("logs");
        Document document = new Document("datetime", NOW)
                .append("messages", List.of(
                        new Document("message", "connecting")));
        logs.insertOne(document);
    }

    public static void disconnectFromMongoDatabase() {
        logToMongoDatabase("disconnecting");

        mongoDatabaseClient.close();
    }

    public static void connectToRelationalDatabase(String host, String port, String db, String user, String password) {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
        try {
            relationalDatabaseConnection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void disconnectFromRelationalDatabase() {
        try {
            relationalDatabaseConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadProperties(String cfgFile) {
        if (properties == null)
            properties = new Properties();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(cfgFile);
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    System.out.println("Problem with closing the configuration file");
                }
        }
    }

    public static String getPropertyValue(String property, String defaultValue) {
        return properties.getProperty(property, defaultValue);
    }

    public static Connection getRelationalDatabaseConnection() {
        return relationalDatabaseConnection;
    }

    private Config() {

    }
}
