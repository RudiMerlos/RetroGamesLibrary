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
import org.rmc.retrogameslibrary.model.Emulator;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.EmulatorService;

public class MongoEmulatorService extends MongoService implements EmulatorService {

    MongoCollection<Document> collection = null;

    public MongoEmulatorService() {
        super();
        collection = db.getCollection("emulators");
    }

    @Override
    public void insert(Emulator emulator) throws CrudException {
        try {
            Document document = new Document();
            document.put("_id", emulator.getId());
            document.put("name", emulator.getName());
            document.put("path", emulator.getPath());
            collection.insertOne(document);
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
    }

    @Override
    public void modify(Emulator emulator) throws CrudException {
        try {
            // @formatter:off
            List<Bson> update = List.of(
                Updates.set("name", emulator.getName()),
                Updates.set("path", emulator.getPath())
            );
            // @formatter:on
            collection.updateOne(Filters.eq("_id", emulator.getId()), update);
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
    }

    @Override
    public void remove(Emulator emulator) throws CrudException {
        try {
            DeleteResult result = collection.deleteOne(Filters.eq("_id", emulator.getId()));
            if (result.getDeletedCount() != 1)
                throw new CrudException(new Throwable("No se ha eliminado ningún documento"));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
    }

    private Emulator documentToEmulator(Document document) {
        return new Emulator(document.getLong("_id"), document.getString("name"),
                document.getString("path"));
    }

    @Override
    public Emulator getById(Long id) throws CrudException {
        Emulator emulator = null;
        try {
            Document document = collection.find(Filters.eq("_id", id)).first();
            if (document != null) {
                emulator = documentToEmulator(document);
            }
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return emulator;
    }

    @Override
    public List<Emulator> getAll() throws CrudException {
        List<Emulator> emulators = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext())
                emulators.add(documentToEmulator(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return emulators;
    }

    @Override
    public List<Emulator> getByName(String name) throws CrudException {
        List<Emulator> emulators = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = collection.find(Filters.eq("name", name)).iterator();
            while (cursor.hasNext())
                emulators.add(documentToEmulator(cursor.next()));
        } catch (Exception e) {
            throw new CrudException("Error de MongoDB", e);
        }
        return emulators;
    }
}
