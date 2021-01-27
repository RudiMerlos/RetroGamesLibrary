package org.rmc.retrogameslibrary.service.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.rmc.retrogameslibrary.repository.CrudException;

// Singleton class
public class MongoConnection {

    private static MongoConnection instance = null;

    private MongoClient client;
    private MongoDatabase db;

    private MongoConnection() {
        client = null;
        db = null;
    }

    // Return a MongoConnection instance
    public static MongoConnection getInstance() {
        if (instance == null)
            instance = new MongoConnection();
        return instance;
    }

    // Set MongoDB connection
    public void connect(String host, int port, String database) throws CrudException {
        try {
            client = new MongoClient(host, port);
            db = client.getDatabase(database);
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
    }

    // Return the MongoDatabase
    public MongoDatabase getConnection() {
        return db;
    }

    // Close resources
    public void close() {
        db = null;
        client.close();
        client = null;
    }
}
