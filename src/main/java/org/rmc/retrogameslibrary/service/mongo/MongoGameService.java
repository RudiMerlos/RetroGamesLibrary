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
import org.rmc.retrogameslibrary.model.Game;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.GameService;
import org.rmc.retrogameslibrary.service.PlatformService;

public class MongoGameService extends MongoService implements GameService {

    MongoCollection<Document> collection = null;

    public MongoGameService() {
        super();
        collection = db.getCollection("games");
    }

    @Override
    public void insert(Game game) throws CrudException {
        try {
            Document document = new Document();
            document.put("_id", game.getId());
            document.put("title", game.getTitle());
            document.put("description", game.getDescription());
            document.put("year", game.getYear());
            document.put("gender", game.getGender());
            document.put("screenshot", game.getScreenshot());
            document.put("path", game.getPath());
            document.put("command", game.getCommand());
            document.put("platform_id", game.getPlatform().getId());
            collection.insertOne(document);
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
    }

    @Override
    public void modify(Game game) throws CrudException {
        try {
            // @formatter:off
            List<Bson> update = List.of(
                Updates.set("title", game.getTitle()),
                Updates.set("description", game.getDescription()),
                Updates.set("year", game.getYear()),
                Updates.set("gender", game.getGender()),
                Updates.set("screenshot", game.getScreenshot()),
                Updates.set("path", game.getPath()),
                Updates.set("command", game.getCommand()),
                Updates.set("platform_id", game.getPlatform().getId())
            );
            // @formatter:on
            collection.updateOne(Filters.eq("_id", game.getId()), update);
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
    }

    @Override
    public void remove(Game game) throws CrudException {
        try {
            DeleteResult result = collection.deleteOne(Filters.eq("_id", game.getId()));
            if (result.getDeletedCount() != 1)
                throw new CrudException(new Throwable("No se ha eliminado ningún documento"));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
    }

    private Game documentToEmulator(Document document) throws CrudException {
        PlatformService platformService = new MongoPlatformService();
        Platform platform = platformService.getById(document.getLong("platform_id"));
        return new Game(document.getLong("_id"), document.getString("title"),
                document.getString("description"), document.getInteger("year"),
                document.getString("gender"), document.getString("screenshot"),
                document.getString("path"), document.getString("command"), platform);
    }

    @Override
    public Game getById(Long id) throws CrudException {
        Game game = null;
        try {
            Document document = collection.find(Filters.eq("_id", id)).first();
            if (document != null) {
                game = documentToEmulator(document);
            }
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return game;
    }

    @Override
    public List<Game> getAll() throws CrudException {
        List<Game> games = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext())
                games.add(documentToEmulator(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return games;
    }

    @Override
    public List<Game> getByTitle(String title) throws CrudException {
        List<Game> games = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = collection.find(Filters.eq("title", title)).iterator();
            while (cursor.hasNext())
                games.add(documentToEmulator(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return games;
    }

    @Override
    public List<Game> getByYear(int year) throws CrudException {
        List<Game> games = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = collection.find(Filters.eq("year", year)).iterator();
            while (cursor.hasNext())
                games.add(documentToEmulator(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return games;
    }

    @Override
    public List<Game> getByGender(String gender) throws CrudException {
        List<Game> games = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = collection.find(Filters.eq("gender", gender)).iterator();
            while (cursor.hasNext())
                games.add(documentToEmulator(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return games;
    }

    @Override
    public List<Game> getByPlatform(Platform platform) throws CrudException {
        List<Game> games = new ArrayList<>();
        try {
            MongoCursor<Document> cursor =
                    collection.find(Filters.eq("platform_id", platform.getId())).iterator();
            while (cursor.hasNext())
                games.add(documentToEmulator(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return games;
    }
}
