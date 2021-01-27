package org.rmc.retrogameslibrary.service.mongo;

import com.mongodb.client.MongoDatabase;

public class MongoService {

    protected MongoDatabase db = null;

    protected MongoService() {
        db = MongoConnection.getInstance().getConnection();
    }
}
