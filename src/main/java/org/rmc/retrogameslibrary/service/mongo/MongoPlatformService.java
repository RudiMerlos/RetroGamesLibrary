package org.rmc.retrogameslibrary.service.mongo;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.PlatformService;

public class MongoPlatformService extends MongoService implements PlatformService {

    MongoCollection<Document> collection = null;

    public MongoPlatformService() {
        super();
        collection = db.getCollection("platforms");
    }

    @Override
    public void insert(Platform platform) throws CrudException {
        try {
            Document document = new Document();
            document.put("_id", platform.getId());
            document.put("name", platform.getName());
            document.put("model", platform.getModel());
            document.put("company", platform.getCompany());
            document.put("country", platform.getCountry());
            document.put("year", platform.getYear());
            collection.insertOne(document);
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
    }

    @Override
    public void modify(Platform platform) throws CrudException {
        try {
            // @formatter:off
            List<Bson> update = List.of(
                Updates.set("name", platform.getName()),
                Updates.set("model", platform.getModel()),
                Updates.set("company", platform.getCompany()),
                Updates.set("country", platform.getCountry()),
                Updates.set("year", platform.getYear())
            );
            // @formatter:on
            collection.updateOne(Filters.eq("_id", platform.getId()), update);
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
    }

    @Override
    public void remove(Platform platform) throws CrudException {
        try {
            DeleteResult result = collection.deleteOne(Filters.eq("_id", platform.getId()));
            if (result.getDeletedCount() != 1)
                throw new CrudException(new Throwable("No se ha eliminado ningún documento"));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
    }

    private Platform documentToPlatform(Document document) {
        return new Platform(document.getLong("_id"), document.getString("name"),
                document.getString("model"), document.getString("company"),
                document.getString("country"), document.getInteger("year"));
    }

    @Override
    public Platform getById(Long id) throws CrudException {
        Platform platform = null;
        try {
            Document document = collection.find(Filters.eq("_id", id)).first();
            if (document != null) {
                platform = documentToPlatform(document);
            }
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return platform;
    }

    @Override
    public List<Platform> getAll() throws CrudException {
        List<Platform> platforms = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext())
                platforms.add(documentToPlatform(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return platforms;
    }

    @Override
    public List<Platform> getByName(String name) throws CrudException {
        List<Platform> platforms = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = collection.find(Filters.eq("name", name)).iterator();
            while (cursor.hasNext())
                platforms.add(documentToPlatform(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return platforms;
    }

    @Override
    public List<Platform> getByModel(String model) throws CrudException {
        List<Platform> platforms = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = collection.find(Filters.eq("model", model)).iterator();
            while (cursor.hasNext())
                platforms.add(documentToPlatform(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return platforms;
    }

    @Override
    public List<Platform> getByCompany(String company) throws CrudException {
        List<Platform> platforms = new ArrayList<>();
        try {
            MongoCursor<Document> cursor =
                    collection.find(Filters.eq("company", company)).iterator();
            while (cursor.hasNext())
                platforms.add(documentToPlatform(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return platforms;
    }

    @Override
    public List<Platform> getByYear(int year) throws CrudException {
        List<Platform> platforms = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = collection.find(Filters.eq("year", year)).iterator();
            while (cursor.hasNext())
                platforms.add(documentToPlatform(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return platforms;
    }

    @Override
    public Platform getByNameAndModel(String name, String model) throws CrudException {
        Platform platform = null;
        try {
            Document document = collection
                    .find(Filters.and(Filters.eq("name", name), Filters.eq("model", model)))
                    .first();
            if (document != null) {
                platform = documentToPlatform(document);
            }
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return platform;
    }
}
